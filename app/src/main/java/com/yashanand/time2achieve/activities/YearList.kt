package com.yashanand.time2achieve.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.firestore.FirebaseFirestore
import com.yashanand.time2achieve.R
import com.yashanand.time2achieve.adapters.YearListAdapter

class YearList : AppCompatActivity() {

    private var re_view: RecyclerView? = null
    private var LayoutManger: LinearLayoutManager? = null
    private var yearQuestionlist: ArrayList<String>? = null
    private var YearAdapter: YearListAdapter? = null
    lateinit var ExamType: String
    lateinit var branch_Type: String
    var images: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_year_list)

        SupportActionBar()
        all_prevoius_year_QA()
        re_view = findViewById(R.id.year_listView)
        yearQuestionlist = ArrayList()

        LoadBannerAd()

    }

    private fun all_prevoius_year_QA() {

        try {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("time2achieve").document(branch_Type)
                .collection(ExamType).document("YearList")
            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val doc = task.result
                    if (doc != null) {
                        if (doc.exists()) {
                            images = doc.getString("Image").toString()
                            val count: Long = doc.get("count") as Long
                            for (i in count downTo 1) {
                                val exam = doc.getString(ExamType + i)
                                if (exam != null) {
                                    yearQuestionlist?.add(exam)
                                }
                            }
                        }
                    }

                    LayoutManger = LinearLayoutManager(this)
                    re_view?.layoutManager = LayoutManger
                    re_view?.setHasFixedSize(true)
                    re_view?.addItemDecoration(  //line decorate
                        DividerItemDecoration(
                            re_view?.context,
                            (LayoutManger as LinearLayoutManager).orientation
                        )
                    )

                    if (yearQuestionlist!!.isEmpty()) {
                        val re_nodata = findViewById<RelativeLayout>(R.id.re_nodata)
                        re_nodata.visibility = View.VISIBLE
                    } else {
                        YearAdapter =
                            yearQuestionlist?.let {
                                images?.let { it1 ->
                                    YearListAdapter(
                                        this,
                                        it,
                                        it1,
                                        branch_Type,
                                        ExamType
                                    )
                                }
                            }
                    }
                    re_view?.adapter = YearAdapter
                    findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE

                } else {
                    Log.d("noexits", "Cached get failed: ", task.exception)
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
            ExamType = intent.getStringExtra("position")
            branch_Type = intent.getStringExtra("branch_type")
            supportActionBar?.title = "$ExamType".toUpperCase()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}