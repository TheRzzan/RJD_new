package com.morozov.core_db_impl

import android.content.Context
import com.morozov.core_db_api.Dao
import com.morozov.core_db_api.DbContactModel

class DaoImpl(context: Context): Dao {

    private val dbHelper = ContactsDBHelper(context)

    override fun getItemsCount(): Int {
        return dbHelper.getCount()
    }

    override fun getItemAt(position: Int): DbContactModel? {
        return dbHelper.getItemAt(position)
    }

    override fun removeItem(item: DbContactModel): Boolean {
        dbHelper.removeItemWithPhone(item.phoneNum)
        return true
    }

    override fun addItem(item: DbContactModel): Boolean {
        dbHelper.addContact(item)
        return true
    }

    override fun updateItem(old: DbContactModel, new: DbContactModel): Boolean {
        dbHelper.removeItemWithPhone(old.phoneNum)
        dbHelper.addContact(new)
        return true
    }
}