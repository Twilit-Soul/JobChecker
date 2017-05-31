package com.turlington

import org.openqa.selenium.firefox.FirefoxDriver
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


/**
 * Checks for job postings.
 * Created by Mitchell on 7/18/2016.
 */
class JobChecker internal constructor(private val jobSites: Set<JobSite>, private val fileLoader: FileLoader, private val notifier: iNotify) : iJobChecker {
    private val jobListings: MutableSet<String> = fileLoader.jobListingsFromFile as MutableSet<String>

    init {
        if (jobSites.stream().anyMatch { s -> s is WebDriverSite }) {
            val driver = FirefoxDriver()
            jobSites.filterIsInstance<WebDriverSite>()
                    .forEach { it.webDriver = driver }
        }
    }

    override fun checkSites() {
        val random = Random()
        val stopPath = Paths.get("stop.stop")
        while (Files.notExists(stopPath)) {
            for (jobSite in jobSites) {
                jobSite.goToPage()
                checkJobs(jobSite)
            }
            val minSeconds = random.nextDouble() + random.nextInt(3)
            Main.waitMillis((minSeconds * 60.0 * 1000.0).toLong())
        }
    }

    override fun checkJobs(jobsite: JobSite) {
        val jobsFound = jobsite.getJobListings()
        jobsFound.stream().filter { jobListing -> !jobListings.contains(jobListing.title) }.forEach { jobListing ->
            jobListings.add(jobListing.title)
            fileLoader.saveFile(jobListing)
            notifier.announce(jobListing)
        }
    }
}
