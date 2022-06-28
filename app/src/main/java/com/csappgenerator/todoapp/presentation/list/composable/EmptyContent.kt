package com.csappgenerator.todoapp.presentation.list.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.ui.theme.NO_CONTENT_ICON_SIZE

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(NO_CONTENT_ICON_SIZE),
            painter = painterResource(id = R.drawable.ic_no_content),
            contentDescription = stringResource(R.string.no_content_icon),
        )
        Text(
            text = stringResource(R.string.no_content),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )

    }
}

@Composable
@Preview
fun EmptyContentPreview() {
    EmptyContent()
}