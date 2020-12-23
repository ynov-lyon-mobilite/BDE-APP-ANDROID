package com.example.ynov_lyon_bde.ui.screens.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.Event
import kotlinx.android.synthetic.main.card.view.*
import kotlinx.android.synthetic.main.fragment_card_description.view.*

class EventCard(var event: Event): Fragment() {

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.card, container, false)
        return view
    }

}
