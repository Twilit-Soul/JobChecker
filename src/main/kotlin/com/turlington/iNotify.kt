package com.turlington

/**
 * What to do when a job is found.
 * Created by Valerie on 7/18/2016.
 */
interface iNotify {
    /**
     * Announce that a job has been found.
     */
    fun announce(jobListing: JobListing)
}
