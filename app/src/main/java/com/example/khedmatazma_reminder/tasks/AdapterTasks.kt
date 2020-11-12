package com.example.khedmatazma_reminder.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.khedmatazma_reminder.R
import kotlinx.android.synthetic.main.struct_task.view.*

import java.util.ArrayList


/**
 * Created by saeed
 */

class AdapterTasks(
    private val context: Context, // Recyclerview will extend to
    //recyclerview adapter
    private val arrayList: ArrayList<Task>?
) : RecyclerView.Adapter<AdapterTasks.ViewHolder>() {

    internal var onScrollChanged: OnScrollChanged? = null


    init {

    }

    override fun getItemCount(): Int {
        return arrayList?.size ?: 0

    }

    override fun onBindViewHolder(hl: ViewHolder, position: Int) {
        val task = arrayList!![position]

        if (itemCount - 1 == position && onScrollChanged != null && itemCount > 1) {
            //just when we have an item more than default plus & arrive to end list
            onScrollChanged!!.onEndList()
        }

        hl.v.txtTaskDesc.text = task.description
        hl.v.txtTaskTitle.text = task.title


        val status = ""
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.struct_task, viewGroup, false)
        return ViewHolder(v)
    }
    //***********************************

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // View holder for gridview recycler view as we used in listview
        private val context: Context
        var  v : View

        init {
            context = view.context
            this.v = view
        }
    }

    fun setOnScrollChanged(onScrollChanged: OnScrollChanged): AdapterTasks {
        this.onScrollChanged = onScrollChanged
        return this
    }

    interface OnScrollChanged {
        fun onEndList()
    }

}