package com.stellarworker.nasaexplorer.api.asteroids_neows

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("self")
    val self: String? = null
)

data class Links(
    @SerializedName("next")
    val next: String? = null,

    @SerializedName("prev")
    val prev: String? = null,

    @SerializedName("self")
    val self: String? = null
)

data class Kilometers(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double? = null,

    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
)

data class Meters(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double? = null,

    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
)

data class Miles(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double? = null,

    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
)

data class Feet(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double? = null,

    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double? = null,
)

data class EstimatedDiameter(
    @SerializedName("kilometers")
    val kilometers: Kilometers? = null,

    @SerializedName("meters")
    val meters: Meters? = null,

    @SerializedName("miles")
    val miles: Miles? = null,

    @SerializedName("feet")
    val feet: Feet? = null,
)

data class RelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: String? = null,

    @SerializedName("kilometers_per_hour")
    val kilometersPerHour: String? = null,

    @SerializedName("miles_per_hour")
    val milesPerHour: String? = null
)

data class MissDistance(
    @SerializedName("astronomical")
    val astronomical: String? = null,

    @SerializedName("lunar")
    val lunar: String? = null,

    @SerializedName("kilometers")
    val kilometers: String? = null,

    @SerializedName("miles")
    val miles: String? = null
)

data class CloseApproachData(
    @SerializedName("close_approach_date")
    val closeApproachDate: String? = null,

    @SerializedName("close_approach_date_full")
    val closeApproachDateFull: String? = null,

    @SerializedName("epoch_date_close_approach")
    val epochDateCloseApproach: String? = null,

    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocity? = null,

    @SerializedName("miss_distance")
    val missDistance: MissDistance? = null,

    @SerializedName("orbiting_body")
    val orbitingBody: String? = null,
)

data class NearEarthObject(

    @SerializedName("links")
    val links: Link? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("neo_reference_id")
    val neoReferenceId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("nasa_jpl_url")
    val nasaJPLUrl: String? = null,

    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitudeH: Double? = null,

    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameter? = null,

    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean? = null,

    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachData>? = null,

    @SerializedName("is_sentry_object")
    val isSentryObject: Boolean? = null
)

data class AsteroidsNeoWSResponse(
    @SerializedName("links")
    val links: Links? = null,

    @SerializedName("element_count")
    val elementCount: Int? = null,

    @SerializedName("near_earth_objects")
    val nearEarthObjects: Map<String, List<NearEarthObject>>? = null
)
