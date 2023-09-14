package apiutil

import java.net.URLEncoder
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SSGSigner(
    private val tenantId: Int,
    private val devKey: String,
    private val devSecret: String,
    ssgCredentials: SSGCredentials?
) {
    private val authHmac = Mac.getInstance("HMACSHA256").apply { init(SecretKeySpec(devSecret.toByteArray(), "AES")) }
    private val signHmac by lazy {
        Mac.getInstance("HMACSHA256").apply { init(SecretKeySpec(decryptedAppSecret, "AES")) }
    }
    private val rsaCredentials: RSACredentials = ssgCredentials?.rsaCredentials ?: generateRsaCredentials()

    private var appInstance: AppInstance? = ssgCredentials?.appInstance

    private val decryptedAppSecret by lazy {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding").apply {
            val privateDecoded = Base64.getDecoder().decode(rsaCredentials.privatePEM)
            val privateKey = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(privateDecoded))

            init(Cipher.DECRYPT_MODE, privateKey)
        }

        val plain = Base64.getDecoder().decode(appInstance!!.encryptedSecret)
        cipher.doFinal(plain)
    }

    fun auth(appInstance: AppInstance): SSGCredentials {
        this.appInstance = appInstance

        return SSGCredentials(rsaCredentials = rsaCredentials, appInstance = appInstance)
    }

    fun authRequest() = AuthRequest(
        body = AuthRequestBody(
            tenantId = tenantId,
            publicKey = rsaCredentials.publicPEM.replace("\n", "").replace("\r", ""),
            hardwareId = "Android Phone",
            osType = "Android"
        ),
        headers = mapOf(
            "Content-Type" to "application/json",
            "Api-Developer-Key" to "${devKey}:${rsaCredentials.encodedDevKey}"
        )
    )

    fun sign(path: String, body: String?, method: String, queryParams: Map<String, String>?): Map<String, Any> {
        val appInstance = appInstance ?: throw RuntimeException("Need to auth first!")


        val bodyHash = body.hash()
        val claim = claim()
        val params = queryParams.sort().takeIf { it.isNotBlank() }?.let { "\n$it" } ?: ""

        val toSign = "${method.uppercase()}\n${path}\ncontent-sha256=$bodyHash\napi-jwt-claim=${claim}$params"

        val signature = Base64.getEncoder().encodeToString((signHmac.doFinal(toSign.toByteArray())))

        return mapOf(
            "Content-Type" to "application/stream+json",
            "Api-Jwt-Claim" to claim,
            "Content-SHA256" to bodyHash,
            "SSG-Instance-HMAC" to "${appInstance.appInstanceId}:$signature"
        )
    }

    private fun String?.hash(): String {
        if (isNullOrBlank()) {
            return ""
        }

        return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(toByteArray()))
    }

    private fun claim(): String {
        val now = System.currentTimeMillis()
        val claim = """
            {
                "iat": ${(now / 1000).toInt()},
                "jti": "${appInstance!!.appInstanceId}${now}"
            }
        """.trimIndent()
        return Base64.getEncoder().encodeToString(claim.toByteArray())
    }

    private fun Map<String, String>?.sort(): String {
        if (isNullOrEmpty()) {
            return ""
        }

        return map { (k, v) -> "${k.lowercase()}=${URLEncoder.encode(v, Charsets.UTF_8).lowercase()}" }.sorted()
            .joinToString("&")
    }

    private fun generateRsaCredentials(): RSACredentials {
        val pair = KeyPairGenerator.getInstance("RSA").apply { initialize(1024) }.generateKeyPair()
        val privatePEM = Base64.getEncoder().encodeToString(pair.private.encoded)
        val publicPEM = Base64.getEncoder().encodeToString(pair.public.encoded)

        return RSACredentials(
            publicPEM = publicPEM,
            privatePEM = privatePEM,
            encodedDevKey = Base64.getEncoder().encodeToString((authHmac.doFinal(publicPEM.toByteArray())))
        )
    }
}

data class SSGCredentials(val rsaCredentials: RSACredentials, val appInstance: AppInstance)
data class RSACredentials(val privatePEM: String, val publicPEM: String, val encodedDevKey: String)
data class AuthRequest(val body: AuthRequestBody, val headers: Map<String, String>)
data class AuthRequestBody(val tenantId: Int, val publicKey: String, val osType: String, val hardwareId: String)
data class AuthResponse(val appInstance: AppInstance)
data class AppInstance(val appInstanceId: String, val encryptedSecret: String)
