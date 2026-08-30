package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme =
  darkColorScheme(
    primary = BentoDarkPrimary,
    onPrimary = BentoDarkOnPrimaryContainer,
    primaryContainer = BentoDarkPrimaryContainer,
    onPrimaryContainer = BentoDarkOnPrimaryContainer,
    secondary = BentoDarkPrimary,
    onSecondary = BentoDarkBg,
    secondaryContainer = BentoDarkSurfaceContainer,
    onSecondaryContainer = BentoDarkOnSurface,
    tertiary = Color(0xFFFFB1C8),
    background = BentoDarkBg,
    surface = BentoDarkSurface,
    surfaceContainer = BentoDarkSurfaceContainer,
    surfaceContainerHigh = BentoDarkSurfaceVariant,
    onBackground = BentoDarkOnSurface,
    onSurface = BentoDarkOnSurface,
    onSurfaceVariant = BentoDarkOnSurfaceVariant,
    outline = BentoOutlineVariant
  )

private val LightColorScheme =
  lightColorScheme(
    primary = BentoPrimary,
    onPrimary = Color.White,
    primaryContainer = BentoPrimaryContainer,
    onPrimaryContainer = BentoOnPrimaryContainer,
    secondary = BentoSecondary,
    onSecondary = Color.White,
    secondaryContainer = BentoSecondaryContainer,
    onSecondaryContainer = BentoOnSecondaryContainer,
    tertiary = BentoTertiary,
    tertiaryContainer = BentoTertiaryContainer,
    background = BentoBg,
    surface = BentoSurface,
    surfaceContainer = BentoSurfaceContainer,
    surfaceContainerHigh = BentoSurfaceContainerHigh,
    surfaceVariant = BentoSurfaceVariant,
    onBackground = BentoOnSurface,
    onSurface = BentoOnSurface,
    onSurfaceVariant = BentoOnSurfaceVariant,
    outline = BentoOutline,
    outlineVariant = BentoOutlineVariant
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
