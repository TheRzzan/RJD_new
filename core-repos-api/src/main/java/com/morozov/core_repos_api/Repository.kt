package com.morozov.core_repos_api

import io.reactivex.Observable
import io.reactivex.Single

interface Repository {

    fun getItemsCount(): Single<Int>
    fun getItemAt(position: Int): Single<ContactModel>
    fun getAllItems(): Observable<List<ContactModel>>
    fun removeItem(item: ContactModel): Single<Boolean>
    fun addItem(item: ContactModel): Single<Boolean>
    fun updateItem(item: ContactModel): Single<Boolean>
}