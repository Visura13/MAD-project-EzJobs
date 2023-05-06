package com.example.projecphase1.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projecphase1.R
import com.example.projecphase1.models.UnemployeeModel
import com.google.firebase.database.FirebaseDatabase

class UnemployeeDetails : AppCompatActivity() {

    private lateinit var tvUnempId:TextView
    private lateinit var tvUnempName : TextView
    private lateinit var tvUnempnic : TextView
    private lateinit var tvUnempnumber:TextView
    private lateinit var tvUnempaddress : TextView
    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unemployee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("UnempID").toString(),
                intent.getStringExtra("unempname").toString()
            )

        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("UnempID").toString()

            )
        }




    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Unemployee").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Unemployee data deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this,editdetails::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this,"Deleting Error ${error.message}",Toast.LENGTH_LONG).show()

        }
    }

    private fun initView(){
        tvUnempId = findViewById(R.id.tvUnempId)
        tvUnempName = findViewById(R.id.tvUnempName)
        tvUnempnic = findViewById(R.id.tvUnempnic)
        tvUnempnumber = findViewById(R.id.tvUnempnumber)
        tvUnempaddress = findViewById(R.id.tvUnempaddress)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }
    private fun setValuesToViews(){
        tvUnempId.text = intent.getStringExtra("UnempID")
        tvUnempName.text = intent.getStringExtra("unempname")
        tvUnempnic.text = intent.getStringExtra("unempnic")
        tvUnempnumber.text = intent.getStringExtra("unempnumber")
        tvUnempaddress.text = intent.getStringExtra("unempaddress")



    }

    @SuppressLint("MissingInflatedId")

    private fun openUpdateDialog(UnempID:String, uname:String){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.updatedialog,null)

        mDialog.setView(mDialogView)

        val etUnempName = mDialogView.findViewById<EditText>(R.id.etUnempName)
        val etUnempnic = mDialogView.findViewById<EditText>(R.id.etUnempnic)
        val etUnempnumber = mDialogView.findViewById<EditText>(R.id.etUnempnumber)
        val etUnempaddress = mDialogView.findViewById<EditText>(R.id.etUnempaddress)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etUnempName.setText(intent.getStringExtra("unempname").toString())
        etUnempnic.setText(intent.getStringExtra("unempnic").toString())
        etUnempnumber.setText(intent.getStringExtra("unempnumber").toString())
        etUnempaddress.setText(intent.getStringExtra("unempaddress").toString())

        mDialog.setTitle("Updating $uname Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateUnempData(
                UnempID,
                etUnempName.text.toString(),
                etUnempnic.text.toString(),
                etUnempnumber.text.toString(),
                etUnempaddress.text.toString()
            )
            Toast.makeText(applicationContext, "Unemployee data Updated!!",Toast.LENGTH_LONG).show()

            tvUnempName.text = etUnempName.text.toString()
            tvUnempnic.text = etUnempnic.text.toString()
            tvUnempnumber.text = etUnempnumber.text.toString()
            tvUnempaddress.text = etUnempaddress.text.toString()

            alertDialog.dismiss()
        }

    }
    private fun updateUnempData(
        id:String,
        name:String,
        nic:String,
        number:String,
        address:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Unemployee").child(id)
        val unempInfo = UnemployeeModel(id, name, nic, number, address)
        dbRef.setValue(unempInfo)
    }


}