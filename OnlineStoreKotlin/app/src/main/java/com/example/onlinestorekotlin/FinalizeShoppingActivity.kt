package com.example.onlinestorekotlin

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_finalize_shopping.*
import java.util.ArrayList

class FinalizeShoppingActivity : AppCompatActivity() {
    lateinit var amountEt: EditText
    lateinit var noteEt: EditText
    lateinit var nameEt: TextView
    lateinit var upiIdEt: EditText
    lateinit var send: Button

    internal val UPI_PAYMENT = 0
    var ttPrice:Long = 0
    var inv = ""
    var B = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)
        inv = intent.getStringExtra("LATEST_INVOICE_NUMBER").toString()
        B = intent.getStringExtra("BRAND").toString()
        var calculateTotalPriceUrl = IP.ip+"OnlineStoreApp/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"
        var requestQ = Volley.newRequestQueue(this@FinalizeShoppingActivity)
        var stringRequest = StringRequest(Request.Method.GET, calculateTotalPriceUrl, Response.Listener { response ->

            //btnPaymentProcessing.text = "Pay $$response via Paypal Now!"
            var ans : String = "Rs. "
            ans+=response
            totalMoney.setText(ans)
            ttPrice = response.toLong()

        }, Response.ErrorListener { error ->


        })
        requestQ.add(stringRequest)







        initializeViews()

        send.setOnClickListener {
            //Getting the values from the EditTexts
            val amount = ttPrice
            val note = "Hello"
            val name = nameEt.text.toString()
            val upiId = upiIdEt.text.toString()
            payUsingUpi(amount.toString(), upiId, name, note)
        }
        var cartProductsURL2 = IP.ip+"OnlineStoreApp/fetch_paid_order.php?invoice_num=${inv.toInt()}"

        var requestQu = Volley.newRequestQueue(this@FinalizeShoppingActivity)
        var jsonAR2 = JsonArrayRequest(Request.Method.GET, cartProductsURL2, null, Response.Listener {
            response ->
            var a = response.length()
            Log.d("myTag", a.toString())
            for (productJOIndex in 0.until(response.length())){

                var URL = IP.ip+"OnlineStoreApp/insert_paid_items.php?email=${Person.email}" +
                        "&room=${Person.room}" +
                        "&product_id=${response.getJSONObject(productJOIndex).getInt("product_id")}" +
                        "&amount=${response.getJSONObject(productJOIndex).getInt("amount")}" +
                        "&brand=${B}"


                val q = Volley.newRequestQueue(this@FinalizeShoppingActivity)
                val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener {
                    response ->
                    Log.d("myTag", "This is my message3")

                }, Response.ErrorListener { error ->
                    val dialogBuilder= AlertDialog.Builder(this)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(error.message)
                    dialogBuilder.create().show()

                })
                q.add(stringRequest)
            }

        }, Response.ErrorListener {
            error ->
            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })
        requestQu.add(jsonAR2)
    }

    internal fun initializeViews() {
        send = findViewById(R.id.send)

        //noteEt = findViewById(R.id.note)
        nameEt = findViewById(R.id.name)
        upiIdEt = findViewById(R.id.upi_id)
    }


    fun payUsingUpi(amount: String, upiId: String, name: String, note: String) {

        val uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build()


        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        // will always show a dialog to user to choose an app
        val chooser = Intent.createChooser(upiPayIntent, "Pay with")

        // check if intent resolves
        if (null != chooser.resolveActivity(packageManager)) {
            startActivityForResult(chooser, UPI_PAYMENT)
        } else {
            Toast.makeText(this@FinalizeShoppingActivity, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            UPI_PAYMENT -> if (Activity.RESULT_OK == resultCode || resultCode == 11) {
                if (data != null) {
                    val trxt = data.getStringExtra("response")
                    Log.d("UPI", "onActivityResult: $trxt")
                    val dataList = ArrayList<String>()
                    dataList.add(trxt.toString())
                    upiPaymentDataOperation(dataList)
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null")
                    val dataList = ArrayList<String>()
                    dataList.add("nothing")
                    upiPaymentDataOperation(dataList)
                }
            } else {
                Log.d("UPI", "onActivityResult: " + "Return data is null") //when user simply back without payment
                val dataList = ArrayList<String>()
                dataList.add("nothing")
                upiPaymentDataOperation(dataList)
            }
        }
    }

    private fun upiPaymentDataOperation(data: ArrayList<String>) {
        if (isConnectionAvailable(this@FinalizeShoppingActivity)) {
            var str: String? = data[0]
            Log.d("UPIPAY", "upiPaymentDataOperation: " + str!!)
            var paymentCancel = ""
            if (str == null) str = "discard"
            var status = ""
            var approvalRefNo = ""
            val response = str.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in response.indices) {
                val equalStr = response[i].split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (equalStr.size >= 2) {
                    if (equalStr[0].toLowerCase() == "Status".toLowerCase()) {
                        status = equalStr[1].toLowerCase()
                    } else if (equalStr[0].toLowerCase() == "ApprovalRefNo".toLowerCase() || equalStr[0].toLowerCase() == "txnRef".toLowerCase()) {
                        approvalRefNo = equalStr[1]
                    }
                } else {
                    paymentCancel = "Payment cancelled by user."
                }
            }

            if (status == "success") {
                //Code to handle successful transaction here.

                var intent = Intent(this, ThankYou::class.java)
                startActivity(intent)


                Toast.makeText(this@FinalizeShoppingActivity, "Transaction successful.", Toast.LENGTH_SHORT).show()
                Log.d("UPI", "responseStr: $approvalRefNo")
            } else if ("Payment cancelled by user." == paymentCancel) {
                Toast.makeText(this@FinalizeShoppingActivity, "Payment cancelled by user.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@FinalizeShoppingActivity, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@FinalizeShoppingActivity, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val netInfo = connectivityManager.activeNetworkInfo
                if (netInfo != null && netInfo.isConnected
                        && netInfo.isConnectedOrConnecting
                        && netInfo.isAvailable) {
                    return true
                }
            }
            return false
        }
    }
}