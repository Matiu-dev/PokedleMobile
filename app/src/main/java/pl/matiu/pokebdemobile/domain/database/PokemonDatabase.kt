package pl.matiu.pokebdemobile.domain.database

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteOpenHelper
import pl.matiu.pokebdemobile.domain.PokemonModel
import pl.matiu.pokebdemobile.domain.PokemonTypeConverter

@Database(entities = [PokemonModel::class], version = 1)
@TypeConverters(PokemonTypeConverter::class)
abstract class PokemonDatabase: RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDao
}