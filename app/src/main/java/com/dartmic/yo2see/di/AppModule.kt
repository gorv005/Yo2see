package com.dartmic.yo2see.di


import android.app.Application
import com.dartmic.yo2see.interfaces.ApplicationSchedulerProvider
import com.dartmic.yo2see.interfaces.SchedulerProvider
import com.dartmic.yo2see.managers.PreferenceManager
import com.dartmic.yo2see.ui.categories.CategoriesViewModel
import com.dartmic.yo2see.ui.categories.CategoryRepository
import com.dartmic.yo2see.ui.categories.CategoryRepositoryImpl
import com.dartmic.yo2see.ui.signup.RegistrationViewModel
import com.gsa.ui.login.*
import com.gsa.ui.register.RegisterRepository
import com.gsa.ui.register.RegisterRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object AppModule {


    /*  bean { PreferenceManager(androidApplication()) }

    bean { ActivityLifecycleManager() }

//     for splash activity
    viewModel { AuthViewModel(get()) }
    bean { AuthnticationRepository() }*/
    val appModule: Module = module {
        single { getSharedPrefrence(androidApplication()) }
        single { ApplicationSchedulerProvider() as SchedulerProvider }

        single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
        viewModel { LoginViewModel(get(), get(), get()) }

        single<RegisterRepository> { RegisterRepositoryImpl(get(), get()) }
        viewModel { RegistrationViewModel(get(), get()) }

        single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
        viewModel { CategoriesViewModel(get(), get(), get()) }

        single<ProductListRepository> { ProductListRepositoryImpl(get(), get()) }
        viewModel { ProductListnViewModel(get(), get(), get()) }


    }


    fun getSharedPrefrence(app: Application): PreferenceManager {
        return PreferenceManager(app)
    }
}