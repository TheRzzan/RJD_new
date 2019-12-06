package com.morozov.rjd_new

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.morozov.feature_contacts_api.ContactsFeatureApi
import com.morozov.feature_contacts_api.FeatureContactsCallback
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein = App.kodein

    private val contactsFeatureApi: ContactsFeatureApi by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startContactsListFeature()
    }

    private fun startContactsListFeature() {
        contactsFeatureApi.contactsStarter().start(supportFragmentManager, R.id.contentMain,
            object : FeatureContactsCallback {
                override fun onContactSelected(position: Int) {
                    Toast.makeText(applicationContext, "Selected $position", Toast.LENGTH_SHORT).show()
                }

                override fun onAddFriendClicked() {
                    Toast.makeText(applicationContext, "Add friend", Toast.LENGTH_SHORT).show()
                }

                override fun onAddColleagueClicked() {
                    Toast.makeText(applicationContext, "Add colleague", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
