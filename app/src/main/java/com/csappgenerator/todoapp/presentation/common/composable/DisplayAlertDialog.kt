package com.csappgenerator.todoapp.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.csappgenerator.todoapp.R

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDeleteItemConfirmDialog: Boolean,
    closeDeleteItemDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (openDeleteItemConfirmDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        onYesClicked()
                        closeDeleteItemDialog()
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        contentColor = MaterialTheme.colors.secondary
                    )

                ) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        closeDeleteItemDialog()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)

                ) {
                    Text(text = stringResource(R.string.no))
                }
            },
            onDismissRequest = { },
            backgroundColor = MaterialTheme.colors.primaryVariant,
            contentColor = MaterialTheme.colors.onSurface
        )
    }

}