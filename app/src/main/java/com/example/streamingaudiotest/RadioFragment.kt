package com.example.streamingaudiotest

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette

private const val ARG_URL = "URL"
private const val ARG_Name = "Name"
private const val ARG_Icon = "Icon"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RadioFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RadioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RadioFragment : Fragment() {

    private var url: String? = null
    private var name: String? = null
    private var icon: Int? = null
    private var listener: OnFragmentInteractionListener? = null

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
        val inflatedView = inflater.inflate(R.layout.fragment_radio, container, false)

        val image = inflatedView.findViewById<ImageView>(R.id.imageView)
        val textView = inflatedView.findViewById<TextView>(R.id.radioName)

        image.setImageResource(icon ?: R.drawable.ic_error_ico)
        textView.text = name ?: "Error"

        val bitmap = BitmapFactory.decodeResource(resources, icon ?: R.drawable.ic_error_ico)
        Palette.Builder(bitmap).generate {
            it?.let { palette ->
                val dominantColor = palette.getDominantColor(
                    ContextCompat.getColor(
                        context!!,
                        R.color.colorPrimaryDark
                    )
                )
                val lightColor = palette.getVibrantColor(Color.BLACK)

                textView.setTextColor(lightColor)
                view?.setBackgroundColor(dominantColor)

                val parentView = activity?.findViewById<ConstraintLayout>(R.id.ParentView)
                parentView?.setBackgroundColor(dominantColor)
            }
        }

        return inflatedView
    }
    
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param url Parameter 1.
         * @param name Parameter 2.
         * @param icon Parameter 3.
         * @return A new instance of fragment RadioFragment.
         */
        @JvmStatic
        fun newInstance(url: String, name: String, icon: Int) =
            RadioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                    putString(ARG_Name, name)
                    putInt(ARG_Icon, icon)
                }
            }
    }
}
