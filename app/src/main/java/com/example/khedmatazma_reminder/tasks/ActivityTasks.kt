package com.example.khedmatazma_reminder.tasks

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.khedmatazma_reminder.DatabaseManager
import com.example.khedmatazma_reminder.G
import com.example.khedmatazma_reminder.R
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.activity_tasks.*
import kotlinx.android.synthetic.main.dialog_new_task.*
import java.text.SimpleDateFormat
import java.util.*


class ActivityTasks : AppCompatActivity()  {

    internal lateinit var adapterTasks: AdapterTasks
    var taskList = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        initViews()

        loadDbTasks()

        fab.setOnClickListener{
            var task = Task();
            task.date = "1399/5/5"
            task.time = "5:15"

            getNewTask(this  , task)

        }
    }


    private fun initViews() {
        rcyclTasks.layoutManager =
                StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)//Here 2 is no. of columns to be displayed

        adapterTasks = AdapterTasks(baseContext, taskList).setOnScrollChanged(object : AdapterTasks.OnScrollChanged{
            override fun onEndList() {

            }
        })
        rcyclTasks.adapter = adapterTasks // set adapter on recyclerview
    }

    /**
     * if you dont have any time send items as ""
     */
    private fun getNewTask(activity: Activity , task : Task?) {
        var dialog = Dialog(activity)
        var mTask : Task

        if(task != null){
            mTask = task
        }else{
            mTask = Task()
            val pCal = PersianCalendar()
            mTask.date = pCal.persianYear.toString() + "/" + pCal.persianMonth.toString() + "/" + pCal.persianDay.toString()
            mTask.time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_new_task)

        dialog.btnDialogDate.text = mTask.date
        dialog.btnDialogTime.text = mTask.time

        dialog.btnDialogTime.setOnClickListener {
            showTimePicker(mTask , object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
                    mTask.title = hourOfDay.toString() + ":" + minute
                    dialog.btnDialogTime.text = mTask.title
                }
            })
        }
        dialog.btnDialogDate.setOnClickListener {
            showDatePicker(mTask , object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                    mTask.date = year.toString() + "/" + (monthOfYear + 1) + "/" + dayOfMonth
                    dialog.btnDialogDate.text = mTask.date
                }
            })
        }



        dialog.btnRegisterNewTask.setOnClickListener{
            var title = dialog.edtTaskTitle.text.toString()
            var desc = dialog.edtTaskDesc.text.toString()
            var db = DatabaseManager()

            //TODO( if task id exist update it else register new to db)
            db.registerTask(G.getLogedInId(), title, desc)

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
                        task.getMonth().toInt(),
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
        var list = db.getTasks()
        taskList.clear()
        for ( i in list)
            taskList.add(i)
        adapterTasks.notifyDataSetChanged()
    }

}