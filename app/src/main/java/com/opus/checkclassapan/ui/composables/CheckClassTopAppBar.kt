package com.opus.checkclassapan.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckClassTopAppBar(
    title: String,
    navigationIcon: ImageVector,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = "Navigation Icon"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CheckClassTopAppBarPreview() {
    CheckClassTopAppBar(
        title = "Preview Title",
        navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        onNavigationIconClick = {}
    )
}
