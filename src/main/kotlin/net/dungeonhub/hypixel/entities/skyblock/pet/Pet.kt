package net.dungeonhub.hypixel.entities.skyblock.pet

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import me.nullicorn.nedit.type.NBTCompound
import me.nullicorn.nedit.type.NBTList
import net.dungeonhub.hypixel.entities.inventory.SkyblockRarity
import net.dungeonhub.provider.getAsJsonObjectOrNull
import net.dungeonhub.provider.getAsJsonPrimitiveOrNull
import java.util.*

// TODO map type to pet type
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
    // TODO make decent
    val goldenDragonLevels: List<Double>
        get() = GDRAG_LEVELS

    fun gdragExpToLevel(xp: Double): Int {
        for ((index, requiredXp) in goldenDragonLevels.withIndex()) {
            if (xp < requiredXp) return index
        }

        return goldenDragonLevels.size
    }

    companion object {
        val GDRAG_LEVELS = listOf(
            0,
            660,
            1_390,
            2_190,
            3_070,
            4_030,
            5_080,
            6_230,
            7_490,
            8_870,
            10_380,
            12_030,
            13_830,
            15_790,
            17_920,
            20_230,
            22_730,
            25_430,
            28_350,
            31_510,
            34_930,
            38_630,
            42_630,
            46_980,
            51_730,
            56_930,
            62_630,
            68_930,
            75_930,
            83_730,
            92_430,
            102_130,
            112_930,
            124_930,
            138_230,
            152_930,
            169_130,
            186_930,
            206_430,
            227_730,
            250_930,
            276_130,
            303_530,
            333_330,
            365_730,
            400_930,
            439_130,
            480_530,
            525_330,
            573_730,
            625_930,
            682_130,
            742_530,
            807_330,
            876_730,
            950_930,
            1_030_130,
            1_114_830,
            1_205_530,
            1_302_730,
            1_406_930,
            1_518_630,
            1_638_330,
            1_766_530,
            1_903_730,
            2_050_430,
            2_207_130,
            2_374_830,
            2_554_530,
            2_747_230,
            2_953_930,
            3_175_630,
            3_413_330,
            3_668_030,
            3_940_730,
            4_232_430,
            4_544_130,
            4_877_830,
            5_235_530,
            5_619_230,
            6_030_930,
            6_472_630,
            6_949_330,
            7_466_030,
            8_027_730,
            8_639_430,
            9_306_130,
            10_032_830,
            10_824_530,
            11_686_230,
            12_622_930,
            13_639_630,
            14_741_330,
            15_933_030,
            17_219_730,
            18_606_430,
            20_103_130,
            21_719_830,
            23_466_530,
            25_353_230,
            25_353_230,
            25_358_785,
            27_245_485,
            29_132_185,
            31_018_885,
            32_905_585,
            34_792_285,
            36_678_985,
            38_565_685,
            40_452_385,
            42_339_085,
            44_225_785,
            46_112_485,
            47_999_185,
            49_885_885,
            51_772_585,
            53_659_285,
            55_545_985,
            57_432_685,
            59_319_385,
            61_206_085,
            63_092_785,
            64_979_485,
            66_866_185,
            68_752_885,
            70_639_585,
            72_526_285,
            74_412_985,
            76_299_685,
            78_186_385,
            80_073_085,
            81_959_785,
            83_846_485,
            85_733_185,
            87_619_885,
            89_506_585,
            91_393_285,
            93_279_985,
            95_166_685,
            97_053_385,
            98_940_085,
            100_826_785,
            102_713_485,
            104_600_185,
            106_486_885,
            108_373_585,
            110_260_285,
            112_146_985,
            114_033_685,
            115_920_385,
            117_807_085,
            119_693_785,
            121_580_485,
            123_467_185,
            125_353_885,
            127_240_585,
            129_127_285,
            131_013_985,
            132_900_685,
            134_787_385,
            136_674_085,
            138_560_785,
            140_447_485,
            142_334_185,
            144_220_885,
            146_107_585,
            147_994_285,
            149_880_985,
            151_767_685,
            153_654_385,
            155_541_085,
            157_427_785,
            159_314_485,
            161_201_185,
            163_087_885,
            164_974_585,
            166_861_285,
            168_747_985,
            170_634_685,
            172_521_385,
            174_408_085,
            176_294_785,
            178_181_485,
            180_068_185,
            181_954_885,
            183_841_585,
            185_728_285,
            187_614_985,
            189_501_685,
            191_388_385,
            193_275_085,
            195_161_785,
            197_048_485,
            198_935_185,
            200_821_885,
            202_708_585,
            204_595_285,
            206_481_985,
            208_368_685,
            210_255_385
        ).map { it.toDouble() }
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

// TODO maybe remove this?
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