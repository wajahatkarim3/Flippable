package com.wajahatkarim.flippable_demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController
import com.wajahatkarim.flippable.FlippableState

data class WordCard(
    val id: Int,
    val word: String,
    val partOfSpeech: String,
    val definition: String,
    val example: String,
    val accentColor: Color,
)

val sampleCards = listOf(
    WordCard(0, "Ephemeral", "adj.", "Lasting for a very short time", "Social media fame is often ephemeral.", Color(0xFF6200EE)),
    WordCard(1, "Serendipity", "n.", "The occurrence of happy accidents", "It was pure serendipity that they met.", Color(0xFF0097A7)),
    WordCard(2, "Eloquent", "adj.", "Fluent or persuasive in speaking or writing", "She gave an eloquent speech at the ceremony.", Color(0xFF388E3C)),
    WordCard(3, "Pragmatic", "adj.", "Dealing with things sensibly and practically", "A pragmatic approach to problem solving.", Color(0xFFE64A19)),
    WordCard(4, "Resilient", "adj.", "Able to recover quickly from difficulties", "Children are surprisingly resilient.", Color(0xFF7B1FA2)),
    WordCard(5, "Meticulous", "adj.", "Showing great attention to detail", "She was meticulous in her research.", Color(0xFF0288D1)),
    WordCard(6, "Tenacious", "adj.", "Holding firmly to a purpose or goal", "His tenacious spirit eventually led to success.", Color(0xFFC62828)),
    WordCard(7, "Ambiguous", "adj.", "Open to more than one interpretation", "The exam question was frustratingly ambiguous.", Color(0xFF4E342E)),
    WordCard(8, "Candid", "adj.", "Truthful and straightforward", "I really appreciated her candid feedback.", Color(0xFF00695C)),
    WordCard(9, "Profound", "adj.", "Having deep insight or great intensity", "A profound silence filled the room.", Color(0xFF37474F)),
)

@Composable
fun FlippableListScreen() {
    val controllers = remember { sampleCards.map { FlippableController() } }
    val flipStates: SnapshotStateList<Boolean> = remember {
        sampleCards.map { false }.toMutableStateList()
    }
    var isAllFlipped by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                isAllFlipped = !isAllFlipped
                flipStates.indices.forEach { i -> flipStates[i] = isAllFlipped }
                controllers.forEach { controller ->
                    if (isAllFlipped) controller.flipToBack() else controller.flipToFront()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isAllFlipped) Color(0xFF424242) else MaterialTheme.colors.primary
            )
        ) {
            Text(
                text = if (isAllFlipped) "Hide All Answers" else "Reveal All Answers",
                style = MaterialTheme.typography.button,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {
            itemsIndexed(items = sampleCards, key = { _, card -> card.id }) { index, card ->
                Flippable(
                    frontSide = { WordCardFront(card) },
                    backSide = { WordCardBack(card) },
                    flipController = controllers[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    initialSide = if (flipStates[index]) FlippableState.BACK else FlippableState.FRONT,
                    flipAnimationType = FlipAnimationType.VERTICAL_CLOCKWISE,
                    onFlippedListener = { side ->
                        flipStates[index] = (side == FlippableState.BACK)
                    }
                )
            }
        }
    }
}

@Composable
fun WordCardFront(card: WordCard) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFF5F5F5),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = card.word,
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = card.partOfSpeech,
                        style = MaterialTheme.typography.caption,
                        color = Color(0xFF9E9E9E),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
            Text(
                text = "TAP TO SEE DEFINITION",
                style = MaterialTheme.typography.overline,
                color = Color(0xFFBDBDBD),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp)
            )
        }
    }
}

@Composable
fun WordCardBack(card: WordCard) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = card.accentColor, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = card.word,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = card.partOfSpeech,
                        style = MaterialTheme.typography.caption,
                        color = Color.White.copy(alpha = 0.5f),
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = card.definition,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "\"${card.example}\"",
                    style = MaterialTheme.typography.caption,
                    color = Color.White.copy(alpha = 0.75f),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}
