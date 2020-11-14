package com.example.khedmatazma_reminder.tasks

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.khedmatazma_reminder.*
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.activity_tasks.*
import kotlinx.android.synthetic.main.dialog_new_task.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ActivityTasks : AppCompatActivity()  {

    internal lateinit var adapterTasks: AdapterTasks
    var taskList = ArrayList<Task>()
    private lateinit var activity : Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()

        setContentView(R.layout.activity_tasks)

        initViews()

        loadDbTasks()

        activity = this

        fab.setOnClickListener{
            editTask(this  , null)
        }

        imgExitAccount.setOnClickListener{
            G.setLogedInId(0)
            startActivity(Intent(this , ActivitySplash::class.java))
            finish()
        }
    }


    private fun initViews() {
        rcyclTasks.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)//Here 2 is no. of columns to be displayed

        adapterTasks = AdapterTasks(baseContext, taskList).setOnScrollChanged(object : AdapterTasks.OnScrollChanged{
            override fun onEndList() {

            }
        }).setOnEditTask(object  : AdapterTasks.OnEditTask{
            override fun onEdit(task: Task) {
                editTask(activity , task)
            }

        })
        rcyclTasks.adapter = adapterTasks // set adapter on recyclerview
    }

    /**
     * if you dont have any time send items as ""
     */
    private fun editTask(activity: Activity, task : Task?) {
        var dialog = Dialog(activity)
        var mTask: Task


        if (task != null) {
            mTask = task
        } else {
            mTask = Task()
            val pCal = PersianCalendar()
            mTask.date = pCal.persianYear.toString() + "/" + (pCal.persianMonth + 1).toString() + "/" + pCal.persianDay.toString()
            mTask.time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        }

        mTask.fk_user = G.getLogedInId()

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_new_task)

        dialog.btnDialogDate.text = mTask.date
        dialog.btnDialogTime.text = mTask.time
        dialog.edtTaskDesc.setText(mTask.description)
        dialog.edtTaskTitle.setText(mTask.title)

        dialog.btnDialogTime.setOnClickListener {
            showTimePicker(mTask, object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
                    mTask.time = hourOfDay.toString() + ":" + minute
                    dialog.btnDialogTime.text = mTask.time
                }
            })
        }

        dialog.btnDialogDate.setOnClickListener {
            showDatePicker(mTask, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                    mTask.date = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
                    dialog.btnDialogDate.text = mTask.date
                }
            })
        }



        dialog.btnRegisterNewTask.setOnClickListener {
            mTask.title = dialog.edtTaskTitle.text.toString()
            mTask.description = dialog.edtTaskDesc.text.toString()
            var db = DatabaseManager()

            if (mTask.id == 0) {// 0 means this task is new and no need to update that on db
                mTask.id = db.registerTask(mTask)
            } else {
                db.updateTask(mTask)
            }

            val taskToWork = TaskToWork()
            taskToWork.setTask(mTask , this)
            loadDbTasks()
            dialog.dismiss()
        }

        dialog.btnCancelNewTask.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun showDatePicker(task : Task , pickerListener : DatePickerDialog.OnDateSetListener){
        val persianCalendar = PersianCalendar()
        val datePickerDialog =
                DatePickerDialog.newInstance(
                        pickerListener
                        ,
                        task.getYear().toInt(),
                        task.getMonth().toInt() -1 ,
                        task.getDay().toInt()
                )
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog")
    }

    fun showTimePicker(task : Task , listener : TimePickerDialog.OnTimeSetListener){
        val pCal = PersianCalendar()
        val tmPicker = TimePickerDialog.newInstance(listener , task.getHour().toInt() , task.getMinute().toInt() , false)
        tmPicker.show(getFragmentManager() , "TimePicker" )
    }

    fun loadDbTasks(){
        var db = DatabaseManager();
        var list = db.getTasks(G.getLogedInId())
        taskList.clear()
        for ( i in list)
            taskList.add(0, i)
        adapterTasks.notifyDataSetChanged()
    }


}