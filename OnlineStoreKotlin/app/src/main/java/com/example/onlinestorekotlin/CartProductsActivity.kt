package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*
import kotlinx.android.synthetic.main.activity_fetch_products.*

class CartProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductsURL = "http://192.168.1.38/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<TemporaryProduct>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET, cartProductsURL, null, Response.Listener {
            response ->
            for (productJOIndex in 0.until(response.length())){
                cartProductsList.add( TemporaryProduct( response.getJSONObject(productJOIndex).getInt("id"),
                        response.getJSONObject(productJOIndex).getString("name"),
                        response.getJSONObject(productJOIndex).getInt("price"),
                        response.getJSONObject(productJOIndex).getInt("amount"),
                        response.getJSONObject(productJOIndex).getString("picture")))
            }

            val tpAdapter = TemporaryProductAdapter(this@CartProductsActivity, cartProductsList)
            temporaryOrderRV.layoutManager = LinearLayoutManager(this@CartProductsActivity)
            temporaryOrderRV.adapter = tpAdapter


        }, Response.ErrorListener {
            error ->
            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })
        requestQ.add(jsonAR)
        }
        override fun onCreateOptionsMenu(menu: Menu?) : Boolean{
            menuInflater.inflate(R.menu.cart_menu, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {

        if (menuItem.itemId == R.id.continueShoppingItem) {

            var intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)

        } else if (menuItem.itemId == R.id.declineOrderItem) {

            var deleteUrl = "http://192.168.1.38//OnlineStoreApp/decline_order.php?email=${Person.email}"
            var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET, deleteUrl, Response.Listener{
                response ->

                var intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)

            }, Response.ErrorListener {
                error ->
                val dialogBuilder= AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()


            })

            requestQ.add(stringRequest)
        }else if(menuItem.itemId == R.id.verifyOrderItem){

            //println(cartProductsList.size)
            //Log.d("myTag", "This is my message");

            var cartProductsURL2 = "http://192.168.1.38/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"

            var requestQu = Volley.newRequestQueue(this@CartProductsActivity)
            var jsonAR2 = JsonArrayRequest(Request.Method.GET, cartProductsURL2, null, Response.Listener {
                response ->
                for (productJOIndex in 0.until(response.length())){

                    var URL = "http://192.168.1.38/OnlineStoreApp/insert_paid_items.php?email=${Person.email}" +
                            "&room=${Person.room}" +
                            "&product_id=${response.getJSONObject(productJOIndex).getInt("id")}" +
                            "&amount=${response.getJSONObject(productJOIndex).getInt("amount")}" +
                            "&brand=${response.getJSONObject(productJOIndex).getString("brand")}"

                    val q = Volley.newRequestQueue(this@CartProductsActivity)
                    val stringRequest = StringRequest(Request.Method.GET, URL, Response.Listener {
                        response ->

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





            var verifyOrderURL = "http://192.168.1.38//OnlineStoreApp/verify_order.php?email=${Person.email}"
            var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
            var stringRequest = StringRequest(Request.Method.GET, verifyOrderURL, Response.Listener {
                response ->


                var intent = Intent(this, FinalizeShoppingActivity::class.java)
                Toast.makeText(this, "Orders Verified", Toast.LENGTH_LONG).show()
                intent.putExtra("LATEST_INVOICE_NUMBER", response)
                startActivity(intent)


            }, Response.ErrorListener { error ->
                val dialogBuilder= AlertDialog.Builder(this)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })



            requestQ.add(stringRequest)

        }

        return super.onOptionsItemSelected(menuItem)
    }

}
