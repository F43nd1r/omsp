package com.faendir.om.parser.solution.model.part

import com.faendir.om.parser.solution.model.Position
import com.faendir.om.parser.solution.model.to

enum class GlyphType(internal val key: String, val shape : Set<Position>) {
    BONDER("bonder", setOf(0 to 0, 1 to 0)),
    MULTI_BONDER("bonder-speed", setOf(0 to 0, 1 to 0, -1 to 1, 0 to -1)),
    TRIPLEX_BONDER("bonder-prisma", setOf(0 to 0, 1 to 0, 0 to 1)),
    UNBONDER("unbonder", setOf(0 to 0, 1 to 0)),
    CALCIFICATION("glyph-calcification", setOf(0 to 0)),
    DUPLICATION("glyph-duplication", setOf(0 to 0, 1 to 0)),
    PROJECTION("glyph-projection", setOf(0 to 0, 1 to 0)),
    PURIFICATION("glyph-purification", setOf(0 to 0, 1 to 0, 0 to 1)),
    ANIMISMUS("glyph-life-and-death", setOf(0 to 0, 1 to 0, 0 to 1, 1 to -1)),
    DISPOSAL("glyph-disposal", setOf(0 to 0, 1 to 0, 0 to 1, -1 to 1, -1 to 0, 0 to -1, 1 to -1)),
    UNIFICATION("glyph-unification", setOf(0 to 0, 0 to 1, -1 to 1, 0 to -1, 1 to -1)),
    DISPERSION("glyph-dispersion", setOf(0 to 0, 1 to 0, -1 to 0, 0 to -1, 1 to -1)),
    EQUILIBRIUM("glyph-marker", setOf(0 to 0));

    companion object {
        fun fromString(key: String): GlyphType? {
            return GlyphType.values().find { it.key == key }
        }
    }
}