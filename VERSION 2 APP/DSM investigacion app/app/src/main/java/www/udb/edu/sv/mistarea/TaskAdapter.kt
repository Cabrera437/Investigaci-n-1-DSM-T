package www.udb.edu.sv.mistarea

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class TaskAdapter(context: Context, private val tasks: List<Task>) :
    ArrayAdapter<Task>(context, 0, tasks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val task = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)

        val taskTitle = view.findViewById<TextView>(R.id.taskTitle)
        val taskCheckbox = view.findViewById<CheckBox>(R.id.taskCheckbox)

        taskTitle.text = task?.title
        taskCheckbox.isChecked = task?.isCompleted ?: false

        taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            task?.isSelected = isChecked
        }

        return view
    }
}
