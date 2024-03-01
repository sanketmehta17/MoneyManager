package com.example.expenses.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expenses.R
import com.example.mcproject.models.recordModel

class RecyclerViewAdapter (): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    private var tran_list = emptyList<recordModel>()

    inner class MyViewHolder(notesListItemView: View?) :
        RecyclerView.ViewHolder(notesListItemView!!) {
        val dateTextView: TextView? = notesListItemView?.findViewById<TextView>(R.id.tran_date)
        val itemTextView: TextView? = notesListItemView?.findViewById<TextView>(R.id.tran_item)
        val amountTextView: TextView? = notesListItemView?.findViewById<TextView>(R.id.tran_amt)
        var tranPosition = 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val noteListItemView: View =
            layoutInflater.inflate(R.layout.fragment_record_list, parent, false)
        return MyViewHolder(noteListItemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val tranx: recordModel = tran_list[position]
        holder.dateTextView?.text = tranx.tran_date
        holder.itemTextView?.text = tranx.item
        holder.amountTextView?.text = tranx.amount
        holder.tranPosition = position
    }

    override fun getItemCount(): Int {
        return tran_list.size
    }

    fun setNotes(listTran: ArrayList<recordModel>) {
        this.tran_list = listTran
        notifyDataSetChanged()
    }
}