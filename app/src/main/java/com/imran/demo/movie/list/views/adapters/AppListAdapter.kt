package com.imran.demo.movie.list.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imran.demo.movie.list.R
import com.imran.demo.movie.list.data.model.AppModel
import com.imran.demo.movie.list.utils.OnItemClickCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_app.view.*
import kotlinx.android.synthetic.main.item_app.view.imgAdd
import kotlinx.android.synthetic.main.item_app.view.imgAppIcon
import kotlinx.android.synthetic.main.item_app.view.tvAppName
import kotlinx.android.synthetic.main.item_app_scheduled.view.*

/**
 * Created by Md. Imran Chowdhury on 8/1/2021.
 */
class AppListAdapter(
    private var mCallback: OnItemClickCallback<AppModel>? = null
) : ListAdapter<AppModel, AppViewHolder>(AppItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder.createViewHolder(parent, mCallback)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    fun updateList(list: List<AppModel>) {
        val packageNameList = list.map {
            it.appPackageName
        }.toList()
        for (app in currentList) {
            if (packageNameList.contains(app.appPackageName)) {
                app.isSelected = true
                notifyItemChanged(currentList.indexOf(app))
            }
        }
    }

}

class AppViewHolder(
    override val containerView: View,
    private val mCallback: OnItemClickCallback<AppModel>? = null
) : LayoutContainer, RecyclerView.ViewHolder(containerView) {

    private var mModel: AppModel? = null

    init {
        containerView.imgAdd.setOnClickListener {
            mModel?.isSelected = mModel?.isSelected != true
            mModel?.let { it1 -> mCallback?.onClick(it1) }
        }
    }

    fun bindView(item: AppModel?) {
        mModel = item
        Glide.with(containerView.context)
            .load(item?.appPackageName?.let {
                containerView.context.packageManager.getApplicationIcon(
                    it
                )
            })
            .into(containerView.imgAppIcon)

        containerView.tvAppName.text = item?.appName

        if (item?.isSelected == true) {
            containerView.imgAdd.setImageResource(R.drawable.ic_baseline_check_circle_24)
        } else {
            containerView.imgAdd.setImageResource(R.drawable.ic_baseline_add_circle_24)
        }

    }

    companion object {
        fun createViewHolder(
            parent: ViewGroup,
            listener: OnItemClickCallback<AppModel>? = null
        ): AppViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
            return AppViewHolder(itemView, listener)
        }
    }

}

@Suppress("ReplaceCallWithBinaryOperator")
object AppItemDiffCallback : DiffUtil.ItemCallback<AppModel>() {
    override fun areItemsTheSame(oldItem: AppModel, newItem: AppModel): Boolean =
        oldItem.appPackageName == newItem.appPackageName

    override fun areContentsTheSame(oldItem: AppModel, newItem: AppModel): Boolean =
        oldItem.equals(newItem)

}