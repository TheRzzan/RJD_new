package com.morozov.feature_contacts_api

import androidx.fragment.app.FragmentManager

interface ContactsStarter {

    fun start(manager: FragmentManager, container: Int, callback: FeatureContactsCallback)
}