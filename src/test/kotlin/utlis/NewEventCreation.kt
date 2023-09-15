package utlis

import state.Location
import state.Validity
import java.util.*
import kotlin.random.Random

data class NewEventCreation(
    val providerId: Long? = generateRandomProviderId(),
    var title: String? = generateRandomTitle(),
    var text: String? = generateRandomText(),
    var thumbImage: String? = generateRandomImageURL(),
    var image: String? = generateRandomImageURL(),
    var externalLink: String? = generateRandomExternalLink(),
    var location: Location? = generateRandomLocation(),
    var isFeatured: Boolean? = generateRandomBoolean(),
    var isRegional: Boolean? = generateRandomBoolean(),
    var startDate: Date? = generateRandomDate(),
    var endDate: Date? = generateRandomDate(),
    val status: String? = generateRandomStatus(),
    val validity: Validity? = generateRandomValidity()
)

fun generateRandomProviderId(): Long {
    return Random.nextLong()
}

fun generateRandomTitle(): String {
    return "Generated Title"
}

fun generateRandomText(): String {
    return "Generated Text"
}

fun generateRandomImageURL(): String {
    return "http://example.com/generated-image-${Random.nextInt(100)}.jpg"
}

fun generateRandomExternalLink(): String {
    return "http://example.com/generated-link-${Random.nextInt(100)}"
}

fun generateRandomLocation(): Location {
    return Location(
        lat = Random.nextDouble(32.00000,33.00000),
        lng = Random.nextDouble(-97.00000,-96.00000),
        address = "Generated Address"
    )
}

fun generateRandomBoolean(): Boolean {
    return Random.nextBoolean()
}

fun generateRandomDate(): Date {
    return Date()
}

fun generateRandomStatus(): String {
    val statuses = listOf("ACTIVE", "INACTIVE", "PENDING")
    return statuses.random()
}

fun generateRandomValidity(): Validity {
    return Validity(
        startDate = generateRandomDate(),
        endDate = generateRandomDate()
    )
}


