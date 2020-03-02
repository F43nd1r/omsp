package com.faendir.omsp.part

data class Track(
    override var position: Position,
    var positions: List<Position>
) : Part(){
    override val name = "track"
}