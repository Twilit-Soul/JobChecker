package com.turlington;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Saves and loads jobs from text file.
 * Created by Mitchell on 7/18/2016.
 */
public class FileLoader {
    private final Path filePath = Paths.get("jobs.txt");

    Set<String> getJobListingsFromFile() {
        try {
            if (Files.exists(filePath)) {
                return Files.lines(filePath).collect(Collectors.toSet());
            }
            return new HashSet<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(1);
        return null;
    }

    void saveFile(JobListing jobListing) {
        try {
            Files.write(filePath, (jobListing.getTitle()+"\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace(); //TODO: better error message
        }
    }
}
