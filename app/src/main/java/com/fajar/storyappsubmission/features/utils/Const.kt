package com.fajar.storyappsubmission.features.utils

import android.Manifest

object Const {
    const val DETAIL_ITEM = "detail_item"
    const val FILENAME_FORMAT = "dd-MMM-yyyy"
    const val CAMERA_X_RESULT = 200
    const val time: Int = 3000
    var PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}