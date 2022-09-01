package com.example.flashes

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdaptor: NewsAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRecyclerView = findViewById<RecyclerView>(R.id.newsRecyclerView)

        newsRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchData()
        mAdaptor = NewsAdaptor(this)
        newsRecyclerView.adapter = mAdaptor
    }

    private fun fetchData()
    {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=cd96a49336974616a5e67e072da00be1"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length())
                {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getJSONObject("source").getString("name"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("publishedAt")
                    )
                    newsArray.add(news)
                }

                mAdaptor.updateNews(newsArray)
            },
            {
                Toast.makeText(this, "Something Went Wrong!!", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {

        val builder = CustomTabsIntent.Builder()

        val colorInt: Int = Color.parseColor("#C50000") // @color/red
        val defaultColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorInt)
            .build()
        builder.setDefaultColorSchemeParams(defaultColors)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}