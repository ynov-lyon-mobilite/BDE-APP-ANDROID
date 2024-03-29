package com.example.ynov_lyon_bde.ui.screens.profil

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ynov_lyon_bde.data.model.Event
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.EventType
import com.example.ynov_lyon_bde.data.model.ImageType
import com.example.ynov_lyon_bde.domain.services.RedirectConnectService
import com.example.ynov_lyon_bde.ui.screens.event.CardDescriptionDirections
import com.example.ynov_lyon_bde.domain.services.SharedPreferencesService
import com.example.ynov_lyon_bde.ui.screens.connection.LoginActivity
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.view.*
import kotlinx.android.synthetic.main.fragment_personal_informations_user.*

class ProfileFragment : Fragment() {

    //Fake data
    private val data = listOf(
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
        Event("1", "Test", "05/02/2021", EventType.DEFAULTVALUE, ImageType.DEFAULTVALUE, "5 Rue michel", "Voici une petite description", 0),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //REDIRECT CONNECT (if user current isn't connect)
        val redirectService = RedirectConnectService()
        context?.let { activity?.let { it1 -> redirectService.redirect(it, it1, RedirectConnectService.TypeAlertDialog.PROFIL) } }


        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // Get text, filiere and email value
        val name = view.findViewById<TextView>(R.id.name)
        val filiere = view.findViewById<TextView>(R.id.filiere)
        val email = view.findViewById<TextView>(R.id.email)
        // Get infos current user
        val sharedPreferencesService = SharedPreferencesService()
        val currentUser = context?.let { sharedPreferencesService.retrivedUser(it)}
        // If user is no connected, name, filiere and email is empty
        if (currentUser == null) {
            name.setText("")
            filiere.setText("")
            email.setText("")
        } else {
            val firstname = currentUser?.firstName.toString()
            val lastname = currentUser?.lastName.toString()
            val mail = currentUser?.mail.toString()
            val promotion = currentUser?.promotion.toString()
            val formation = currentUser?.formation.toString()
            val completeName = "$firstname $lastname"
            val completeFormation = "$promotion $formation"
            // Assign his values
            name.setText(completeName)
            filiere.setText(completeFormation)
            email.setText(mail)
        }

        view.settings.setOnClickListener {
            // Redirect to settings user profile fragment
            Navigation.findNavController(view)
                .navigate(R.id.actionAccountFragmentToSettingsUserProfile);
        }
        return view
    }

    //Populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RecyclerView initialized here
        recyclerView_tickets.apply {
            //Set a LinearLayoutManager to handle Android; Correctly positions all the data in the list.
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            //Set the custom adapter to the RecyclerView; Links the RecyclerView view to a list of data.
            adapter = RecyclerViewAdapter(data)
        }
    }
}


