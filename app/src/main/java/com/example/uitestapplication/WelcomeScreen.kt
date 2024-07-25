package com.example.uitestapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun WelcomeScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image background on top section
            Image(
                painter = painterResource(id = R.drawable.top_bg), // Replace with your image resource
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp) // Adjust height as per your design
            )

            // Bottom section with rounded corners, shadow, and stick to bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp) // Adjust height to take 50% of the screen
                    .align(Alignment.BottomCenter)
                    .shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)) // Shadow effect
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center, // Center content vertically
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Listen to the best music with Melody now!",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Get Started outlined button
                    OutlinedButton(
                        onClick = { /* TODO: Handle button click */ },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        modifier = Modifier.padding(top = 8.dp),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Text(text = "Get Started", color = Color.White)
                    }
                }
            }
        }
    }
}
