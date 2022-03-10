package com.socialx.android.SearchNews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
//import android.widget.SearchView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.socialx.android.R
import com.socialx.android.SearchNews.adapter.MyAdapter
import com.socialx.android.SearchNews.data.News
import java.util.*
import kotlin.collections.ArrayList

class SearchNews : AppCompatActivity() {


    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<News>
    private lateinit var tempArrayList : ArrayList<News>
    private lateinit var imageId : Array<Int>
    private lateinit var heading : Array<String>
    private lateinit var desc : Array<String>
    private lateinit var author : Array<String>
    private lateinit var time : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_news)

        imageId = arrayOf(
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
        )
        heading = arrayOf(
            "Technical Analysis: LUNA Hits New High, as Biden Signs Crypto Executive Order – Market Updates Bitcoin News - Bitcoin News",
            "Gold Price Forecast: XAUUSD battling to recover the \$2,000 level - FXStreet",
            "Shiba Inu is All Set to Reach US\$0.000030 in the Coming Days - Analytics Insight",
            "Oil rises as market weighs OPEC filling Russia supply gap By Reuters - Investing.com",
            "UP Election Results 2022 LIVE: BJP’s ‘bulldozer’ gets thumbs up from UP, Yogi shatters 37-year record to return as CM"
        )
        desc = arrayOf(
            "LUNA hits a new all-time high, as markets reacted to news that U.S. President Joe Biden had signed an executive order on cryptocurrencies.",
            "Gold Price retreated sharply from \$2,070.50 a troy ounce, now trading in the \$1,990 price zone, as fears about an escalation in the Ukraine-Russia cri",
            "Shiba Inu price has found a resistance level at US$0.0000227, which will help the cryptocurrency reach new heights. The Dogecoin killer is expected to break through US$0.000030 in the coming days.",
            "Oil rises as market weighs OPEC filling Russia supply gap",
            "UP Election Results 2022 Live, Uttar Pradesh Election Live Vote Counting, UP Assembly Election Results 2022 Live Updates: The BJP is on its course to retain power in the politically crucial state with Yogi Adityanath set to become chief minister for a second straight term"
        )
        author = arrayOf(
            "Bitcoin - News",
            "FXStreet",
            "Adilin Beatrice",
            "Reuters",
            "Financial Express"
        )
        time = arrayOf(
            "3 hours ago",
            "1 hours ago",
            "1 hours ago",
            "2 hours ago",
            "1 hours ago"
        )
        newRecyclerView = findViewById(R.id.recyclerViewSearch)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<News>()
        tempArrayList = arrayListOf<News>()
        getUserData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()) {
                    newArrayList.forEach {
                        if(it.heading.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            tempArrayList.add(it)
                        }
                    }
                    newRecyclerView.adapter!!.notifyDataSetChanged()
                } else {
                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    newRecyclerView.adapter!!.notifyDataSetChanged()
                }

                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getUserData() {
        for(i in imageId.indices) {
            val news = News(imageId[i], heading[i], desc[i], author[i], time[i])
            newArrayList.add(news)
        }

        tempArrayList.addAll(newArrayList)

        newRecyclerView.adapter = MyAdapter(tempArrayList)
    }
}