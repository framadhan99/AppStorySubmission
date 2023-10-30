package com.fajar.storyappsubmission.features.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.fajar.storyappsubmission.R
import com.fajar.storyappsubmission.features.utils.isEmailValid
import com.google.android.material.textfield.TextInputEditText

class EmailEditText : TextInputEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init(){
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.isBlank() == true) error =
                    resources.getString(R.string.error_empty_email)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmailValid() == false) error = resources.getString(R.string.error_format_email)
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        background = ContextCompat.getDrawable(context, R.drawable.edit_text_border) as Drawable
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        super.onDraw(canvas)
    }

}