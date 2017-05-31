package com.turlington

/**
 * Contains information about the site for the job posting.
 * Created by Mitchell on 7/18/2016.
 */
abstract class JobSite {
    internal var url: String? = null

    internal abstract fun goToPage()

    abstract fun getJobListings(): Set<JobListing>
}
