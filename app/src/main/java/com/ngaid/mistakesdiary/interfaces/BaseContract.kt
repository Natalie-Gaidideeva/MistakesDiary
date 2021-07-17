package com.ngaid.mistakesdiary.interfaces

interface BaseContract {
    interface View {}

    interface Presenter<V : View> {
        fun onAttach(view: V)
        fun onDestroy()
    }
}