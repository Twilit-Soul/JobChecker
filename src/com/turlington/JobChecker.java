package com.turlington;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Random;
import java.util.Set;


/**
 * Checks for job postings.
 * Created by Mitchell on 7/18/2016.
 */
public class JobChecker implements iJobChecker {
	private final Set<JobSite> jobSites;
    private final Set<String> jobListings;
    private final FileLoader fileLoader;
    private final iNotify notifier;

    JobChecker(Set<JobSite> jobSites, FileLoader fileLoader, iNotify notifier) {
        this.notifier = notifier;
        this.fileLoader = fileLoader;
        this.jobListings = fileLoader.getJobListingsFromFile();
        this.jobSites = jobSites;
		WebDriver driver = new FirefoxDriver();
        for (JobSite jobSite : jobSites) {
            jobSite.setWebDriver(driver);
        }
    }

    @Override
    public void checkSites() {
    	Random random = new Random();
        //noinspection InfiniteLoopStatement
        while (true) { //TODO: maybe should give a real way to exit the loop aside from terminating program manually.
            for (JobSite jobSite : jobSites) {
                jobSite.goToPage();
                checkJobs(jobSite);
            }
            double minSeconds = random.nextDouble() + random.nextInt(3);
            Main.waitMillis((long) (minSeconds * 60 * 1000));
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
