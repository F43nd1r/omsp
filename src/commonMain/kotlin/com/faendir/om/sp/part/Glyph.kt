package com.faendir.om.sp.part

data class Glyph(
    override var position: Position,
    override var rotation: Int,
    val type: GlyphType
) : Part() {
    override val name = type.key
}