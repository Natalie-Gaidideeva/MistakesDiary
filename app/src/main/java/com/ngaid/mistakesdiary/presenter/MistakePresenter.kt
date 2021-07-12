package com.ngaid.mistakesdiary.presenter

import com.ngaid.mistakesdiary.App
import com.ngaid.mistakesdiary.MistakeActivity
import com.ngaid.mistakesdiary.model.Mistake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

//TODO: create interfaces for presenters and view with attach/detach
class MistakePresenter {

    private var mistakeView: WeakReference<MistakeActivity>? = null

    fun attachView(view: MistakeActivity) {
        mistakeView = WeakReference(view)
    }

    fun saveNewMistake(
        description: String, type: Int, correction: String,
        experience: String, epochDay: Long, id: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            App.db.mistakeDao().insert(
                Mistake(description, type, correction, experience, epochDay, id)
            )
        }
    }

    fun setMistakeForEdit(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val mistake = App.db.mistakeDao().getById(id)
            mistakeView?.get()?.fillInMistakeForm(mistake)
        }
    }
}