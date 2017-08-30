package com.turlington

import org.apache.log4j.Logger
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


/**
 * Checks for job postings.
 * Created by Mitchell on 7/18/2016.
 */
class JobChecker internal constructor(private val jobSites: Set<JobSite>, private val fileLoader: FileLoader, private val notifier: iNotify) : iJobChecker {
    private val logger = Logger.getLogger(javaClass)
    private val jobListings: MutableSet<String> = fileLoader.jobListingsFromFile as MutableSet<String>

    override fun checkSites() {
        val random = Random()
        val stopPath = Paths.get("stop.stop")
        while (Files.notExists(stopPath)) {
            try {
                for (jobSite in jobSites) {
                    jobSite.goToPage()
                    checkJobs(jobSite)
                }
            } catch (e: Exception) {
                logger.error("Error checking sites: ${e.message}", e)
            }
            val minSeconds = random.nextDouble() + random.nextInt(3)
            Main.waitMillis((minSeconds * 60.0 * 1000.0).toLong())
        }
    }

    override fun checkJobs(jobSite: JobSite) {
        try {
            val jobsFound = jobSite.getJobListings()
            jobsFound.filter { !jobListings.contains(it.title) }.forEach { jobListing ->
                jobListings.add(jobListing.title)
                fileLoader.saveFile(jobListing)
                notifier.announce(jobListing)
            }
        } catch (e: Exception) {
            logger.error("Error getting jobs: ${e.message}", e)
        }
    }
}
