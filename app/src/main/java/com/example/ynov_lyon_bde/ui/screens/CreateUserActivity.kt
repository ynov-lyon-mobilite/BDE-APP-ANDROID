
package com.example.ynov_lyon_bde.ui.screens


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ynov_lyon_bde.R
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.viewmodel.CreateUserViewModel
import kotlinx.android.synthetic.main.activity_createuser.*
import kotlinx.coroutines.*
import org.json.JSONObject


class CreateUserActivity : AppCompatActivity(){

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createuser)

        val createUserViewModel = CreateUserViewModel()
        //return previous activity
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //Spinner to take class for a new user
        val spinnerP: Spinner = findViewById(R.id.spinnerPromotion)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.promotion_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerP.adapter = adapter
        }

        //Spinner to take faculty for a new user
        val spinnerF: Spinner = findViewById(R.id.spinnerFormation)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.formation_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerF.adapter = adapter
        }

        buttonCreateUser.setOnClickListener {
            // Take informations User
            val email = if(android.util.Patterns.EMAIL_ADDRESS.matcher(editTextMail.text.toString()).matches())  editTextMail.text.toString() else null
            var firstName: String? = null
            var lastName: String? = null
            if(email != null){
                val names: String = editTextMail.text.toString().split('@')[0]
                firstName = names.split('.')[0]
                lastName = names.split('.')[1]
            }

            val password = editTextPassword.text.toString()
            val promotion = spinnerP.getSelectedItem().toString()
            val formation = spinnerF.getSelectedItem().toString()

            //send request to api to create user
            if(firstName!= null && lastName!= null && email!= null && password!= null && promotion!= null && formation!= null){
                var result : String? = null
                val userDto = UserDTO(
                    firstName = firstName,
                    lastName = lastName,
                    mail = email,
                    password = password,
                    promotion = promotion,
                    formation = formation
                )
                GlobalScope.launch(Dispatchers.Main) {
                    val deferred = async(Dispatchers.IO) {
                        val resultRequest = createUserViewModel.signUp(userDto)

                        if (resultRequest != null) {
                            val jsonResultRequest = JSONObject(resultRequest)
                            result = jsonResultRequest.getJSONObject("data").getString("message")
                        }
                    }
                    deferred.await()
                    Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Formulaire mal renseigné", Toast.LENGTH_SHORT).show()
            }

        }
    }
}




