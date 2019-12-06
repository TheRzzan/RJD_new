package com.morozov.feature_contacts_impl.ui.fragments.contacts

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.morozov.core_repos_api.ContactModel
import com.morozov.feature_contacts_impl.ui.adapters.item_touch_helper.ItemTouchHelperCallback
import com.morozov.feature_contacts_impl.ui.fragments.MainObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

@SuppressLint("CheckResult")
class ContactsViewModel: ViewModel(), ItemTouchHelperCallback {

    private val contactsLiveData = MutableLiveData<ViewCommand>()
    private val contactsList: MutableList<ContactModel> = mutableListOf()

    init {
//        val repository = MainObject.repository
//        if (repository != null) {
//            repository.addItem(
//                ContactModel("Jeka", "Horfal", "", "44444",
//                    true, "", null, Date(), null, false)
//            )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe({}, {})
//
//            repository.addItem(
//                ContactModel("Sasha", "Fafin", "Lol", "12345",
//                    true, "", null, Date(), null, false)
//            )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe({}, {})
//
//            repository.addItem(
//                ContactModel("Aza", "Braza", "", "11111",
//                    false, "", "Golem", Date(), null, false)
//            )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe({}, {})
//
//            repository.addItem(
//                ContactModel("Koko", "Jambo", "", "222222",
//                    false, "", "Garlem", Date(), null, false)
//            )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe({}, {})
//
//            repository.addItem(
//                ContactModel("Hihi", "Hahi", "", "33333",
//                    false, "", "Durko", Date(), null, false)
//            )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe({}, {})
//        }

        showAll()
    }

    fun getContacts(): LiveData<ViewCommand> = contactsLiveData

    fun showAll() {
        observeList(Observer {
            contactsList.clear()
            contactsList.addAll(it)
            contactsLiveData.value = ViewCommand(ViewCommand.PersonLabel.ALL, ViewCommand.Command.SHOW_NEW_LIST, contactsList)
        })
    }

    fun showFriends() {
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
            ?.subscribe{
                onNext.onChanged(it)
            }
    }

    // ItemTouchHelperCallback impl
    var lastRemovePos: Int = -1
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