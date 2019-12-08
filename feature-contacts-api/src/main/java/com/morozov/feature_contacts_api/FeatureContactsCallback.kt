package com.morozov.feature_contacts_api

interface FeatureContactsCallback {

    fun onContactSelected(phone: String, position: Int)
    fun onAddFriendClicked()
    fun onAddColleagueClicked()
}