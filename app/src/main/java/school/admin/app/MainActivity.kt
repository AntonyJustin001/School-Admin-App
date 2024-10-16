package school.admin.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import school.admin.app.data.preference.SharedPreferencesHelper
import school.admin.app.utils.prefs

class MainActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = SharedPreferencesHelper(this)
        db = FirebaseFirestore.getInstance()

        if (savedInstanceState == null) {
            loadFragment(school.admin.app.screens.splash.SplashScreen())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed() // If no fragments are in the back stack, exit the activity
        }
    }

}
