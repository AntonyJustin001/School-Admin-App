package school.admin.app

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import school.admin.app.screens.students.StudentDeleteBottomSheet
import school.admin.app.screens.students.StudentDetailsScreen
import school.admin.app.utils.loadImageFromUrl
import school.admin.app.utils.loadScreen
import tena.health.care.models.Student

class StudentsListAdapter(val context: Context, val activity: FragmentActivity, val parentFragmentManager: FragmentManager, val fragment:Fragment,
                          private val items: List<Student>) :
    RecyclerView.Adapter<StudentsListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rc_item_student, parent, false)
        return ItemViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val studentProfile: ImageView = itemView.findViewById(R.id.ivStudentPic)
        private val studentName: TextView = itemView.findViewById(R.id.tvStudentName)
        private val studentSection: TextView = itemView.findViewById(R.id.tvStudentSection)
        private val studentDelete: ImageView = itemView.findViewById(R.id.ivDeleteStudent)
        private val studentEdit: ImageView = itemView.findViewById(R.id.ivEditStudent)
        private val studentHolder: MaterialCardView = itemView.findViewById(R.id.studentHolder)

        fun bind(student: Student) {
            loadImageFromUrl(context,student.profilePic,studentProfile)
            studentName.text = student.name
            studentSection.text = student.section
            studentDelete.setOnClickListener {
                deleteJobDialog(student)
            }
            studentEdit.setOnClickListener {
                loadScreen(activity, StudentDetailAddEdit(student.name),"Type","Edit")
            }
            studentHolder.setOnClickListener {
                loadScreen(activity, StudentDetailsScreen(student.name))
            }
        }

        fun deleteJobDialog(student: Student) {
            val bottomSheetFragment = StudentDeleteBottomSheet(student)
            bottomSheetFragment.setTargetFragment(fragment,0)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }
}