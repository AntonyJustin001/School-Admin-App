package school.admin.app.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.google.firebase.firestore.FirebaseFirestore
import school.admin.app.R
import school.admin.app.StudentsListAdapter
import school.admin.app.screens.signIn.SignInScreen
import school.admin.app.screens.students.StudentListScreen
import school.admin.app.screens.teachers.TeacherListScreen
import school.admin.app.screens.videos.ShowAllRecordedVideos
import school.admin.app.utils.loadScreen
import tena.health.care.models.RecordedVideo
import tena.health.care.models.Student
import tena.health.care.models.Teacher

class HomeScreen : Fragment() {

    private lateinit var cvStudents: MaterialCardView
    private lateinit var cvLessons: MaterialCardView
    private lateinit var cvNotifications: MaterialCardView
    private lateinit var cvTeachers: MaterialCardView

    private lateinit var tvStudentCount: TextView
    private lateinit var tvTeacherCount: TextView
    private lateinit var tvLessonCount: TextView
    private lateinit var tvNotificationCount: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cvStudents = view.findViewById(R.id.cvStudents)
        cvLessons = view.findViewById(R.id.cvLessons)
        cvNotifications = view.findViewById(R.id.cvNotifications)
        tvStudentCount = view.findViewById(R.id.tvStudentCount)
        tvLessonCount = view.findViewById(R.id.tvLessonCount)
        tvTeacherCount = view.findViewById(R.id.tvTeachersCount)
        tvNotificationCount = view.findViewById(R.id.tvNotificationCount)
        cvTeachers =  view.findViewById(R.id.cvTeachers)

        cvStudents.setOnClickListener {
            loadScreen(requireActivity(), StudentListScreen())
        }
        cvLessons.setOnClickListener {
            loadScreen(requireActivity(), ShowAllRecordedVideos())
        }
        cvNotifications.setOnClickListener {

        }
        cvTeachers.setOnClickListener {
            loadScreen(requireActivity(), TeacherListScreen())
        }

        getAllStudents { students ->
            students.forEach {
                Log.e("Students","Student - $it")
            }
            tvStudentCount.text = "${students.size}"
        }

        getAllLessons { lessons ->
            val confirmedVideos = lessons.toMutableList()
            confirmedVideos.removeIf { it.status == "Deleted" }
            lessons.forEach {
                Log.e("Lessons","Lesson - $it")
            }
            tvLessonCount.text = "${confirmedVideos.size}"
        }

        getAllTeachers { teachers ->
            teachers.forEach {
                Log.e("tvTeachersCount","Student - $it")
            }
            tvTeacherCount.text = "${teachers.size}"
        }

    }

    fun getAllStudents(onProductsRetrieved: (List<Student>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("student")
            .get()
            .addOnSuccessListener { result ->
                val studentList = mutableListOf<Student>()
                for (document in result) {
                    val student = document.toObject(Student::class.java)
                    studentList.add(student)
                }
                onProductsRetrieved(studentList)
            }
            .addOnFailureListener { e ->
                // Handle the error
                println("Error getting products: $e")
            }
    }

    fun getAllLessons(onProductsRetrieved: (List<RecordedVideo>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("videos")
            .get()
            .addOnSuccessListener { result ->
                val videos = mutableListOf<RecordedVideo>()
                for (document in result) {
                    val video = document.toObject(RecordedVideo::class.java)
                    videos.add(video)
                }
                onProductsRetrieved(videos)
            }
            .addOnFailureListener { e ->
                // Handle the error
                println("Error getting products: $e")
            }
    }

    fun getAllTeachers(onProductsRetrieved: (List<Teacher>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("teacher")
            .get()
            .addOnSuccessListener { result ->
                val teachers = mutableListOf<Teacher>()
                for (document in result) {
                    val teacher = document.toObject(Teacher::class.java)
                    teachers.add(teacher)
                }
                onProductsRetrieved(teachers)
            }
            .addOnFailureListener { e ->
                // Handle the error
                println("Error getting products: $e")
            }
    }

}