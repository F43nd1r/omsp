package com.faendir.om.parser.solution.model.part

import com.faendir.om.parser.solution.model.Position
import com.faendir.om.parser.solution.model.Action

data class Arm(
    override var number: Int,
    override var position: Position,
    override var rotation: Int,
    override var size: Int,
    override var steps: List<Action>,
    val type: ArmType
) : Part() {
    override val name = type.key
}