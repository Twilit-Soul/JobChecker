package com.turlington

import com.turlington.edjoin.Datum
import com.turlington.edjoin.EdjoinSearchResult

/**
 * Gets information about Edjon jobs.
 * Created by Mitchell on 7/18/2016.
 */
internal class EdJoin internal constructor() : GsonSite<EdjoinSearchResult>(EdjoinSearchResult::class.java) {

    init {
        url ="https://www.edjoin.org/Home/LoadJobs?rows=10&page=1&sort=postingDate&order=desc&keywords=&searchType=" +
                "all&states=24&regions=30&jobTypes=2,3,48,64,0&days=0&catID=0&onlineApps=false&recruitmentCenterID=0" +
                "&stateID=0&regionID=0&districtID=0&countyID=0&searchID=0&_=1492882740084"
    }

    override fun getJobListings(): Set<JobListing> {
        return searchResult!!.data.map { this.getJobListing(it) }.toSet()
    }

    private fun getJobListing(data: Datum): JobListing {
        val jobListing = JobListing(data.positionTitle)
        jobListing.emailSubject = "New EdJoin job found!"
        jobListing.setUrl("https://www.edjoin.org/Home/JobPosting/${data.postingID}")
        jobListing.addProperty("Deadline", data.displayUntil)
        jobListing.addProperty("City", data.city)
        jobListing.addProperty("County", data.countyName)
        jobListing.addProperty("District/School", data.districtName)
        jobListing.addProperty("Salary Info", data.salaryInfo)
        return jobListing
    }
}
