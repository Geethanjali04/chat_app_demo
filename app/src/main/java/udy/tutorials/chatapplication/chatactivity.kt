package udy.tutorials.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatactivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sentButton:ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<message>
    private lateinit var mRef:DatabaseReference

      var receiverRoom:String?=null
      var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)

        val uname =intent.getStringExtra("uname")
        val receiveruid=intent.getStringExtra("uid")

        val senderuid= FirebaseAuth.getInstance().currentUser?.uid

        mRef=FirebaseDatabase.getInstance().getReference()

        senderRoom = receiveruid + senderuid
        //just a comment
        receiverRoom =senderuid + receiveruid


        supportActionBar?.title = uname
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sentButton = findViewById(R.id.sentButton)
        messageList=ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        chatRecyclerView.layoutManager= LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter


        //message sent to db and then visible to diff users
        mRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()
                for(postSnapshot in snapshot.children)
                {
                    val msg=postSnapshot.getValue(message::class.java)
                    messageList.add(msg!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





        sentButton.setOnClickListener{
         //get what is written in msg box
            val msg=messageBox.text.toString()
            val msgobj=message(msg,senderuid)
            //to create another node of sender room
            mRef.child("chats").child(senderRoom!!).child("messages").push().setValue(msgobj).addOnSuccessListener{
                mRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(msgobj)
            }

           messageBox.setText("")

        }
    }
}