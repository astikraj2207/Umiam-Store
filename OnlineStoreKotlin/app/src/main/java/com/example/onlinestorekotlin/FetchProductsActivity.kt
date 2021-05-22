package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_products.*

class FetchProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_products)

        val selectedBrand = intent.getStringExtra("BRAND")

        var productsList = ArrayList<Product>()
        val productsURL = "http://192.168.1.38/OnlineStoreApp/fetch_products.php?brand=$selectedBrand"
        val requestQ = Volley.newRequestQueue(this@FetchProductsActivity)
        val jsonAR = JsonArrayRequest(Request.Method.GET, productsURL, null, Response.Listener {
            response ->
            for (productJOIndex in 0.until(response.length())){
                productsList.add( Product( response.getJSONObject(productJOIndex).getInt("id"),
                                           response.getJSONObject(productJOIndex).getString("name"),
                                           response.getJSONObject(productJOIndex).getInt("price"),
                                           response.getJSONObject(productJOIndex).getString("picture")))
            }
            val pAdapter = ProductAdapter(this@FetchProductsActivity, productsList)
            productsRV.layoutManager = LinearLayoutManager(this@FetchProductsActivity)
            productsRV.adapter = pAdapter

        }, Response.ErrorListener {
            error ->
//            val dialogBuilder= AlertDialog.Builder(this)
//            dialogBuilder.setTitle("Message")
//            dialogBuilder.setMessage(error.message)
//            dialogBuilder.create().show()
        })
        requestQ.add(jsonAR)
    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean{
        menuInflater.inflate(R.menu.go_to_cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {

        if (menuItem.itemId == R.id.goToCart) {

            var intent = Intent(this, CartProductsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(menuItem)
    }
}