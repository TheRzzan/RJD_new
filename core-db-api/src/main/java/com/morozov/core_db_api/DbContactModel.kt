package com.morozov.core_db_api

import android.net.Uri
import java.util.*

data class DbContactModel(var name: String, var family: String, var surname: String?,
                          var phoneNum: String, var isFriend: Boolean,
                          var workPhone: String?, var position: String?,
                          var birthday: Date?, var photo: Uri? = null,
                          var saveInDevice: Boolean = false)