package com.example.expenses.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.expenses.R
import com.example.expenses.database.DBHandler
import com.example.mcproject.models.User

/**
 * This class creates a user profile
 */

class CreateUserFragment: Fragment()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_user, container, false)
        val toolBar = view.findViewById<Toolbar>(R.id.topAppBarCreateUser)

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
        val username = view.findViewById<TextView>(R.id.username)
        val profile = view.findViewById<RadioGroup>(R.id.profile_radio)
        val btn = view.findViewById<Button>(R.id.btn_save)

        btn.setOnClickListener{
            var id: Int = profile.checkedRadioButtonId
            if(username.text.toString().isNotEmpty() && id != -1){
                val radio: RadioButton = view.findViewById(id)
                var user = User(username.text.toString(), radio.text.toString())
                var db = context?.let { it1 -> DBHandler(it1) }
                db?.insertRow(user)
                val fragment: Fragment = MenuFragment()
                val fragmentManager: FragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

            }
            else{
                Toast.makeText(context, "Fill all field", Toast.LENGTH_SHORT)}

        }
    }
}
