package com.faendir.omsp.part

data class IO(
    override var index: Int,
    override var position: Position,
    override var rotation: Int,
    val type: IOType
) : Part() {
    override val name = type.key
}