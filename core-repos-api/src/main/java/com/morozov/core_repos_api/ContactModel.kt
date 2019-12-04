package com.morozov.core_repos_api

import android.net.Uri
import java.util.*

data class ContactModel(var name: String, var family: String, var surname: String?,
                          var phoneNum: String, var isFriend: Boolean,
                          var workPhone: String?, var position: String?,
                          var birthday: Date?, var photo: Uri? = null,
                          var saveInDevice: Boolean = false)