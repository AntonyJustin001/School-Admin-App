package school.admin.app.screens.signIn

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.text.set
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import school.admin.app.R
import school.admin.app.dialog.CustomDialog
import school.admin.app.screens.home.HomeScreen
import school.admin.app.utils.USER_DETAILS
import school.admin.app.utils.loadScreen
import school.admin.app.utils.prefs
import tena.health.care.models.User

class SignInScreen : Fragment() {

    private lateinit var auth: FirebaseAuth
    lateinit var customDialog: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val emailEditText = view.findViewById<EditText>(R.id.etEmail)
        val passwordEditText = view.findViewById<EditText>(R.id.etPassword)
        val signInButton = view.findViewById<MaterialButton>(R.id.layoutSignIn)
        val progressBar = view.findViewById<LottieAnimationView>(R.id.progressBar)
        //val tvSignUp = view.findViewById<TextView>(R.id.tvSignUp)

        val fullText = resources.getText(R.string.dont_have_ac_sign_up)
        val spannableString = SpannableString(fullText)

        // Set the first link ("Click here")
        val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                //loadScreen(requireActivity(),SignUpScreen())
            }
        }
        val start1 = fullText.indexOf("SIGNUP")
        val end1 = start1 + "SIGNUP".length
        spannableString[start1, end1] = clickableSpan1
        spannableString[start1, end1] = ForegroundColorSpan(Color.BLUE)

        // Set the spannable string to the TextView
        //tvSignUp.text = spannableString
        //tvSignUp.movementMethod = android.text.method.LinkMovementMethod.getInstance()


        signInButton.setOnClickListener {
            val name = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            progressBar.visibility = View.VISIBLE

            if(!name.equals("")) {
                if(!password.equals("")) {

                    if(name == "admin" && password == "admin") {
                        //Store UserData in Local
                        val userData = User(
                            name = name,
                            emailId = ""
                        )
                        prefs.put(USER_DETAILS, Gson().toJson(userData))

                        //LoadHome Screen
                        loadScreen(requireActivity(), HomeScreen())
                        progressBar.visibility = View.GONE
                        Log.e("Test", "Sign-In Successful")
                    } else {
                        Log.e("Test", "Sign-In Failed")
                        progressBar.visibility = View.GONE
                        customDialog = CustomDialog(resources.getDrawable(R.drawable.ic_wrong),
                            resources.getString(R.string.error), "Invalid UserName and Password")
                        customDialog.show(childFragmentManager, "CustomDialog")
                    }

                } else {
                    customDialog = CustomDialog(resources.getDrawable(R.drawable.ic_wrong),
                        resources.getString(R.string.error), "Please Enter Password")
                    customDialog.show(childFragmentManager, "CustomDialog")
                    progressBar.visibility = View.GONE
                }
            } else {
                customDialog = CustomDialog(resources.getDrawable(R.drawable.ic_wrong),
                    resources.getString(R.string.error), "Please Enter UserName")
                customDialog.show(childFragmentManager, "CustomDialog")
                progressBar.visibility = View.GONE
            }

        }

    }

}