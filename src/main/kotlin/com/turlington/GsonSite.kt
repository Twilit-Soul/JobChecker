package com.turlington

import com.google.gson.Gson
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Sends a GET request to the given url, and converts the json to type T.
 * Created by Mitchell on 4/22/2017.
 */
internal abstract class GsonSite<T>(url: String, private val clazz: Class<T>) : JobSite(url) {
    private val gson = Gson()
    var searchResult: T? = null

    override fun goToPage() {
        try {
            searchResult = gson.fromJson(sendGET(url), clazz)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        private val USER_AGENT = "Mozilla/5.0"

        /**
         * Sends a simple GET request to the given URL, and attempts to return the response.
         * @param url To send the request to.
         * *
         * @return The response.
         * *
         * @throws IOException If there are any errors connecting or reading the response.
         */
        private fun sendGET(url: String): String? {
            var i = 0
            while (i++ < 5) {
                try {
                    HttpClientBuilder.create().build().use { httpClient ->
                        val request = HttpGet(url)
                        request.addHeader("User-Agent", USER_AGENT)
                        val response = httpClient.execute(request)
                        BufferedReader(InputStreamReader(response.entity.content)).use { reader ->
                            val sb = StringBuilder()
                            var line: String? = reader.readLine()
                            while (line != null) {
                                sb.append(line)
                                line = reader.readLine()
                            }
                            return sb.toString()
                        }
                    }
                } catch (e1: IOException) {
                    if (i > 4) throw e1
                    Main.waitMillis(5000)
                }
            }
            return null
        }
    }
}
