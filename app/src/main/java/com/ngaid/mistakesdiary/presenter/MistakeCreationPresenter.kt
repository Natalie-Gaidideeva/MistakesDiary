package com.ngaid.mistakesdiary.presenter

import com.ngaid.mistakesdiary.App
import com.ngaid.mistakesdiary.interfaces.CreationContract
import com.ngaid.mistakesdiary.model.Mistake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MistakeCreationPresenter : BasePresenter<CreationContract.View>(),
    CreationContract.Presenter {

    override fun saveCreatedMistake(
        description: String, type: Int, correction: String,
        experience: String, epochDay: Long
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            App.db.mistakeDao().insert(
                Mistake(description, type, correction, experience, epochDay)
            )
        }
    }
}