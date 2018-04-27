package com.turlington

import java.util.*

/**
 * Information about a job.
 * Created by Valerie on 7/18/2016.
 */
class JobListing internal constructor(internal val title: String, private var url: String? = null,
                                      internal var emailSubject: String = "{Default Subject}") {
    private val jobProperties = HashMap<String, String>()

    internal fun addProperty(name: String, value: String) {
        jobProperties.put(name, value)
    }

    private val properties: Map<String, String>
        get() = jobProperties

    internal fun setUrl(url: String) {
        this.url = url
    }

    internal val emailText: String
        get() {
            val text = StringBuilder("Title: $title")
            if (url != null) {
                text.append("<br>Link: ").append(url)
            }
            for ((key, value) in properties) {
                text.append("<br>").append(key).append(": ").append(value)
            }
            return text.toString()
        }
}
