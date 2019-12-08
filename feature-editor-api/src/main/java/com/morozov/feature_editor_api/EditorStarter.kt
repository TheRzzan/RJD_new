package com.morozov.feature_editor_api

import androidx.fragment.app.FragmentManager

interface EditorStarter {

    fun start(isFriend: Boolean, manager: FragmentManager, container: Int, callback: FeatureEditorCallback)
    fun start(contactNumber: String, manager: FragmentManager, container: Int, callback: FeatureEditorCallback)
}