package state

import apiutil.AppInstance
import java.net.http.HttpResponse
import java.util.*

data class ScenarioData(
    //AppInstance
    var appInstance: AppInstance? = null,

    // Response
    var apiResponse: HttpResponse<String>? = null,
    var requestBody: String? = null,
    var responseBody: String? = null,

    // create event
    val providerId: Long? = null,
    var title: String? = null,
    var text: String? = null,
    var thumbImage: String? = null,
    var image: String? = null,
    var externalLink: String? = null,
    var location: Location? = null,
    var isFeatured: Boolean? = true,
    var isRegional: Boolean? = true,
    var startDate: Date? = null,
    var endDate: Date? = null,
    val status: String? = null,
    val validity: Validity? = null
)



