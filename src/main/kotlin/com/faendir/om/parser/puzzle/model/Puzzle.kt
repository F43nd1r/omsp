package com.faendir.om.parser.puzzle.model

data class Puzzle(
    var name: String,
    internal var creator: Long,
    var tools: Set<Tool>,
    var inputs: List<Molecule>,
    var outputs: List<Molecule>,
    var outputScale: Int,
    var productionInfo: ProductionInfo?
) {
}