package com.example.geoquizjetpack

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        setContent { MyScreen() }
    }
}

@Composable
fun MyScreen() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    QuizLayout(isLandscape = isLandscape)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizLayout(isLandscape: Boolean) {

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
    var correctCount by rememberSaveable { mutableIntStateOf(0) }
    var incorrectCount by rememberSaveable { mutableIntStateOf(0) }
    var backgroundColor by remember { mutableStateOf(Color.LightGray) }

    val currentQuestion = mQuestionBank[currentIndex]

    Scaffold(
        topBar = { TopAppBar(title = { Text("GeoQuiz") }) }
    ) { padding ->
        val layoutModifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(padding)
            .padding(horizontal = 24.dp)

        if (isLandscape) {
            // Layout horizontal
            Row(
                modifier = layoutModifier,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuestionSection(currentQuestion.textResId)
                AnswerButtons(
                    currentQuestion = currentQuestion,
                    onAnswer = { isCorrect ->
                        if (isCorrect) {
                            backgroundColor = Color(0xFFB2DFDB) // verde suave
                            correctCount++
                            Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
                        } else {
                            backgroundColor = Color(0xFFFFCDD2) // rojo suave
                            incorrectCount++
                            Toast.makeText(context, "Incorrect!", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                NextButton(
                    onNext = {
                        currentIndex = (currentIndex + 1) % mQuestionBank.size
                        backgroundColor = Color.LightGray
                    }
                )
            }
        } else {
            // Layout vertical
            Column(
                modifier = layoutModifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                QuestionSection(currentQuestion.textResId)
                Spacer(Modifier.height(16.dp))
                AnswerButtons(
                    currentQuestion = currentQuestion,
                    onAnswer = { isCorrect ->
                        if (isCorrect) {
                            backgroundColor = Color(0xFFB2DFDB)
                            correctCount++
                            Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
                        } else {
                            backgroundColor = Color(0xFFFFCDD2)
                            incorrectCount++
                            Toast.makeText(context, "Incorrect!", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
                Spacer(Modifier.height(24.dp))
                NextButton(
                    onNext = {
                        currentIndex = (currentIndex + 1) % mQuestionBank.size
                        backgroundColor = Color.LightGray
                    }
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "✔ Correctas: $correctCount   ✖ Incorrectas: $incorrectCount",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun QuestionSection(textResId: Int) {
    Text(
        text = stringResource(textResId),
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun AnswerButtons(currentQuestion: Question, onAnswer: (Boolean) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        FilledTonalButton(onClick = { onAnswer(currentQuestion.answer) }) {
            Text(stringResource(R.string.btnTrue))
        }
        FilledTonalButton(onClick = { onAnswer(!currentQuestion.answer) }) {
            Text(stringResource(R.string.btnFalse))
        }
    }
}

@Composable
fun NextButton(onNext: () -> Unit) {
    FilledTonalButton(onClick = onNext) {
        Text(text = stringResource(R.string.btnNext))
    }
}

