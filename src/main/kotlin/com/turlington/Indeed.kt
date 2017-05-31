package com.turlington

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

internal class Indeed internal constructor(what: String, where: String, jobType: String) : WebDriverSite() {
    init {
        url ="https://www.indeed.com/jobs?q=$what&l=$where&jt=$jobType"
    }

    override fun getJobListings(): Set<JobListing> {
        handlePopUp()
        val jobs = webDriver!!.findElements(By.cssSelector("div[data-tn-component='organicJob']"))
        return jobs.map{ this.getJobListing(it) }.toSet()
    }

    /**
     * Indeed wants us to get Indeed Prime. A little ambitious for this little app, unfortunately.
     * Maybe if it saves up long enough.
     */
    private fun handlePopUp() {
        try {
            waitForElement("#prime-popover-close-button", 1).click()
        } catch (e: Exception) {
            //If we don't find it, all the merrier.
        }
    }

    private fun getJobListing(job: WebElement): JobListing {
        val jobTitleElement = job.findElement(By.cssSelector("h2.jobtitle > a"))
        val jobListing = JobListing(jobTitleElement.getAttribute("title"))
        jobListing.emailSubject = "New Indeed Job Found! - ${jobTitleElement.getAttribute("title")}"
        jobListing.setUrl(jobTitleElement.getAttribute("href"))
        addProperty(jobListing, job, "Company", "span.company > span:nth-child(1)")
        addProperty(jobListing, job, "Location", "span > span.location > span")
        addProperty(jobListing, job, "Description", "table > tbody > tr > td > div> span.summary")
        addProperty(jobListing, job, "Date", "table > tbody > tr > td > div > div > span.date")
        return jobListing
    }
}
