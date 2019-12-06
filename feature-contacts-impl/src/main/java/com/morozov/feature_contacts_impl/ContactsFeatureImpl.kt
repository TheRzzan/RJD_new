package com.morozov.feature_contacts_impl

import com.morozov.core_repos_api.Repository
import com.morozov.feature_contacts_api.ContactsFeatureApi
import com.morozov.feature_contacts_api.ContactsStarter
import com.morozov.feature_contacts_impl.ui.fragments.MainObject

class ContactsFeatureImpl(repository: Repository, private val starter: ContactsStarter)
    : ContactsFeatureApi {

    init {
        MainObject.repository = repository
    }

    override fun contactsStarter(): ContactsStarter = starter
}