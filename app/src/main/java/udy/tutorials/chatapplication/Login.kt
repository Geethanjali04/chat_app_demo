package udy.tutorials.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class Login : AppCompatActivity() {
    private lateinit var edituname: EditText
    private lateinit var editpswrd: EditText
    private lateinit var loginbtn: Button
    private lateinit var signupbtn: Button
    private lateinit var myAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        edituname = findViewById(R.id.email)
        editpswrd = findViewById(R.id.password)
        loginbtn = findViewById(R.id.loginbtn)
        signupbtn = findViewById(R.id.signupbtn)
        myAuth = FirebaseAuth.getInstance()
        signupbtn.setOnClickListener {
            var intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
        loginbtn.setOnClickListener {
            val email = edituname.text.toString()
            val pass = editpswrd.text.toString()
            login(email, pass)
        }
    }

    private fun login(email: String, pass: String) {
        myAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "User not exist create new", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}