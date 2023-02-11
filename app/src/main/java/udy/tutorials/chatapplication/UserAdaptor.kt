package udy.tutorials.chatapplication
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context:Context,val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

// inflate our item to do xml file to actually get it as view
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val view:View=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }
  //take data from todos list and send to corresponding to view
    //holder to access view in IN userviewholder
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       val currentUser=userList[position]
        holder.textName.text=currentUser.uname
      // to open that activity
        holder.itemView.setOnClickListener{
            val intent = Intent(context,chatactivity::class.java)
            intent.putExtra("uname",currentUser.uname)
            intent.putExtra("uid",currentUser?.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
     return userList.size
    }
    class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
       val textName=itemView.findViewById<TextView>(R.id.txt_name)
    }
}