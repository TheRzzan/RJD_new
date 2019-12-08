package com.morozov.feature_editor_api

import androidx.fragment.app.FragmentManager

interface EditorStarter {

    fun start(manager: FragmentManager, container: Int, callback: FeatureEditorCallback)
}