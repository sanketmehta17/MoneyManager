package com.example.expenses.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.expenses.database.DBHandler
import com.example.expenses.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * the class displays the records of income and expenses
 * stored in the SQLite database using Recyclerview
 * Use the [IncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_income, container, false)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarIncome)
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = view.findViewById<TextView>(R.id.description_text)
        val amount = view.findViewById<TextView>(R.id.amount_text)
        val user = view.findViewById<TextView>(R.id.user)
        val rad = view.findViewById<RadioGroup>(R.id.group_radio)

        val btn = view.findViewById<Button>(R.id.btn_Save)
        btn.setOnClickListener {
            // Get the checked radio button id from radio group
            var id: Int = rad.checkedRadioButtonId
            if (id != -1 && item.text.toString().isNotEmpty() && amount.text.toString()
                    .isNotEmpty() ) {
                // If any radio button checked from radio group
                // Get the instance of radio button using id
                val radio: RadioButton = view.findViewById(id)
                var db = context?.let { it1 -> DBHandler(it1) }
                db?.readTrnx(item.text.toString(), radio.text.toString(),
                    amount.text.toString().toDouble(),user.text.toString())

            } else {
                // If no radio button checked in this radio group
                Toast.makeText(
                    context, "Transaction Type not selected",
                    Toast.LENGTH_SHORT
                ).show()
            }

            item.text = ""
            amount.text = ""
            user.text = ""
            item.requestFocus()
        }
    }

}