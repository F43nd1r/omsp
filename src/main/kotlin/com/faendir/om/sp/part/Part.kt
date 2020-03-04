package com.faendir.om.sp.part

import com.faendir.om.sp.base.Action

abstract class Part {
    abstract val name: String
    abstract var position: Position
    open var number: Int = 0
    open var rotation: Int = 0
    open var size: Int = 1
    open var steps: List<Action> = emptyList()
    open var index: Int = 0
}

data class Position(val x: Int, val y: Int)

infix fun Int.to(other: Int) : Position = Position(this, other)