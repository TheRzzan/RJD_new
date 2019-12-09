package com.morozov.core_repos_api

import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

data class ContactModel(var name: String, var family: String, var surname: String?,
                          var phoneNum: String, var isFriend: Boolean,
                          var workPhone: String?, var position: String?,
                          var birthday: Date?, var photo: Uri? = null,
                          var saveInDevice: Boolean = false) {
    override fun equals(other: Any?): Boolean {
        if (other is ContactModel) {
            val dayMtYrFormat = SimpleDateFormat("dd/MM/yyyy")
            var b = name == other.name &&
                    family == other.family &&
                    surname == other.surname &&
                    phoneNum == other.phoneNum &&
                    isFriend == other.isFriend &&
                    workPhone == other.workPhone &&
                    position == other.position &&
                    photo == other.photo &&
                    saveInDevice == other.saveInDevice

            b = if (birthday != null && other.birthday != null) {
                b && dayMtYrFormat.format(birthday) == dayMtYrFormat.format(other.birthday)
            } else {
                b && birthday == other.birthday
            }

            return b
        } else {
            return super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + family.hashCode()
        result = 31 * result + (surname?.hashCode() ?: 0)
        result = 31 * result + phoneNum.hashCode()
        result = 31 * result + isFriend.hashCode()
        result = 31 * result + (workPhone?.hashCode() ?: 0)
        result = 31 * result + (position?.hashCode() ?: 0)
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + (photo?.hashCode() ?: 0)
        result = 31 * result + saveInDevice.hashCode()
        return result
    }
}