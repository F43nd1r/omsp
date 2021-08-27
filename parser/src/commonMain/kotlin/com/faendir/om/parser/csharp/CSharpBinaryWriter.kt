package com.faendir.om.parser.csharp

import okio.BufferedSink
import okio.Closeable

class CSharpBinaryWriter(private val output: BufferedSink) : Closeable {
    fun write(i: Int) = output.writeIntLe(i)

    fun write(l: Long) = output.writeLongLe(l)

    fun write(b: Byte) = output.writeByte(b.toInt())

    fun write(c: Char) {
        output.writeByte(c.code)
    }

    fun write(b: Boolean) {
        output.writeByte(if (b) 1 else 0)
    }

    fun write(s: String) {
        val bytes = s.encodeToByteArray()
        writeStringLength(bytes.size)
        output.write(bytes, 0, bytes.size)
    }

    private fun writeStringLength(length: Int) {
        var v = length
        while (v >= 0x80) {
            output.writeByte((v or 0x80))
            v = v shr 7
        }
        output.writeByte(v)
    }

    fun <T> write(list: List<T>, writeOne: CSharpBinaryWriter.(T) -> Unit) {
        write(list.size)
        list.forEach { writeOne(it) }
    }

    override fun close() {
        output.close()
    }
}