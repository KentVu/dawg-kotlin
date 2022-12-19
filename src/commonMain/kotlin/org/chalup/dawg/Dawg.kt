package org.chalup.dawg

import okio.Sink
import okio.Source
import okio.buffer
import kotlin.jvm.JvmStatic

class Dawg
internal constructor(private val nodeReader: NodeReader) {
    operator fun contains(word: String): Boolean {
        var nodeIndex = 0
        var charIndex = 0

        do {
            val node = nodeReader[nodeIndex]

            if (node.letter == word[charIndex]) {
                if (charIndex + 1 == word.length) {
                    return node.endOfWord
                } else {
                    nodeIndex = node.firstChildIndex
                    charIndex += 1
                }
            } else if (!node.lastChild) {
                nodeIndex += 1
            } else {
                nodeIndex = 0
            }
        } while (nodeIndex != 0)

        return false
    }

    fun words(): List<String> = nodeReader.words()
    fun encode(sink: Sink, format: DawgFormat = supportedFormats.maxBy { it.version }!!): Unit = with(sink.buffer()) {
        writeUtf8("DAWG")
        writeByte(format.version.toInt())
        format.encode(nodeReader, this)
        flush()
    }

    fun prefixSearch(prefix: String): List<String> {
        var nodeIndex = 0
        var charIndex = 0
        val result = mutableListOf<String>()
        val path = mutableListOf<Node>()

        do {
            val node = nodeReader[nodeIndex]

            if (node.letter == prefix[charIndex]) {
                path += node
                nodeIndex = node.firstChildIndex
                if (charIndex + 1 == prefix.length) {
                    if (node.endOfWord) {
                        result.add(prefix)
                    }
                    if (nodeIndex != 0) {
                        nodeReader.findWords(nodeIndex, prefix, result)
                    }
                    break
                } else {
                    charIndex += 1
                }
            } else if (!node.lastChild) {
                nodeIndex += 1
            } else {
                nodeIndex = 0
            }
        } while (nodeIndex != 0)

        return result
    }

    fun containsPrefix(prefix: String): Boolean {
        var nodeIndex = 0
        var charIndex = 0

        do {
            val node = nodeReader[nodeIndex]

            if (node.letter == prefix[charIndex]) {
                nodeIndex = node.firstChildIndex
                charIndex += 1
                if (charIndex == prefix.length) {
                    return true
                }
            } else if (!node.lastChild) {
                nodeIndex += 1
            } else {
                nodeIndex = 0
            }
        } while (nodeIndex != 0)

        return false
    }

    companion object {
        @JvmStatic
        fun decode(source: Source): Dawg = with(source.buffer()) {
            check(readUtf8(4) == "DAWG")

            val formatVersion = readByte()
            val format = supportedFormats.firstOrNull { it.version == formatVersion }
                ?: throw UnsupportedOperationException("Unsupported format version $formatVersion")

            Dawg(format.decode(this))
        }

        @JvmStatic
        fun generate(words: List<String>): Dawg =
            DawgBuilder()
                .build(words)
                .let { ListNodeReader(it) }
                .let { Dawg(it) }
    }
}
