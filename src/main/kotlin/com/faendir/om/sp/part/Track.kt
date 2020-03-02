package com.faendir.om.sp.part

data class Track(
    override var position: Position,
    var positions: List<Position>
) : Part(){
    override val name = "track"
}