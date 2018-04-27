package com.turlington

/**
 * For testing right now.
 * Created by Valerie on 7/18/2016.
 */
class Notify internal constructor(private val emailInfo: EmailInfo) : iNotify {
    override fun announce(jobListing: JobListing) = EmailUtil.sendEmail(emailInfo, jobListing)
}
