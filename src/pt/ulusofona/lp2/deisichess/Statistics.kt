package pt.ulusofona.lp2.deisichess

fun maisDeCincoCapturas(gameManager: GameManager): List<String> {
    val pieces = gameManager.getGameStatus().getTheBoard().getAllPieces()
    val filtered = pieces.values.filter { it.getCaptures()>5 }
    val sortedPieces = filtered.sortedByDescending { it.getCaptures() }
    return sortedPieces.map { "${if (it.getTeam() == 20) "BRANCA" else "PRETA"}:${it.getName()}:${it.getCaptures()}" }
}
fun topCincoCapturas(gameManager: GameManager): List<String> {
    val pieces = gameManager.getGameStatus().getTheBoard().getAllPieces()
    val filtered = pieces.values.filter { it.getCaptures()>=0 }
    val sortedPieces = filtered.sortedWith(
        compareByDescending<Piece> { it.getCaptures() }.thenByDescending { it.getName() }
    )
    val listResult = sortedPieces.map { "${it.getName()} ${if (it.getTeam() == 20) "(BRANCA)" else "(PRETA)"} fez ${it.getCaptures()} capturas" }
    return listResult.take(5)
}
fun topCincoPontos(gameManager: GameManager): List<String> {
    val pieces = gameManager.getGameStatus().getTheBoard().getAllPieces()
    val filtered = pieces.values.filter { it.getEarnedPoints()>0 }

    val sortedPieces = filtered.sortedWith(
        compareByDescending<Piece> { it.getEarnedPoints() } .thenBy { it.getName() }
    )

    val listResult = sortedPieces.map { "${it.getName()} ${if (it.getTeam() == 20) "(BRANCA)" else "(PRETA)"} tem ${it.getEarnedPoints()} pontos" }

    return listResult.take(5)
}
fun top3MaisBaralhadas(gameManager: GameManager): List<String> {
    val pieces = gameManager.getGameStatus().getTheBoard().getAllPieces()
    val filtered = pieces.values.filter { it.getInvalidMoves() > 0 }
    val sortedPieces = filtered.sortedWith(
        compareByDescending<Piece> { (it.getInvalidMoves()).toDouble() / (it.getInvalidMoves() + it.getValidMoves()).toDouble() } .thenBy { it.getName() }
    )
    val listResult = sortedPieces.map { "${it.getTeam()}:${it.getName()}:${it.getInvalidMoves()}:${it.getValidMoves()}" }
    return listResult.take(3)
}
fun tiposCapturados(gameManager: GameManager): List<String> {
    val pieces = gameManager.getGameStatus().getTheBoard().getAllPieces()
    val filtered = pieces.values.filter { !it.isInGame }
    val sortedPieces = filtered.sortedBy { it.getPieceNameType() }
    var listResult = sortedPieces.map { it.getPieceNameType() }
    listResult = listResult.distinct()
    return listResult
}

fun getStatsCalculator(stat: StatType): (GameManager) -> List<String> {
    when (stat){
        StatType.PECAS_MAIS_5_CAPTURAS -> return ::maisDeCincoCapturas
        StatType.TOP_5_CAPTURAS -> return ::topCincoCapturas
        StatType.TOP_5_PONTOS -> return ::topCincoPontos
        StatType.PECAS_MAIS_BARALHADAS -> return ::top3MaisBaralhadas
        StatType.TIPOS_CAPTURADOS -> return ::tiposCapturados
    }
}

