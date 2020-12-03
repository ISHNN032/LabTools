package com.ishnn.labtools.ui.qna

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ishnn.labtools.R

class QnAFragment : Fragment() {

    private lateinit var qnaViewModel: QnAViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        qnaViewModel =
                ViewModelProviders.of(this).get(QnAViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_qna, container, false)
        val textView: TextView = root.findViewById(R.id.text_qna)
        qnaViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}