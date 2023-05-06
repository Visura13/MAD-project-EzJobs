
package com.example.projecphase1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projecphase1.R
import com.example.projecphase1.models.UnemployeeModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var UEusername: EditText
    private lateinit var UEnic: EditText
    private lateinit var UEnumber: EditText
    private lateinit var UEaddress: EditText
    private lateinit var UEpassword: EditText
    private lateinit var btnregister: Button

    private lateinit var dbRef:DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UEusername = findViewById(R.id.editTextTextPersonName2)
        UEnic = findViewById(R.id.editTextTextPersonName11)
        UEnumber = findViewById(R.id.editTextPhone2)
        UEaddress = findViewById(R.id.editTextTextPersonName3)
        UEpassword = findViewById(R.id.editTextTextEmailAddress2)
        btnregister = findViewById(R.id.button)

         dbRef = FirebaseDatabase.getInstance().getReference("Unemployee")





        btnregister.setOnClickListener{

            saveUnEmployeeData()





            var intent = Intent(this, profile::class.java)
            startActivity(intent)
        }
    }

    private fun saveUnEmployeeData(){
        //getting values
        val uname = UEusername.text.toString()
        val unic = UEnic.text.toString()
        val unumber = UEnumber.text.toString()
        val uaddress = UEaddress.text.toString()
        val upassword = UEpassword.text.toString()

        if(uname.isEmpty()){
            UEusername.error = "Please enter Username"
        }
        if(unic.isEmpty()){
            UEnic.error = "Please enter NIC"
        }
        if(unumber.isEmpty()){
            UEnumber.error = "Please enter Phone Number"
        }
        if(uaddress.isEmpty()){
            UEaddress.error = "Please enter Address"
        }
        if(upassword.isEmpty()){
            UEpassword.error = "Please enter Password"
        }

        val UempID = dbRef.push().key!!

        val unemployee = UnemployeeModel(UempID, uname, unic, unumber, uaddress, upassword)

        dbRef.child(UempID).setValue(unemployee)
            .addOnCompleteListener {
                Toast.makeText(this, "Registered Successfully!!", Toast.LENGTH_LONG).show()

                UEusername.text.clear()
                UEnic.text.clear()
                UEnumber.text.clear()
                UEaddress.text.clear()
                UEpassword.text.clear()





            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }




    }


}