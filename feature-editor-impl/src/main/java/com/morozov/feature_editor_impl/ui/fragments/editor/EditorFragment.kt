package com.morozov.feature_editor_impl.ui.fragments.editor

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
import kotlinx.android.synthetic.main.fragment_editor.*

class EditorFragment: Fragment() {

    companion object {
        const val TAG = "EditorFragment_TAG"
    }

    private val mContactModel: ContactModel
    private val mHandler: EditorHandler

    init {
        if (MainObject.phoneNumber != null) {
            mContactModel = ContactModel("", "", "", "", MainObject.isFriend!!,
                "", "", null, null, true)
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