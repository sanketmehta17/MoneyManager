package com.example.expenses.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.expenses.R
import com.example.expenses.ui.home.AddFragment
import com.example.expenses.Output

private const val ARG_NAME = "name"
private const val ARG_AMOUNT = "amount"
private const val ARG_DATE = "date"
private const val ARG_REPEAT = "repeat"
private const val ARG_TYPE = "type"

class ExpensesFragment : Fragment() {
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView: ListView = view.findViewById(R.id.recurring)
        listView.adapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_list_item_1,
            Output.out
        )
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