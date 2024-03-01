package com.example.expenses.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.expenses.Model
import com.example.expenses.Output
import com.example.expenses.R
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.expenses.fragments.MenuFragment


private const val ARG_NAME = "name"
private const val ARG_AMOUNT = "amount"
private const val ARG_DATE = "date"
private const val ARG_REPEAT = "repeat"
private const val ARG_TYPE = "type"

class AddFragment : Fragment() {

    private var name: String? = null
    private var amount: String? = null
    private var date: String? = null
    private var repeat: String? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            amount = it.getString(ARG_AMOUNT)
            date = it.getString(ARG_DATE)
            repeat = it.getString(ARG_REPEAT)
            type = it.getString(ARG_TYPE)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        val toolBar = view.findViewById<Toolbar>(R.id.topBarRecurExp)

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

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        val spinner: Spinner = v.findViewById(R.id.spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            v.context,
            R.array.repeat_type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        val button: Button = v.findViewById(R.id.addexp)
        button.setOnClickListener {
            val nameInput = v.findViewById<TextView>(R.id.name).text.toString()
            val amountInput = v.findViewById<TextView>(R.id.amount).text.toString()
            val dateInput = v.findViewById<TextView>(R.id.date).text.toString()
            val repeatInput = v.findViewById<TextView>(R.id.repeat).text.toString()
            val typeIn = spinner.selectedItem.toString()
            if (nameInput.isNotEmpty() && amountInput.isNotEmpty() && dateInput.isNotEmpty() && repeatInput.isNotEmpty() && typeIn.isNotEmpty()) {
                val addExpenses = Model(nameInput, amountInput, dateInput, repeatInput, typeIn)
                Output.out.add(addExpenses)
                Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object {

        fun newInstance(param1: String, param2: String, param3: String, param4: String, param5: String) =
            AddFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, param1)
                    putString(ARG_AMOUNT, param2)
                    putString(ARG_DATE, param3)
                    putString(ARG_REPEAT, param4)
                    putString(ARG_TYPE, param5)
                }
            }
    }
}