package com.bytedance.homework.homework6

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytedance.homework.R

class TodoListActivity : AppCompatActivity() {

    private var todoListView: RecyclerView? = null  //也可用by lazy延迟初始化
    private val dbHelper = MyDBHelper(this, "TodoList.db", 1)  //数据库OpenHelper
    private var db: SQLiteDatabase? = null  //声明数据库db

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()  //隐藏该页面的标题栏
        setContentView(R.layout.activity_to_do_list)
        todoListView = findViewById(R.id.rv_todoList)  //todoListView绑定RecyclerView
        todoListView?.layoutManager = LinearLayoutManager(this)  //todoListView采用线性布局管理器
        db = dbHelper.writableDatabase  //获取数据引用
        refreshTodoList()  //刷新TodoList列表
        findViewById<LinearLayout>(R.id.create_new_list).setOnClickListener {
            createTodo()  //点击事件，新建TodoList列表项并写入db数据库
        }
    }

    //新建TodoList列表项，并且往数据库写数据
    @SuppressLint("Recycle")
    private fun createTodo() {
        val cursor = (db?: dbHelper.writableDatabase).query("todolist", null, null, null, null, null, null, null)
        if(cursor.count == 24) {
            Toast.makeText(this, "TodoList列表数量已达上限~~~", Toast.LENGTH_SHORT).show()
        }else {
            val view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_new_todo, null)
            AlertDialog.Builder(this).apply {
                setTitle("新建列表")
                setView(view)
                setNegativeButton("取消") { dialog, which ->
                }
                setPositiveButton("创建列表") { p0, p1 ->
                    val title = view.findViewById<EditText>(R.id.et_dialog)?.text.toString()  //这个地方必须用view.findViewById, 不能直接find
                    if (title.isEmpty()) {
                        Toast.makeText(this@TodoListActivity, "列表标题不能为空！", Toast.LENGTH_SHORT).show()
                        createTodo()
                    } else {
                        Toast.makeText(this@TodoListActivity, "已添加TodoList列表: $title", Toast.LENGTH_SHORT).show()
                        val values = ContentValues().apply {
                            put("title", title)
                        }
                        db?.insert("todolist", null, values)  //添加values到db数据库的todolist表
                        refreshTodoList()
                    }
                }
                show()
            }
        }
    }

    //查询数据库的todolist表，并刷新TodoList列表
    @SuppressLint("NotifyDataSetChanged", "Range")
    private fun refreshTodoList() {
        //定义一个查询用的cursor游标
        val cursor = (db?: dbHelper.writableDatabase).query("todolist", null, null, null, null, null, null, null)
        val data = mutableListOf<String>()  //todoList的数据
        if (cursor.moveToFirst()) {  //将数据库的每一行数据都转换成一个JavaBean数据列表
            do {
                data.add(cursor.getString(cursor.getColumnIndex("title")))
            } while (cursor.moveToNext())
        }
        cursor.close()
        val adapter = TodoListAdapter(data)  //更新adapter
        todoListView?.adapter = adapter
        adapter.notifyDataSetChanged()  //adapter更新数据
        adapter.setOnItemClickListener(object: TodoListAdapter.OnItemClickListener {  //adapter设置点击事件
            override fun onItemClick(position: Int) {
                Toast.makeText(this@TodoListActivity, "You clicked No.$position item.", Toast.LENGTH_SHORT).show()
                skipTaskList(position)
            }
        })
        adapter.setOnItemLongClickListener(object : TodoListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) {
                Toast.makeText(this@TodoListActivity, "You long clicked No.$position item.", Toast.LENGTH_SHORT).show()
                renameOrDeleteTaskList(position)
            }
        })
    }

    @SuppressLint("Range")
    private fun renameOrDeleteTaskList(position: Int) {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_new_todo, null)
        AlertDialog.Builder(this@TodoListActivity).apply {
            setTitle("删除 Or 重命名？")
            setView(view)
            setNegativeButton("删除") { dialog, which ->
                if(position <= 3) {
                    Toast.makeText(this@TodoListActivity, "默认列表不能删除！", Toast.LENGTH_SHORT).show()
                }else {
                    val cursor = (db?: dbHelper.writableDatabase).query("todolist", null, null, null, null, null, null, null)
                    if (cursor.moveToFirst()) {
                        for(i in 0 until position) {
                            cursor.moveToNext()  //导航cursor
                        }
                    }
                    db?.delete("todolist", "title = ?", arrayOf(cursor.getString(cursor.getColumnIndex("title"))))
                    cursor.close()
                    db?.delete("tasklist", "is_$position = ?", arrayOf("yes"))  //删除列表内的对应所有task
                    refreshTodoList()
                    Toast.makeText(this@TodoListActivity, "删除成功！", Toast.LENGTH_SHORT).show()
                }
            }
            setPositiveButton("重命名") { p0, p1 ->
                val new_title = view.findViewById<EditText>(R.id.et_dialog)?.text.toString()  //这个地方必须用view.findViewById, 不能直接find
                if (new_title.isEmpty()) {
                    Toast.makeText(this@TodoListActivity, "列表标题不能为空！", Toast.LENGTH_SHORT).show()
                    renameOrDeleteTaskList(position)
                } else {
                    val cursor = (db?: dbHelper.writableDatabase).query("todolist", null, null, null, null, null, null, null)
                    if (cursor.moveToFirst()) {
                        for(i in 0 until position) {
                            cursor.moveToNext()
                        }
                    }
                    val values = ContentValues().apply { put("title", new_title) }
                    db?.update("todolist", values, "title = ?", arrayOf(cursor.getString(cursor.getColumnIndex("title"))))
                    cursor.close()
                    refreshTodoList()  //刷新todolist
                    Toast.makeText(this@TodoListActivity, "重命名成功！", Toast.LENGTH_SHORT).show()
                }
            }
            show()
        }
    }

    private fun skipTaskList(position: Int) {
        val myIntent = Intent(this@TodoListActivity, TaskListActivity::class.java)
        myIntent.putExtra("position", position)
        startActivity(myIntent)
    }
}