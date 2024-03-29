package com.example.ynov_lyon_bde.ui.screens.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.domain.viewmodel.scanner.QRScannerViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.ynov_lyon_bde.domain.services.RedirectConnectService
import kotlinx.android.synthetic.main.fragment_card_description.view.*

class CardDescription : Fragment() {

    private val args: CardDescriptionArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_description, container, false)
        val event = args.Event

        val bottomNavigationBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationBar?.visibility = View.VISIBLE

        view.back_home_fragment.setOnClickListener { findNavController().popBackStack() }

        view.qrcode_button.setOnClickListener{
            val action = CardDescriptionDirections.actionCardDescriptionToQRScannerFragment(event)
            view.findNavController().navigate(action)
        }

        view.eventTitle.text = event.name
        view.eventDescriptionType.text = event.type.eventType
        view.dateEvent.text = event.date
        view.eventDescription.text = event.description
        view.eventHour.text = event.date
        view.eventAddress.text = event.address

        view.button_take_place.setOnClickListener {
            //REDIRECT CONNECT (if user current isn't connect)
            val redirectService = RedirectConnectService()
            context?.let { activity?.let { it1 -> redirectService.redirect(it, it1, RedirectConnectService.TypeAlertDialog.ACHAT) } }
        }
        return view
    }

}
