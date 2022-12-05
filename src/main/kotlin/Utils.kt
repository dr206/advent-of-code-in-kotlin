import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/main/resources", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Splits a list into multiple lists based on given predicate.
 */
fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> =
    fold(mutableListOf(mutableListOf<T>()))
    { acc, item ->
        if (predicate(item)) acc.add(mutableListOf())
        else acc.last().add(item)
        acc
    }
