package com.morozov.core_repos_impl

import com.morozov.core_db_api.Dao
import com.morozov.core_repos_api.ContactModel
import com.morozov.core_repos_api.Repository
import io.reactivex.Observable
import io.reactivex.Single
import java.util.NoSuchElementException

class RepositoryImpl(private val dao: Dao): Repository {

    override fun getItemsCount(): Single<Int> {
        return Single.fromCallable {
            return@fromCallable dao.getItemsCount()
        }
    }

    override fun getItemAt(position: Int): Single<ContactModel> {
        return Single.fromCallable {
            val model = dao.getItemAt(position) ?: throw NoSuchElementException("No contact model at index $position")
            return@fromCallable ContactModelConverter.convertFrom(model)
        }
    }

    override fun getItemByPhone(phone: String): Single<ContactModel> {
        return Single.fromCallable {
            var model: ContactModel? = null

            for (i in 0..dao.getItemsCount()) {
                val tmpModel = dao.getItemAt(i) ?: continue
                if (phone == tmpModel.phoneNum){
                    model = ContactModelConverter.convertFrom(tmpModel)
                    break
                }
            }

            return@fromCallable model
        }
    }

    override fun getAllItems(): Observable<List<ContactModel>> {
        return Observable.create{ subscriber ->
            val listContact = mutableListOf<ContactModel>()
            for (i in 0..dao.getItemsCount()) {
                val tmpModel = dao.getItemAt(i) ?: continue
                listContact.add(ContactModelConverter.convertFrom(tmpModel))
            }
            subscriber.onNext(listContact)
        }
    }

    override fun removeItem(item: ContactModel): Single<Boolean> {
        return Single.fromCallable {
            return@fromCallable dao.removeItem(ContactModelConverter.convertToDbModel(item))
        }
    }

    override fun addItem(item: ContactModel): Single<Boolean> {
        return Single.fromCallable {
            return@fromCallable dao.addItem(ContactModelConverter.convertToDbModel(item))
        }
    }

    override fun updateItem(old: ContactModel, new: ContactModel): Single<Boolean> {
        return Single.fromCallable {
            return@fromCallable dao.updateItem(ContactModelConverter.convertToDbModel(old),
                                               ContactModelConverter.convertToDbModel(new))
        }
    }
}