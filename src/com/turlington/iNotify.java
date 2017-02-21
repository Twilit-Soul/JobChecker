package com.turlington;

/**
 * What to do when a job is found.
 * Created by Mitchell on 7/18/2016.
 */
public interface iNotify {

    /**
     * Announce that a job has been found.
     * @param jobListing
     */
    void announce(JobListing jobListing);
}
