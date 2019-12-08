package com.morozov.feature_editor_impl.ui.handlers

import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ObservableField
import com.morozov.core_repos_api.ContactModel

abstract class EditorHandler(private val model: ContactModel) {

    var photo: ObservableField<Drawable?> = ObservableField(null as Drawable)
    var birthday: ObservableField<String?> = ObservableField(null as String)

    fun onSaveInDeviceEnabled(check: Boolean) {
        model.saveInDevice = check
    }

    abstract fun onSelectPhotoClicked(view: View)
    abstract fun onSelectBirthdayClicked(view: View)
}