package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class resoultFragment (val photoInfo:ArrayList<PhotoInfo>): Fragment() {
    var itemAdapter: ItemAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_resoult, container, false)
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        var onItemClickListener = object: View.OnClickListener {
            override fun onClick(view: View?) {

                val position: Int = view?.tag.toString().toInt()
                photoInfo.removeAt(position)

                itemAdapter?.notifyDataSetChanged()
            }

            }

        itemAdapter= ItemAdapter(photoInfo,onItemClickListener)
        recyclerView.setAdapter(itemAdapter)
        return view
    }

}
