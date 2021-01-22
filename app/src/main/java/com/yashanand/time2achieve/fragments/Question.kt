package com.yashanand.time2achieve.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import com.yashanand.time2achieve.R
import java.io.File

class Question(val Q: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val pdf = view.findViewById<PDFView>(R.id.pdfViewQ)
        FileLoader.with(activity)
            .load(Q, false)
            .fromDirectory("PDF", FileLoader.DIR_INTERNAL)
            .asFile(object : FileRequestListener<File> {
                override fun onLoad(
                    request: FileLoadRequest?,
                    response: FileResponse<File>?
                ) {
                    val pdfFile = response!!.body
                    pdf.fromFile(pdfFile).password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onDraw { canvas, pageWidth, pageHeight, displayedPage ->
                            //enter your code
                        }
                        .onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->
                            //enter your code
                        }
                        .onPageChange { page, pageCount ->
                            //enter your code
                        }
                        .onPageError { page, t ->
                            Toast.makeText(
                                activity,
                                "Error while opening page: $page",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        .onTap { false }
                        .onRender { nbPages, pageWidth, pageHeight ->
                            view.findViewById<PDFView>(R.id.pdfViewQ).fitToWidth()
                        }
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.RED)
                        .load()
                }

                override fun onError(request: FileLoadRequest?, t: Throwable?) {
                    if (t != null) {
                        view.findViewById<TextView>(R.id.noDataQ).visibility = View.VISIBLE
                    }
                }
            })



        return view
    }


}