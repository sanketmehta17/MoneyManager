package com.example.expenses.fragments

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenses.R
import com.example.expenses.adapter.RecyclerViewAdapter
import com.example.expenses.database.DBHandler
import com.example.mcproject.models.recordModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarReport)
        toolBar.setNavigationOnClickListener {

            val fragment: Fragment = MenuFragment()
            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mylist: ArrayList<recordModel>?
        val btnIncome =view.findViewById<Button>(R.id.btn_inc)
        val btnExpense =view.findViewById<Button>(R.id.btn_exp)
        val btntotalExpense =view.findViewById<TextView>(R.id.exp_total)
        val btntotalIncome =view.findViewById<TextView>(R.id.inc_total)
        val mytotal = readtotal()

        btnIncome.setOnClickListener {
            btntotalIncome.text = mytotal[0]
            btntotalExpense.text=""
            val recRecyclerViewUI: RecyclerView = view.findViewById(R.id.recycler)
            recRecyclerViewUI.layoutManager = LinearLayoutManager(activity)
            val recRecyclerViewAdapter = RecyclerViewAdapter()
            recRecyclerViewUI.adapter = recRecyclerViewAdapter
            mylist = storeDataInArrays("btnIncome")
            recRecyclerViewAdapter.setNotes(mylist!!)
        }

        btnExpense.setOnClickListener {
            btntotalIncome.text=""
            btntotalExpense.text=mytotal[1]
            val recRecyclerViewUI: RecyclerView = view.findViewById(R.id.recycler)
            recRecyclerViewUI.layoutManager = LinearLayoutManager(activity)
            val recRecyclerViewAdapter = RecyclerViewAdapter()
            recRecyclerViewUI.adapter = recRecyclerViewAdapter
            mylist = storeDataInArrays("btnExpense")
            recRecyclerViewAdapter.setNotes(mylist!!)
        }

    }

    private fun readtotal(): ArrayList<String> {
        var db = context?.let { DBHandler(it) }
        var mytotal = ArrayList<String>()
        if (db != null) {
            mytotal =  db.getTotal()
        }
        return mytotal
    }
    private fun storeDataInArrays(btn_type: String): ArrayList<recordModel>{
        val records = ArrayList<recordModel>()
        var db = context?.let { DBHandler(it) }
        var cursor: Cursor? = null
        if (btn_type == "btnIncome") {
            cursor = db?.readTransaction("Income")
        }
        else if(btn_type == "btnExpense"){
            cursor = db?.readTransaction("Expenses")
        }
        if (cursor != null) {
            if (cursor.count != 0) {
                while (cursor.moveToNext()) {
                    val tran_date = cursor.getString(0)
                    val item = cursor.getString(1)
                    val amt = cursor.getString(2)
                    val record = recordModel(tran_date, item, "$"+amt)
                    records.add(record)

                }
            }
        }
        return records
    }

}
