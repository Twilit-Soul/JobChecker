package com.turlington;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class WyzantJob extends JobSite {
	private static boolean initialized = false;
	private final String username, password;

	private void init(String url) {
		if (!initialized) {
			webDriver.get(url);
			WebElement userNameInput = webDriver.findElement(By.cssSelector("#Username"));
			userNameInput.sendKeys(username);
			WebElement passwordInput = webDriver.findElement(By.cssSelector("#Password"));
			passwordInput.sendKeys(password);
			WebElement signInButton = webDriver.findElement(By.cssSelector("#signinBtn"));
			signInButton.submit();
			initialized = true;
		}
	}

	WyzantJob(String username, String password) {
		if (username == null || password == null) {
			Main.explainWyzantInfoAndQuit();
		}
		this.username = username;
		this.password = password;
		url = "https://www.wyzant.com/Tutor/Jobs?f=5&cat=10&sub=144&lt=2";
	}

	@Override
	public Set<JobListing> getJobListings() {
		init(url);
		final List<WebElement> jobs = webDriver.findElements(By.cssSelector(".job-details.column.medium-8.large-9"));
		return jobs.stream().map(this::getJobListing).collect(Collectors.toSet());
	}

	private final Pattern pattern = Pattern.compile("Posted by (.*),.*");

	private JobListing getJobListing(WebElement job) {
		WebElement titleElement = job.findElement(By.cssSelector("h4")); //Get title element
		WebElement postedBy = job.findElement(By.cssSelector("p.text-light")); //Get postedBy info
		Matcher postedByMatcher = pattern.matcher(postedBy.getText());
		String studentName = postedByMatcher.find() ? postedByMatcher.group(1) : "error";
		String jobTitle =  studentName + " - " + titleElement.getText(); //(more of a unique title)
		WebElement description = job.findElement(By.cssSelector("p"));
		WebElement parentElement = job.findElement(By.xpath("./..")); //I'm sure there's a better way.
		WebElement applyButton = parentElement.findElement(By.cssSelector(".column.medium-4.large-3 a.btn.btn-wide"));
		JobListing jobListing = new JobListing(jobTitle);
		jobListing.setEmailSubject("New Wyzant Job found! - " + jobTitle);
		jobListing.addProperty("Description", description.getText());
		jobListing.addProperty("Posted", postedBy.getText());
		jobListing.setUrl(applyButton.getAttribute("href"));
		return jobListing;
	}
}
