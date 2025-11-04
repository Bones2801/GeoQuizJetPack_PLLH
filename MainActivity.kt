package com.example.geoquizjetpack

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyScreen()
        }
    }
}

@Composable
fun MyScreen() {
    HandleOrientationChanges()
}

@Composable
fun HandleOrientationChanges() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (isLandscape) {
        LandscapeLayout()
    } else {
        PortraitLayout()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitLayout() {
    val context = LocalContext.current

    val mQuestionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_oceans, true)
    )

    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentQuestion = mQuestionBank[currentIndex]

    Scaffold(
        topBar = { TopAppBar(title = { Text("GeoQuiz") }) }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(currentQuestion.textResId),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    FilledTonalButton(onClick = {
                        val msg = if (currentQuestion.answer) "Correct!" else "Incorrect!"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }) { Text(stringResource(R.string.btnTrue)) }

                    FilledTonalButton(onClick = {
                        val msg = if (!currentQuestion.answer) "Correct!" else "Incorrect!"
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }) { Text(stringResource(R.string.btnFalse)) }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    FilledTonalButton(onClick = {
                        currentIndex = (currentIndex + 1) % mQuestionBank.size
                    }) { Text(text = stringResource(R.string.btnNext)) }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandscapeLayout() {
    val context = LocalContext.current

    val mQuestionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_oceans, true)
    )

    var currentIndex by rememberSaveable { mutableIntStateOf(0) }
    val currentQuestion = mQuestionBank[currentIndex]

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("GeoQuiz") })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Texto de la pregunta
                Text(
                    text = stringResource(currentQuestion.textResId),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                )

                // Botones de respuesta
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FilledTonalButton(onClick = {
                            val msg = if (currentQuestion.answer) "Correct!" else "Incorrect!"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }) { Text(stringResource(R.string.btnTrue)) }

                        FilledTonalButton(onClick = {
                            val msg = if (!currentQuestion.answer) "Correct!" else "Incorrect!"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }) { Text(stringResource(R.string.btnFalse)) }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    FilledTonalButton(onClick = {
                        currentIndex = (currentIndex + 1) % mQuestionBank.size
                    }) { Text(text = stringResource(R.string.btnNext)) }
                }
            }
        }
    }
}
