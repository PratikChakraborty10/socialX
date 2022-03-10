package com.socialx.android.News

//import okhttp3.Request

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.socialx.android.MainActivity
import com.socialx.android.News.data.MySingleton
import com.socialx.android.News.data.NewsListAdapter
import com.socialx.android.News.data.PostModel
import com.socialx.android.R
import com.socialx.android.SearchNews.SearchNews
import com.socialx.android.account.SettingsActivity
import com.socialx.android.auth.BaseActivity
import kotlinx.android.synthetic.main.activity_news.*
import org.json.JSONObject


class NewsActivity : BaseActivity() {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        // Changing the color of status bar
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.red)

        // Calling the recycler view from xml layout
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_search -> startActivity(Intent(this, SearchNews::class.java))
            R.id.nav_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchData() {
        //val url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=b6c05f4cbdda4053b19ca82b9cbbcae4"

        /* apiKey from newsapi.org was not getting recognised here in this activity with volley library
         So, for time being I have used this 3rd party api, which displays the news from newsapi.org
         and it is also having same parameters and is available for
         public use. This is bug that is causing this error, will figure out later or I will use Retrofit / MVVM */

        val url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
        /* Using jsonObjectRequest with Volley Library
         Link: https://google.github.io/volley/ */
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{
                showProgressDialog()
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<PostModel>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = PostModel(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("urlToImage"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("publishedAt")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
                hideProgressDialog()

            },
            Response.ErrorListener {
                Toast.makeText(
                    this, "Something went wrong!\nCheck your internet connection",
                    Toast.LENGTH_LONG)
                    .show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}