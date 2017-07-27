package com.turlington

import java.util.*

/**
 * Information about a job.
 * Created by Mitchell on 7/18/2016.
 */
class JobListing internal constructor(internal val title: String) {
    private var url: String? = null
    private val jobProperties = HashMap<String, String>()
    internal var emailSubject: String = "{Default Subject}"

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
                text.append("\nLink: ").append(url)
            }
            for ((key, value) in properties) {
                text.append("\n").append(key).append(": ").append(value)
            }
            return text.toString()
        }
}
