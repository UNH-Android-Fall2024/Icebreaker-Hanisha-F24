package com.example.icebreakerapp

//import android.os.Bundle
//import android.util.Log
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.icebreakerapp.databinding.ActivityMainBinding
////import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.firestore.toObject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.icebreakerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val className = "Android-Fall24"
    //Value stored in variable to access/manipulate DB
    private val db = Firebase.firestore
    private val TAG = "IcebeakerF24"
    //Mutable means it can change the size/delete/add  i.e works dynamic in nature.
    // ? -> It is null safe
    private var questionsList: MutableList<Questions>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getQuestionsFromFirebase()

        //Binding - Allows to link our views to controller.It automatically bind our ID's.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        getQuestionsFromFirebase()

        binding.btnSetRandomQuestion.setOnClickListener{
            binding.txtQuestion.text = questionsList!!.random().text
        }

        binding.btnSubmit.setOnClickListener{
            writeStudentToFirebase()
        }
        // Setting the view of the app i.e Resources(res) -> layout -> activity_main.xml
        /* setContentView(R.layout.activity_main) */
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

    private fun getQuestionsFromFirebase(){
        //It reads all the documents from given collection
        db.collection("Questions").get()
            .addOnSuccessListener { result ->
                // It initializes a mutable list
                questionsList = mutableListOf()
                for(document in result){
                    try {
                        val question = document.toObject(Questions::class.java)
                        Log.d("Firestore", "Question: $question")
                        //Adding to list
                        // !! -> Forcing not to check null safe
                        questionsList!!.add(question)
                        Log.d(TAG,"$question")
                    } catch (e: Exception) {
                        Log.e("Firestore", "Error converting document", e)
                    }

//                    val question = document.toObject(Questions::class.java)

                }
            }
            .addOnFailureListener{error ->
                Log.w(TAG,"Error in retereving the question",error)
            }
    }
}