package com.example.streamingaudiotest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

private const val ARG_URL = "URL"
private const val ARG_Name = "Name"
private const val ARG_Icon = "Icon"
private lateinit var inflatedView: View

class RadioFragment : Fragment() {

    private var url: String? = null
    private var name: String? = null
    private var icon: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
            name = it.getString(ARG_Name)
            icon = it.getInt(ARG_Icon)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(R.layout.fragment_radio, container, false)
        val image = inflatedView.findViewById<ImageView>(R.id.imageView)
        val textView = inflatedView.findViewById<TextView>(R.id.radioName)

        image.setImageResource(icon ?: R.drawable.ic_error_ico)
        textView.text = name ?: "Error"
        return inflatedView
    }


    companion object {
        @JvmStatic
        fun newInstance(url: String, name: String, icon: Int): RadioFragment {
            return RadioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                    putString(ARG_Name, name)
                    putInt(ARG_Icon, icon)
                }
            }
        }
    }
}
