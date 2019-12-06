package com.morozov.feature_contacts_impl.ui.fragments.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.morozov.feature_contacts_impl.R
import com.morozov.feature_contacts_impl.ui.adapters.contacts.ContactsAdapter
import com.morozov.feature_contacts_impl.ui.adapters.item_touch_helper.ItemTouchHelperClass
import com.morozov.feature_contacts_impl.ui.adapters.listeners.OnItemClickListener
import com.morozov.feature_contacts_impl.ui.fragments.MainObject
import kotlinx.android.synthetic.main.fragment_contacts_list.*

class ContactsFragment: Fragment(), OnItemClickListener {

    companion object {
        const val TAG = "ContactsFragment_TAG"
    }

    private lateinit var viewModel: ContactsViewModel

    lateinit var adapter: ContactsAdapter
    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_contacts_list, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[ContactsViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        viewModel.getContacts().observeForever {
            when (it.command) {
                ContactsViewModel.ViewCommand.Command.UPDATE_OLD_LIST -> {
                    adapter.notifyDataSetChanged()
                }
                ContactsViewModel.ViewCommand.Command.SHOW_NEW_LIST -> {
                    it.data?.let { it1 -> adapter.setData(it1) }
                }
            }

            val colorPrimary = context?.resources?.getColor(R.color.colorPrimary)
            val colorSecondary = context?.resources?.getColor(R.color.secondary_text)

            colorSecondary?.let { it1 -> textFriends.setTextColor(it1) }
            colorSecondary?.let { it1 -> textColleagues.setTextColor(it1) }
            colorSecondary?.let { it1 -> textAll.setTextColor(it1) }

            when (it.personLabel) {
                ContactsViewModel.ViewCommand.PersonLabel.FRIEND -> {
                    colorPrimary?.let { it1 -> textFriends.setTextColor(it1) }
                }
                ContactsViewModel.ViewCommand.PersonLabel.COLLEAGUE -> {
                    colorPrimary?.let { it1 -> textColleagues.setTextColor(it1) }
                }
                ContactsViewModel.ViewCommand.PersonLabel.ALL -> {
                    colorPrimary?.let { it1 -> textAll.setTextColor(it1) }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textAll.setOnClickListener {
            viewModel.showAll()
        }
        textFriends.setOnClickListener {
            viewModel.showFriends()
        }
        textColleagues.setOnClickListener {
            viewModel.showColleagues()
        }

        adapter = ContactsAdapter(this, viewModel, relativeContacts)
        recyclerContacts.adapter = adapter
        recyclerContacts.layoutManager = LinearLayoutManager(context)

        val callback = ItemTouchHelperClass(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerContacts)

        buttonAdd.setOnClickListener {

        }

        buttonFriend.setOnClickListener {
            MainObject.callback?.onAddFriendClicked()
        }

        buttonColleague.setOnClickListener {
            MainObject.callback?.onAddColleagueClicked()
        }
    }

    // OnItemClickListener impl
    override fun onItemClick(view: View?, position: Int) {
        MainObject.callback?.onContactSelected(position)
    }
}