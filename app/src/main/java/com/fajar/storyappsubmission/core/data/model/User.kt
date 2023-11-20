package com.fajar.storyappsubmission.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var userId : String,
    var name: String,
    var token : String,
): Parcelable