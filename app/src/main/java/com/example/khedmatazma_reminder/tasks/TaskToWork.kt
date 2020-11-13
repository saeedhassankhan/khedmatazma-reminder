package com.example.khedmatazma_reminder.tasks

import android.app.Activity
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.khedmatazma_reminder.NotifyWork
import com.example.khedmatazma_reminder.R
import com.google.android.material.snackbar.Snackbar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.activity_tasks.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TaskToWork {
    private lateinit var  activity : Activity
    fun setTask(task : Task , activity : Activity){
        this.activity = activity

        val gDate = task.getGregorianDate()
        val splitedDate = gDate.split("/")

        val customCalendar = Calendar.getInstance()
        customCalendar.set(
            splitedDate[0].toInt(), splitedDate[1].toInt() - 1, splitedDate[2].toInt(), task.getHour().toInt(), task.getMinute().toInt(), 0
        )
        val customTime = customCalendar.timeInMillis
        val currentTime = System.currentTimeMillis()
        if (customTime > currentTime) {
            val data = Data.Builder().putInt(task.id.toString(), 0).build()
            val delay = customTime - currentTime
            scheduleNotification(delay, data)

            val titleNotificationSchedule = activity.getString(R.string.notification_schedule_title)
            val patternNotificationSchedule = activity.getString(R.string.notification_schedule_pattern)
            Snackbar.make(
                activity.coordinator_l ,
                titleNotificationSchedule + SimpleDateFormat(
                    patternNotificationSchedule, Locale.getDefault()
                ).format(customCalendar.time).toString(),
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            val errorNotificationSchedule = activity.getString(R.string.notification_schedule_error)
            Snackbar.make(activity.coordinator_l , errorNotificationSchedule, Snackbar.LENGTH_LONG).show()
        }

    }

    private fun scheduleNotification(delay: Long, data: Data) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(activity)

        //instanceWorkManager.enqueue(notificationWork)
        //instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, REPLACE, notificationWork).enqueue()

        instanceWorkManager.beginUniqueWork(
            System.currentTimeMillis().toString(),
            ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
    }

}