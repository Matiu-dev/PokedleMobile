package pl.matiu.pokebdemobile.domain

data class PokemonModel(
    val name: String?,
    val typeList: List<String>?,
    val color: String?,
    val environment: String?,
    val evolutionStage: Int?,
    val averageHeight: Float?,
    val averageWeight: Float?,
    val isLegendary: Boolean,
    val isMythical: Boolean
)