package com.morozov.feature_contacts_impl.start

import androidx.fragment.app.FragmentManager
import com.morozov.feature_contacts_api.ContactsStarter
import com.morozov.feature_contacts_api.FeatureContactsCallback
import com.morozov.feature_contacts_impl.ui.fragments.MainObject
import com.morozov.feature_contacts_impl.ui.fragments.contacts.ContactsFragment

class ContactsStarterImpl: ContactsStarter {

    override fun start(manager: FragmentManager, container: Int, callback: FeatureContactsCallback) {
        MainObject.callback = callback

        manager.beginTransaction()
            .replace(container, ContactsFragment())
            .addToBackStack(ContactsFragment.TAG)
            .commit()
    }
}