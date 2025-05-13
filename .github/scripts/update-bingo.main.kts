import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.writeText

val bingoEndpoint = URL("https://api.hypixel.net/v2/resources/skyblock/bingo")

fun getBingoData(): String {
    println("Fetching the current Bingo data...")
    return bingoEndpoint.readText(StandardCharsets.UTF_8)
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