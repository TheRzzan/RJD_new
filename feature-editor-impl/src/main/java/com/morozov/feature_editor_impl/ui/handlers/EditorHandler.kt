package com.morozov.feature_editor_impl.ui.handlers

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ObservableField
import com.morozov.core_repos_api.ContactModel

abstract class EditorHandler(private val model: ContactModel) {

    val photo: ObservableField<Drawable?> = ObservableField(null as Drawable)
    val birthday: ObservableField<String?> = ObservableField(null as String)
    val isSaveEnabled: ObservableField<Boolean> = ObservableField(false)

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