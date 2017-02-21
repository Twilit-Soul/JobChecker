package com.turlington;

/**
 * For testing right now.
 * Created by Mitchell on 7/18/2016.
 */
public class Notify implements iNotify {

    @Override
    public void announce(JobListing jobListing) {
        EmailUtil.sendEmail(jobListing);
    }
}
