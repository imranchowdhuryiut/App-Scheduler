package com.imran.demo.movie.list.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imran.demo.movie.list.R
import com.imran.demo.movie.list.data.model.AppModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_app.view.*
import kotlinx.android.synthetic.main.item_app.view.imgAppIcon
import kotlinx.android.synthetic.main.item_app.view.tvAppName
import kotlinx.android.synthetic.main.item_app_scheduled.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Imran Chowdhury on 2/5/2022.
 */
class ScheduledAppListAdapter(): ListAdapter<AppModel, ScheduledAppViewHolder>(AppItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledAppViewHolder {
        return ScheduledAppViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ScheduledAppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}


class ScheduledAppViewHolder(
    override val containerView: View
) : LayoutContainer, RecyclerView.ViewHolder(containerView) {

    private val dateFormat by lazy { SimpleDateFormat("hh:mm") }

    fun bind(item: AppModel?) {
        Glide.with(containerView.context)
            .load(item?.appPackageName?.let {
                containerView.context.packageManager.getApplicationIcon(
                    it
                )
            })
            .into(containerView.imgAppIcon)

        containerView.tvAppName.text = item?.appName
        val date = Date(item?.startTime ?: 0)
        containerView.tvTime.text = dateFormat.format(date)
    }



    companion object {
        fun createViewHolder(parent: ViewGroup): ScheduledAppViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.item_app_scheduled,
                parent,
                false
            )
            return ScheduledAppViewHolder(itemView)
        }
    }

}