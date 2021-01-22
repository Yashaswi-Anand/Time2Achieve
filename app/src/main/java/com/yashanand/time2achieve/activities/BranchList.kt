package com.yashanand.time2achieve.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.yashanand.time2achieve.R
import com.yashanand.time2achieve.adapters.BranchAdapter

class BranchList : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManger: LinearLayoutManager? = null
    private var listOfExam: ArrayList<String>? = null
    private var branchAdapter: BranchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch_list)

        SupportActionBar()
        recyclerView = findViewById(R.id.listRecycler)
        listOfExam = ArrayList()
        upload()
    }


    private fun upload() {
        try {
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("time2achieve").document("branch")

            docRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val doc = task.result
                    if (doc != null) {
                        if (doc.exists()) {
                            val count: Long = doc.get("count") as Long

                            for (i in 1..count) {
                                val branch = doc.getString("branch$i")
                                if (branch != null) {
                                    listOfExam!!.add(branch)
                                }
                            }
                        }
                    }
                    gridLayoutManger = GridLayoutManager(this, 2)
                    recyclerView?.layoutManager = gridLayoutManger
                    recyclerView?.setHasFixedSize(true)

                    branchAdapter =
                        BranchAdapter(
                            this,
                            listOfExam!!
                        )
                    recyclerView?.adapter = branchAdapter
                    findViewById<ProgressBar>(R.id.progressbar).visibility = View.GONE

                } else {
                    Log.d("noexits", "Cached get failed: ", task.exception)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                DialogAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun DialogAbout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("About")
        val about =
            "This application is very-very useful for all aspirants from the technical field.\n" +
                    "Branch: CSE & IT,EE,CE,ME and more.\n" +
                    "You will find a very organized system of all previous year question papers separated year-wise/exam name wise.\n" +
                    "Exam: Gate, ISRO, ESE and more\n" +
                    "This app provides you support to avoid the haphazardness of wandering for question papers.\n" +
                    "Don't worry for previous year papers\n" +
                    "Everything you want is available here at single platform T2A.\n"
        builder.setMessage(about)
        builder.setPositiveButton("CLOSE") { dialogInterface, which ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun SupportActionBar() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = " Time 2 Achieve"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}