package com.faendir.om.parser.csharp

import com.faendir.om.parser.util.ZERO
import kotlinx.io.core.*


class CSharpBinaryReader(private val input: Input) : Closeable {
    fun readInt(): Int = input.readIntLittleEndian()

    fun readLong(): Long = input.readLongLittleEndian()

    fun readByte(): Byte = input.readByte()

    fun readChar(): Char = readByte().toChar()

    fun readBoolean(): Boolean = readByte() != Byte.ZERO

    fun readString(): String {
        val length = getStringLength()
        return input.readTextExactBytes(length)
    }

    private fun getStringLength(): Int {
        var count = 0
        var shift = 0
        do {
            val b = input.readByte().toInt()
            count = count or (b and 0x7F) shl shift
            shift += 7
        } while (b and 0x80 != 0)
        return count
    }

    fun <T> readList(readOne: CSharpBinaryReader.() -> T): List<T> = (0 until readInt()).map { readOne() }

    override fun close() {
        input.close()
    }
}