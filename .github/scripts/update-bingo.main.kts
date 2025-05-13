@file:DependsOn("com.google.code.gson:gson:2.13.1")

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.writeText

val bingoEndpoint = URL("https://api.hypixel.net/v2/resources/skyblock/bingo")

val gson: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

fun getBingoData(): String {
    println("Fetching the current Bingo data...")
    val json = bingoEndpoint.readText(StandardCharsets.UTF_8)

    print("Formatting the data...")
    return gson.toJson(gson.fromJson(json, JsonObject::class.java))
}

fun getBingoFile(): Path {
    val date = DateTimeFormatter.ofPattern("yyyy-MM").format(LocalDate.now())
    val filePath = "src/test/resources/resources/skyblock_bingo/skyblock_bingo-$date.json"

    println("This months Bingo will be stored in $filePath")
    return Path(filePath)
}

val currentData = getBingoData()

if (currentData.isBlank()) {
    throw RuntimeException("The fetched data is empty! Aborting...")
}

val file = getBingoFile()

try {
    print("Creating the file...")
    file.createFile()
} catch (_: FileAlreadyExistsException) {
    println("File already exists, overwriting the content...")
}

print("Writing data...")
file.writeText(currentData)

println("Done!")