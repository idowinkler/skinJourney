package com.example.skinJourney

import android.app.Activity
import android.view.View
import android.widget.FrameLayout

object ProgressBarHandler {
    private var progressBarView: FrameLayout? = null

    fun init(activity: Activity) {
        progressBarView = activity.findViewById(R.id.loadingOverlay)
    }

    fun show() {
        progressBarView?.visibility = View.VISIBLE
    }

    fun hide() {
        progressBarView?.visibility = View.GONE
    }
}
