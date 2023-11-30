package pt.ulusofona.lp2.deisichess


fun maisDeCinco(gameManager: GameManager): List<String> {
    return gameManager.statistList(StatType.PECAS_MAIS_5_CAPTURAS)
}
fun topCincoCapturas(gameManager: GameManager): List<String> {
    return gameManager.statistList(StatType.TOP_5_CAPTURAS)
}
fun topCincoPontos(gameManager: GameManager): List<String> {
    return gameManager.statistList(StatType.TOP_5_PONTOS)
}
fun maisBaralhadas(gameManager: GameManager): List<String> {
    return gameManager.statistList(StatType.PECAS_MAIS_BARALHADAS)
}
fun tiposCapturados(gameManager: GameManager): List<String> {
    return gameManager.statistList(StatType.TIPOS_CAPTURADOS)
}

fun getStatsCalculator(stat: StatType): (GameManager) -> List<String> {
    when (stat){
        StatType.PECAS_MAIS_5_CAPTURAS -> return ::maisDeCinco
        StatType.TOP_5_CAPTURAS -> return ::topCincoCapturas
        StatType.TOP_5_PONTOS -> return ::topCincoPontos
        StatType.PECAS_MAIS_BARALHADAS -> return ::maisBaralhadas
        StatType.TIPOS_CAPTURADOS -> return ::tiposCapturados
    }
}

