package com.yashanand.time2achieve.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.yashanand.time2achieve.R
import com.yashanand.time2achieve.fragments.Answer
import com.yashanand.time2achieve.fragments.Question

class ScrollingActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var ExamType: String
    lateinit var branch_Type: String
    lateinit var YearNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        SupportActionBar()
        LoadBannerAd()
        upload()
    }

    private fun upload() {

        try {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("time2achieve").document(branch_Type)
                .collection(ExamType).document(YearNumber)

            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Document found in the offline cache
                    val doc = task.result
                    if (doc != null) {
                        var pdfQ = doc.getString("Question").toString()
                        var pdfA = doc.getString("Answer").toString()
                        Log.d("QA=", "Q: $pdfQ A:$pdfA")
                        val adapter =
                            MyAdapter(this, supportFragmentManager, tabLayout.tabCount, pdfQ, pdfA)
                        viewPager.adapter = adapter
                        findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE

                        viewPager.addOnPageChangeListener(
                            TabLayout.TabLayoutOnPageChangeListener(
                                tabLayout
                            )
                        )
                        tabLayout.addOnTabSelectedListener(object :
                            TabLayout.OnTabSelectedListener {
                            override fun onTabSelected(tab: TabLayout.Tab) {
                                viewPager.currentItem = tab.position
                            }

                            override fun onTabUnselected(tab: TabLayout.Tab) {}
                            override fun onTabReselected(tab: TabLayout.Tab) {}
                        })
                    }

                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun LoadBannerAd() {

        val adView = findViewById<View>(R.id.adView) as AdView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                val toastMessage: String = ""
                //Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
                onAdLoaded()
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                val toastMessage: String = ""
                //   Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                val toastMessage: String = ""
                //Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onAdClicked() {
                super.onAdClicked()
                val toastMessage: String = ""
                // Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                val toastMessage: String = ""
                //Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onAdLeftApplication() {
                super.onAdLeftApplication()
                val toastMessage: String = ""
                //Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun SupportActionBar() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (intent != null) {
            YearNumber = intent.getStringExtra("YearNumber")
            ExamType = intent.getStringExtra("examtype")
            branch_Type = intent.getStringExtra("branchtype")
        }


        findViewById<TextView>(R.id.tool_text).text = "$ExamType Question/Answer $YearNumber"
        findViewById<TextView>(R.id.tool_text).isSelected
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("QUESTION"))
        tabLayout.addTab(tabLayout.newTab().setText("ANSWER"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

@Suppress("DEPRECATION")
internal class MyAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    var PdfQ: String,
    var PdfA: String
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Question(PdfQ)
            }
            1 -> {
                Answer(PdfA)
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}


