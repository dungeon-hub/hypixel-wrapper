package net.dungeonhub.hypixel.entities.player

enum class KnownSocialMediaType(override val apiName: String) : SocialMediaType {
    Discord("DISCORD"),
    Hypixel("HYPIXEL"),
    Twitter("TWITTER"),
    Youtube("YOUTUBE"),
    TikTok("TIKTOK"),
    Instagram("INSTAGRAM"),
    Twitch("TWITCH");

    class UnknownSocialMediaType(override val apiName: String) : SocialMediaType

    companion object {
        fun fromApiName(apiName: String): SocialMediaType =
            KnownSocialMediaType.entries.firstOrNull { it.apiName == apiName } ?: UnknownSocialMediaType(apiName)
    }
}