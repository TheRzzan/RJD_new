package com.morozov.feature_contacts_impl.ui.adapters.contacts

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.morozov.core_repos_api.ContactModel
import com.morozov.feature_contacts_impl.R
import com.morozov.feature_contacts_impl.ui.adapters.listeners.OnItemClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_card_recycler.view.*
import java.text.SimpleDateFormat

class ContactsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    @SuppressLint("CheckResult")
    fun populate(contactModel: ContactModel, position: Int, listener: OnItemClickListener) {
        if (contactModel.photo != null) {
            itemView.setOnClickListener {
                listener.onItemClick(itemView.imageCard, position)
            }

            Observable.create<Bitmap> { subscriber ->
                val imageUri = contactModel.photo
                val imageStream = itemView.context.contentResolver?.openInputStream(imageUri)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                imageStream?.close()
                subscriber.onNext(Bitmap.createScaledBitmap(selectedImage, 180, 180, false))
            }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        itemView.imageCard.setImageBitmap(it)
                    }, {
                        it.printStackTrace()
                    }, {
                        println("Done!")
                    }
                )

            itemView.textLetter.visibility = View.GONE
        } else {
            itemView.setOnClickListener {
                listener.onItemClick(null, position)
            }

            itemView.imageCard.setImageBitmap(null)
            itemView.imageCard.setImageDrawable(ColorDrawable(itemView.context.resources.getColor(R.color.colorPrimary)))
            itemView.textLetter.visibility = View.VISIBLE
        }

        ViewCompat.setTransitionName(itemView.imageCard, position.toString() + "contact_image")

        if (contactModel.name.isNotEmpty())
            itemView.textLetter.text = contactModel.name[0].toString()

        itemView.textNFS.text = "${contactModel.family} ${contactModel.name} ${contactModel.surname}"

        itemView.textPhoneNumber.text = contactModel.phoneNum

        if (contactModel.isFriend) {
            itemView.textBirthday.visibility = View.VISIBLE
            itemView.textPosition.visibility = View.GONE
            itemView.textWorkPhone.visibility = View.GONE

            itemView.textIsFriend.text = "Д"

            val dayMtYrFormat = SimpleDateFormat("dd.MM.yyyy")
            itemView.textBirthday.text = dayMtYrFormat.format(contactModel.birthday)
        } else {
            itemView.textBirthday.visibility = View.GONE
            itemView.textPosition.visibility = View.VISIBLE
            itemView.textWorkPhone.visibility = View.VISIBLE

            itemView.textIsFriend.text = "К"

            itemView.textPosition.text = contactModel.position
            itemView.textWorkPhone.text = contactModel.workPhone
        }
    }
}