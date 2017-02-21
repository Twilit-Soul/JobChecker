package com.turlington;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Set;

/**
 * Checks for job postings.
 * Created by Mitchell on 7/18/2016.
 */
public class JobChecker implements iJobChecker {
    private final WebDriver webDriver;
    private final Set<JobSite> jobSites;
    private final Set<String> jobListings;
    private final FileLoader fileLoader;
    private final iNotify notifier;

    public JobChecker(Set<JobSite> jobSites, FileLoader fileLoader, iNotify notifier) {
        webDriver = new FirefoxDriver();
        this.notifier = notifier;
        this.fileLoader = fileLoader;
        this.jobListings = fileLoader.getJobListingsFromFile();
        this.jobSites = jobSites;
        for (JobSite jobSite : jobSites) {
            jobSite.setWebDriver(webDriver);
        }
    }

    @Override
    public void checkSites() {
        //noinspection InfiniteLoopStatement
        while (true) { //TODO: probably should give a real way to exit the loop aside from terminating program manually.
            for (JobSite jobSite : jobSites) {
                jobSite.goToPage();
                checkJobs(jobSite);
            }
            try {
                Thread.sleep(1800000L);
            } catch (InterruptedException e) {
                //Who cares
            }
        }
    }

    @Override
    public void checkJobs(JobSite jobsite) {
        Set<JobListing> jobsFound = jobsite.getJobListings();
        jobsFound.stream().filter(jobListing -> !jobListings.contains(jobListing.getTitle())).forEach(jobListing -> {
            jobListings.add(jobListing.getTitle());
            fileLoader.saveFile(jobListing);
            notifier.announce(jobListing);
        });
    }
}
