package kaczmarek.moneycalculator.core.utils

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.semantics.testTagsAsResourceId

val LocalMessageOffsets = staticCompositionLocalOf { mutableStateMapOf<Int, Int>() }

fun Modifier.noOverlapByMessage(): Modifier = composed {
    val key = currentCompositeKeyHash
    val localMessageOffsets = LocalMessageOffsets.current

    DisposableEffect(currentCompositeKeyHash) {
        onDispose { localMessageOffsets.remove(key) }
    }
    then(
        onGloballyPositioned { layoutCoordinates ->
            if (layoutCoordinates.isAttached) {
                localMessageOffsets[key] = layoutCoordinates.size.height
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Stable
fun Modifier.testTagAsId(tag: String) = semantics(
    properties = {
        testTag = tag
        testTagsAsResourceId = true
    },
)