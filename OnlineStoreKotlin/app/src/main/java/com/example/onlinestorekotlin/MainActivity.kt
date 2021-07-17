package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_btnLogin.setOnClickListener {
            val loginURL = IP.ip+"OnlineStoreApp/login_app_user.php?email="+
                    activity_main_edtEmail.text.toString() + "&pass=" + activity_main_edtPassword.text.toString()
            val requestQ = Volley.newRequestQueue(this@MainActivity)
            val stringRequest = StringRequest(Request.Method.GET, loginURL, Response.Listener { 
                response ->
                if(response.equals("The user does exist")){
                    Person.email = activity_main_edtEmail.text.toString()

                    val roomURL = IP.ip+"OnlineStoreApp/get_room.php?email="+activity_main_edtEmail.text.toString()
                    val requestRoom = Volley.newRequestQueue(this@MainActivity)
                    val stringRoom = StringRequest(Request.Method.GET, roomURL, Response.Listener {
                        response ->
                        Person.room = response
                    }, Response.ErrorListener {
                        error ->
                        val dialogBuilder= AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Message")
                        dialogBuilder.setMessage(error.message)
                        dialogBuilder.create().show()
                    })

                    requestRoom.add(stringRoom)
                    var rm = Person.room
                    Log.d("roomTag", rm.toString())

                    Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()


                    if(Person.email=="admin@gmail.com"){
                        val intent = Intent(this@MainActivity, PeopleOrders::class.java)
                        startActivity(intent)
                    }else {

                        val homeIntent = Intent(this@MainActivity, HomeScreen::class.java)
                        startActivity(homeIntent)
                    }
                }else{
                    val dialogBuilder= AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(response)
                    dialogBuilder.create().show()
                }
            }, Response.ErrorListener { 
                error ->
//                val dialogBuilder= AlertDialog.Builder(this)
//                dialogBuilder.setTitle("Message")
//                dialogBuilder.setMessage(error.message)
//                dialogBuilder.create().show()
                Toast.makeText(this@MainActivity, "response", Toast.LENGTH_SHORT).show()
            })
            requestQ.add(stringRequest)
        }

        activity_main_btnSignUp.setOnClickListener {
            var signUpIntent = Intent(this@MainActivity, SignUpLayout::class.java)
            startActivity(signUpIntent)

        }
    }
}