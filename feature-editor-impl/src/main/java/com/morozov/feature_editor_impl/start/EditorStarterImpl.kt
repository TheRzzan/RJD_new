package com.morozov.feature_editor_impl.start

import androidx.fragment.app.FragmentManager
import com.morozov.feature_editor_api.EditorStarter
import com.morozov.feature_editor_api.FeatureEditorCallback
import com.morozov.feature_editor_impl.ui.fragments.MainObject
import com.morozov.feature_editor_impl.ui.fragments.editor.EditorFragment

class EditorStarterImpl: EditorStarter {

    override fun start(isFriend: Boolean, manager: FragmentManager, container: Int, callback: FeatureEditorCallback) {
        MainObject.callback = callback
        MainObject.phoneNumber = null
        MainObject.isFriend = isFriend
        start(manager, container)
    }

    override fun start(contactNumber: String, manager: FragmentManager, container: Int, callback: FeatureEditorCallback) {
        MainObject.callback = callback
        MainObject.phoneNumber = contactNumber
        MainObject.isFriend = null
        start(manager, container)
    }

    private fun start(manager: FragmentManager, container: Int) {
        manager.beginTransaction()
            .replace(container, EditorFragment())
            .addToBackStack(EditorFragment.TAG)
            .commit()
    }
}