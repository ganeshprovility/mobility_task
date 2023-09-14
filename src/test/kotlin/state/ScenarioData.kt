package state

import apiutil.AppInstance
import java.net.http.HttpResponse

data class ScenarioData (
    //AppInstance
    var appInstance: AppInstance? = null,

    // Response
    var apiResponse: HttpResponse<String>? = null,
    var requestBody: String? = null,

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
    var startDate: String? = null,
    var endDate: String? = null,
    val status: String? = null,
    val validity: Validity? = null
)


