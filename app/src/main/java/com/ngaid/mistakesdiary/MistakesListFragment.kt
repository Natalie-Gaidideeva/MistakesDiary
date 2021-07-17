package com.ngaid.mistakesdiary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import com.ngaid.mistakesdiary.adapters.MistakesListAdapter
import com.ngaid.mistakesdiary.interfaces.ListContract
import com.ngaid.mistakesdiary.model.ShortMistake
import com.ngaid.mistakesdiary.presenter.ListPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MistakesListFragment : ListFragment(), ListContract.View {

    private val presenter: ListPresenter = ListPresenter()
    private lateinit var inflater: LayoutInflater

    override fun onCreateView(
        lInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater = lInflater
        presenter.bindMistakes()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override suspend fun setAdapter(mistakes: List<ShortMistake>) {
        withContext(Dispatchers.Main) {
            listAdapter = MistakesListAdapter(requireContext(), mistakes, inflater)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.onAttach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}