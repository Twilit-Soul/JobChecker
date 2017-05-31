package com.turlington

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.io.IOException

/**
 * Created by Mitchell on 4/22/2017.
 */
abstract class JSoupSite : JobSite() {
    internal var document: Document? = null

    override fun goToPage() {
        try {
            document = Jsoup.connect(url).get()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
