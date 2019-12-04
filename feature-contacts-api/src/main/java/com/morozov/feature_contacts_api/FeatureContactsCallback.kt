package com.morozov.feature_contacts_api

interface FeatureContactsCallback {

    fun onContactSelected(position: Int)
    fun onAddFriendClicked()
    fun onAddColleagueClicked()
}