package com.example.fiziktedaviapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat

// Dark theme color scheme (for future support)
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    primaryContainer = PrimaryVariant,
    secondary = Secondary,
    secondaryContainer = SecondaryVariant,
    tertiary = Accent,
    background = Color(0xFF121212), // Dark background
    surface = Color(0xFF1E1E1E),    // Dark surface
    error = Error,
    onPrimary = TextOnPrimary,
    onSecondary = TextOnSecondary,
    onTertiary = TextOnPrimary,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = TextOnPrimary
)

// Light theme color scheme (default)
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    primaryContainer = PrimaryLight,
    secondary = Secondary,
    secondaryContainer = SecondaryLight,
    tertiary = Accent,
    background = Background,
    surface = Surface,
    surfaceVariant = SurfaceVariant,
    error = Error,
    onPrimary = TextOnPrimary,
    onSecondary = TextOnSecondary,
    onTertiary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onError = TextOnPrimary,
    outline = Disabled,         // For borders and dividers
    outlineVariant = DisabledText // Subtle outline variant
)

@Composable
fun FizikTedaviAppTheme(
    darkTheme: Boolean = false, // Force light theme by default
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic color to use our custom theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}