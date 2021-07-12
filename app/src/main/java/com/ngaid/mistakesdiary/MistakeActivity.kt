package com.ngaid.mistakesdiary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ngaid.mistakesdiary.databinding.ActivityNewMistakeBinding
import com.ngaid.mistakesdiary.model.Mistake
import com.ngaid.mistakesdiary.presenter.MistakePresenter
import com.ngaid.mistakesdiary.presenter.epochDayToMonthDd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MistakeActivity : AppCompatActivity() {
    //TODO: separate newMistake and editMistake activities

    companion object {
        const val ID = "ID"
    }

    private lateinit var b: ActivityNewMistakeBinding       //bad practise
    private val mistakePresenterImpl = MistakePresenter()

    private var mistakeId = 0
    private val isEditMode
        get() = mistakeId != 0                                      //if we are opening certain mistake from to edit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_new_mistake)
        mistakePresenterImpl.attachView(this)

        mistakeId = intent.getIntExtra(ID, 0)

        //toolbar
        b.toolbarOfMistake.title = epochDayToMonthDd(App.currentDateInEpoch)
        setSupportActionBar(b.toolbarOfMistake)

        if (isEditMode) mistakePresenterImpl.setMistakeForEdit(mistakeId)

        b.saveMistakeBtn.setOnClickListener {
            onClickSaveMistakeBtn()
        }
    }

    suspend fun fillInMistakeForm(mistake: Mistake) {
        withContext(Dispatchers.Main) {
            b.mistakeET.setText(mistake.description)
            b.correctionET.setText(mistake.correction)
            b.experienceET.setText(mistake.experience)
            when (mistake.type) {
                0 -> b.rbLight.isChecked = true
                1 -> b.rbMedium.isChecked = true
                else -> b.rbHard.isChecked = true
            }
        }
    }

    private fun onClickSaveMistakeBtn() {
        //TODO: make function with mistake.notblank check in presenter! and fun with toast probably?
        if (b.mistakeET.text.toString().isNotBlank())
            mistakePresenterImpl.saveNewMistake(
                description = b.mistakeET.text.toString(),
                type = getMistakeType(),
                correction = b.correctionET.text.toString(),
                experience = b.experienceET.text.toString(),
                epochDay = App.currentDateInEpoch,
                id = if (isEditMode) mistakeId else 0
            )
        else Toast.makeText(this, getString(R.string.no_mistake_to_save_error), Toast.LENGTH_LONG)
            .show()
        finish()
    }

    private fun getMistakeType(): Int {
        return when {
            b.rbLight.isChecked -> 0
            b.rbMedium.isChecked -> 1
            else -> 2
        }
    }
}