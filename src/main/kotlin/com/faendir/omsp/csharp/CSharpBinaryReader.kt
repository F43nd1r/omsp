package com.faendir.omsp.csharp

import java.io.EOFException
import java.io.InputStream


class CSharpBinaryReader(private val input: InputStream) : AutoCloseable {
    fun readInt(): Int {
        val ch1: Int = input.read()
        val ch2: Int = input.read()
        val ch3: Int = input.read()
        val ch4: Int = input.read()
        if (ch1 or ch2 or ch3 or ch4 < 0) throw EOFException()
        return (ch4 shl 24) + (ch3 shl 16) + (ch2 shl 8) + (ch1 shl 0)
    }

    fun readByte(): Byte {
        val value = input.read()
        if (value < 0) throw EOFException()
        return value.toByte()
    }

    fun readChar() : Char {
        return readByte().toChar()
    }

    fun readString(): String {
        val length = getStringLength()

        val buffer = ByteArray(length)
        if (input.read(buffer) < 0) {
            throw EOFException()
        }
        return String(buffer)
    }

    private fun getStringLength(): Int {
        var count = 0
        var shift = 0
        do {
            val b = input.read()
            count = count or (b and 0x7F) shl shift
            shift += 7
        } while (b and 0x80 != 0)
        return count
    }

    override fun close() {
        input.close()
    }
}