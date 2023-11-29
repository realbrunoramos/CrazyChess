package pt.ulusofona.lp2.deisichess


fun fun1(gameManager: GameManager): List<String> {
    return listOf("String1", "String2")
}

fun getStatsCalculator(stat: StatType): (GameManager) -> List<String> {
    when (stat){
        StatType.PECAS_MAIS_5_CAPTURAS -> return ::fun1
        StatType.TOP_5_CAPTURAS -> TODO()
        StatType.TOP_5_PONTOS -> TODO()
        StatType.PECAS_MAIS_BARALHADAS -> TODO()
        StatType.TIPOS_CAPTURADOS -> TODO()
    }
}



/*
fun calculaMaximo(numeros: Array<Int>) : Int { … }
fun calculaMinimo(numeros: Array<Int>) : Int { … }
fun calculaMedia(numeros: Array<Int>) : Int { … }

fun getFuncaoCalculadora(tipo: TipoCalculo): Function1<Array<Int>,Int> {
    when (tipo) {
        TipoCalculo.MINIMO -> return ::calculaMinimo
        TipoCalculo.MAXIMO -> return ::calculaMaximo
        TipoCalculo.MEDIA -> return ::calculaMedia
    }
}
*/
