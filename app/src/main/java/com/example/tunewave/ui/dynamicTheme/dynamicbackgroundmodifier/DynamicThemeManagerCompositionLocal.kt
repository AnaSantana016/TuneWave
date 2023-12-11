package com.example.tunewave.ui.dynamicTheme.dynamicbackgroundmodifier

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.tunewave.ui.dynamicTheme.manager.DynamicThemeManager
import com.example.tunewave.ui.dynamicTheme.manager.TunewaveDynamicThemeManager
import com.example.tunewave.usecases.downloadDrawableFromUrlUseCase.TunewaveDownloadDrawableFromUrlUseCase
import kotlinx.coroutines.Dispatchers

val LocalDynamicThemeManager: ProvidableCompositionLocal<DynamicThemeManager> =
    staticCompositionLocalOf {
        TunewaveDynamicThemeManager(
            downloadDrawableFromUrlUseCase = TunewaveDownloadDrawableFromUrlUseCase(Dispatchers.IO),
            defaultDispatcher = Dispatchers.IO
        )
    }