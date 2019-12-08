package com.morozov.feature_editor_impl

import com.morozov.core_repos_api.Repository
import com.morozov.feature_editor_api.EditorFeatureApi
import com.morozov.feature_editor_api.EditorStarter
import com.morozov.feature_editor_impl.ui.fragments.MainObject

class EditorFeatureImpl(repository: Repository, private val starter: EditorStarter)
    : EditorFeatureApi {

    init {
        MainObject.repository = repository
    }

    override fun editorStarter(): EditorStarter = starter
}