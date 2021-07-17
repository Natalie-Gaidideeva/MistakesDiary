package com.ngaid.mistakesdiary

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ngaid.mistakesdiary.databinding.ActivityMistakeBinding
import com.ngaid.mistakesdiary.interfaces.EditionContract
import com.ngaid.mistakesdiary.model.Mistake
import com.ngaid.mistakesdiary.presenter.MistakeEditionPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MistakeEditionView : AppCompatActivity(), EditionContract.View {

    companion object {
        const val ID = "ID"
    }

    private lateinit var b: ActivityMistakeBinding       //bad practise
    private val presenter: MistakeEditionPresenter = MistakeEditionPresenter()
    private var mistakeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mistakeId = intent.getIntExtra(ID, 0)
        b = DataBindingUtil.setContentView(this, R.layout.activity_mistake)

        //rep
        b.toolbarOfMistake.title = epochDayToMonthDd(App.currentDateInEpoch)
        setSupportActionBar(b.toolbarOfMistake)
        b.saveMistakeBtn.setOnClickListener {
            onClickEditMistakeBtn()
        }

        presenter.onAttach(this)
        presenter.setMistakeForEdit(mistakeId)
    }

    override suspend fun fillInMistakeForm(mistake: Mistake) {
        withContext(Dispatchers.Main) {
            b.descriptionET.setText(mistake.description)
            b.correctionET.setText(mistake.correction)
            b.experienceET.setText(mistake.experience)
            when (mistake.type) {
                0 -> b.rbLight.isChecked = true
                1 -> b.rbMedium.isChecked = true
                else -> b.rbHard.isChecked = true
            }
        }
    }

    private fun onClickEditMistakeBtn() {
        //rep
        val description = b.descriptionET.text.toString()
        if (description.isBlank()) {
            Toast.makeText(this, getString(R.string.no_mistake_to_save_error), Toast.LENGTH_LONG)
                .show()
            return
        }
        presenter.saveEditedMistake(
            description = description,
            type = getMistakeType(),
            correction = b.correctionET.text.toString(),
            experience = b.experienceET.text.toString(),
            epochDay = App.currentDateInEpoch,
            id = mistakeId
        )
        finish()
    }

    //rep
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