package com.example.quizquiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import java.lang.Exception

class QuizStartFragment : Fragment() ,
        QuizSolveFragment.QuizSolveListener

{
    interface QuizStartListener{
        fun onQuizStart()
    }

    lateinit var listener: QuizStartListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is QuizStartListener){
            listener = parentFragment as QuizStartListener
        }else{
            throw Exception("QuizStartListener 미구현")
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.quiz_start_fragment, container, false)
        view.findViewById<Button>(R.id.start).setOnClickListener {
            listener.onQuizStart()
        }

        return view
    }

    companion object {}

    override fun onAnswerSelected(isCorrect: Boolean) {
        TODO("Not yet implemented")
    }
}