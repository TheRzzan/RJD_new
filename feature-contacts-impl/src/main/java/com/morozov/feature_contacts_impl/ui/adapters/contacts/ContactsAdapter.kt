package com.morozov.feature_contacts_impl.ui.adapters.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.morozov.core_repos_api.ContactModel
import com.morozov.feature_contacts_impl.R
import com.morozov.feature_contacts_impl.ui.adapters.ListAdapter
import com.morozov.feature_contacts_impl.ui.adapters.item_touch_helper.ItemTouchHelperCallback
import com.morozov.feature_contacts_impl.ui.adapters.item_touch_helper.ItemTouchHelperClass
import com.morozov.feature_contacts_impl.ui.adapters.listeners.OnItemClickListener

class ContactsAdapter(private val listener: OnItemClickListener,
                      private val itemCallback: ItemTouchHelperCallback,
                      private val view: View):
    ListAdapter<ContactModel, ContactsViewHolder>(),
    ItemTouchHelperClass.ItemTouchHelperAdapter{

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ContactsViewHolder =
        ContactsViewHolder(
            LayoutInflater.from(container.context).inflate(
                R.layout.item_card_recycler,
                container,
                false
            )
        )

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.populate(data()[position], position, listener)
    }

    /*
    * ItemTouchHelperAdapter implementation
    *
    * */
    override fun onItemRemoved(position: Int) {
        itemCallback.removeItem(position)

        Snackbar.make(view, "Удалено", Snackbar.LENGTH_LONG)
            .setAction("Отмена") {
                itemCallback.undoLastRemove()
            }.show()
    }
}