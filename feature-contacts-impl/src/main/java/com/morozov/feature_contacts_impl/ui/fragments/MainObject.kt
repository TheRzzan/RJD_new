package com.morozov.feature_contacts_impl.ui.fragments

import com.morozov.core_repos_api.Repository
import com.morozov.feature_contacts_api.FeatureContactsCallback

object MainObject {

    var repository: Repository? = null
    var callback: FeatureContactsCallback? = null
    var isFriends: Boolean? = null
}