package com.morozov.feature_editor_impl.ui.fragments.editor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.morozov.core_repos_api.ContactModel
import com.morozov.feature_editor_impl.R
import com.morozov.feature_editor_impl.databinding.FragmentEditorBinding
import com.morozov.feature_editor_impl.ui.fragments.MainObject
import com.morozov.feature_editor_impl.ui.handlers.EditorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_editor.*
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("CheckResult")
class EditorFragment: Fragment() {

    companion object {
        const val TAG = "EditorFragment_TAG"
    }

    /*
    * Calendar
    *
    * */
    var calendar = Calendar.getInstance()
    val calendarListener = DatePickerDialog.OnDateSetListener{ datePicker: DatePicker, year: Int, month: Int, day: Int ->
        val dayMtYrFormat = SimpleDateFormat("dd/MM/yyyy")
        val now = dayMtYrFormat.parse("$day/${month + 1}/$year")

        mContactModel.birthday = now
        mHandler.birthday.set("$day/${month + 1}/$year")
        mHandler.verifyIsReadyToSave()
    }

    private lateinit var mContactModel: ContactModel
    private val mHandler: EditorHandler

    init {
        if (MainObject.phoneNumber != null) {
            val phoneNumber = MainObject.phoneNumber
            if (phoneNumber != null){
                MainObject.repository?.getItemByPhone(phoneNumber)
                    ?.subscribe({
                        mContactModel = it
                    },{
                        it.printStackTrace()
                    })
            }
        } else {
            mContactModel = ContactModel("", "", "", "", MainObject.isFriend!!,
                "", "", null, null, false)
        }

        mHandler = object : EditorHandler(mContactModel) {
            override fun onSelectPhotoClicked(view: View) {
                val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                photoPickerIntent.type = "image/*"
                startActivityForResult(photoPickerIntent, 0)
            }

            override fun onSelectBirthdayClicked(view: View) {
                val dialog = DatePickerDialog(
                    view.context, calendarListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                dialog.show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentEditorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_editor, container, false)

        binding.contactModel = mContactModel
        binding.handler = mHandler

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCheck.setOnClickListener {
            if (mHandler.tmpContactModel.phoneNum.isEmpty())
                mHandler.tmpContactModel.phoneNum = mContactModel.phoneNum

            MainObject.repository?.updateItem(mHandler.tmpContactModel, mContactModel)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe({
                    if (it)
                        MainObject.callback?.onFinished()
                }, {
                    it.printStackTrace()
                })
        }

        buttonCross.setOnClickListener {
            activity?.onBackPressed()
        }

        prepareFragment()
    }

    private fun prepareFragment() {
        if (mContactModel.isFriend)
            prepareForFriend()
        else
            prepareForColleague()
    }

    private fun prepareForFriend() {
        textHeader.text = resources.getString(R.string.add_friend)

        editPosition.visibility = View.GONE
        editWorkPhone.visibility = View.GONE
        relativeDayMonthYear.visibility = View.VISIBLE

        mContactModel.isFriend = true
    }

    private fun prepareForColleague() {
        textHeader.text = resources.getString(R.string.add_colleague)

        editPosition.visibility = View.VISIBLE
        editWorkPhone.visibility = View.VISIBLE
        relativeDayMonthYear.visibility = View.GONE

        mContactModel.isFriend = false
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if (reqCode == 0 && resultCode == Activity.RESULT_OK) {
            try {
                val imageUri = data?.data
                val imageStream = activity?.contentResolver?.openInputStream(imageUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                imageCard.setImageBitmap(Bitmap.createBitmap(selectedImage))
                mContactModel.photo = imageUri
                mHandler.verifyIsReadyToSave()
                imageCamera.visibility = View.GONE
                imageStream?.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        }
    }
}