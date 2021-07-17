package com.ngaid.mistakesdiary.interfaces

interface CreationContract {
    interface View : BaseContract.View {}

    interface Presenter : BaseContract.Presenter<View> {
        fun saveCreatedMistake(
            description: String,
            type: Int,
            correction: String,
            experience: String,
            epochDay: Long
        )
    }
}