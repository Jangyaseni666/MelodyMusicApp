package com.example.myapplication.screens

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R

@Composable
fun WelcomeScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.top_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .align(Alignment.BottomCenter)
                    .shadow(16.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
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

                    OutlinedButton(
                        onClick = { navController.navigate("main_login_screen") },
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

@Preview
@Composable
fun WelcomeScreenPreview() {
    // Preview setup with a dummy NavController for demonstration
    val navController = rememberNavController()
    WelcomeScreen(navController = navController)
}
