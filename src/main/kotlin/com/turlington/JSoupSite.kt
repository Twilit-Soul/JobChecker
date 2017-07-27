package com.turlington

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.io.IOException

/**
 * Created by Mitchell on 4/22/2017.
 */
abstract class JSoupSite(url: String) : JobSite(url) {
    internal var document: Document = Jsoup.connect(url).get()

    override fun goToPage() {
        try {
            document = Jsoup.connect(url).get()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
