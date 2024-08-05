package com.example.spotify
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.spotify.model.ApiInterface
import com.example.spotify.model.Data
import com.example.spotify.model.Track
import com.example.spotify.model.TrackDetailsResponse
import com.example.spotify.model.TrackRecResponse
import com.example.spotify.model.TracksResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val tracks = mutableStateListOf<Data>()
    private var trackDetails by mutableStateOf<Track?>(null)
    private var exoPlayer: ExoPlayer? = null
    private var isPlaying by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.background(Color.DarkGray)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black), // Background color for the top bar
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Logo (Placeholder for actual logo drawable)
//                    Image(
//                        painter = painterResource(id = R.drawable.your_logo), // Replace with your logo drawable resource
//                        contentDescription = null,
//                        modifier = Modifier.size(40.dp) // Adjust size as needed
//                    )
                    // App Name
                    Text(
                        text = "Melody",
                        color = Color.White, // Text color
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(20.dp)
                    )
                }
                AppNavigation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen(navController) }
            composable("trackDetails/{trackId}") { backStackEntry ->
                val trackId = backStackEntry.arguments?.getString("trackId")
                TrackDetailsScreen(navController, trackId)
            }
        } }

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


        fun getPopRecommendations(apiInterface: ApiInterface) {
            val call = apiInterface.getRecommendations(
                limit = 10,
                seedGenres = "pop", // Adjust as needed
                seedArtists = "",
                seedTracks = ""
            )

            call.enqueue(object : Callback<TrackRecResponse> {
                override fun onResponse(call: Call<TrackRecResponse>, response: Response<TrackRecResponse>) {
                    if (response.isSuccessful) {
                        val tracksList = response.body()?.tracks
                        if (tracksList != null) {
                            PopRecommendations = tracksList
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
        fun getRockRecommendations(apiInterface: ApiInterface) {
            val call = apiInterface.getRecommendations(
                limit = 10,
                seedGenres = "rock", // Adjust as needed
                seedArtists = "",
                seedTracks = ""
            )

            call.enqueue(object : Callback<TrackRecResponse> {
                override fun onResponse(call: Call<TrackRecResponse>, response: Response<TrackRecResponse>) {
                    if (response.isSuccessful) {
                        val tracksList = response.body()?.tracks
                        if (tracksList != null) {
                            RockRecommendations = tracksList
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
        fun getClassicalRecommendations(apiInterface: ApiInterface) {
            val call = apiInterface.getRecommendations(
                limit = 10,
                seedGenres = "classical", // Adjust as needed
                seedArtists = "",
                seedTracks = ""
            )

            call.enqueue(object : Callback<TrackRecResponse> {
                override fun onResponse(call: Call<TrackRecResponse>, response: Response<TrackRecResponse>) {
                    if (response.isSuccessful) {
                        val tracksList = response.body()?.tracks
                        if (tracksList != null) {
                            ClassicalRecommendations = tracksList
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
        fun getCountryRecommendations(apiInterface: ApiInterface) {
            val call = apiInterface.getRecommendations(
                limit = 10,
                seedGenres = "classical", // Adjust as needed
                seedArtists = "",
                seedTracks = ""
            )

            call.enqueue(object : Callback<TrackRecResponse> {
                override fun onResponse(call: Call<TrackRecResponse>, response: Response<TrackRecResponse>) {
                    if (response.isSuccessful) {
                        val tracksList = response.body()?.tracks
                        if (tracksList != null) {
                            CountryRecommendations = tracksList
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
            getPopRecommendations(apiInterface)
            getRockRecommendations(apiInterface)
            getClassicalRecommendations(apiInterface)
            getCountryRecommendations(apiInterface)
        }
        Column(modifier = Modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())) {
            Text(text = "Welcome, User!", fontWeight = FontWeight.Bold, fontSize = 32.sp, style = TextStyle(color = Color.White))
//            TextField(
//                value = query,
//                onValueChange = { query = it },
//                placeholder = { Text("Search for tracks") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 8.dp)
//                    .border(BorderStroke(1.dp, Color.Gray)),
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//                keyboardActions = KeyboardActions(onSearch = {
//                    if (query.isNotEmpty()) {
//                        searchTracks(apiInterface, query)
//                    } else {
//                        Toast.makeText(context, "Please enter a search query", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            )
//            Button(
//                onClick = {
//                    if (query.isNotEmpty()) {
//                        searchTracks(apiInterface, query)
//                    } else {
//                        Toast.makeText(context, "Please enter a search query", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Search")
//            }
//            LazyColumn(modifier = Modifier.fillMaxWidth().height(100.dp)) {
//                items(tracks) { track ->
//                    TrackItem(track, apiInterface, navController)
//                }
//            }
//            Button(
//                onClick = {
//                    getRecommendations(apiInterface)
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Get Recommendations")
//            }
            Spacer(modifier = Modifier.height(30.dp))
            Divider(thickness = 2.dp)
            Text(text = "POP Recomendations", fontWeight = FontWeight.Bold, fontSize = 28.sp, style = TextStyle(color = Color.White))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                items(PopRecommendations) { track ->
                    RecItem(track, apiInterface, navController)
                }
            }
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(20.dp))

            Divider(thickness = 2.dp)
            Text(text = "ROCK Recomendations", fontWeight = FontWeight.Bold, fontSize = 28.sp, style = TextStyle(color = Color.White))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                items(RockRecommendations) { track ->
                    RecItem(track, apiInterface, navController)
                }
            }
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(20.dp))

            Divider(thickness = 2.dp)
            Text(text = "CLASSICAL Recomendations", fontWeight = FontWeight.Bold, fontSize = 28.sp, style = TextStyle(color = Color.White))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                items(ClassicalRecommendations) { track ->
                    RecItem(track, apiInterface, navController)
                }
            }
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(20.dp))

            Divider(thickness = 2.dp)
            Text(text = "COUNTRY Recomendations", fontWeight = FontWeight.Bold, fontSize = 28.sp, style = TextStyle(color = Color.White))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                items(CountryRecommendations) { track ->
                    RecItem(track, apiInterface, navController)
                }
            }
            Divider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(20.dp))

        }
    }

    @Composable
    fun TrackItem(track: Data, apiInterface: ApiInterface, navController: NavController) {
        val scope = rememberCoroutineScope()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = track.albumOfTrack.coverArt.sources.firstOrNull()?.url)
                            .apply { crossfade(true) }
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .border(BorderStroke(2.dp, Color.Gray)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = track.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = track.artists.items.joinToString(", ") { it.profile.name }, fontSize = 16.sp, color = Color.Gray)
                    Text(text = "ID: ${track.id}", fontSize = 14.sp, color = Color.Gray)
                }
                Button(onClick = {
                    scope.launch {
                        getTrackDetails(apiInterface, track.id) { trackDetail ->
                            trackDetails = trackDetail
                            navController.navigate("trackDetails/${track.id}")
                        }
                    }
                }) {
                    Text("Details")
                }
            }
        }
    }

    @Composable
    fun RecItem(track: Track, apiInterface: ApiInterface, navController: NavController) {
        val scope = rememberCoroutineScope()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = track.album.images[1].url)
                            .apply { crossfade(true) }
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .border(BorderStroke(2.dp, Color.Gray)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .width(150.dp)
                ) {
                    Text(text = track.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(text = track.artists[0].name, fontSize = 16.sp, color = Color.Gray)
                }
                Button(onClick = {
                    scope.launch {
                        getTrackDetails(apiInterface, track.id) { trackDetail ->
                            trackDetails = trackDetail
                            navController.navigate("trackDetails/${track.id}")
                        }
                    }
                }) {
                    Text("Details")
                }
            }
        }
    }

    @Composable
    fun TrackDetailsScreen(navController: NavController, trackId: String?) {
        val trackDetail = trackDetails
        LocalContext.current
        rememberCoroutineScope()

        LaunchedEffect(trackId) {
            trackId?.let { id ->
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://spotify23.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiInterface = retrofit.create(ApiInterface::class.java)

                getTrackDetails(apiInterface, id) { track ->
                    trackDetails = track
                }
            }
        }

        trackDetail?.let { track ->
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = track.album.images.firstOrNull()?.url)
                            .apply { crossfade(true) }
                            .build()
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
                Text(text = track.name, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text(text = track.artists.joinToString(", ") { it.name }, fontSize = 20.sp, color = Color.Gray)
                Text(text = "Album: ${track.album.name}", fontSize = 20.sp, color = Color.Gray)
                Text(text = "ID: ${track.id}", fontSize = 16.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {
                        if (isPlaying) {
                            exoPlayer?.pause()
                            isPlaying = false
                        } else {
                            playPreview(track.preview_url)
                            isPlaying = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = if (isPlaying) R.drawable.pause else R.drawable.play_arrow),
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }
            }
        }
    }

    private fun searchTracks(apiInterface: ApiInterface, query: String) {
        val call = apiInterface.search(query)

        call.enqueue(object : Callback<TracksResponse> {
            override fun onResponse(call: Call<TracksResponse>, response: Response<TracksResponse>) {
                if (response.isSuccessful) {
                    val tracksList = response.body()?.tracks?.items?.map { it.data }
                    if (tracksList != null) {
                        tracks.clear()
                        tracks.addAll(tracksList)
                    } else {
                        Toast.makeText(this@MainActivity, "No track results found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.errorBody()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to fetch data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getTrackDetails(apiInterface: ApiInterface, trackId: String, onSuccess: (Track) -> Unit) {
        val call = apiInterface.getTracks(trackId)

        call.enqueue(object : Callback<TrackDetailsResponse> {
            override fun onResponse(call: Call<TrackDetailsResponse>, response: Response<TrackDetailsResponse>) {
                if (response.isSuccessful) {
                    val trackDetailsList = response.body()?.tracks
                    if (!trackDetailsList.isNullOrEmpty()) {
                        onSuccess(trackDetailsList.first())
                    } else {
                        Toast.makeText(this@MainActivity, "No details found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.errorBody()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TrackDetailsResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to fetch data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun playPreview(previewUrl: String?) {
        if (previewUrl.isNullOrEmpty()) {
            Toast.makeText(this, "Preview URL is not available", Toast.LENGTH_SHORT).show()
            return
        }

        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(this).build()
        }

        exoPlayer?.apply {
            setMediaItem(MediaItem.fromUri(previewUrl))
            prepare()
            play()
        }
    }
}