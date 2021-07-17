package com.ngaid.mistakesdiary.interfaces

import com.ngaid.mistakesdiary.model.Mistake

interface EditionContract {
    interface View : BaseContract.View {
        suspend fun fillInMistakeForm(mistake: Mistake)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun saveEditedMistake(
            description: String,
            type: Int,
            correction: String,
            experience: String,
            epochDay: Long,
            id: Int
        )

        fun setMistakeForEdit(id: Int)
    }
}