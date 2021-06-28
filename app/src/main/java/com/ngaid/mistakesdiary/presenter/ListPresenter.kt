package com.ngaid.mistakesdiary.presenter

import com.ngaid.mistakesdiary.App
import com.ngaid.mistakesdiary.MistakesListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class ListPresenter {

    private var listFragment: WeakReference<MistakesListFragment>? = null

    fun attachView(fragment: MistakesListFragment) {
        listFragment = WeakReference(fragment)
    }

    fun bindMistakes() {
        CoroutineScope(Dispatchers.IO).launch {
            val mistakes = App.db.mistakeDao().getByDate(App.currentDateInEpoch)
            listFragment?.get()?.setAdapter(mistakes)
        }
    }
}