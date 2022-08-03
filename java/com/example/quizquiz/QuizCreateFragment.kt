package com.example.quizquiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quizquiz.database.Quiz
import com.example.quizquiz.database.QuizDatabase

class QuizCreateFragment : Fragment()
{
    lateinit var db: QuizDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.quiz_create_fragment, container, false)
        val question = view.findViewById<EditText>(R.id.question)
        val answer = view.findViewById<EditText>(R.id.answer)
        val category = view.findViewById<EditText>(R.id.category)
        val type = view.findViewById<EditText>(R.id.type)
        val guesses = view.findViewById<EditText>(R.id.guesses)
        // val guesses_list = mutableListOf<String?>(guesses.text.toString().split(","))


        db = QuizDatabase.getInstance(requireContext())

//        Thread(Runnable {
//            for (quiz in db.quizDAO().getAll()) {
//                Log.d("mytag", quiz.toString())
//            }
//        }).start()

        view.findViewById<Button>(R.id.add_btn).setOnClickListener {
            when (type.text.toString()) {
                "ox" -> {
                    Thread(Runnable {
                        db.quizDAO().insert(
                            Quiz(type ="ox",
                                question = question.text.toString(),
                                answer = answer.text.toString(),
                                category = category.text.toString())
                        )
                    }).start()

                }
                "multiple_choice" -> {
                    val guesses_list = guesses.text.toString().split(',')
                    Thread(Runnable {
                        db.quizDAO().insert(
                            Quiz(type ="multiple_choice",
                                question = question.text.toString(),
                                answer = answer.text.toString(),
                                category = category.text.toString(),
                                guesses = guesses_list
                            )


                        )
                    }).start()
                    Log.d("mytag", guesses.text.toString())
                    Log.d("mytag", guesses_list.toString())
                }
            }
            Toast.makeText(activity, "추가되었습니다", Toast.LENGTH_SHORT).show()

            question.setText(null)
            answer.setText(null)
            category.setText(null)
            guesses.setText(null)
            type.setText(null)
        }


        return view
    }


}