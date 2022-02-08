package com.imran.demo.movie.list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by Imran Chowdhury on 2/6/2022.
 */
class AppOpenerReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName = intent?.extras?.getString("PACKAGE_NAME") ?: ""
        val launchIntent = context?.packageManager?.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            context.startActivity(launchIntent)
        }
    }
}