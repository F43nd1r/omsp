package com.faendir.om.parser.csharp

import kotlinx.io.core.*

class CSharpBinaryWriter(private val output: Output) : Closeable {
    fun write(i: Int) = output.writeIntLittleEndian(i)

    fun write(l: Long) = output.writeLongLittleEndian(l)

    fun write(b: Byte) = output.writeByte(b)

    fun write(c: Char) {
        output.writeByte(c.toByte())
    }

    fun write(b: Boolean) {
        output.writeByte(if (b) 1 else 0)
    }

    fun write(s: String) {
        val bytes = s.toByteArray()
        writeStringLength(bytes.size)
        output.writeFully(bytes, 0, bytes.size)
    }

    private fun writeStringLength(length: Int) {
        var v = length
        while (v >= 0x80) {
            output.writeByte((v or 0x80).toByte())
            v = v shr 7
        }
        output.writeByte(v.toByte())
    }

    fun <T> write(list: List<T>, writeOne: CSharpBinaryWriter.(T) -> Unit) {
        write(list.size)
        list.forEach { writeOne(it) }
    }

    override fun close() {
        output.close()
    }
}