package com.faendir.omsp.part

enum class GlyphType(internal val key: String) {
    BONDER("bonder"),
    MULTI_BONDER("bonder-speed"),
    TRIPLEX_BONDER("bonder-prisma"),
    UNBONDER("unbonder"),
    CALCIFICATION("glyph-calcification"),
    DUPLICATION("glyph-duplication"),
    PROJECTION("glyph-projection"),
    PURIFICATION("glyph-purification"),
    ANIMISMUS("glyph-life-and-death"),
    DISPOSAL("glyph-disposal"),
    UNIFICATION("glyph-unification"),
    DISPERSION("glyph-dispersion"),
    EQUILIBRIUM("glyph-marker");

    companion object {
        fun fromString(key: String): GlyphType? {
            return GlyphType.values().find { it.key == key }
        }
    }
}