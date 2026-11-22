package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Dark Obsidian & Slate Surfaces
val ObsidianVoid = Color(0xFF07060D)
val ObsidianSurface = Color(0xFF0E0B1A)
val ObsidianCard = Color(0xFF161228)
val ObsidianElevated = Color(0xFF211B3A)
val ObsidianGlass = Color(0xCC110E1F)
val ObsidianGlassHeavy = Color(0xEB0B0816)

// Low-End Flat Surfaces (zero alpha blending overhead)
val LowEndFlatBg = Color(0xFF0B0A12)
val LowEndFlatCard = Color(0xFF171524)
val LowEndFlatElevated = Color(0xFF221F33)

// Aura Red-Purple Neon (Default)
val NeonPurple = Color(0xFFA855F7)
val ElectricPurple = Color(0xFF8B5CF6)
val VibrantViolet = Color(0xFFC084FC)
val SoftLavender = Color(0xFFE9D5FF)
val DeepPurpleGlow = Color(0xFF4C1D95)
val NeonRed = Color(0xFFFF2A54)
val CrimsonGlow = Color(0xFFEF4444)
val RoseFlare = Color(0xFFFF5C7C)
val DeepCrimson = Color(0xFF7F1D1D)
val SakuraPetalPink = Color(0xFFFF8FAB)

// Crimson Shadow Preset
val CrimsonShadowPrimary = Color(0xFFE11D48)
val CrimsonShadowSecondary = Color(0xFF9F1239)
val CrimsonShadowBg = Color(0xFF0A060A)
val CrimsonShadowCard = Color(0xFF180D14)

// Midnight Violet Preset
val MidnightVioletPrimary = Color(0xFF9333EA)
val MidnightVioletSecondary = Color(0xFF6366F1)
val MidnightVioletBg = Color(0xFF060714)
val MidnightVioletCard = Color(0xFF111229)

// Nether Crimson Bastion Accents
val NetherMagmaRed = Color(0xFFFF3B30)
val NetherEmberAmber = Color(0xFFFF9F1C)
val NetherGlowGold = Color(0xFFFFD166)

// End Void Aurora Accents
val EndAuroraPurple = Color(0xFFB455FF)
val EndEnderCyan = Color(0xFF22D3EE)
val EndStarlight = Color(0xFFE0F2FE)

// Deep Dark Sculk Accents
val SculkBiolumCyan = Color(0xFF00E5FF)
val SculkDeepViolet = Color(0xFF8B5CF6)
val SculkEchoTeal = Color(0xFF14B8A6)

// Text & Status Tokens
val AuraTextPrimary = Color(0xFFF8F7FF)
val AuraTextSecondary = Color(0xFFB9B2D3)
val AuraTextMuted = Color(0xFF7D749E)
val AuraBorderSubtle = Color(0x47A855F7)
val AuraEmeraldOnline = Color(0xFF10B981)
val AuraCyanPing = Color(0xFF22D3EE)
val AuraAmberWarn = Color(0xFFF59E0B)

// Quick Color Swatches for Custom Theme Builder
val AuraColorSwatches: List<Color> = listOf(
    NeonRed,
    NeonPurple,
    ElectricPurple,
    CrimsonShadowPrimary,
    NetherEmberAmber,
    EndEnderCyan,
    SculkBiolumCyan,
    AuraEmeraldOnline,
    ObsidianVoid,
    ObsidianSurface,
    ObsidianCard,
    LowEndFlatCard,
    CrimsonShadowBg,
    MidnightVioletBg
)

fun Color.toHexString(): String {
    val r = ((red * 255f).toInt().coerceIn(0, 255))
    val g = ((green * 255f).toInt().coerceIn(0, 255))
    val b = ((blue * 255f).toInt().coerceIn(0, 255))
    return "#%02X%02X%02X".format(r, g, b)
}

fun parseHexColorOrNull(raw: String): Color? {
    val clean = raw.trim().removePrefix("#")
    if (clean.length != 6 && clean.length != 8) return null
    val parsed = clean.toLongOrNull(16) ?: return null
    val argb = if (clean.length == 6) (0xFF000000L or parsed) else parsed
    return Color(argb.toInt())
}
