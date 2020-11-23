package com.xeinebiu.lyrics_finder

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

private fun String.encodeParam(): String = URLEncoder.encode(this, "utf-8")

object LyricsFinder {
    private const val MIN_LENGTH = 100
    fun find(title: String): String? {
        val urls = bing("$title lyrics")
        for (url in urls) {
            val r = findLyrics(url)
            if (r != null && r.length > MIN_LENGTH)
                return r
        }
        return null
    }

    private fun findLyrics(url: String): String? {
        val response = getHttpContent(url)
        val elements = Jsoup.parse(response).body().select("*")
        var maxBr = 0
        var result: Element? = null
        for (e in elements) {
            val brElements = e.children().count { it.tag()?.name.equals("br") }
            if (brElements > maxBr) {
                maxBr = brElements
                result = e
            }
        }
        if (result != null)
            return getText(result)
        return null
    }

    private fun getText(parentElement: Element): String? {
        var result = ""
        for (child in parentElement.childNodes()) {
            if (child is TextNode)
                result += child.text()

            if (child is Element) {
                if (child.tag().name.equals("br", ignoreCase = true))
                    result += "\n"

                result += getText(child)
            }
        }
        return result
    }

    private fun bing(query: String): List<String> {
        val uri = "https://www.bing.com/search?q=${query.encodeParam()}&go=Search&qs=ds&form=QBRE"
        val response = getHttpContent(uri)
        val doc = Jsoup.parse(response)

        return doc.getElementById("b_content")
            .getElementById("b_results")
            .getElementsByClass("b_algo")
            .flatMap { it.getElementsByTag("h2") }
            .mapNotNull {
                try {
                    val href = it.getElementsByTag("a").attr("href")
                    if (href.startsWith("http") && !href.contains("https://www.bing.com"))
                        href
                    else
                        null
                } catch (e: Exception) {
                    null
                }
            }
    }

    private fun getHttpContent(url: String): String {
        val httpURLConnection = URL(url).openConnection() as HttpURLConnection
        httpURLConnection.setRequestProperty(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.66 Safari/537.36"
        )
        return httpURLConnection.inputStream.bufferedReader().readText()
    }
}