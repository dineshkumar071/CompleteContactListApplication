package com.intern.internproject.base

import android.R
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.intern.internproject.respository.model.CLMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


open class CLBaseActivity : AppCompatActivity() {
    var layout: ConstraintLayout? = null
    private var progressBar: ProgressBar? = null

    /**show the progress bar */
    fun showProgressBar() {
        layout = ConstraintLayout(this)
        progressBar = ProgressBar(this, null, R.attr.progressBarStyleLarge)
        progressBar?.isIndeterminate = true
        progressBar?.indeterminateDrawable?.setColorFilter(
            Color.parseColor("#D81B60"),
            PorterDuff.Mode.MULTIPLY
        )
        progressBar?.visibility = View.VISIBLE
        val progressBarLayoutParams = ConstraintLayout.LayoutParams(
            100,
            100
        )
        val parentLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        progressBarLayoutParams.bottomToBottom = ConstraintSet.PARENT_ID
        progressBarLayoutParams.endToEnd = ConstraintSet.PARENT_ID
        progressBarLayoutParams.startToStart = ConstraintSet.PARENT_ID
        progressBarLayoutParams.topToTop = ConstraintSet.PARENT_ID
        layout?.addView(progressBar, progressBarLayoutParams)
        addContentView(layout, parentLayoutParams)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    /**hide the progress bar*/
    fun hideProgressBar() {
        if (layout != null) {
            layout?.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: CLMessage) {
        /* event fire here when you post event from other class or fragment */
    }
}