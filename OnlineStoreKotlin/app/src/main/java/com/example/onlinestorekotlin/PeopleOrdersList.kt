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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_people_orders_list.*

class PeopleOrdersList : AppCompatActivity() {
    var selectedRoom : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_orders_list)

         selectedRoom = intent.getStringExtra("ROOM").toString()
        var peopleOrdersURL = "http://192.168.1.38/OnlineStoreApp/fetch_paid_items.php?room=${selectedRoom}"
        var peopleProductsList = ArrayList<TemporaryProduct>()
        var requestQ = Volley.newRequestQueue(this@PeopleOrdersList)
        var jsonAR = JsonArrayRequest(Request.Method.GET, peopleOrdersURL, null, Response.Listener {
            response ->
            for (productJOIndex in 0.until(response.length())){
                peopleProductsList.add( TemporaryProduct( response.getJSONObject(productJOIndex).getInt("id"),
                        response.getJSONObject(productJOIndex).getString("name"),
                        response.getJSONObject(productJOIndex).getInt("price"),
                        response.getJSONObject(productJOIndex).getInt("amount"),
                        response.getJSONObject(productJOIndex).getString("picture")))
            }

            val tppAdapter = TemporaryProductAdapter(this@PeopleOrdersList, peopleProductsList)
            peopleOrderRV.layoutManager = LinearLayoutManager(this@PeopleOrdersList)
            peopleOrderRV.adapter = tppAdapter



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
        menuInflater.inflate(R.menu.delete_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {

       if (menuItem.itemId == R.id.declineAllOrders) {
//           var intent = Intent(this, PeopleOrders::class.java)
//           startActivity(intent)
            var deleteUrl = "http://192.168.1.38//OnlineStoreApp/delete_paid_items.php?room=${selectedRoom}"
            var requestQ = Volley.newRequestQueue(this@PeopleOrdersList)
            var stringRequest = StringRequest(Request.Method.GET, deleteUrl, Response.Listener{
                response ->

                var intent = Intent(this, PeopleOrders::class.java)
                startActivity(intent)

            }, Response.ErrorListener {
                error ->
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