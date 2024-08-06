package com.example.myapplication.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.myapplication.api.ApiInterface
import com.example.myapplication.model.Track
import com.example.myapplication.model.TrackRecResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }
    var PopRecommendations by remember { mutableStateOf<List<Track>>(emptyList()) }
    var RockRecommendations by remember { mutableStateOf<List<Track>>(emptyList()) }
    var ClassicalRecommendations by remember { mutableStateOf<List<Track>>(emptyList()) }
    var CountryRecommendations by remember { mutableStateOf<List<Track>>(emptyList()) }
    val context = LocalContext.current

    val retrofit = Retrofit.Builder()
        .baseUrl("https://spotify23.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiInterface = retrofit.create(ApiInterface::class.java)

    fun getRecommendations(genre: String, onSuccess: (List<Track>) -> Unit) {
        val call = apiInterface.getRecommendations(
            limit = 10,
            seedGenres = genre,
            seedArtists = "",
            seedTracks = ""
        )

        call.enqueue(object : Callback<TrackRecResponse> {
            override fun onResponse(call: Call<TrackRecResponse>, response: Response<TrackRecResponse>) {
                if (response.isSuccessful) {
                    val tracksList = response.body()?.tracks
                    if (tracksList != null) {
                        onSuccess(tracksList)
                    } else {
                        Toast.makeText(context, "No recommendations found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Error: ${response.errorBody()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TrackRecResponse>, t: Throwable) {
                Log.e("API", "Failure: ${t.message}")
                Toast.makeText(context, "Failed to fetch recommendations: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    LaunchedEffect(Unit) {
        getRecommendations("pop") { tracks -> PopRecommendations = tracks }
        getRecommendations("rock") { tracks -> RockRecommendations = tracks }
        getRecommendations("classical") { tracks -> ClassicalRecommendations = tracks }
        getRecommendations("country") { tracks -> CountryRecommendations = tracks }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set the background color to black
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Welcome, User!",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            style = TextStyle(color = Color.White)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Divider(thickness = 2.dp, color = Color.White) // Optional: Change Divider color to white
        Text(
            text = "POP Recommendations",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            style = TextStyle(color = Color.White)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            items(PopRecommendations) { track ->
                RecItem(track, apiInterface, navController)
            }
        }
        Divider(thickness = 2.dp, color = Color.White) // Optional: Change Divider color to white
        Spacer(modifier = Modifier.height(20.dp))

        Divider(thickness = 2.dp, color = Color.White) // Optional: Change Divider color to white
        Text(
            text = "ROCK Recommendations",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            style = TextStyle(color = Color.White)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            items(RockRecommendations) { track ->
                RecItem(track, apiInterface, navController)
            }
        }
        Divider(thickness = 2.dp, color = Color.White) // Optional: Change Divider color to white
        Spacer(modifier = Modifier.height(20.dp))

        Divider(thickness = 2.dp, color = Color.White) // Optional: Change Divider color to white
        Text(
            text = "CLASSICAL Recommendations",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            style = TextStyle(color = Color.White)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            items(ClassicalRecommendations) { track ->
                RecItem(track, apiInterface, navController)
            }
        }
        Divider(thickness = 2.dp, color = Color.White) // Optional: Change Divider color to white
        Spacer(modifier = Modifier.height(20.dp))

        Divider(thickness = 2.dp, color = Color.White) // Optional: Change Divider color to white
        Text(
            text = "COUNTRY Recommendations",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            style = TextStyle(color = Color.White)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            items(CountryRecommendations) { track ->
                RecItem(track, apiInterface, navController)
            }
        }
    }
}

@Composable
fun RecItem(track: Track, apiInterface: ApiInterface, navController: NavController) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(track.album.images.firstOrNull()?.url)
            .crossfade(true)
            .build()
    )
    Card(
        modifier = Modifier
            .padding(10.dp)
            .border(BorderStroke(1.dp, Color.Gray))
            .clickable {
                navController.navigate("trackDetails/${track.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = "Track Image",
                modifier = Modifier
                    .size(100.dp)
                    .border(BorderStroke(1.dp, Color.Gray)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = track.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = track.artists.joinToString(", ") { it.name },
                fontSize = 14.sp,
                style = TextStyle(color = Color.White)
            )
        }
    }
}
