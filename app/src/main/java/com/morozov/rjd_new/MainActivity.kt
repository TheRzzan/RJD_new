package com.morozov.rjd_new

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.morozov.feature_contacts_api.ContactsFeatureApi
import com.morozov.feature_contacts_api.FeatureContactsCallback
import com.morozov.feature_editor_api.EditorFeatureApi
import com.morozov.feature_editor_api.FeatureEditorCallback
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein = App.kodein

    private val contactsFeatureApi: ContactsFeatureApi by instance()
    private val editorFeatureApi: EditorFeatureApi by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startContactsListFeature()
    }

    private fun startContactsListFeature() {
        contactsFeatureApi.contactsStarter().start(supportFragmentManager, R.id.contentMain,
            object : FeatureContactsCallback {
                override fun onContactSelected(phone: String, position: Int) {
                    startEditorFeature(phone)
                }

                override fun onAddFriendClicked() {
                    startEditorFeature(true)
                }

                override fun onAddColleagueClicked() {
                    startEditorFeature(false)
                }
            })
    }

    private fun startEditorFeature(isFriend: Boolean) {
        editorFeatureApi.editorStarter().start(isFriend, supportFragmentManager, R.id.contentMain,
            object : FeatureEditorCallback {
                override fun onFinished() {
                    onBackPressed()
                }
            })
    }

    private fun startEditorFeature(contactNumber: String) {
        editorFeatureApi.editorStarter().start(contactNumber, supportFragmentManager, R.id.contentMain,
            object : FeatureEditorCallback {
                override fun onFinished() {
                    onBackPressed()
                }
            })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1)
            supportFragmentManager.popBackStack()
        super.onBackPressed()
    }

    private fun clearBackStack() {
        for (i in 0..supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }
}
