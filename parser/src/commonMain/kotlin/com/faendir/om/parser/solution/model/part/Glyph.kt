package com.faendir.om.parser.solution.model.part

import com.faendir.om.parser.solution.model.Position

data class Glyph(
    override var position: Position,
    override var rotation: Int,
    override var number: Int,
    val type: GlyphType
) : Part() {
    override val name = type.key
}