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
            binding.txtQuestion.text = "Hello"
        }

        binding.btnSubmit.setOnClickListener{
            binding.txtQuestion.text=""
        }
        // Setting the view of the app i.e Resources(res) -> layout -> activity_main.xml
        /* setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        } */
        writeStudentToFirebase()
    }

    private fun writeStudentToFirebase(){
        //Getting values & storing it into variables
        val firstName = binding.txtFirstName.text
        val lastName = binding.txtLastName.text
        val nickName = binding.txtNickName.text
        val answer = binding.txtAnswer.text

//        Log.d("IcebreakerF24", msg: "Variables: $firstName $lastName $nickName $answer")

        val student = hashMapOf<String,String>(
            "firstName" to firstName.toString(),
            "lastName" to lastName.toString(),
            "nickName" to nickName.toString(),
            "answer" to answer.toString(),
            "class" to className,
            "question" to binding.txtQuestion.text.toString()
        )
    }

}