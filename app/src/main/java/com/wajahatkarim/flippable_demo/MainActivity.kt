package com.wajahatkarim.flippable_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.Flippable
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top
                    ) {

                        var duration: Int by remember { mutableStateOf(400) }
                        var flipOnTouchEnabled: Boolean by remember { mutableStateOf(true) }
                        val flipEnabled: Boolean by remember { mutableStateOf(true) }
                        var autoFlipEnabled: Boolean by remember { mutableStateOf(false) }
                        var selectedAnimType: FlipAnimationType by remember { mutableStateOf(FlipAnimationType.VERTICAL_ANTI_CLOCKWISE) }

                        val flipController = rememberFlipController()
                        val flipController2 = rememberFlipController()

                        Flippable(
                            frontSide = {
                                EnglishWordFrontSide(flipController)
                            },
                            backSide = {
                                EnglishWordBackSide(flipController)
                            },
                            flipController = flipController,
                            flipDurationMs = duration,
                            flipOnTouch = flipOnTouchEnabled,
                            flipEnabled = flipEnabled,
                            autoFlip = autoFlipEnabled,
                            autoFlipDurationMs = 2000,
                            flipAnimationType = selectedAnimType
                        )

                        Flippable(
                            flipDurationMs = duration,
                            flipOnTouch = flipOnTouchEnabled,
                            flipController = flipController2,
                            flipEnabled = flipEnabled,
                            frontSide = {
                                Image(
                                    painter = painterResource(id = R.drawable.card_front),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(height = 150.dp)
                                )
                            },
                            backSide = {
                                Image(
                                    painter = painterResource(id = R.drawable.card_back),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(height = 150.dp)
                                )
                            },
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(top = 50.dp),
                            contentAlignment = Alignment.TopCenter,
                            onFlippedListener = { currentSide ->
                                println(currentSide)
                            },
                            autoFlip = autoFlipEnabled,
                            autoFlipDurationMs = 2000,
                            flipAnimationType = selectedAnimType
                        )

                        ConstraintLayout {

                            val (sliderDuration, checkFlipOnTouch, checkAutoFlip, groupAnimType, lblAnimType) = createRefs()

                            Slider(
                                value = duration.toFloat(),
                                valueRange = 100f..4000f,
                                onValueChange = {
                                    duration = it.toInt()
                                },
                                onValueChangeFinished = {

                                },
                                modifier = Modifier
                                    .constrainAs(sliderDuration) {
                                        top.linkTo(parent.top)
                                        start.linkTo(parent.start)
                                    }
                            )

                            LabelledCheckBox(
                                checked = flipOnTouchEnabled,
                                label = "Flip On Touch",
                                modifier = Modifier
                                    .wrapContentWidth(align = Alignment.Start)
                                    .constrainAs(checkFlipOnTouch) {
                                        top.linkTo(sliderDuration.bottom)
                                        start.linkTo(sliderDuration.start)
                                    },
                                onCheckedChange = {
                                    flipOnTouchEnabled = it
                                }
                            )

                            LabelledCheckBox(
                                checked = autoFlipEnabled,
                                label = "Auto-Flip back after 2 seconds",
                                modifier = Modifier
                                    .wrapContentWidth(align = Alignment.Start)
                                    .constrainAs(checkAutoFlip) {
                                        end.linkTo(parent.end)
                                        top.linkTo(checkFlipOnTouch.top)
                                        bottom.linkTo(checkFlipOnTouch.bottom)
                                    },
                                onCheckedChange = {
                                    autoFlipEnabled = it
                                }
                            )

                            Text(
                                text = "Animation Type",
                                style = MaterialTheme.typography.subtitle2,
                                maxLines = 1,
                                color = Color(0xFF696969),
                                modifier = Modifier
                                    .constrainAs(lblAnimType) {
                                        top.linkTo(checkAutoFlip.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                                    .padding(top = 30.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .constrainAs(groupAnimType) {
                                        top.linkTo(lblAnimType.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                    }
                            ) {
                                Row {
                                    LabelledRadioButton(
                                        checked = selectedAnimType == FlipAnimationType.HORIZONTAL_CLOCKWISE,
                                        onCheckedChange = {
                                            selectedAnimType = FlipAnimationType.HORIZONTAL_CLOCKWISE
                                        },
                                        label = "Horizontal CW"
                                    )

                                    Spacer(modifier = Modifier.size(4.dp))
                                    LabelledRadioButton(
                                        checked = selectedAnimType == FlipAnimationType.HORIZONTAL_ANTI_CLOCKWISE,
                                        onCheckedChange = {
                                            selectedAnimType = FlipAnimationType.HORIZONTAL_ANTI_CLOCKWISE
                                        },
                                        label = "Horizontal Anti-CW"
                                    )
                                }

                                Row {
                                    LabelledRadioButton(
                                        checked = selectedAnimType == FlipAnimationType.VERTICAL_CLOCKWISE,
                                        onCheckedChange = {
                                            selectedAnimType = FlipAnimationType.VERTICAL_CLOCKWISE
                                        },
                                        label = "Vertical CW"
                                    )

                                    Spacer(modifier = Modifier.size(4.dp))
                                    LabelledRadioButton(
                                        checked = selectedAnimType == FlipAnimationType.VERTICAL_ANTI_CLOCKWISE,
                                        onCheckedChange = {
                                            selectedAnimType = FlipAnimationType.VERTICAL_ANTI_CLOCKWISE
                                        },
                                        label = "Vertical Anti-CW"
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LabelledCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                indication = rememberRipple(color = MaterialTheme.colors.primary),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onCheckedChange(!checked) }
            )
            .requiredHeight(ButtonDefaults.MinHeight)
            .padding(4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null
        )

        Spacer(Modifier.size(6.dp))

        Text(
            text = label,
        )
    }
}

@Composable
fun LabelledRadioButton(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit),
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(
                indication = rememberRipple(color = MaterialTheme.colors.primary),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onCheckedChange(!checked) }
            )
            .requiredHeight(ButtonDefaults.MinHeight)
            .padding(4.dp)
    ) {
        RadioButton(
            selected = checked,
            onClick = null
        )

        Spacer(Modifier.size(6.dp))

        Text(
            text = label,
        )
    }
}