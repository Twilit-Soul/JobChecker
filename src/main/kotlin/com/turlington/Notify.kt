package com.turlington

/**
 * For testing right now.
 * Created by Mitchell on 7/18/2016.
 */
class Notify internal constructor(private val fromEmail: String, private val password: String, private val toEmail: String) : iNotify {
    override fun announce(jobListing: JobListing) {
        EmailUtil.sendEmail(fromEmail, password, toEmail, jobListing)
    }
}
