package com.faendir.om.sp.part

data class Conduit(
    override var position: Position,
    override var rotation: Int,
    var id: Int,
    internal var positions: List<Position>
) : Part() {
    override val name = "pipe"
}