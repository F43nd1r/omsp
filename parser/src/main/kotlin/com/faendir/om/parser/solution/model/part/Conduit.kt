package com.faendir.om.parser.solution.model.part

import com.faendir.om.parser.solution.model.Position

data class Conduit(
    override var position: Position,
    override var rotation: Int,
    var id: Int,
    var positions: List<Position>
) : Part() {
    override val name = "pipe"
}