package com.ngaid.mistakesdiary.presenter

import com.ngaid.mistakesdiary.App
import com.ngaid.mistakesdiary.interfaces.ListContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListPresenter: BasePresenter<ListContract.View>(), ListContract.Presenter {

    override fun bindMistakes() {
        CoroutineScope(Dispatchers.IO).launch {
            val mistakes = App.db.mistakeDao().getByDate(App.currentDateInEpoch)
            view?.setAdapter(mistakes)
        }
    }
}