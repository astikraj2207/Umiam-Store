package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_people_orders.*

class PeopleOrders : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_orders)

        var peopleURL = "http://192.168.1.38/OnlineStoreApp/fetch_people.php"
        var peopleList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@PeopleOrders)
        var jsonAR = JsonArrayRequest(Request.Method.GET, peopleURL, null, Response.Listener {
            response ->
            for (jsonObject in 0.until(response.length())){
                peopleList.add(response.getJSONObject(jsonObject).getString("room"))
            }
            var peopleListAdapter = ArrayAdapter(this@PeopleOrders, R.layout.brand_item_text_view, peopleList)
            peopleListview.adapter = peopleListAdapter

        }, Response.ErrorListener { error ->
            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })
        requestQ.add(jsonAR)

        peopleListview.setOnItemClickListener { adapterView, view, i, l ->
            val tappedPeople = peopleList.get(i)
            val intent = Intent(this@PeopleOrders, PeopleOrdersList::class.java)
            intent.putExtra("ROOM", tappedPeople)
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?) : Boolean{
        menuInflater.inflate(R.menu.details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {

        if (menuItem.itemId == R.id.logout) {

            var intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this@PeopleOrders, "You are successfully logged Out!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        else if(menuItem.itemId == R.id.contactUs){
            var intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(menuItem)
    }
}