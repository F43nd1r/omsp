package com.faendir.om.parser.csharp

import com.faendir.om.parser.util.ZERO
import okio.BufferedSource
import okio.Closeable
import okio.Source


class CSharpBinaryReader(private val input: BufferedSource) : Closeable {
    fun readInt(): Int = input.readIntLe()

    fun readLong(): Long = input.readLongLe()

    fun readByte(): Byte = input.readByte()

    fun readChar(): Char = readByte().toInt().toChar()

    fun readBoolean(): Boolean = readByte() != Byte.ZERO

    fun readString(): String {
        val length = getStringLength()
        return input.readUtf8(length)
    }

    private fun getStringLength(): Long {
        var count = 0L
        var shift = 0
        do {
            val b = input.readByte().toLong()
            count = count or ((b and 0x7F) shl shift)
            shift += 7
        } while (b and 0x80 != 0L)
        return count
    }

    fun <T> readList(readOne: CSharpBinaryReader.() -> T): List<T> = (0 until readInt()).map { readOne() }

    override fun close() {
        input.close()
    }
}