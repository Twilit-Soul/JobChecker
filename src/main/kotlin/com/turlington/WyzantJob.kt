package com.turlington

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import java.util.regex.Pattern

internal class WyzantJob(private val username: String, private val password: String) : WebDriverSite() {

    private fun init(url: String) {
        if (!initialized) {
            webDriver!!.get(url)
            val userNameInput = webDriver!!.findElement(By.cssSelector("#Username"))
            userNameInput.sendKeys(username)
            val passwordInput = webDriver!!.findElement(By.cssSelector("#Password"))
            passwordInput.sendKeys(password)
            val signInButton = webDriver!!.findElement(By.cssSelector("#signinBtn"))
            signInButton.submit()
            initialized = true
        }
    }

    init {
        url = "https://www.wyzant.com/Tutor/Jobs?f=5&cat=10&sub=144&lt=2"
    }

    override fun getJobListings(): Set<JobListing> {
        init(url!!)
        val jobs = webDriver!!.findElements(By.cssSelector(".job-details.column.medium-8.large-9"))
        return jobs.map{getJobListing(it)}.toSet()
    }

    private val pattern = Pattern.compile("Posted by (.*),.*")

    private fun getJobListing(job: WebElement): JobListing {
        val titleElement = job.findElement(By.cssSelector("h4")) //Get title element
        val postedBy = job.findElement(By.cssSelector("p.text-light")) //Get postedBy info
        val postedByMatcher = pattern.matcher(postedBy.text)
        val studentName = if (postedByMatcher.find()) postedByMatcher.group(1) else "error"
        val jobTitle = "$studentName - ${titleElement.text}" //(more of a unique title)
        val description = job.findElement(By.cssSelector("p"))
        val parentElement = job.findElement(By.xpath("./..")) //I'm sure there's a better way.
        val applyButton = parentElement.findElement(By.cssSelector(".column.medium-4.large-3 a.btn.btn-wide"))
        val jobListing = JobListing(jobTitle)
        jobListing.emailSubject = "New Wyzant Job found! - $jobTitle"
        jobListing.addProperty("Description", description.text)
        jobListing.addProperty("Posted", postedBy.text)
        jobListing.setUrl(applyButton.getAttribute("href"))
        return jobListing
    }

    companion object {
        private var initialized = false
    }
}
