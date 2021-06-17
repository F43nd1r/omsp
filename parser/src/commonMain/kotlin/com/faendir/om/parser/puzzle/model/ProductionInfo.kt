package com.faendir.om.parser.puzzle.model

data class ProductionInfo(
    var shrinkLeft: Boolean,
    var shrinkRight: Boolean,
    var isolateInputsFromOutputs: Boolean,
    var cabinets: List<Cabinet>,
    var conduits: List<Conduit>,
    var vials: List<Vial>
)

data class Cabinet(var hexIndex: HexIndex, var type: CabinetType)

enum class CabinetType(internal val id: String) {
    SMALL("Small"),
    SMALL_WIDE("SmallWide"),
    SMALL_WIDER("SmallWider"),
    MEDIUM("Medium"),
    MEDIUM_WIDE("MediumWide"),
    LARGE("Large");

    companion object {
        fun fromString(id: String): CabinetType? = values().find { it.id == id }
    }
}

data class Conduit(
    var hexIndexA: HexIndex,
    var hexIndexB: HexIndex,
    var offsets: List<HexIndex>
)

data class Vial(
    var hexIndex: HexIndex,
    var style: VialStyle,
    var count: Int //values 0-3
)

enum class VialStyle {
    TOP,
    BOTTOM
}

