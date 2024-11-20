package com.gulsengunes.checkmate.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gulsengunes.checkmate.model.Task

class SharedPreferencesHelper(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveTasks(tasks: List<Task>) {
        val json = gson.toJson(tasks)
        sharedPreferences.edit().putString("TASKS_KEY", json).apply()
    }

    fun loadTasks(): List<Task> {
        val json = sharedPreferences.getString("TASKS_KEY", "[]")
        val type = object : TypeToken<List<Task>>() {}.type
        return gson.fromJson(json, type)
    }
}