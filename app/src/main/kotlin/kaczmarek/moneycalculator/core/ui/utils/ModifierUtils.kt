package kaczmarek.moneycalculator.core.ui.utils

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onGloballyPositioned

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