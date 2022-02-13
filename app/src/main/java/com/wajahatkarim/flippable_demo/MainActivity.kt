package com.wajahatkarim.flippable_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wajahatkarim.flippable.FlipView
import com.wajahatkarim.flippable_demo.ui.theme.FlippableDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlippableDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FlipView(
                        flipDurationMs = 400,
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
                        })
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