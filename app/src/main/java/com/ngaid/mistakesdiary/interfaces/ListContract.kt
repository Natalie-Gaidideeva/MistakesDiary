package com.ngaid.mistakesdiary.interfaces

import com.ngaid.mistakesdiary.model.ShortMistake

interface ListContract {
    interface View : BaseContract.View {
        suspend fun setAdapter(mistakes: List<ShortMistake>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun bindMistakes()
    }
}