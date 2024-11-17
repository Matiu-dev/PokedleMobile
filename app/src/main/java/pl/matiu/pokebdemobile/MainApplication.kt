package pl.matiu.pokebdemobile

import android.app.Application
import org.koin.core.context.startKoin
import pl.matiu.pokebdemobile.data.di.mainModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(mainModule)
        }
    }
}