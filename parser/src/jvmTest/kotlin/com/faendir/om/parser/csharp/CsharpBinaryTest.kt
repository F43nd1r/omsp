package com.faendir.om.parser.csharp

import okio.Buffer
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Test

class CsharpBinaryTest {

    @Test
    fun readShortString() {
        val buffer = Buffer()
        val writer = CSharpBinaryWriter(buffer)
        val string = "a".repeat(100)
        writer.write(string)

        val reader = CSharpBinaryReader(buffer)
        val read = reader.readString()
        MatcherAssert.assertThat(read, Matchers.equalTo(string))
    }

    @Test
    fun readLongString() {
        val buffer = Buffer()
        val writer = CSharpBinaryWriter(buffer)
        val string = "a".repeat(10000)
        writer.write(string)

        val reader = CSharpBinaryReader(buffer)
        val read = reader.readString()
        MatcherAssert.assertThat(read, Matchers.equalTo(string))
    }
}