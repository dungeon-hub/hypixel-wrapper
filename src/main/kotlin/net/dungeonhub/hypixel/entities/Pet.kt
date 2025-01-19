package net.dungeonhub.hypixel.entities

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import me.nullicorn.nedit.type.NBTCompound
import me.nullicorn.nedit.type.NBTList
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.util.*

//TODO map type to pet type
class Pet(
    val uuid: UUID?,
    val uniqueId: UUID?,
    val type: String,
    val exp: Double,
    val active: Boolean,
    val tier: SkyblockRarity,
    val heldItem: PetItem?,
    val candyUsed: Int,
    val skin: String?,
    val extraData: JsonObject?
) {
    //TODO make decent
    val goldenDragonLevels: List<Double>
        get() = listOf(
            0,
            660,
            1390,
            2190,
            3070,
            4030,
            5080,
            6230,
            7490,
            8870,
            10380,
            12030,
            13830,
            15790,
            17920,
            20230,
            22730,
            25430,
            28350,
            31510,
            34930,
            38630,
            42630,
            46980,
            51730,
            56930,
            62630,
            68930,
            75930,
            83730,
            92430,
            102130,
            112930,
            124930,
            138230,
            152930,
            169130,
            186930,
            206430,
            227730,
            250930,
            276130,
            303530,
            333330,
            365730,
            400930,
            439130,
            480530,
            525330,
            573730,
            625930,
            682130,
            742530,
            807330,
            876730,
            950930,
            1030130,
            1114830,
            1205530,
            1302730,
            1406930,
            1518630,
            1638330,
            1766530,
            1903730,
            2050430,
            2207130,
            2374830,
            2554530,
            2747230,
            2953930,
            3175630,
            3413330,
            3668030,
            3940730,
            4232430,
            4544130,
            4877830,
            5235530,
            5619230,
            6030930,
            6472630,
            6949330,
            7466030,
            8027730,
            8639430,
            9306130,
            10032830,
            10824530,
            11686230,
            12622930,
            13639630,
            14741330,
            15933030,
            17219730,
            18606430,
            20103130,
            21719830,
            23466530,
            25353230,
            25353230,
            25358785,
            27245485,
            29132185,
            31018885,
            32905585,
            34792285,
            36678985,
            38565685,
            40452385,
            42339085,
            44225785,
            46112485,
            47999185,
            49885885,
            51772585,
            53659285,
            55545985,
            57432685,
            59319385,
            61206085,
            63092785,
            64979485,
            66866185,
            68752885,
            70639585,
            72526285,
            74412985,
            76299685,
            78186385,
            80073085,
            81959785,
            83846485,
            85733185,
            87619885,
            89506585,
            91393285,
            93279985,
            95166685,
            97053385,
            98940085,
            100826785,
            102713485,
            104600185,
            106486885,
            108373585,
            110260285,
            112146985,
            114033685,
            115920385,
            117807085,
            119693785,
            121580485,
            123467185,
            125353885,
            127240585,
            129127285,
            131013985,
            132900685,
            134787385,
            136674085,
            138560785,
            140447485,
            142334185,
            144220885,
            146107585,
            147994285,
            149880985,
            151767685,
            153654385,
            155541085,
            157427785,
            159314485,
            161201185,
            163087885,
            164974585,
            166861285,
            168747985,
            170634685,
            172521385,
            174408085,
            176294785,
            178181485,
            180068185,
            181954885,
            183841585,
            185728285,
            187614985,
            189501685,
            191388385,
            193275085,
            195161785,
            197048485,
            198935185,
            200821885,
            202708585,
            204595285,
            206481985,
            208368685,
            210255385
        ).map { it.toDouble() }

    fun gdragExpToLevel(xp: Double): Int {
        for((index, requiredXp) in goldenDragonLevels.withIndex()) {
            if(xp < requiredXp) return index
        }

        return goldenDragonLevels.size
    }
}

fun JsonObject.toPet(): Pet {
    return Pet(
        getAsJsonPrimitiveOrNull("uuid")?.asString?.let { UUID.fromString(it) },
        getAsJsonPrimitiveOrNull("uniqueId")?.asString?.let { UUID.fromString(it) },
        getAsJsonPrimitive("type").asString,
        getAsJsonPrimitive("exp").asDouble,
        getAsJsonPrimitive("active").asBoolean,
        SkyblockRarity.fromApiName(getAsJsonPrimitive("tier").asString),
        getAsJsonPrimitiveOrNull("heldItem")?.asString?.let { KnownPetItem.fromApiName(it) },
        getAsJsonPrimitiveOrNull("candyUsed")?.asInt ?: 0,
        getAsJsonPrimitiveOrNull("skin")?.asString,
        getAsJsonObjectOrNull("extra")
    )
}

//TODO maybe remove this?
fun NBTCompound.toJsonObject(): JsonObject {
    val result = JsonObject()

    entries.forEach { entry ->
        result.add(entry.key, parseNbtContent(entry.value))
    }

    return result
}

fun NBTList.toJsonArray(): JsonArray {
    val result = JsonArray()

    toList().forEach { result.add(parseNbtContent(it)) }

    return result
}

private fun parseNbtContent(content: Any): JsonElement {
    return when (content) {
        is NBTCompound -> content.toJsonObject()
        is NBTList -> content.toJsonArray()
        is Number -> JsonPrimitive(content)
        else -> JsonPrimitive(content.toString())
    }
}