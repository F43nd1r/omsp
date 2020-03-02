package com.faendir.om.sp.solution

import com.faendir.om.sp.part.Part

data class NonSolvedSolution(
    override var puzzle: String,
    override var name: String,
    override var parts: List<Part> = emptyList()
) : Solution