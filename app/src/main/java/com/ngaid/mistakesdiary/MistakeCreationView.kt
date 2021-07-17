package com.ngaid.mistakesdiary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ngaid.mistakesdiary.databinding.ActivityMistakeBinding
import com.ngaid.mistakesdiary.interfaces.CreationContract
import com.ngaid.mistakesdiary.presenter.MistakeCreationPresenter

class MistakeCreationView : AppCompatActivity(), CreationContract.View {

    private lateinit var b: ActivityMistakeBinding       //bad practise
    private val presenter: MistakeCreationPresenter = MistakeCreationPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_mistake)

        b.toolbarOfMistake.title = epochDayToMonthDd(App.currentDateInEpoch)
        setSupportActionBar(b.toolbarOfMistake)
        b.saveMistakeBtn.setOnClickListener {
            onClickCreateMistakeBtn()
        }

        presenter.onAttach(this)
    }

    private fun onClickCreateMistakeBtn() {
        val description = b.descriptionET.text.toString()
        if (description.isBlank()) {
            Toast.makeText(this, getString(R.string.no_mistake_to_save_error), Toast.LENGTH_LONG)
                .show()
            return
        }
        presenter.saveCreatedMistake(
            description = description,
            type = getMistakeType(),
            correction = b.correctionET.text.toString(),
            experience = b.experienceET.text.toString(),
            epochDay = App.currentDateInEpoch
        )
        finish()
    }

    private fun getMistakeType(): Int {
        return when {
            b.rbLight.isChecked -> 0
            b.rbMedium.isChecked -> 1
            else -> 2
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}