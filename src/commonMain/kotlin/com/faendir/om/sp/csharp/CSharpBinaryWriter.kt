package com.faendir.om.sp.csharp

import kotlinx.io.core.Closeable
import kotlinx.io.core.Output
import kotlinx.io.core.toByteArray
import kotlinx.io.core.writeIntLittleEndian

class CSharpBinaryWriter(private val output: Output) : Closeable {
    fun write(i: Int) = output.writeIntLittleEndian(i)

    fun write(b: Byte) = output.writeByte(b)

    fun write(c: Char) {
        output.writeByte(c.toByte())
    }

    fun write(s : String) {
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

    override fun close() {
        output.close()
    }
}