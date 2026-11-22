package com.example.ui.launcher

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AuraColorSwatches
import com.example.ui.theme.AuraLauncherTheme
import com.example.ui.theme.AuraTheme
import com.example.ui.theme.CustomAuraTheme
import com.example.ui.theme.LowEndFlatCard
import com.example.ui.theme.MinecraftThemePreset
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.NeonRed
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianVoid
import com.example.ui.theme.SavedThemePreferences
import com.example.ui.theme.parseHexColorOrNull
import com.example.ui.theme.toHexString

@Composable
fun ThemesAndPerformanceScreen(
    themePrefs: SavedThemePreferences,
    onBack: () -> Unit,
    onToggleLowEndMode: (Boolean) -> Unit,
    onApplyLiteQuickPreset: () -> Unit,
    onSelectPreset: (MinecraftThemePreset) -> Unit,
    onSelectCustomTheme: (CustomAuraTheme) -> Unit,
    onSaveCustomTheme: (CustomAuraTheme) -> Unit,
    onDeleteCustomTheme: (String) -> Unit,
    onPickCustomBackground: (Uri) -> Unit,
    onResetCustomBackground: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = AuraTheme.accents
    val colorScheme = MaterialTheme.colorScheme

    var customName by remember { mutableStateOf("My Neon Theme") }
    var primaryHex by remember { mutableStateOf(NeonRed.toHexString()) }
    var secondaryHex by remember { mutableStateOf(NeonPurple.toHexString()) }
    var backgroundHex by remember { mutableStateOf(ObsidianVoid.toHexString()) }
    var cardHex by remember { mutableStateOf(ObsidianCard.toHexString()) }
    var activeColorSlot by remember { mutableIntStateOf(0) }

    val bgPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onPickCustomBackground(uri)
        }
    }

    BackHandler(onBack = onBack)

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedMinecraftBackdrop(
            themePreset = MinecraftThemePreset.fromId(themePrefs.selectedPresetId),
            customBackgroundPath = themePrefs.customBackgroundPath,
            lowEndMode = themePrefs.lowEndMode,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(accents.glassPanel)
                    .border(1.dp, accents.subtleBorder, RoundedCornerShape(16.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("themes_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to Home",
                            tint = colorScheme.onSurface
                        )
                    }
                    Column {
                        Text(
                            text = "THEMES & PERFORMANCE",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = colorScheme.onSurface
                        )
                        Text(
                            text = if (themePrefs.lowEndMode) {
                                "Low-End Performance Mode ON"
                            } else {
                                "Standard Visual Effects"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = if (themePrefs.lowEndMode) accents.onlineEmerald else accents.vibrantViolet
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(accents.glassPanelHeavy)
                        .border(1.dp, accents.subtleBorder, RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Speed,
                            contentDescription = null,
                            tint = accents.neonRed
                        )
                        Text(
                            text = "LOW-END DEVICE OPTIMIZATION",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = colorScheme.onSurface
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Low-End Performance Mode",
                                style = MaterialTheme.typography.titleSmall,
                                color = colorScheme.onSurface
                            )
                            Text(
                                text = "Disables animated glows/particles, uses flat surfaces, and downsamples images to prevent lag & OOM.",
                                style = MaterialTheme.typography.bodySmall,
                                color = colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = themePrefs.lowEndMode,
                            onCheckedChange = onToggleLowEndMode,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorScheme.onPrimary,
                                checkedTrackColor = accents.neonRed
                            ),
                            modifier = Modifier.testTag("low_end_mode_switch")
                        )
                    }

                    OutlinedButton(
                        onClick = onApplyLiteQuickPreset,
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp)
                            .testTag("apply_lite_preset_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Bolt,
                            contentDescription = null,
                            tint = accents.neonRed
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Apply Lite / Low-End Quick Preset",
                            color = colorScheme.onSurface
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(accents.glassPanelHeavy)
                        .border(1.dp, accents.subtleBorder, RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "BUILT-IN THEME PRESETS",
                        style = MaterialTheme.typography.labelMedium,
                        color = accents.vibrantViolet
                    )

                    MinecraftThemePreset.entries.forEach { preset ->
                        val isSelected = themePrefs.selectedCustomThemeId == null &&
                            themePrefs.selectedPresetId == preset.id

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 64.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) ObsidianElevated else accents.cardSurface)
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    brush = if (isSelected) accents.neonBorderGradient else SolidColor(accents.subtleBorder),
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable { onSelectPreset(preset) }
                                .padding(10.dp)
                                .testTag("preset_theme_${preset.id}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (preset.backgroundRes != null) {
                                Image(
                                    painter = painterResource(id = preset.backgroundRes),
                                    contentDescription = preset.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(LowEndFlatCard)
                                        .border(1.dp, accents.subtleBorder, RoundedCornerShape(10.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Bolt,
                                        contentDescription = null,
                                        tint = accents.neonRed
                                    )
                                }
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = preset.title,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = preset.badge,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = accents.neonRed
                                    )
                                }
                                Text(
                                    text = preset.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = accents.neonRed
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(accents.glassPanelHeavy)
                        .border(1.dp, accents.subtleBorder, RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "CUSTOM BACKGROUND IMAGE",
                        style = MaterialTheme.typography.labelMedium,
                        color = accents.vibrantViolet
                    )
                    Text(
                        text = if (themePrefs.customBackgroundPath != null) {
                            "Custom wallpaper active (auto-downsampled for safe memory use)"
                        } else {
                            "Using built-in scenic theme background"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { bgPickerLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = accents.neonPurple),
                            modifier = Modifier
                                .weight(1f)
                                .defaultMinSize(minHeight = 48.dp)
                                .testTag("pick_custom_bg_button")
                        ) {
                            Icon(Icons.Rounded.Image, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Pick Image")
                        }

                        if (themePrefs.customBackgroundPath != null) {
                            OutlinedButton(
                                onClick = onResetCustomBackground,
                                modifier = Modifier
                                    .defaultMinSize(minHeight = 48.dp)
                                    .testTag("reset_custom_bg_button")
                            ) {
                                Icon(Icons.Rounded.Restore, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Reset")
                            }
                        }
                        
                    }
                }

                val previewPrimary = parseHexColorOrNull(primaryHex) ?: NeonRed
                val previewSecondary = parseHexColorOrNull(secondaryHex) ?: NeonPurple
                val previewBg = parseHe
                HexColorOrNull(backgroundHex) ?: ObsidianVoid
                val previewCard = parseHexColorOrNull(cardHex) ?: ObsidianCard

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(accents.glassPanelHeavy)
                        .border(1.dp, accents.subtleBorder, RoundedCornerShape(18.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Rounded.Palette, contentDescription = null, tint = accents.neonRed)
                        Text(
                            text = "CREATE CUSTOM THEME",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = colorScheme.onSurface
                        )
                    }

                    OutlinedTextField(
                        value = customName,
                        onValueChange = { customName = it },
                        label = { Text("Theme Name") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accents.neonPurple,
                            unfocusedBorderColor = accents.subtleBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("custom_theme_name_input")
                    )

                    val slotLabels = listOf(
                        "Primary" to primaryHex,
                        "Secondary" to secondaryHex,
                        "Background" to backgroundHex,
                        "Card" to cardHex
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        slotLabels.forEachIndexed { index, (label, hex) ->
                            val selectedSlot = activeColorSlot == index
                            val slotColor = parseHexColorOrNull(hex) ?: ObsidianCard
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .defaultMinSize(minHeight = 48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (selectedSlot) ObsidianElevated else ObsidianSurface)
                                    .border(
                                        width = if (selectedSlot) 1.5.dp else 1.dp,
                                        color = if (selectedSlot) accents.neonRed else accents.subtleBorder,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable { activeColorSlot = index }
                                    .padding(vertical = 6.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(slotColor)
                                        .border(1.dp, colorScheme.onSurface.copy(alpha = 0.5f), CircleShape)
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colorScheme.onSurface,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                    Text(
                        text = "Tap a color swatch or enter hex:",
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AuraColorSwatches.forEach { swatch ->
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(swatch)
                                    .border(1.5.dp, colorScheme.onSurface.copy(alpha = 0.45f), CircleShape)
                                    .clickable {
                                        val hex = swatch.toHexString()
                                        when (activeColorSlot) {
                                            0 -> primaryHex = hex
                                            1 -> secondaryHex = hex
                                            2 -> backgroundHex = hex
                                            else -> cardHex = hex
                                        }
                                    }
                            )
                        }
                    }

                    val activeHexValue = when (activeColorSlot) {
                        0 -> primaryHex
                        1 -> secondaryHex
                        2 -> backgroundHex
                        else -> cardHex
                    }
                    OutlinedTextField(
                        value = activeHexValue,
                        onValueChange = { updated ->
                            when (activeColorSlot) {
                                0 -> primaryHex = updated
                                1 -> secondaryHex = updated
                                2 -> backgroundHex = updated
                                else -> cardHex = updated
                            }
                        },
                        label = { Text("Hex Color (#RRGGBB)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(previewBg)
                            .border(1.dp, previewSecondary, RoundedCornerShape(12.dp))
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(previewCard)
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = customName.ifBlank { "Preview" },
                                style = MaterialTheme.typography.labelMedium,
                                color = colorScheme.onSurface
                            )
                        }
                        
