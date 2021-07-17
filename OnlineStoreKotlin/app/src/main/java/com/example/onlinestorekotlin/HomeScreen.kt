package com.example.onlinestorekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        var brandsURL = IP.ip+"OnlineStoreApp/fetch_brands.php"
        var brandsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@HomeScreen)
        var jsonAR = JsonArrayRequest(Request.Method.GET, brandsURL, null, Response.Listener {
            response ->
            for (jsonObject in 0.until(response.length())){
                brandsList.add(response.getJSONObject(jsonObject).getString("brand"))
            }
            var brandsListAdapter = ArrayAdapter(this@HomeScreen, R.layout.brand_item_text_view, brandsList)
            brandsListview.adapter = brandsListAdapter

        }, Response.ErrorListener { error ->
            val dialogBuilder= AlertDialog.Builder(this)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()

        })
        requestQ.add(jsonAR)

        brandsListview.setOnItemClickListener { adapterView, view, i, l ->
            val tappedBrand = brandsList.get(i)
            val intent = Intent(this@HomeScreen, FetchProductsActivity::class.java)
            intent.putExtra("BRAND", tappedBrand)
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
            Toast.makeText(this@HomeScreen, "You are successfully logged Out!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        else if(menuItem.itemId == R.id.contactUs){
            var intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(menuItem)
    }
}