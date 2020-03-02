package com.faendir.om.sp.part

enum class ArmType(internal val key: String) {
    ARM1("arm1"),
    ARM2("arm2"),
    ARM3("arm3"),
    ARM6("arm6"),
    PISTON("piston"),
    VAN_BERLOS_WHEEl("baron");

    companion object {
        fun fromString(key: String): ArmType? {
            return ArmType.values().find { it.key == key }
        }
    }
}