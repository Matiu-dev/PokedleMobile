package pl.matiu.pokebdemobile

object FlavorConfig {
    val isLocalServer: Boolean
        get() = BuildConfig.FLAVOR == "localServer"

    val isExternalServer: Boolean
        get() = BuildConfig.FLAVOR == "externalServer"
}