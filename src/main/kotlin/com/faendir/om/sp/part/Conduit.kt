package com.faendir.om.sp.part

data class Conduit(
    override var position: Position,
    var id: Int,
    var positions: List<Position>
) : Part() {
    override val name = "pipe"
}