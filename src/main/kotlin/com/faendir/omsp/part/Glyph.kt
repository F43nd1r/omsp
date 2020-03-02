package com.faendir.omsp.part

data class Glyph(
    override var position: Position,
    override var rotation: Int,
    val type: GlyphType
) : Part() {
    override val name = type.key
}