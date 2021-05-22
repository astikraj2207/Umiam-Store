package com.example.onlinestorekotlin

import android.app.VoiceInteractor
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.sign_up_layout.*

class SignUpLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_layout)

        sign_up_layout_btnSignUp.setOnClickListener {
            if(sign_up_layout_edtPassword.text.toString().equals(sign_up_layout_edtConfirmPassword.text.toString())){

                val signUpURL = "http://192.168.1.38/OnlineStoreApp/join_new_user.php?email="+
                        sign_up_layout_edtEmail.text.toString()+"&username="+
                        sign_up_layout_edtUsername.text.toString()+"&pass="+
                        sign_up_layout_edtPassword.text.toString()+"&room="+
                        sign_up_layout_edtRoomNo.text.toString()+"&phone="+
                        sign_up_layout_edtPhoneNo.text.toString()
                val requestQ = Volley.newRequestQueue(this@SignUpLayout)
                val stringRequest = StringRequest(Request.Method.GET, signUpURL, Response.Listener { response ->

                    if(response.equals("A user with this Email Address already exists")){
                        val dialogBuilder= AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Message")
                        dialogBuilder.setMessage(response)
                        dialogBuilder.create().show()
                    }else{
                        Person.email = sign_up_layout_edtEmail.text.toString()
                        Person.room = sign_up_layout_edtRoomNo.text.toString()
//                        var s = sign_up_layout_edtPhoneNo.text.toString()
//                        Person.phone = Integer.parseInt(s)
                        Toast.makeText(this@SignUpLayout, response, Toast.LENGTH_SHORT).show()

                        val homeIntent = Intent(this@SignUpLayout, HomeScreen::class.java)
                        startActivity(homeIntent)
                    }

                }, Response.ErrorListener {
                    error ->
//                    val dialogBuilder= AlertDialog.Builder(this)
//                    dialogBuilder.setTitle("Message")
//                    dialogBuilder.setMessage(error.message)
//                    dialogBuilder.create().show()
                })
                requestQ.add(stringRequest)
            }
            else{
                val dialogBuilder= AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage("Password Mismatch")
                dialogBuilder.create().show()
            }
        }

        sign_up_layout_btnLogin.setOnClickListener {

            finish()

        }
    }
}