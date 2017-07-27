package com.turlington

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

/**
 * This is a site that will be accessed via WebDriver.
 * Created by Mitchell on 4/22/2017.
 */
internal abstract class WebDriverSite(var webDriver: WebDriver, url: String) : JobSite(url) {

    override fun goToPage() {
        webDriver.get(url)
    }

    fun waitForElement(cssSelector: String, seconds: Int): WebElement {
        return WebDriverWait(webDriver, seconds.toLong()).until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)))
    }

    /**
     * Finds an element under the job element, and adds a property to the job listing using the
     * given property name. Uses the [getText()][WebElement.getText] of the child element found as
     * the property text.
     * @param jobListing To add a property to.
     * *
     * @param jobElement To search child elements of.
     * *
     * @param propertyName Name of the property being added.
     * *
     * @param cssSelector Used to search child elements of jobElement.
     */
    fun addProperty(jobListing: JobListing, jobElement: WebElement, propertyName: String, cssSelector: String) {
        jobListing.addProperty(propertyName, jobElement.selectText(cssSelector))
    }

    private fun WebElement.selectText(css: String) = findElement(By.cssSelector(css)).text
}
