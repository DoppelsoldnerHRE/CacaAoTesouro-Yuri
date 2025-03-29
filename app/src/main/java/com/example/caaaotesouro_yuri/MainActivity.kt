package com.example.caaaotesouro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.caaaotesouro.ui.theme.CaçaAoTesouroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaçaAoTesouroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TreasureHuntApp()
                }
            }
        }
    }
}

@Composable
fun TreasureHuntApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onStartClicked = { navController.navigate("clue1") }
            )
        }

        composable("clue1") {
            ClueScreen(
                clueNumber = 1,
                clueText = "Pista 1: Eu tenho cidades, mas não tenho casas. Tenho montanhas, mas não tenho árvores. Tenho água, mas não tenho peixes. O que eu sou?",
                correctAnswer = "mapa",
                onNextClicked = { navController.navigate("clue2") },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable("clue2") {
            ClueScreen(
                clueNumber = 2,
                clueText = "Pista 2: Quanto mais você tira, maior eu fico. O que eu sou?",
                correctAnswer = "buraco",
                onNextClicked = { navController.navigate("clue3") },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable("clue3") {
            ClueScreen(
                clueNumber = 3,
                clueText = "Pista 3: Eu não posso ser visto, não posso ser sentido, não posso ser ouvido e não posso ser cheirado. Eu estou atrás das estrelas e embaixo das colinas. Vazio sou o começo e o fim de tudo. O que eu sou?",
                correctAnswer = "escuro",
                onNextClicked = { navController.navigate("treasure") },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable("treasure") {
            TreasureScreen(
                onRestartClicked = {
                    navController.popBackStack("home", inclusive = false)
                }
            )
        }
    }
}

@Composable
fun HomeScreen(onStartClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Caça ao Tesouro",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onStartClicked,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Iniciar Caça ao Tesouro")
        }
    }
}

@Composable
fun ClueScreen(
    clueNumber: Int,
    clueText: String,
    correctAnswer: String,
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    var answer by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Pista $clueNumber",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = clueText,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = answer,
                onValueChange = {
                    answer = it
                    showError = false
                },
                label = { Text("Sua resposta") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError
            )
            if (showError) {
                Text(
                    text = "Resposta incorreta! Tente novamente.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBackClicked,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Voltar")
            }
            Button(
                onClick = {
                    if (answer.equals(correctAnswer, ignoreCase = true)) {
                        onNextClicked()
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("Próxima Pista")
            }
        }
    }
}

@Composable
fun TreasureScreen(onRestartClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Parabéns!",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Você encontrou o tesouro!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onRestartClicked,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Recomeçar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    CaçaAoTesouroTheme {
        HomeScreen(onStartClicked = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClueScreen() {
    CaçaAoTesouroTheme {
        ClueScreen(
            clueNumber = 1,
            clueText = "Pista de exemplo",
            correctAnswer = "resposta",
            onNextClicked = {},
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTreasureScreen() {
    CaçaAoTesouroTheme {
        TreasureScreen(onRestartClicked = {})
    }
}