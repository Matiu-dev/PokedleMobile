package pl.matiu.pokebdemobile.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonTypeConverter
import pl.matiu.pokebdemobile.domain.entity.PokemonShots

@Database(entities = [PokemonModel::class, PokemonShots::class], version = 2)
@TypeConverters(PokemonTypeConverter::class)
abstract class PokemonDatabase: RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDao
    abstract fun getPokemonShotsDao(): PokemonShotsDao
}