package com.ngaid.mistakesdiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import com.ngaid.mistakesdiary.adapters.MistakesListAdapter
import com.ngaid.mistakesdiary.model.ShortMistake
import com.ngaid.mistakesdiary.presenter.ListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MistakesListFragment : ListFragment() {

    private lateinit var inflater: LayoutInflater

    override fun onCreateView(
        lInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater = lInflater

        val listPresenter = ListPresenter()
        listPresenter.attachView(this)
        listPresenter.bindMistakes()                    //TODO:try pass setAdapter() as lambda!

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    suspend fun setAdapter(mistakes: List<ShortMistake>) {
        withContext(Dispatchers.Main) {
            listAdapter = MistakesListAdapter(requireContext(), mistakes, inflater)
        }
    }
}