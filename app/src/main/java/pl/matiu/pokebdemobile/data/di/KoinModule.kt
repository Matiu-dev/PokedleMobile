package pl.matiu.pokebdemobile.data.di

import androidx.room.Room
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import pl.matiu.pokebdemobile.data.api.PokeApi
import org.koin.core.module.dsl.viewModelOf
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.data.repository.PokemonShotsRepositoryImpl
import pl.matiu.pokebdemobile.domain.PokemonRepository
import pl.matiu.pokebdemobile.domain.database.PokemonDatabase
import pl.matiu.pokebdemobile.presentation.PokemonViewModel

val mainModule = module {
    singleOf(::PokeApi)
    viewModelOf(::PokemonViewModel)
    singleOf( ::PokemonRepositoryImpl) { bind<PokemonRepository>() }
    singleOf( ::PokemonShotsRepositoryImpl) { bind<PokemonShotsRepositoryImpl>() }
    single {
        Room.databaseBuilder(
            get(),
            PokemonDatabase::class.java,
            "Pokemon_DB"
        ).build()
    }

    single {
        get<PokemonDatabase>().getPokemonDao()
    }

    single {
        get<PokemonDatabase>().getPokemonShotsDao()
    }
}