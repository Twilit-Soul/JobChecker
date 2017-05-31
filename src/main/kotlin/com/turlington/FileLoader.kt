package com.turlington

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.APPEND
import java.nio.file.StandardOpenOption.CREATE
import java.util.stream.Stream

/**
 * Saves and loads jobs from text file.
 * Created by Mitchell on 7/18/2016.
 */
internal class FileLoader {
    private val filePath = Paths.get("jobs.txt")

    val jobListingsFromFile: Set<String>
        get() {
            try {
                if (Files.exists(filePath)) {
                    return Files.lines(filePath).toSet()
                }
                return HashSet()
            } catch (e: IOException) {
                e.printStackTrace()
                System.exit(1)
                return HashSet()
            }
        }

    fun saveFile(jobListing: JobListing) {
        Files.write(filePath, ("${jobListing.title}\n").toByteArray(), CREATE, APPEND)
    }

    fun <T> Stream<T>.toSet(): Set<T> {
        val set = HashSet<T>()
        forEach { set.add(it) }
        return set
    }
}
