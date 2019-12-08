package com.morozov.rjd_new

import android.app.Application
import com.morozov.core_db_api.Dao
import com.morozov.core_db_impl.DaoImpl
import com.morozov.core_repos_api.Repository
import com.morozov.core_repos_impl.RepositoryImpl
import com.morozov.feature_contacts_api.ContactsFeatureApi
import com.morozov.feature_contacts_api.ContactsStarter
import com.morozov.feature_contacts_impl.ContactsFeatureImpl
import com.morozov.feature_contacts_impl.start.ContactsStarterImpl
import com.morozov.feature_editor_api.EditorFeatureApi
import com.morozov.feature_editor_api.EditorStarter
import com.morozov.feature_editor_impl.EditorFeatureImpl
import com.morozov.feature_editor_impl.start.EditorStarterImpl
import org.kodein.di.Kodein
import org.kodein.di.LazyKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class App: Application() {

    companion object {
        lateinit var kodein: LazyKodein
    }

    override fun onCreate() {
        super.onCreate()

        val dbModule = Kodein.Module("DataBase") {
            bind<Dao>() with singleton { DaoImpl(applicationContext) }
        }

        val reposModule = Kodein.Module("Repository") {
            bind<Repository>() with singleton { RepositoryImpl(instance()) }
        }

        val fContactsModule = Kodein.Module("FeatureContacts") {
            bind<ContactsStarter>() with singleton { ContactsStarterImpl() }
            bind<ContactsFeatureApi>() with singleton { ContactsFeatureImpl(instance(), instance()) }
        }

        val fEditorModule = Kodein.Module("FeatureEditor") {
            bind<EditorStarter>() with singleton { EditorStarterImpl() }
            bind<EditorFeatureApi>() with singleton { EditorFeatureImpl(instance(), instance()) }
        }

        kodein = Kodein.lazy {
            import(dbModule)
            import(reposModule)
            import(fContactsModule)
            import(fEditorModule)
        }
    }
}