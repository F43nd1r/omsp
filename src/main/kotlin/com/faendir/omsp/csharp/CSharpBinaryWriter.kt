package com.faendir.omsp.csharp

import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CSharpBinaryWriter(private val output: OutputStream) : AutoCloseable {
    fun write(i: Int) {
        val buffer = ByteBuffer.allocate(Int.SIZE_BYTES)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        buffer.putInt(i)
        output.write(buffer.array())
    }

    fun write(b: Byte) {
        output.write(b.toInt())
    }

    fun write(c: Char) {
        output.write(c.toInt())
    }

    fun write(s : String) {
        val bytes = s.toByteArray()
        writeStringLength(bytes.size)
        output.write(bytes)
    }

    private fun writeStringLength(length: Int) {
        var v = length
        while (v >= 0x80) {
            output.write(v or 0x80)
            v = v shr 7
        }
        output.write(v)
    }

    override fun close() {
        output.close()
    }
}