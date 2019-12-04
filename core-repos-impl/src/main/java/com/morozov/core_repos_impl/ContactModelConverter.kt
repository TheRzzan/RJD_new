package com.morozov.core_repos_impl

import com.morozov.core_db_api.DbContactModel
import com.morozov.core_repos_api.ContactModel

object ContactModelConverter {

    fun convertFrom(item: DbContactModel): ContactModel {
        return ContactModel(item.name, item.family, item.surname,
            item.phoneNum, item.isFriend, item.workPhone,
            item.position, item.birthday, item.photo,
            item.saveInDevice)
    }

    fun convertToDbModel(item: ContactModel): DbContactModel {
        return DbContactModel(item.name, item.family, item.surname,
            item.phoneNum, item.isFriend, item.workPhone,
            item.position, item.birthday, item.photo,
            item.saveInDevice)
    }
}