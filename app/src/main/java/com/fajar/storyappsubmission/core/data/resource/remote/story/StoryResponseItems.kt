package com.fajar.storyappsubmission.core.data.resource.remote.story

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Story")
@Parcelize
data class StoryResponseItems (

    @field:SerializedName("photoUrl")
    val photoUrl : String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description : String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("lat")
    val lat: Double? = null
): Parcelable