package com.example.icebreakerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.icebreakerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val className = "android-fall24"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Binding - Allows to link our views to controller.It automatically bind our ID's.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSetRandomQuestion.setOnClickListener{
//            binding.txtQuestion.text = "Hello"
        }

        binding.btnSubmit.setOnClickListener{
//            binding.txtQuestion.text=""
            writeStudentToFirebase()
        }
        // Setting the view of the app i.e Resources(res) -> layout -> activity_main.xml
        /* setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } */
    }

    private fun writeStudentToFirebase(){
        //Getting values from view & storing it into variables
        val firstName = binding.txtFirstName.text
        val lastName = binding.txtLastName.text
        val nickName = binding.txtNickName.text
        val answer = binding.txtAnswer.text

        // ${} - Needed when we want to access the extension after (.) like(${binding.txtFirstName.text})
        Log.d("IcebreakerF24","Variables: $firstName,$lastName,$nickName,$answer")
    }

}