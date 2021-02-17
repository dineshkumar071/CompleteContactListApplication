package com.intern.internproject.web_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intern.internproject.R
import kotlinx.android.synthetic.main.cl_activity_web_view.*

class CLWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cl_activity_web_view)
        title = "Terms and Conditions"
        wv_mallow.loadUrl("https://www.mallow-tech.com/pages/contact.html")
    }
}
