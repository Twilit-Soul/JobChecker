package com.turlington;

import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * Contains information about the site for the job posting.
 * Created by Mitchell on 7/18/2016.
 */
public abstract class JobSite {
    protected String url;
    protected WebDriver webDriver;

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public abstract void goToPage();

    public abstract Set<JobListing> getJobListings();
}
