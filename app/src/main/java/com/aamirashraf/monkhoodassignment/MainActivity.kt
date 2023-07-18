package com.aamirashraf.monkhoodassignment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.aamirashraf.monkhoodassignment.databinding.ActivityMainBinding
import com.aamirashraf.monkhoodassignment.ui.FirebaseActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var datePicker: DatePickerDialog.OnDateSetListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.fabdate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, datePicker, year, month, day)
            datePickerDialog.show()
        }
        datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            binding.etDobMain.text = selectedDate
        }
            imagePickerLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        val data: Intent? = result.data
                        // Handle the selected image here
                        val imageUri: Uri? = data?.data
                        binding.ivMain.setImageURI(imageUri)
                    }
                }

            binding.fabUpload.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                imagePickerLauncher.launch(intent)

            }
            binding.btnSubmitMain.setOnClickListener {
                val etName = binding.etNameMain.text.toString()
                val phone = binding.etNumberMain.text.toString()
                val email = binding.etEmailMain.text.toString()
                val dob = binding.etDobMain.text.toString()
                if (etName.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && dob.isNotEmpty()) {
                    Intent(this@MainActivity, FirebaseActivity::class.java).also {
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Some Fields are Empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }


