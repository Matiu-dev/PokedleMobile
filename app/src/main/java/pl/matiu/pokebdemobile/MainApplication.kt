package pl.matiu.pokebdemobile

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.matiu.pokebdemobile.data.di.mainModule
import pl.matiu.pokebdemobile.domain.database.PokemonDatabase

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(mainModule)
        }
    }
}