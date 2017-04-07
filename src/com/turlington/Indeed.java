package com.turlington;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Indeed extends JobSite {

	Indeed(String what, String where, String jobType) {
		url = "https://www.indeed.com/jobs?q="+what+"&l="+where+"&jt="+jobType;
	}

	@Override
	public Set<JobListing> getJobListings() {
		handlePopUp();
		final List<WebElement> jobs = webDriver.findElements(By.cssSelector("div[data-tn-component='organicJob']"));
		return jobs.stream().map(this::getJobListing).collect(Collectors.toSet());
	}

	/**
	 * Indeed wants us to get Indeed Prime. A little ambitious for this little app, unfortunately.
	 * Maybe if it saves up long enough.
	 */
	private void handlePopUp() {
		try {
			waitForElement("#prime-popover-close-button", 1).click();
		} catch (Exception e) {
			//If we don't find it, all the merrier.
		}
	}

	private JobListing getJobListing(WebElement job) {
		final WebElement jobTitleElement = job.findElement(By.cssSelector("h2.jobtitle > a"));
		final JobListing jobListing = new JobListing(jobTitleElement.getAttribute("title"));
		jobListing.setEmailSubject("New Indeed Job Found! - " + jobTitleElement.getAttribute("title"));
		jobListing.setUrl(jobTitleElement.getAttribute("href"));
		addProperty(jobListing, job, "Company", "span.company > span:nth-child(1)");
		addProperty(jobListing, job, "Location", "span > span.location > span");
		addProperty(jobListing, job, "Description", "table > tbody > tr > td > div> span.summary");
		addProperty(jobListing, job, "Date", "table > tbody > tr > td > div > div > span.date");
		return jobListing;
	}
}
