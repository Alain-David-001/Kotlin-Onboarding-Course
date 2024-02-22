package jetbrains.kotlin.course.almost.done

fun trimPicture(picture: String):String = picture.trimIndent()

fun applyBordersFilter(picture: String):String {
    val width:Int = getPictureWidth(picture)
    val result = StringBuilder()
    result.append("$borderSymbol".repeat(width+4) + newLineSymbol)
    for(x in picture.lines()){
        result.append(borderSymbol + "$separator" + x)
        result.append("$separator".repeat(width-x.count()))
        result.append(separator + "$borderSymbol" + newLineSymbol)
    }
    result.append("$borderSymbol".repeat(width+4))
    return result.toString()
}

fun applySquaredFilter(picture: String):String {
    val width:Int = getPictureWidth(picture)
    val result = StringBuilder()
    result.append("$borderSymbol".repeat(2*(width+4)) + newLineSymbol)
    for(x in picture.lines()){
        result.append(borderSymbol + "$separator" + x + "$separator".repeat(width-x.count()) + separator + borderSymbol)
        result.append(borderSymbol + "$separator" + x + "$separator".repeat(width-x.count()) + separator + borderSymbol)
        result.append(newLineSymbol)
    }
    result.append("$borderSymbol".repeat(2*(width+4)) + newLineSymbol)
    for(x in picture.lines()){
        result.append(borderSymbol + "$separator" + x + "$separator".repeat(width-x.count()) + separator + borderSymbol)
        result.append(borderSymbol + "$separator" + x + "$separator".repeat(width-x.count()) + separator + borderSymbol)
        result.append(newLineSymbol)
    }
    result.append("$borderSymbol".repeat(2*(width+4)))
    return result.toString()
}

fun applyFilter(picture: String, filterName: String):String {
    return when (filterName){
        "borders" -> applyBordersFilter(trimPicture(picture))
        "squared" -> applySquaredFilter((trimPicture(picture)))
        else -> error("unrecognized filter name")
    }
}

fun safeReadLine():String  {
    val userInput = readlnOrNull()
    return userInput ?: error("null value received")
}

fun chooseFilter(): String {
    println("Please choose the filter: 'borders' or 'squared'")
    var filter = safeReadLine()
    while(filter !in listOf("borders", "squared")) {
        println("Please input 'borders' or 'squared'")
        filter = safeReadLine()
    }
    return filter
}

fun choosePicture():String {
    println("Please choose a picture. The possible options are: ${allPictures()}")
    val input = readln()
    return getPictureByName(input) ?: choosePicture()
}

fun getPicture():String {
    println("Do you want to use a predefined picture or a custom one? Please input 'yes' for a predefined image or 'no' for a custom one")
    var input = safeReadLine()
    while(input != "yes" && input!="no"){
        println("Please input 'yes' or 'no'")
        input = safeReadLine()
    }
    return if (input == "yes") choosePicture() else safeReadLine()
}

fun photoshop():Unit{
    val picture = getPicture()
    val filter = chooseFilter()
    println("The old image:")
    println(picture)
    println("The transformed picture:")
    println(applyFilter(picture,filter))
}

fun main() {
    // Uncomment this code on the last step of the game

    photoshop()
}
