package com.faendir.om.sp.solution

import com.faendir.om.sp.part.Part

data class SolvedSolution(
    override var puzzle: String,
    override var name: String,
    var cycles: Int,
    var cost: Int,
    var area: Int,
    var instructions: Int,
    override var parts: List<Part> = emptyList()
) : Solution