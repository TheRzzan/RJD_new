package com.morozov.feature_contacts_impl

import com.morozov.core_repos_api.Repository
import com.morozov.feature_contacts_impl.ui.fragments.MainObject

class ContactsFeatureImpl(repository: Repository) {
    init {
        MainObject.repository = repository
    }
}