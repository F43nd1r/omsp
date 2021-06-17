package com.faendir.om.parser.solution.model.part

import com.faendir.om.parser.solution.model.Position
import com.faendir.om.parser.solution.model.Action

abstract class Part {
    abstract val name: String
    abstract var position: Position
    open var number: Int = 1
    open var rotation: Int = 0
    open var size: Int = 1
    open var steps: List<Action> = emptyList()
    open var index: Int = 0
}

