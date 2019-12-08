package com.morozov.feature_editor_impl.ui.fragments

import com.morozov.core_repos_api.Repository
import com.morozov.feature_editor_api.FeatureEditorCallback

object MainObject {

    var callback: FeatureEditorCallback? = null
    var phoneNumber: String? = null
    var isFriend: Boolean? = null
    var repository: Repository? = null
}