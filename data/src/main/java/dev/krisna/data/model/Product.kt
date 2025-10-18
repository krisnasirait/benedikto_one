package dev.krisna.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id")
    val id: String,

    @SerialName("sku")
    val sku: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("spec")
    val spec: String? = null,

    @SerialName("unit")
    val unit: String? = null,

    @SerialName("default_price")
    val price: Double? = null,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("updated_at")
    val updatedAt: String
)