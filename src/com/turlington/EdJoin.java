package com.turlington;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * List of Jobsite information.
 * Created by Mitchell on 7/18/2016.
 */
public class EdJoin extends JobSite {

    EdJoin(int districtId) {
        url = "https://www.edjoin.org/Home/Jobs?stateID=24&countyID=30&districtID="+districtId;
    }

    @Override
    public Set<JobListing> getJobListings() {
        Set<JobListing> jobListings = new HashSet<>();

        final WebElement tableElement = webDriver.findElement(By.cssSelector("#tblJobs"));
        final List<WebElement> rows = tableElement.findElements(By.cssSelector("tbody > tr"));
        for (WebElement row : rows) {
            final WebElement staffType = row.findElement(By.cssSelector("td.footable-visible.footable-first-column > b:nth-child(3)"));
            if (!staffType.getText().equals("Classified Staff")) {
                continue;
            }
            final WebElement jobTitle = row.findElement(By.cssSelector("td.footable-visible.footable-first-column > b:nth-child(1) > a"));
            if (!jobTitle.getText().contains("Assistant")) {
                continue;
            }
            jobListings.add(getJobListing(row));
        }
        return jobListings;
    }

    private JobListing getJobListing(WebElement row) {
        final WebElement jobTitle = row.findElement(By.cssSelector("td.footable-visible.footable-first-column > b:nth-child(1) > a"));
        final JobListing jobListing = new JobListing(jobTitle.getText());
        jobListing.setEmailSubject("New EdJoin job found!");
        jobListing.setUrl(jobTitle.getAttribute("href"));
        final WebElement deadLine = row.findElement(By.cssSelector("td:nth-child(5)"));
        jobListing.addProperty("Deadline", deadLine.getText().trim());
        final WebElement location = row.findElement(By.cssSelector("td:nth-child(6)"));
        jobListing.addProperty("Location", location.getText().trim().replace("\n", " "));
        final WebElement salaryInfo = row.findElement(By.cssSelector("td.footable-visible.footable-last-column"));
        jobListing.addProperty("Salary Info", salaryInfo.getText().trim());
        return jobListing;
    }
}
