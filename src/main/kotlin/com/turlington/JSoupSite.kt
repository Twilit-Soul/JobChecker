package com.turlington

import org.apache.log4j.Logger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.io.IOException

/**
 * Created by Valerie on 4/22/2017.
 */
abstract class JSoupSite(url: String) : JobSite(url) {
    private val logger = Logger.getLogger(javaClass)
    internal var document: Document = Jsoup.connect(url).get()

    override fun goToPage() {
        try {
            document = Jsoup.connect(url).get()
        } catch (e: IOException) {
            logger.error(e)
        }
    }
}
