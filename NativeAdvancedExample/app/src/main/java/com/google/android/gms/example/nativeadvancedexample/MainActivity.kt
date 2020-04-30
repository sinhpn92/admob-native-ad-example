/*
 * Copyright (C) 2017 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.example.nativeadvancedexample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.doubleclick.PublisherAdRequest
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

const val ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"

/**
 * A simple activity class that displays native ad formats.
 */
class MainActivity : AppCompatActivity() {

    private val items: MutableList<Any> = ArrayList()
    private var adIndex = 1
    private val adapter by lazy {
        RecyclerViewAdapter()
    }
    private val adLoader by lazy {
        val builder = AdLoader.Builder(this, ADMOB_AD_UNIT_ID)
        builder.forUnifiedNativeAd { unifiedNativeAd ->
            if (isDestroyed) {
                unifiedNativeAd.destroy()
                return@forUnifiedNativeAd
            }
            println("===>>> ad loaderr > $adIndex $unifiedNativeAd")
            adapter.setAd(unifiedNativeAd, adIndex)
            val nextAdIndex = adIndex + 4
            if (items.size >= nextAdIndex) {
                loadAds()
                adIndex = nextAdIndex
            }
        }
        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                Toast.makeText(this@MainActivity, "Failed to load native ad: $errorCode",
                        Toast.LENGTH_SHORT).show()
            }
        }).build()
        adLoader
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addMenuItemsFromJson()

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) {
            loadAds()
        }

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        recycler_view.adapter = adapter
        adapter.setItems(items)
    }

    private fun loadAds() {
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun addMenuItemsFromJson() {
        try {
            val jsonDataString = readJsonDataFromFile()
            val menuItemsJsonArray = JSONArray(jsonDataString)
            for (i in 0 until menuItemsJsonArray.length()) {
                val menuItemObject = menuItemsJsonArray.getJSONObject(i)
                val menuItemName = menuItemObject.getString("name")
                val menuItemDescription = menuItemObject.getString("description")
                val menuItemPrice = menuItemObject.getString("price")
                val menuItemCategory = menuItemObject.getString("category")
                val menuItemImageName = menuItemObject.getString("photo")
                val menuItem = MenuItem(menuItemName, menuItemDescription, menuItemPrice,
                        menuItemCategory, menuItemImageName)
                items.add(menuItem)
            }
        } catch (exception: IOException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        }
    }

    @Throws(IOException::class)
    private fun readJsonDataFromFile(): String? {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonDataString: String? = null
            inputStream = resources.openRawResource(R.raw.menu_items_json)
            val bufferedReader = BufferedReader(
                    InputStreamReader(inputStream, "UTF-8"))
            while (bufferedReader.readLine().also { jsonDataString = it } != null) {
                builder.append(jsonDataString)
            }
        } finally {
            inputStream?.close()
        }
        return String(builder)
    }

    override fun onDestroy() {
        items.forEach {
            if (it is UnifiedNativeAd) it.destroy()
        }
        super.onDestroy()
    }
}
