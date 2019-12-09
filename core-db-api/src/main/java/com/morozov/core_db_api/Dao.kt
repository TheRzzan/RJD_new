package com.morozov.core_db_api

interface Dao {

    fun getItemsCount(): Int
    fun getItemAt(position: Int): DbContactModel?
    fun removeItem(item: DbContactModel): Boolean
    fun addItem(item: DbContactModel): Boolean
    fun updateItem(old: DbContactModel, new: DbContactModel): Boolean
}