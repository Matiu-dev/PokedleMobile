package pl.matiu.pokebdemobile.data.repository

import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.mapper.toPokemonShots
import pl.matiu.pokebdemobile.domain.PokemonRepository
import pl.matiu.pokebdemobile.domain.PokemonShotsRepository
import pl.matiu.pokebdemobile.domain.database.PokemonDao
import pl.matiu.pokebdemobile.domain.database.PokemonShotsDao
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.entity.PokemonShots

class PokemonShotsRepositoryImpl: PokemonShotsRepository {

    private val pokemonShotsDao: PokemonShotsDao by inject(PokemonShotsDao::class.java)

    override fun getAllPokemonShoots(): List<PokemonShots> {
        return pokemonShotsDao.getAllPokemonShoots()
    }

    override fun addPokemonShot(pokemonModel: PokemonModel) {
        pokemonShotsDao.insertShotPokemon(pokemonModel.toPokemonShots(pokemonModel))
    }

    override fun deleteAllDataAfterWin() {
        pokemonShotsDao.deleteAllData()
    }
}