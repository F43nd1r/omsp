package com.faendir.omsp.part

enum class IOType(internal val key: String) {
    INPUT("input"),
    OUTPUT("out-std"),
    INFINITE("out-rep");

    companion object {
        fun fromString(key: String): IOType? {
            return IOType.values().find { it.key == key }
        }
    }
}