package com.bytedance.homework.homework6

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.homework.R

class TaskListActivity : AppCompatActivity() {

    private var num: Int = 0
    private var taskListView: RecyclerView? = null
    private var dbHelper = MyDBHelper(this, "TodoList.db", 1)  //数据库OpenHelper
    private var db: SQLiteDatabase? = null  //声明数据库d
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()  //隐藏该页面的标题栏
        setContentView(R.layout.activity_task_list)
        num = intent.getIntExtra("position", -1)  //获得当前tasklist编号，以查询对应数据
        taskListView = findViewById(R.id.rv_tasklist)  //绑定RecyclerView
        taskListView?.layoutManager = LinearLayoutManager(this)
        db = dbHelper.writableDatabase  //获取数据引用
        refreshTaskTitle()  //刷新task标题
        refreshTaskList()  //刷新task列表
        findViewById<ImageView>(R.id.create_new_task).setOnClickListener {
            createTask()  //点击事件，新建task并写入db数据库
            refreshTaskList()  //刷新tasklist列表
        }
    }

    private fun createTask() {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_new_task, null)
        AlertDialog.Builder(this).apply {
            setTitle("添加新任务")
            setView(view)
            setNegativeButton("取消") { dialog, which ->
            }
            setPositiveButton("创建任务") { p0, p1 ->
                val task = view.findViewById<EditText>(R.id.et_dialog)?.text.toString()  //这个地方必须用view.findViewById, 不能直接find
                if (task.isEmpty()) {
                    Toast.makeText(this@TaskListActivity, "输入不能为空！", Toast.LENGTH_SHORT).show()
                    createTask()
                } else if(checkTaskExistence(task)) {
                    Toast.makeText(this@TaskListActivity, "该任务已存在！", Toast.LENGTH_SHORT).show()
                    createTask()
                } else {
                    val values = ContentValues().apply {
                        put("task", task)
                        for (i in 0..23) {
                            if(i == 0 || i == num)  put("is_$i", "yes")
                            else   put("is_$i", "--")
                        }
                    }
                    db?.insert("tasklist", null, values)  //添加values到db数据库的tasklist表
                    refreshTaskList()
                    Toast.makeText(this@TaskListActivity, "添加任务成功！", Toast.LENGTH_SHORT).show()
                }
            }
            show()
        }
    }

    @SuppressLint("Range", "Recycle")
    private fun checkTaskExistence(task: String?): Boolean {
        val cursor = (db?: dbHelper.writableDatabase).query("tasklist", null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                if(task == cursor.getString(cursor.getColumnIndex("task"))) {
                    return true
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return false
    }

    @SuppressLint("Range")
    private fun refreshTaskTitle() {
        val cursor = (db?: dbHelper.writableDatabase).query("todolist", null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            for(i in 0 until num) {
                cursor.moveToNext()
            }
            val title = cursor.getString(cursor.getColumnIndex("title"))
            findViewById<TextView>(R.id.tv_tasklist_title).text = title
        }
        cursor.close()
    }

    @SuppressLint("Range", "NotifyDataSetChanged")
    private fun refreshTaskList() {
        val cursor = (db?: dbHelper.writableDatabase).query("tasklist", null, null, null, null, null, null, null)
        val data = mutableListOf<String>()  //tasklist的数据
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(cursor.getColumnIndex("is_$num")) == "yes") {
                    data.add(cursor.getString(cursor.getColumnIndex("task")))
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        val adapter = TaskListAdapter(data)
        taskListView?.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickListener(object: TaskListAdapter.OnItemClickListener {
            override fun onItemClick(holder: TaskListAdapter.ViewHolder) {
                Toast.makeText(this@TaskListActivity, "task item clicked", Toast.LENGTH_SHORT).show()
                editTask(holder)
            }
        })
        adapter.setOnItemLongClickListener(object : TaskListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(holder: TaskListAdapter.ViewHolder) {
                deleteTask(holder)
            }
        })
    }

    private fun editTask(holder: TaskListAdapter.ViewHolder) {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_new_task, null)
        AlertDialog.Builder(this).apply {
            setTitle("编辑任务")
            setView(view)
            setNegativeButton("取消") { dialog, which ->
            }
            setPositiveButton("确认更新") { p0, p1 ->
                val newTask = view.findViewById<EditText>(R.id.et_dialog)?.text.toString()  //这个地方必须用view.findViewById, 不能直接find
                if (newTask.isEmpty()) {
                    Toast.makeText(this@TaskListActivity, "输入不能为空！", Toast.LENGTH_SHORT).show()
                    editTask(holder)
                } else {
                    val values = ContentValues().apply {
                        put("task", newTask)
                        for (i in 0..23) {
                            if(i == 0 || i == num)  put("is_$i", "yes")
                            else   put("is_$i", "--")
                        }
                    }
                    db?.update("tasklist", values, "task = ?", arrayOf("${holder.task.text}"))  //当遍历到oldTask时，更新此数据
                    refreshTaskList()
                    Toast.makeText(this@TaskListActivity, "更新任务成功！", Toast.LENGTH_SHORT).show()
                }
            }
            show()
        }
    }

    private fun deleteTask(holder: TaskListAdapter.ViewHolder) {
        AlertDialog.Builder(this).apply {
            setTitle("确定删除吗？")
            setNegativeButton("取消") { dialog, which ->
            }
            setPositiveButton("确定删除") { p0, p1 ->
                    db?.delete("tasklist", "task = ?", arrayOf("${holder.task.text}"))  //当遍历到oldTask时，更新此数据
                    refreshTaskList()
                    Toast.makeText(this@TaskListActivity, "删除任务成功！", Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }
}