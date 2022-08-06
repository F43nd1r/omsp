package com.faendir.om.parser.solution.model.part

import com.faendir.om.parser.solution.model.Position

data class IO(
    override var index: Int,
    override var position: Position,
    override var rotation: Int,
    override var number: Int,
    val type: IOType
) : Part() {
    override val name = type.key
}