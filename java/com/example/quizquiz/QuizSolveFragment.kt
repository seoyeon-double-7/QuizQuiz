package com.example.quizquiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quizquiz.database.Quiz
import java.lang.Exception

//q\ quiz solve listener 구현하고, 맞추면 정답 틀렸으면 오담
class QuizSolveFragment : Fragment(){
    interface QuizSolveListener{
        fun onAnswerSelected(isCorrect: Boolean)
    }

    lateinit var listener : QuizSolveListener
    lateinit var quiz : Quiz

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(parentFragment is QuizSolveFragment.QuizSolveListener){
            listener = parentFragment as QuizSolveFragment.QuizSolveListener
        }else{
            throw Exception("QuizSolveListener 미구현")
        }


    }

    companion object{
        fun newInstance(quiz : Quiz): QuizSolveFragment{
            val fragment = QuizSolveFragment()

            val args = Bundle()
            args.putParcelable("quiz", quiz)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.quiz_solve_fragment,
            container,
            false)

        quiz = arguments?.getParcelable("quiz")!!
        view.findViewById<TextView>(R.id.question).text = quiz.question
        val choices = view.findViewById<ViewGroup>(R.id.choices)    // 상위타입으로 업캐스팅 됨

        val answerSelectListener = View.OnClickListener {
            val guess = (it as Button).text.toString()
            if(guess == quiz.answer){
                listener.onAnswerSelected(true)
            }else{
                listener.onAnswerSelected(false)
            }
        }

        when(quiz.type){
            "ox" -> {
                for(sign in listOf("o", "x")){
                    var  btn = Button(activity)
                    btn.text = sign
                    btn.setOnClickListener(answerSelectListener)
                    btn.isAllCaps = false   //소문자로 변경해줌
                    choices.addView(btn)
                }
            }
            "multiple_choice" -> {
                for(sign in quiz.guesses!!){
                    var  btn = Button(activity)
                    btn.text = sign
                    btn.setOnClickListener(answerSelectListener)
                    btn.isAllCaps = false
                    choices.addView(btn)
                }
            }
        }



        return view
    }

}