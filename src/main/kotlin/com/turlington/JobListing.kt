package com.turlington

import java.util.*

/**
 * Information about a job.
 * Created by Mitchell on 7/18/2016.
 */
class JobListing internal constructor(internal val title: String) {
    private var url: String? = null
    private val jobProperties = HashMap<String, String>()
    internal var emailSubject: String? = null

    internal fun addProperty(name: String, value: String) {
        jobProperties.put(name, value)
    }

    private val properties: Map<String, String>
        get() = jobProperties

    private fun getUrl(): Optional<String> {
        if (url != null) {
            return Optional.of(url!!)
        }
        return Optional.empty<String>()
    }

    internal fun setUrl(url: String) {
        this.url = url
    }

    internal val emailText: String
        get() {
            val text = StringBuilder("Title: $title")
            if (getUrl().isPresent) {
                text.append("\nLink: ").append(getUrl().get())
            }
            for ((key, value) in properties) {
                text.append("\n").append(key).append(": ").append(value)
            }
            return text.toString()
        }
}
