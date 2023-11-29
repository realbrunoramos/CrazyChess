package pt.ulusofona.lp2.deisichess


fun fun1(gameManager: GameManager): List<String> {
    // Your implementation here
    return listOf("String1", "String2") // Replace this with your actual implementation
}

fun getStatsCalculator(a: StatType): (GameManager) -> List<String> {
    return { gameManager ->
        fun1(gameManager) // Replace this with the actual creation of StatsCalculator
    }
}

/*
fun  getStatsCalculator(stat : StatType){
    when (stat){
        StatType.PECAS_MAIS_5_CAPTURAS ->
    }
}
*/

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
