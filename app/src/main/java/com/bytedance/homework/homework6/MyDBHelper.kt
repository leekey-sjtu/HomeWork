package com.bytedance.homework.homework6

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf

class MyDBHelper(private val context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version) {

    private val createTodoList = "create table todolist(" +
            "id integer primary key autoincrement," +
            "title text)"
    private val createTaskList = "create table tasklist(" +
            "id integer primary key autoincrement," +
            "task text," +
            "is_0 text," + "is_1 text," + "is_2 text," + "is_3 text," +
            "is_4 text," + "is_5 text," + "is_6 text," + "is_7 text," +
            "is_8 text," + "is_9 text," + "is_10 text," + "is_11 text," +
            "is_12 text," + "is_13 text," + "is_14 text," + "is_15 text," +
            "is_16 text," + "is_17 text," + "is_18 text," + "is_19 text," +
            "is_20 text," + "is_21 text," + "is_22 text," + "is_23 text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTodoList)
        db?.execSQL(createTaskList)
        init(db)
    }

    private fun init(db: SQLiteDatabase?) {
        var values = ContentValues().apply { put("title", "全部任务") }
        db?.insert("todolist", null, values)
        values = ContentValues().apply { put("title", "星标") }
        db?.insert("todolist", null, values)
        values = ContentValues().apply { put("title", "已完成") }
        db?.insert("todolist", null, values)
        values = ContentValues().apply { put("title", "未完成") }
        db?.insert("todolist", null, values)
        values = ContentValues().apply {
            put("task", "Welcome! 这是第一条任务")
            for (i in 0..23) {
                if(i == 0)  put("is_$i", "yes")
                else put("is_$i", "--")
            }
        }
        db?.insert("tasklist", null, values)
        values = ContentValues().apply {
            put("task", "Welcome! 这是第一条星标")
            for (i in 0..23) {
                if(i == 0 || i == 1)  put("is_$i", "yes")
                else put("is_$i", "--")
            }
        }
        db?.insert("tasklist", null, values)
        values = ContentValues().apply {
            put("task", "Welcome! 这是第一条已完成")
            for (i in 0..23) {
                if(i == 0 || i == 2)  put("is_$i", "yes")
                else put("is_$i", "--")
            }
        }
        db?.insert("tasklist", null, values)
        values = ContentValues().apply {
            put("task", "Welcome! 这是第一条未完成")
            for (i in 0..23) {
                if(i == 0 || i == 3)  put("is_$i", "yes")
                else put("is_$i", "--")
            }
        }
        db?.insert("tasklist", null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        if (oldVersion <= 1) {
//            db?.execSQL(createTaskList_3)
//        }
//        if (oldVersion <= 2) {
//            db?.execSQL(createTaskList_4)
//        }
    }
}