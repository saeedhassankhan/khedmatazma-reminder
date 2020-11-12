package com.example.khedmatazma_reminder.tasks

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.khedmatazma_reminder.DatabaseManager
import com.example.khedmatazma_reminder.G
import com.example.khedmatazma_reminder.R
import kotlinx.android.synthetic.main.activity_tasks.*
import kotlinx.android.synthetic.main.dialog_new_task.*
import java.util.ArrayList

class ActivityTasks : AppCompatActivity() {

    internal lateinit var adapterTasks: AdapterTasks
    var taskList = ArrayList<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        initViews()

        loadDbTasks()

        fab.setOnClickListener(){
            getNewTask(this)
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

    private fun getNewTask(activity: Activity) {

        var  dialog =  Dialog(activity);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_new_task);

        dialog.btnRegisterNewTask.setOnClickListener(){
            var title = dialog.edtTaskTitle.text.toString()
            var desc = dialog.edtTaskDesc.text.toString()
            var db = DatabaseManager()
            db.registerTask(G.getLogedInId() , title , desc)
            loadDbTasks()
            dialog.dismiss()
        }

        dialog.btnCancelNewTask.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show();
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