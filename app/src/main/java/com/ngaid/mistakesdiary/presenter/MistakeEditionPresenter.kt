package com.ngaid.mistakesdiary.presenter

import com.ngaid.mistakesdiary.App
import com.ngaid.mistakesdiary.interfaces.EditionContract
import com.ngaid.mistakesdiary.model.Mistake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MistakeEditionPresenter : BasePresenter<EditionContract.View>(),
    EditionContract.Presenter {

    override fun saveEditedMistake(
        description: String, type: Int, correction: String,
        experience: String, epochDay: Long, id: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            App.db.mistakeDao().update(
                Mistake(description, type, correction, experience, epochDay, id)
            )
        }
    }

    override fun setMistakeForEdit(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val mistake = App.db.mistakeDao().getById(id)
            view?.fillInMistakeForm(mistake)
        }
    }
}