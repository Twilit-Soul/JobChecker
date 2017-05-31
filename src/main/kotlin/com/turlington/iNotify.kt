package com.turlington

/**
 * What to do when a job is found.
 * Created by Mitchell on 7/18/2016.
 */
interface iNotify {
    /**
     * Announce that a job has been found.
     */
    fun announce(jobListing: JobListing)
}
