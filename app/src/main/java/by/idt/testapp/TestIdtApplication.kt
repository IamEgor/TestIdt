package by.idt.testapp

import android.app.Application
import by.idt.testapp.data.di.dataModule
import by.idt.testapp.di.appModule
import by.idt.testapp.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestIdtApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@TestIdtApplication)
            modules(appModule, dataModule, domainModule)
        }
    }
}
