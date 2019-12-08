package com.morozov.feature_editor_impl

import com.morozov.feature_editor_api.EditorFeatureApi
import com.morozov.feature_editor_api.EditorStarter

class EditorFeatureImpl(private val starter: EditorStarter): EditorFeatureApi {

    override fun editorStarter(): EditorStarter = starter
}