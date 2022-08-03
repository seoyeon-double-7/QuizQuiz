package com.example.quizquiz

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizquiz.database.Quiz
import com.example.quizquiz.database.QuizDatabase

class QuizFragment : Fragment(),
    QuizStartFragment.QuizStartListener,
    QuizSolveFragment.QuizSolveListener,
        QuizResultFragment.QuizResultListener
{
    lateinit var db : QuizDatabase
    lateinit var quizList : List<Quiz>
    var currentQuizIdx = 0
    var correctCount = 0

    override fun onRetry() {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,
                QuizStartFragment())
            .commit()
    }

    override fun onAnswerSelected(isCorrect: Boolean) {
        if(isCorrect) correctCount++
        currentQuizIdx++

        if(currentQuizIdx == quizList.size){
            childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,
                    QuizResultFragment.newInstance(correctCount, quizList.size))
                .commit()
        }else{
//            if(isCorrect) {
//                correctCount++
//                Toast.makeText(activity, "정답!", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                Toast.makeText(activity, "오답!", Toast.LENGTH_SHORT).show()
//            }
            childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,
                    QuizSolveFragment.newInstance(quizList[currentQuizIdx]))
                .commit()
        }

    }

    override fun onQuizStart() {
        Log.d("mytag", "시작하기!!!")

        AsyncTask.execute {
            currentQuizIdx = 0
            correctCount = 0
            quizList = db.quizDAO().getAll()

            childFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container,
                    QuizSolveFragment.newInstance(quizList[currentQuizIdx]))
                .commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        val view = inflater.inflate(
            R.layout.quiz_fragment,
            container,
            false)

        db = QuizDatabase.getInstance(requireContext())

        childFragmentManager    //support 매니저가 아님!!
            .beginTransaction().add(R.id.fragment_container,
                QuizStartFragment())
            .commit()

        return view
    }




}