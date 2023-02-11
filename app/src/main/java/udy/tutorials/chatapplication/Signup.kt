package udy.tutorials.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private  lateinit var edituname: EditText
    private lateinit var editpswrd: EditText
    private lateinit var editemail:EditText
    private lateinit var signupbtn: Button
    private lateinit var myAuth: FirebaseAuth
    private lateinit var mDref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        myAuth= FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_signup)
        editemail = findViewById(R.id.email)
        edituname=findViewById(R.id.name)
        editpswrd = findViewById(R.id.password)
        signupbtn = findViewById(R.id.signupbtn)
        signupbtn.setOnClickListener{
                val email=editemail.text.toString()
                val pass=editpswrd.text.toString()
                val uname=edituname.text.toString()

            signup(uname,email,pass)
            }


    }
    private fun signup(uname:String,email:String,pass:String){
        myAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    print("geetha")
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(uname,email,myAuth.currentUser?.uid!!)
                    val intent = Intent(this@Signup,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Signup,"Error Occured",Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun addUserToDatabase(uname:String,email:String,uid:String)
    {
        mDref=FirebaseDatabase.getInstance().getReference()
        mDref.child("user").child(uid).setValue(User(uname,email,uid))

    }
}