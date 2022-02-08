package com.imran.demo.movie.list.data.model

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Imran Chowdhury on 2/3/2022.
 */
@Entity(tableName = "app_item")
data class AppModel(
    @PrimaryKey (autoGenerate = true)
    var id: Int? = null,
    var appName: String? = null,
    var appPackageName: String? = null,
    var isSelected: Boolean = false,
    var startTime: Long? = null
)
