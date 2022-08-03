package com.example.quizquiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.quizquiz.database.Quiz
import java.lang.Exception

class QuizResultFragment : Fragment() {
    interface QuizResultListener{
        fun onRetry()
    }

    lateinit var listener : QuizResultFragment.QuizResultListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is QuizResultFragment.QuizResultListener){
            listener = parentFragment as QuizResultFragment.QuizResultListener
        }else{
            throw Exception("QuizSolveListener 미구현")
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.quiz_result_fragment,
            container,
            false
        )
        val correct = arguments?.getInt("correct_count")?.toDouble()!!
        val total = arguments?.getInt("total_count")?.toDouble()!!
        val percentage = correct / total
        Log.d("mytag", percentage.toString())

        view.findViewById<Button>(R.id.retry_btn).setOnClickListener {
            listener.onRetry()
        }


        return view
    }

    companion object{
        fun newInstance(correct_count : Int, total_count : Int): QuizSolveFragment{
            val fragment = QuizSolveFragment()

            val args = Bundle()
            args.putInt("correct_count", correct_count)
            args.putInt("total_count", total_count)
            fragment.arguments = args

            return fragment
        }
    }
}