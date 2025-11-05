package com.opus.checkclassapan.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckClassFloatingActionButton(
    icon: ImageVector,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
    ) {
        Icon(icon, contentDescription = "Floating Action Button Icon")
    }
}

@Preview
@Composable
fun CheckClassFloatingActionButtonPreview() {
    CheckClassFloatingActionButton(
        icon = Icons.Default.Check,
        onClick = {}
    )
}
