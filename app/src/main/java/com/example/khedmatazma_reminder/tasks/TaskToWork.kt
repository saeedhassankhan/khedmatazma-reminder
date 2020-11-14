package com.example.khedmatazma_reminder.tasks

import android.app.Activity
import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.khedmatazma_reminder.NotifyWork
import com.example.khedmatazma_reminder.NotifyWork.Companion.NOTIFICATION_ID
import com.example.khedmatazma_reminder.NotifyWork.Companion.NOTIFICATION_SUB
import com.example.khedmatazma_reminder.NotifyWork.Companion.NOTIFICATION_TITLE
import com.example.khedmatazma_reminder.R
import com.google.android.material.snackbar.Snackbar
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.activity_tasks.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TaskToWork {
    private lateinit var  activity : Activity

    fun stopTask(id : String , context: Context){
        WorkManager.getInstance(context).cancelUniqueWork(id)
    }


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
            val data = Data.Builder()
                    .putInt(NOTIFICATION_ID, task.id)
                    .putString(NOTIFICATION_TITLE , task.title)
                    .putString(NOTIFICATION_SUB , task.description)
                    .build()

            val delay = customTime - currentTime
            scheduleNotification(delay, data , task.id.toString())
            val titleNotificationSchedule = activity.getString(R.string.notification_schedule_title)
            val patternNotificationSchedule = activity.getString(R.string.notification_schedule_pattern)
            Snackbar.make(
                activity.coordinator_l,
                titleNotificationSchedule + " " + task.date + " " + task.time,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            val errorNotificationSchedule = activity.getString(R.string.notification_schedule_error)
            Snackbar.make(activity.coordinator_l , errorNotificationSchedule, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun scheduleNotification(delay: Long, data: Data , id : String) {
        val notificationWork = OneTimeWorkRequest.Builder(NotifyWork::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS).setInputData(data).build()

        val instanceWorkManager = WorkManager.getInstance(activity)

        //instanceWorkManager.enqueue(notificationWork)
        //instanceWorkManager.beginUniqueWork(NOTIFICATION_WORK, REPLACE, notificationWork).enqueue()

        instanceWorkManager.beginUniqueWork(
            id,
            ExistingWorkPolicy.REPLACE, notificationWork).enqueue()
    }

}