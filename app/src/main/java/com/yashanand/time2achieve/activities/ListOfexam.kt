package com.yashanand.time2achieve.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.firestore.FirebaseFirestore
import com.yashanand.time2achieve.R
import com.yashanand.time2achieve.adapters.LIstOfExamAdapter
import com.yashanand.time2achieve.model.ExamList

class ListOfexam : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManger: LinearLayoutManager? = null
    private var listOfExam: ArrayList<ExamList>? = null
    private var ProgTypeAdapter: LIstOfExamAdapter? = null

    var ExamType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ofexam)

        SupportActionBar()
        recyclerView = findViewById(R.id.listOfExamRecycler)
        listOfExam = ArrayList()
        upload()
        LoadBannerAd()
    }

    private fun upload() {

        try {
            val db = FirebaseFirestore.getInstance()
            val docRef = ExamType?.let { db.collection("time2achieve").document(it) }

            docRef?.get()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Document found in the offline cache
                    val doc = task.result
                    Log.d("exist", "Cached document data: ${doc?.data}")
                    if (doc != null) {
                        if (doc.exists()) {
                            val count: Long = doc.get("count") as Long

                            for (i in 1..count) {
                                val exams = doc.getString(ExamType + i)
                                val images = doc.getString("Image$i")
                                if (exams != null) {
                                    listOfExam!!.add(
                                        ExamList(
                                            exams,
                                            images
                                        )
                                    )
                                }
                            }
                        }
                    }
                    gridLayoutManger = LinearLayoutManager(this)
                    recyclerView?.layoutManager = gridLayoutManger
                    recyclerView?.setHasFixedSize(true)
                    ProgTypeAdapter =
                        ExamType?.let {
                            LIstOfExamAdapter(
                                this,
                                listOfExam!!,
                                it
                            )
                        }
                    recyclerView?.adapter = ProgTypeAdapter
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
            supportActionBar?.title = "$ExamType".toUpperCase()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}