package com.example.countriesapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.countriesapp.R
import com.example.countriesapp.ui.theme.Dimen.dimen16
import com.example.countriesapp.ui.theme.Dimen.dimen32
import com.example.countriesapp.ui.theme.Dimen.dimen8
import com.example.countriesapp.ui.theme.Dimen.dimen80

@Composable
fun ErrorStateScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(dimen32),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = stringResource(id = R.string.error_icon),
                tint = Color.Red,
                modifier = Modifier.size(dimen80)
            )
            Spacer(modifier = Modifier.height(dimen16))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(dimen16))
            Button(
                onClick = onRetry,
                shape = RoundedCornerShape(dimen8),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Red
                )
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}