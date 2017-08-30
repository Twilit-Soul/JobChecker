package com.turlington

import org.jsoup.Jsoup

class Blizzard : JSoupSite("https://careers.blizzard.com/en-us/openings/engineering,program-management,project-management/battle-net,classic-games,diablo,hearthstone,heroes-of-the-storm,incubation,overwatch,star-craft-ii,unannounced-project,web-mobile,world-of-warcraft/irvine/full-time/1") {
    override fun getJobListings() = document.select("#jobs a").map {
        val doc = Jsoup.connect(it.absUrl("href")).get()
        val job = JobListing(doc.select("div.Heading.Heading--articleHeadline").first().text())
        job.setUrl(it.absUrl("href"))
        job.addProperty("Job", doc.select("div.Markup.Markup--html").first().outerHtml())
        job.emailSubject = "Blizzard Job Found - ${job.title}"
        job //This is what we're mapping to.
    }.toSet()
}