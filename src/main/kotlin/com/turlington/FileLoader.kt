package com.turlington

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption.APPEND
import java.nio.file.StandardOpenOption.CREATE
import java.util.stream.Collectors

/**
 * Saves and loads jobs from text file.
 * Created by Valerie on 7/18/2016.
 */
internal class FileLoader {
    private val filePath = Paths.get("jobs.txt")

    val jobListingsFromFile: Set<String>
        get() {
            if (Files.exists(filePath)) return Files.lines(filePath).collect(Collectors.toSet())
            return HashSet()
        }

    fun saveFile(jobListing: JobListing) {
        Files.write(filePath, ("${jobListing.title}\n").toByteArray(), CREATE, APPEND)
    }
}
