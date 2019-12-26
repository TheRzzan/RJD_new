package com.morozov.feature_contacts_impl.ui.fragments.contacts

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.morozov.core_repos_api.ContactModel
import com.morozov.feature_contacts_impl.ui.adapters.item_touch_helper.ItemTouchHelperCallback
import com.morozov.feature_contacts_impl.ui.fragments.MainObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class ContactsViewModel: ViewModel(), ItemTouchHelperCallback {

    private val contactsLiveData = MutableLiveData<ViewCommand>()
    private val contactsList: MutableList<ContactModel> = mutableListOf()

    init {
        when (MainObject.isFriends) {
            null -> showAll()
            true -> showFriends()
            false -> showColleagues()
        }
    }

    fun getContacts(): LiveData<ViewCommand> = contactsLiveData

    fun getItemByPosition(position: Int): ContactModel? {
        return if (position < 0 || position >= contactsList.size)
            null
        else
            contactsList[position]
    }

    fun showAll() {
        MainObject.isFriends = null
        observeList(Observer {
            contactsList.clear()
            contactsList.addAll(it)
            contactsLiveData.value = ViewCommand(ViewCommand.PersonLabel.ALL, ViewCommand.Command.SHOW_NEW_LIST, contactsList)
        })
    }

    fun showFriends() {
        MainObject.isFriends = true
        observeList(Observer {
                contactsList.clear()
                for (item in it) {
                    if (item.isFriend)
                        contactsList.add(item)
                }
                contactsLiveData.value = ViewCommand(ViewCommand.PersonLabel.FRIEND, ViewCommand.Command.SHOW_NEW_LIST, contactsList)
        })
    }

    fun showColleagues() {
        MainObject.isFriends = false
        observeList(Observer {
            contactsList.clear()
            for (item in it) {
                if (!item.isFriend)
                    contactsList.add(item)
            }
            contactsLiveData.value = ViewCommand(ViewCommand.PersonLabel.COLLEAGUE, ViewCommand.Command.SHOW_NEW_LIST, contactsList)
        })
    }

    private fun observeList(onNext: Observer<List<ContactModel>>) {
        MainObject.repository?.getAllItems()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({
                onNext.onChanged(it)
            },{
                it.printStackTrace()
            })
    }

    // ItemTouchHelperCallback impl
    private var lastRemovePos: Int = -1
    private lateinit var removedModel: ContactModel

    override fun removeItem(position: Int) {
        val command = contactsLiveData.value
        if (command != null) {
            MainObject.repository?.removeItem(contactsList[position])
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                    {
                        if (it) {
                            lastRemovePos = position
                            removedModel = contactsList[position]
                            contactsList.removeAt(position)
                            contactsLiveData.value = ViewCommand(command.personLabel, ViewCommand.Command.UPDATE_OLD_LIST, contactsList)
                        }
                    },
                    {
                        it.printStackTrace()
                    }
                )
        }
    }

    override fun undoLastRemove() {
        val command = contactsLiveData.value
        if (command != null) {
            MainObject.repository?.addItem(removedModel)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                    {
                        if (it) {
                            contactsList.add(lastRemovePos, removedModel)
                            contactsLiveData.value = ViewCommand(command.personLabel, ViewCommand.Command.UPDATE_OLD_LIST, contactsList)
                        }
                    },
                    {
                        it.printStackTrace()
                    }
                )
        }
    }

    data class ViewCommand(val personLabel: PersonLabel,
                           val command: Command,
                           val data: List<ContactModel>?) {
        enum class PersonLabel {
            FRIEND,
            COLLEAGUE,
            ALL
        }
        enum class Command {
            SHOW_NEW_LIST,
            UPDATE_OLD_LIST
        }
    }
}