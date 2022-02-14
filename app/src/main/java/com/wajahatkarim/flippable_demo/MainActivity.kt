package com.wajahatkarim.flippable_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.FlipView
import com.wajahatkarim.flippable.rememberFlipController
import com.wajahatkarim.flippable_demo.ui.theme.FlippableDemoTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlippableDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    var duration: Int by remember { mutableStateOf(400) }
                    var flipOnTouchEnabled: Boolean by remember { mutableStateOf(true) }
                    val flipViewController = rememberFlipController()

                    FlipView(
                        flipDurationMs = duration,
                        flipOnTouch = flipOnTouchEnabled,
                        flipController = flipViewController,
                        flipEnabled = true,
                        frontSide = {
                            Image(
                                painter = painterResource(id = R.drawable.card_front),
                                contentDescription = "",
                                modifier = Modifier.size(width = 300.dp, height = 150.dp)
                            )
                        },
                        backSide = {
                            Image(
                                painter = painterResource(id = R.drawable.card_back),
                                contentDescription = "",
                                modifier = Modifier.size(width = 300.dp, height = 150.dp)
                            )
                        },
                        modifier = Modifier.padding(top = 20.dp),
                        contentAlignment = Alignment.TopCenter,
                        onFlippedListener = { currentSide ->
                            println(currentSide)
                        },
                        autoFlip = false,
                        autoFlipDurationMs = 1000,
                        flipAnimationType = FlipAnimationType.VERTICAL_ANTI_CLOCKWISE
                    )

                    Slider(
                        value = duration.toFloat(),
                        valueRange = 100f..4000f,
                        onValueChange = {
                            duration = it.toInt()
                            flipViewController.flip()
                        },
                        onValueChangeFinished = {

                        }
                    )

                    Checkbox(
                        checked = flipOnTouchEnabled,
                        modifier = Modifier.wrapContentWidth(align = Alignment.Start),
                        onCheckedChange = {
                            flipOnTouchEnabled = it
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FlippableDemoTheme {
        Greeting("Android")
    }
}