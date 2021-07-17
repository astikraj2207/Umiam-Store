package com.example.onlinestorekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_row.view.*
import kotlinx.android.synthetic.main.temporary_order_row.view.*

class TemporaryProductAdapter(var context:Context, var arrayList: ArrayList<TemporaryProduct>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var temporaryProductView = LayoutInflater.from(context).inflate(R.layout.temporary_order_row, parent, false)
        return TemporaryProductViewHolder(temporaryProductView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TemporaryProductViewHolder).initializeRowUIComponents(arrayList.get(position).id,
                                                                         arrayList.get(position).name,
                                                                         arrayList.get(position).price,
                                                                         arrayList.get(position).amount,
                                                                         arrayList.get(position).pictureName,
                                                                         position)

    }
    fun deleteItem(index: Int, id: Int){
        arrayList.removeAt(index)
        notifyDataSetChanged()
        val deleteURL = IP.ip+"OnlineStoreApp/decline_one_order.php?email=${Person.email}&product_id=${id.toString()}"
        var requestQ = Volley.newRequestQueue(context)
        var stringRequest = StringRequest(Request.Method.GET, deleteURL, Response.Listener {
            response ->
            Toast.makeText(context, "The items are deleted", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener {
            error ->
            val dialogBuilder= AlertDialog.Builder(context)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })
        requestQ.add(stringRequest)

    }

    inner class TemporaryProductViewHolder(pView: View):RecyclerView.ViewHolder(pView){
        fun initializeRowUIComponents(id:Int, name:String, price:Int, amount:Int, picName:String, position: Int){
            itemView.txtTempName.text = name
            itemView.txtTempPrice.text = price.toString()
            itemView.txtTempAmount.text = amount.toString()
            var picURL = IP.ip+"OnlineStoreApp/osimages/"
            picURL = picURL.replace(" ","%20")
            Picasso.get().load(picURL+picName).into(itemView.imgTemporaryProduct)
            itemView.btnDeleteOrder.setOnClickListener {
                deleteItem(position, id)
            }
        }
    }
}