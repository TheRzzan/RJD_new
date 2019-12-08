package com.morozov.feature_editor_impl.ui.handlers

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ObservableField
import com.morozov.core_repos_api.ContactModel

abstract class EditorHandler(private val model: ContactModel) {

    val photo: ObservableField<Drawable?> = ObservableField()
    val birthday: ObservableField<String?> = ObservableField()
    val isSaveEnabled: ObservableField<Boolean> = ObservableField()

    init {
        birthday.set(null)
        isSaveEnabled.set(false)
    }

    fun onSaveInDeviceEnabled(check: Boolean) {
        model.saveInDevice = check
    }

    fun verifyIsReadyToSave() {
        if (model.name.isNotEmpty() && model.family.isNotEmpty() && model.phoneNum.isNotEmpty() &&
                model.birthday != null) {
            isSaveEnabled.set(true)
        } else {
            isSaveEnabled.set(false)
        }
    }

    abstract fun onSelectPhotoClicked(view: View)
    abstract fun onSelectBirthdayClicked(view: View)
}