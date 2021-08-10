package com.fahad.sicpa.util

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 *  Convert [EditText.doOnTextChanged] into [StateFlow] to take advantages of Coroutine Flow.
 *
 *  @return [StateFlow] of CharSequence.
 */
fun EditText.textChanges(): StateFlow<CharSequence> {
    val query = MutableStateFlow<CharSequence>("")
    this.doOnTextChanged { text, _, _, _ ->
        query.value = text?.toString() ?: ""
    }
    return query
}