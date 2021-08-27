package com.faendir.om.parser.solution.model.part

import com.faendir.om.parser.solution.model.Position

data class Track(
    override var position: Position,
    var positions: List<Position>
) : Part(){
    override val name = "track"
}