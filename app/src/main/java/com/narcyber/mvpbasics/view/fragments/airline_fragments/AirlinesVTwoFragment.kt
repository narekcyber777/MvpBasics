package com.narcyber.mvpbasics.view.fragments.airline_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.narcyber.mvpbasics.adapter.airline.AirlineAdapterTwo
import com.narcyber.mvpbasics.databinding.FragmentAirlinesBinding
import com.narcyber.mvpbasics.helper.CustomScrollListener
import com.narcyber.mvpbasics.model.AirlinesContent
import com.narcyber.mvpbasics.presenter.AirLineFragmentPresenter

class AirlinesVTwoFragment : Fragment(), AirLineFragmentPresenter.AirlineFragmentView {
    lateinit var root: FragmentAirlinesBinding
    lateinit var presenter: AirLineFragmentPresenter
    lateinit var adapter: AirlineAdapterTwo
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        root = FragmentAirlinesBinding.inflate(inflater, container, false)
        return root.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inIt()
    }

    private fun inIt() {
        adapter = AirlineAdapterTwo()
        presenter = AirLineFragmentPresenter(this, adapterView = adapter)
        root.recycler.adapter = adapter
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        root.recycler.layoutManager = linearLayoutManager
        presenter.requestForAirlines(1, 100, 0)
        root.recycler.addOnScrollListener(
            object : CustomScrollListener(linearLayoutManager) {
                override fun loadMore() {
                    Log.d("Nar", "its lading now  ")
                    presenter.requestForAirlines(adapter.page++, 100, 0)

                }
            })

    }


    override fun takeAirlineContent(list: MutableList<AirlinesContent>) {
        Log.d("John", adapter.allData.size.toString())
        presenter.insertIntoAdapter(list = list as ArrayList<AirlinesContent>)

    }

    override fun error(error: String) {

    }
}