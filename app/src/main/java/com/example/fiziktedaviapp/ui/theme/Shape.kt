package com.example.fiziktedaviapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Modern, daha yuvarlak köşeler içeren şekiller
val Shapes = Shapes(
    // Küçük bileşenler için (butonlar, text field'lar vb.)
    small = RoundedCornerShape(8.dp),
    
    // Kartlar gibi orta boy bileşenler için
    medium = RoundedCornerShape(12.dp),
    
    // Modal, dialog gibi büyük bileşenler için
    large = RoundedCornerShape(16.dp),
    
    // Extra large bileşenler için (bottom sheet vb.)
    extraLarge = RoundedCornerShape(24.dp)
)
