package com.example.fiziktedaviapp.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

/**
 * Modern tonal kart bileşeni - Material 3'ün "Container" tasarım dilini kullanan modern kart
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TonalCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    tonalElevation: Dp = 4.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Basıldığında veya basılı tutulduğunda animasyonlu elevation değişimi
    val animatedElevation by animateDpAsState(
        targetValue = if (isPressed) (tonalElevation - 2.dp).coerceAtLeast(0.dp) else tonalElevation,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "Elevation Animation"
    )
    
    // Basıldığında hafif renk değişimi için animasyon
    val animatedContainerColor by animateColorAsState(
        targetValue = if (isPressed) 
            MaterialTheme.colorScheme.surfaceVariant 
        else 
            MaterialTheme.colorScheme.surface,
        label = "Container Color Animation"
    )
    
    Card(
        onClick = onClick,
        modifier = modifier
            .shadow(
                elevation = animatedElevation,
                shape = shape,
                clip = false
            )
            .zIndex(if (isPressed) 1f else 0f),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = animatedContainerColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        enabled = enabled,
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

/**
 * Modern içerik kartı - Material 3'ün renk tonlarını kullanan içerik kartı
 */
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    tonalElevation: Dp = 2.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = tonalElevation
        )
    ) {
        Column(
            modifier = Modifier.padding(contentPadding)
        ) {
            content()
        }
    }
}

/**
 * Vurgulu modern kart - Önemli bilgileri vurgulamak için kullanılır
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccentCard(
    title: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    content: @Composable ColumnScope.() -> Unit
) {
    if (onClick != null) {
        OutlinedCard(
            onClick = onClick,
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.outlinedCardColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                content()
            }
        }
    } else {
        OutlinedCard(
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.outlinedCardColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                content()
            }
        }
    }
}

/**
 * Modern info kartı - Bilgi vermek amacıyla kullanılan kart
 */
@Composable
fun InfoCard(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.7f),
    contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor.copy(alpha = 0.8f)
            )
        }
    }
}
