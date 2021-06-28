package com.ngaid.mistakesdiary.presenter

import com.ngaid.mistakesdiary.App
import com.ngaid.mistakesdiary.model.Mistake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

interface EditMistake {
    suspend fun fillInMistakeForm(mistake: Mistake)
}

class MistakePresenter {

    private var mistakeView: WeakReference<EditMistake>? = null

    fun attachView(view: EditMistake) {
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