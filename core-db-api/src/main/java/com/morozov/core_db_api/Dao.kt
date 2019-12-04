package com.morozov.core_db_api

interface Dao {

    fun getItemsCount(): Int
    fun getItemAt(position: Int): DbContactModel?
    fun removeIten(item: DbContactModel): Boolean
    fun addItem(item: DbContactModel): Boolean
    fun updateItem(item: DbContactModel): Boolean
}