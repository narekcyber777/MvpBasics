package com.narcyber.mvpbasics.view.fragments.airline_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.narcyber.mvpbasics.adapter.airline.AirlineAdapter
import com.narcyber.mvpbasics.databinding.FragmentAirlinesBinding
import com.narcyber.mvpbasics.model.AirlinesContent
import com.narcyber.mvpbasics.presenter.AirLineFragmentPresenter
import com.narcyber.mvpbasics.utils.showInToast

class AirlinesVOneFragment : Fragment(), AirLineFragmentPresenter.AirlineFragmentView {
    lateinit var root: FragmentAirlinesBinding
    lateinit var airLineFragmentPresenter: AirLineFragmentPresenter
    lateinit var adapter: AirlineAdapter
    val data: MutableList<AirlinesContent> by lazy {
        ArrayList()
    }

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
        customizeWidgets()
        inIt()
    }

    private fun customizeWidgets() {
        root.recycler.addItemDecoration(
            DividerItemDecoration(
                root.root.context, DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun inIt() {
        adapter = AirlineAdapter(object : AirlineAdapter.ActionNotifyCallBack {
            override fun notifyLastItem() {

                airLineFragmentPresenter.requestForAirlines(
                    adapter.page++,
                    800,
                    0
                )


            }
        })
        airLineFragmentPresenter = AirLineFragmentPresenter(this, adapter)

        root.recycler.adapter = adapter
        root.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                Log.d("TAG", "onQueryTextChange: $newText")
                return true
            }
        })
    }

    override fun takeAirlineContent(list: MutableList<AirlinesContent>) {

        airLineFragmentPresenter.insertIntoAdapter(list = list as ArrayList<AirlinesContent>)

    }


    override fun error(error: String) {
        showInToast(requireContext(), error.toString())
    }

    override fun onDetach() {
        super.onDetach()
        airLineFragmentPresenter.clearDisposables()
    }


}