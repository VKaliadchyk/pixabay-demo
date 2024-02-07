package com.app.pixabaydemo.presentation.serialization

import com.app.pixabaydemo.domain.entity.PixabayImageInfo
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class PixabayImageInfoSerializer : JsonSerializer<PixabayImageInfo> {
    override fun serialize(
        src: PixabayImageInfo,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()

        jsonObject.addProperty("id", src.id)
        jsonObject.addProperty("pageURL", src.pageURL.urlEncode())
        jsonObject.addProperty("type", src.type)
        jsonObject.addProperty("tags", src.tags)
        jsonObject.addProperty("previewURL", src.previewURL.urlEncode())
        jsonObject.addProperty("previewWidth", src.previewWidth)
        jsonObject.addProperty("previewHeight", src.previewHeight)
        jsonObject.addProperty("webFormatURL", src.webFormatURL.urlEncode())
        jsonObject.addProperty("webFormatWidth", src.webFormatWidth)
        jsonObject.addProperty("webFormatHeight", src.webFormatHeight)
        jsonObject.addProperty("largeImageURL", src.largeImageURL.urlEncode())
        src.fullHDURL?.let { fullHDURL ->
            jsonObject.addProperty("fullHDURL", fullHDURL.urlEncode())
        }
        src.imageURL?.let { imageURL ->
            jsonObject.addProperty("imageURL", imageURL.urlEncode())
        }
        jsonObject.addProperty("imageWidth", src.imageWidth)
        jsonObject.addProperty("imageHeight", src.imageHeight)
        jsonObject.addProperty("imageSize", src.imageSize)
        jsonObject.addProperty("views", src.views)
        jsonObject.addProperty("downloads", src.downloads)
        jsonObject.addProperty("likes", src.likes)
        jsonObject.addProperty("comments", src.comments)
        jsonObject.addProperty("userId", src.userId)
        jsonObject.addProperty("user", src.user)
        jsonObject.addProperty("userImageURL", src.userImageURL.urlEncode())

        return jsonObject
    }

    private fun String.urlEncode(): String {
        return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
    }
}