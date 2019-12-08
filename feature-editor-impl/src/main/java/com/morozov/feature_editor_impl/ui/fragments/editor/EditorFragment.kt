package com.morozov.feature_editor_impl.ui.fragments.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.morozov.core_repos_api.ContactModel
import com.morozov.feature_editor_impl.R
import com.morozov.feature_editor_impl.databinding.FragmentEditorBinding
import com.morozov.feature_editor_impl.ui.fragments.MainObject
import com.morozov.feature_editor_impl.ui.handlers.EditorHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_editor.*

@SuppressLint("CheckResult")
class EditorFragment: Fragment() {

    companion object {
        const val TAG = "EditorFragment_TAG"
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
            } else {
                mContactModel = ContactModel("NO", "", "", "", MainObject.isFriend!!,
                    "", "", null, null, false)
            }
        } else {
            mContactModel = ContactModel("", "", "", "", MainObject.isFriend!!,
                "", "", null, null, false)
        }

        mHandler = object : EditorHandler(mContactModel) {
            override fun onSelectPhotoClicked(view: View) {

            }

            override fun onSelectBirthdayClicked(view: View) {

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
            MainObject.callback?.onFinished()
        }
    }
}