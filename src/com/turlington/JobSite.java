package com.turlington;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

/**
 * Contains information about the site for the job posting.
 * Created by Mitchell on 7/18/2016.
 */
public abstract class JobSite {
    String url;
    WebDriver webDriver;

    void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    void goToPage() {
        webDriver.get(url);
    }

    public abstract Set<JobListing> getJobListings();

	WebElement waitForElement(String cssSelector, int seconds) {
		return new WebDriverWait(webDriver, seconds).
				until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
	}

	/**
	 * Finds an element under the job element, and adds a property to the job listing using the
	 * given property name. Uses the {@link WebElement#getText() getText()} of the child element found as
	 * the property text.
	 * @param jobListing To add a property to.
	 * @param jobElement To search child elements of.
	 * @param propertyName Name of the property being added.
	 * @param cssSelector Used to search child elements of jobElement.
	 */
	void addProperty(JobListing jobListing, WebElement jobElement, String propertyName, String cssSelector) {
		final WebElement element = jobElement.findElement(By.cssSelector(cssSelector));
		jobListing.addProperty(propertyName, element.getText());
	}
}
