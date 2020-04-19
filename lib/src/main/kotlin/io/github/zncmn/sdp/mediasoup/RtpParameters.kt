package io.github.zncmn.sdp.mediasoup

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.github.zncmn.sdp.webrtc.RTCPriorityType

/**
 * The RTP capabilities define what mediasoup or an endpoint can receive at media level.
 */
@JsonClass(generateAdapter = true)
data class RtpCapabilities @JvmOverloads constructor(
    var codecs: List<RtpCodecCapability> = arrayListOf(),
    var headerExtensions: List<RtpHeaderExtension> = arrayListOf(),
    var fecMechanisms: List<String> = arrayListOf()
)

/**
 * Media kind ('audio' or 'video').
 */
enum class MediaKind {
    @Json(name = "audio") AUDIO,
    @Json(name = "video") VIDEO
}

@JsonClass(generateAdapter = true)
data class RtpCodecCapability @JvmOverloads constructor(
    /**
     * Media kind.
     */
    var kind: MediaKind,

    /**
     * The codec MIME media type/subtype (e.g. 'audio/opus', 'video/VP8').
     */
    var mimeType: String,

    /**
     * The preferred RTP payload type.
     */
    var preferredPayloadType: Int? = null,

    /**
     * Codec clock rate expressed in Hertz.
     */
    var clockRate: Int,

    /**
     * The number of channels supported (e.g. two for stereo). Just for audio Default 1.
     */
    var channels: Int = 1,

    /**
     * Codec specific parameters. Some parameters (such as 'packetization-mode'
     * and 'profile-level-id' in H264 or 'profile-id' in VP9) are critical for
     * codec matching.
     */
    var parameters: Any? = null,

    /**
     * Transport layer and codec-specific feedback messages for this codec.
     */
    var rtcpFeedback: List<RtcpFeedback> = arrayListOf()
)

enum class RtpHeaderExtensionDirection {
    @Json(name = "sendrecv") SENDRECV,
    @Json(name = "sendonly") SENDONLY,
    @Json(name = "recvonly") RECVONLY,
    @Json(name = "inactive") INACTIVE
}

@JsonClass(generateAdapter = true)
data class RtpHeaderExtension(
    /**
     * Media kind. If empty string, it's valid for all kinds.
     * Default any media kind.
     */
    var kind: MediaKind? = null,

    /**
     * The URI of the RTP header extension, as defined in RFC 5285.
     */
    var uri: String,

    /**
     * The preferred numeric identifier that goes in the RTP packet. Must be unique.
     */
    var preferredId: Long,

    /**
     * If true, it is preferred that the value in the header be encrypted as per
     * RFC 6904. Default false.
     */
    var preferredEncrypt: Boolean = false,

    /**
     * If 'sendrecv', mediasoup supports sending and receiving this RTP extension.
     * 'sendonly' means that mediasoup can send (but not receive) it. 'recvonly'
     * means that mediasoup can receive (but not send) it.
     */
    var direction: RtpHeaderExtensionDirection? = null
)

@JsonClass(generateAdapter = true)
data class RtpParameters(
    /**
     * The MID RTP extension value as defined in the BUNDLE specification.
     */
    var mid: String? = null,

    /**
     * Media and RTX codecs in use.
     */
    var codecs: List<RtpCodecParameters> = arrayListOf(),

    /**
     * RTP header extensions in use.
     */
    var headerExtensions: List<RtpHeaderExtensionParameters> = arrayListOf(),

    /**
     * Transmitted RTP streams and their settings.
     */
    var encodings: List<RtpEncodingParameters> = arrayListOf(),

    /**
     * Parameters used for RTCP.
     */
    var rtcp: RtcpParameters? = null
)

@JsonClass(generateAdapter = true)
data class RtpCodecParameters(
    /**
     * The codec MIME media type/subtype (e.g. 'audio/opus', 'video/VP8').
     */
    var mimeType: String,

    /**
     * The value that goes in the RTP Payload Type Field. Must be unique.
     */
    var payloadType: Int,

    /**
     * Codec clock rate expressed in Hertz.
     */
    var clockRate: Int,

    /**
     * The number of channels supported (e.g. two for stereo). Just for audio.
     * Default 1.
     */
    var channels: Int = 1,

    /**
     * Codec-specific parameters available for signaling. Some parameters (such
     * as 'packetization-mode' and 'profile-level-id' in H264 or 'profile-id' in
     * VP9) are critical for codec matching.
     */
    var parameters: Any? = null,

    /**
     * Transport layer and codec-specific feedback messages for this codec.
     */
    var rtcpFeedback: List<RtcpFeedback> = arrayListOf()
)

@JsonClass(generateAdapter = true)
data class RtcpFeedback(
    /**
     * RTCP feedback type.
     */
    var type: String,

    /**
     * RTCP feedback parameter.
     */
    var parameter: String? = null
)

@JsonClass(generateAdapter = true)
data class RtpEncodingParameters(
    /**
     * The media SSRC.
     */
    var ssrc: Long? = null,

    /**
     * The RID RTP extension value. Must be unique.
     */
    var rid: String? = null,

    /**
     * Codec payload type this encoding affects. If unset, first media codec is
     * chosen.
     */
    var codecPayloadType: Int? = null,

    /**
     * RTX stream information. It must contain a numeric ssrc field indicating
     * the RTX SSRC.
     */
    var rtx: RtxParameter? = null,

    /**
     * It indicates whether discontinuous RTP transmission will be used. Useful
     * for audio (if the codec supports it) and for video screen sharing (when
     * static content is being transmitted, this option disables the RTP
     * inactivity checks in mediasoup). Default false.
     */
    var dtx: Boolean = false,

    /**
     * Number of spatial and temporal layers in the RTP stream (e.g. 'L1T3').
     * See webrtc-svc.
     */
    var scalabilityMode: String? = null,

    /**
     * Others.
     */
    var scaleResolutionDownBy: Double? = null,
    var maxBitrate: Long? = null,
    var priority: RTCPriorityType = RTCPriorityType.LOW,
    var networkPriority: RTCPriorityType = RTCPriorityType.LOW
)

@JsonClass(generateAdapter = true)
data class RtxParameter(
    var ssrc: Long? = null
)

@JsonClass(generateAdapter = true)
data class RtpHeaderExtensionParameters(
    /**
     * The URI of the RTP header extension, as defined in RFC 5285.
     */
    var uri: String,

    /**
     * The numeric identifier that goes in the RTP packet. Must be unique.
     */
    var id: Long,

    /**
     * If true, the value in the header is encrypted as per RFC 6904. Default false.
     */
    var encrypt: Boolean = false,

    /**
     * Configuration parameters for the header extension.
     */
    var parameters: Any? = null
)

@JsonClass(generateAdapter = true)
data class RtcpParameters(
    /**
     * The Canonical Name (CNAME) used by RTCP (e.g. in SDES messages).
     */
    var cname: String?,

    /**
     * Whether reduced size RTCP RFC 5506 is configured (if true) or compound RTCP
     * as specified in RFC 3550 (if false). Default true.
     */
    var reducedSize: Boolean = true,

    /**
     * Whether RTCP-mux is used. Default true.
     */
    var mux: Boolean = true
)