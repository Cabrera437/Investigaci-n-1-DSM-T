package www.udb.edu.sv.mistarea

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextTask: EditText
    private lateinit var buttonAddTask: Button
    private lateinit var buttonDeleteTask: Button
    private lateinit var listViewTasks: ListView

    private val taskList = mutableListOf<Task>()
    private lateinit var adapter: TaskAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("tasks_prefs", Context.MODE_PRIVATE)

        editTextTask = findViewById(R.id.NuevaTarea)
        buttonAddTask = findViewById(R.id.buttonTarea)
        buttonDeleteTask = findViewById(R.id.EliminarTarea)
        listViewTasks = findViewById(R.id.lista)

        adapter = TaskAdapter(this, taskList)
        listViewTasks.adapter = adapter

        buttonAddTask.setOnClickListener {
            val taskTitle = editTextTask.text.toString()
            if (taskTitle.isNotEmpty()) {
                val newTask = Task(taskTitle, false)
                taskList.add(newTask)
                editTextTask.text.clear()
                adapter.notifyDataSetChanged()
                saveTasks()
            } else {
                Toast.makeText(this, "Por favor, ingresa una tarea", Toast.LENGTH_SHORT).show()
            }
        }

        buttonDeleteTask.setOnClickListener {
            val itemsToRemove = taskList.filter { it.isSelected }.toMutableList()

            if (itemsToRemove.isNotEmpty()) {
                taskList.removeAll(itemsToRemove)
                adapter.notifyDataSetChanged()
                saveTasks()
            } else {
                Toast.makeText(this, "Seleccione las tareas a eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        loadTasks()
    }

    private fun saveTasks() {
        val editor = sharedPreferences.edit()
        val taskSet = taskList.map { "${it.title}:${it.isCompleted}" }.toSet()
        editor.putStringSet("tasks", taskSet)
        editor.apply()
    }

    private fun loadTasks() {
        val taskSet = sharedPreferences.getStringSet("tasks", setOf())
        if (taskSet != null) {
            taskList.clear()
            for (taskString in taskSet) {
                val parts = taskString.split(":")
                if (parts.size == 2) {
                    taskList.add(Task(parts[0], parts[1].toBoolean()))
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}
