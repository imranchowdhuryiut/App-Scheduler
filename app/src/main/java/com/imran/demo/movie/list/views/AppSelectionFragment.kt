package com.imran.demo.movie.list.views

import android.app.AlertDialog
import android.app.PendingIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imran.demo.movie.list.R
import android.content.pm.PackageManager
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imran.demo.movie.list.AppScheduler
import com.imran.demo.movie.list.data.model.AppModel
import com.imran.demo.movie.list.databinding.FragmentAppSelectionBinding
import com.imran.demo.movie.list.views.adapters.AppListAdapter
import android.content.pm.ApplicationInfo
import com.imran.demo.movie.list.utils.OnItemClickCallback

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.imran.demo.movie.list.AppOpenerReceiver
import com.imran.demo.movie.list.viewmodels.AppViewModel
import java.util.*
import android.app.AlarmManager
import java.text.SimpleDateFormat


class AppSelectionFragment : Fragment(), OnItemClickCallback<AppModel> {

    private var _binding: FragmentAppSelectionBinding? = null

    private var mAppList = mutableListOf<AppModel>()

    private val mViewModel by viewModels<AppViewModel>()

    private val mAdapter = AppListAdapter(this)

    private var selectedAppsTimeList = mutableListOf<String>()

    private val dateFormat by lazy { SimpleDateFormat("hh:mm") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAppSelectionBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        mViewModel.getAllSavedAppList()
        getSelectedApps()
        getInstalledApps()
    }

    private fun getSelectedApps() {
        mViewModel.appList.observe(viewLifecycleOwner, {
            mAdapter.updateList(it)
            selectedAppsTimeList.addAll(it.map { app ->
                val date = Date(app.startTime ?: 0)
                dateFormat.format(date)
            })
        })
    }

    fun isUserApp(ai: ApplicationInfo): Boolean {
        val mask = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
        return ai.flags and mask == 0
    }

    private fun getInstalledApps() {
        val pm = activity?.packageManager
        pm?.let {
            val appList = mutableListOf<AppModel>()
            val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
            for (packageInfo in packages) {
                if (isUserApp(packageInfo)) {
                    val app = AppModel()
                    Log.d(AppScheduler.tag, "app name:${packageInfo.name}")
                    app.appName = pm.getApplicationLabel(packageInfo).toString()
                    app.appPackageName = packageInfo.packageName
                    appList.add(app)
                }
            }
            mAppList.addAll(appList)
            mAdapter.submitList(mAppList)
        }
    }

    private fun initViews() {
        _binding?.apply {
            layoutCustomToolbar.tvToolbarTitle.text = getString(R.string.select_application)
            layoutCustomToolbar.btnAdd.visibility = View.GONE
            layoutCustomToolbar.btnBack.visibility = View.VISIBLE
            layoutCustomToolbar.btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
            rvAppList.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvAppList.adapter = mAdapter
        }
    }

    override fun onClick(model: AppModel) {
        if (model.isSelected) {
            addApp(model)
        } else {
            removeApp(model)
        }
    }

    private fun removeApp(model: AppModel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure?")
            .setMessage("Do you want to remove app ${model.appName}")
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                mViewModel.delete(model)
                cancelAlarm(model)
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .show()
    }

    private fun cancelAlarm(model: AppModel) {
        val alarmMng = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AppOpenerReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(
            requireContext(),
            101,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmMng.cancel(alarmIntent)
    }

    private fun setAlarm(mCalender: Calendar, model: AppModel) {
        val alarmMg = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AppOpenerReceiver::class.java)
        intent.putExtra("PACKAGE_NAME", model.appPackageName)
        val alarmIntent = PendingIntent.getBroadcast(
            requireContext(),
            101,
            intent,
            0
        )
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = mCalender.timeInMillis
        alarmMg.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )

    }

    private fun addApp(model: AppModel) {
        val mCalender: Calendar = Calendar.getInstance()
        val hour: Int = mCalender.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mCalender.get(Calendar.MINUTE)
        val mTimePicker = TimePickerDialog(
            requireContext(), { _, selectedHour, selectedMinute ->
                mCalender.set(Calendar.HOUR_OF_DAY, selectedHour)
                mCalender.set(Calendar.MINUTE, selectedMinute)

                if (selectedAppsTimeList.contains(dateFormat.format(mCalender.time))) {
                    showMessage()
                } else {
                    model.startTime = mCalender.time.time
                    mViewModel.saveAppData(model)
                    setAlarm(mCalender, model)
                    findNavController().navigateUp()
                }
            }, hour, minute, false
        )

        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    private fun showMessage() {
        AlertDialog.Builder(requireContext())
            .setTitle("Failed")
            .setMessage("Another Application is scheduled at the same time.")
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}