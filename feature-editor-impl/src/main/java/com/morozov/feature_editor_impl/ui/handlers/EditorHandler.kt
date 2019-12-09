package com.morozov.feature_editor_impl.ui.handlers

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ObservableField
import com.morozov.core_repos_api.ContactModel
import java.text.SimpleDateFormat

abstract class EditorHandler(private val model: ContactModel) {

    private val tmpContactModel: ContactModel

    val photo: ObservableField<Drawable?> = ObservableField()
    val birthday: ObservableField<String?> = ObservableField()
    val isSaveEnabled: ObservableField<Boolean> = ObservableField()

    private val dayMtYrFormat = SimpleDateFormat("dd/MM/yyyy")

    init {
        tmpContactModel = ContactModel(model.name, model.family, model.surname,
            model.phoneNum, model.isFriend, model.workPhone, model.position, model.birthday,
            model.photo, model.saveInDevice)

        val birthdayTMP = model.birthday
        if (birthdayTMP == null)
            birthday.set(null)
        else
            birthday.set(dayMtYrFormat.format(birthdayTMP))

        verifyIsReadyToSave()
    }

    fun onSaveInDeviceEnabled(check: Boolean) {
        model.saveInDevice = check
        verifyIsReadyToSave()
    }

    fun verifyIsReadyToSave() {
        var b = false

        if (tmpContactModel != model) {
            if (model.name.isNotEmpty() && model.family.isNotEmpty() && model.phoneNum.isNotEmpty()) {
                if (model.isFriend) {
                    if (model.birthday != null) {
                        b = true
                    }
                } else {
                    val position = model.position
                    if (position != null && position.isNotEmpty()) {
                        b = true
                    }
                }
            }
        }

        isSaveEnabled.set(b)
    }

    abstract fun onSelectPhotoClicked(view: View)
    abstract fun onSelectBirthdayClicked(view: View)
}