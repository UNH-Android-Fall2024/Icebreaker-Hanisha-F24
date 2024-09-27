package com.example.icebreakerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.icebreakerapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val className = "Android-Fall24"
    //Value stored in variable to access/manipulate DB
    private val db = Firebase.firestore
    private val TAG = "IcebeakerF24"

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
        val firstName = binding.txtFirstName
        val lastName = binding.txtLastName
        val nickName = binding.txtNickName
        val answer = binding.txtAnswer

        // ${} - Needed when we want to access the extension after (.) like(${binding.txtFirstName.text})
        Log.d(TAG,"Variables: ${firstName.text},${lastName.text},${nickName.text},${answer.text}")

        val student = hashMapOf(
            "firstName" to firstName.text.toString(),
            "lastName" to lastName.text.toString(),
            "nickName" to nickName.text.toString(),
            "answer" to answer.text.toString(),
            "class" to className,
            "question" to binding.txtQuestion.text.toString()
        )

        // Access a collection mentioned in collectionPath. If it doesn't exist it creates a new one
        // Adds data
        //Check to see if task is done/not done
        db.collection("Students").add(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,"Document added to students collection sucessfully with ID: ${documentReference.id}")
            }
            .addOnFailureListener{exception ->
                Log.w(TAG,"Error adding document",exception)
            }

        //After storing in database fields need to be cleared
        firstName.setText("")
        lastName.setText("")
        nickName.setText("")
        answer.setText("")

    }
}