package com.wajahatkarim.flippable_demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.wajahatkarim.flippable.FlippableController

@Composable
fun EnglishWordFrontSide(
    flipController: FlippableController
) {
    Box(
        modifier = Modifier
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFECECEC),
            elevation = 16.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (txtShuffleWord, txtWord, lblTapToFlip) = createRefs()

                    Text(
                        text = "culminate",
                        style = MaterialTheme.typography.h5,
                        maxLines = 1,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .constrainAs(txtWord) {
                                linkTo(
                                    start = parent.start,
                                    end = parent.end
                                )
                                linkTo(
                                    top = parent.top,
                                    bottom = parent.bottom
                                )
                            }
                    )

                    Text(
                        text = "TAP TO SEE THE MEANING!",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0xFFA5A5A5),
                        maxLines = 1,
                        modifier = Modifier
                            .constrainAs(lblTapToFlip) {
                                linkTo(
                                    start = parent.start,
                                    end = parent.end
                                )
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(bottom = 8.dp)
                    )

                    Button(
                        onClick = {
                              flipController.flip()
                        },
                        modifier = Modifier
                            .constrainAs(txtShuffleWord) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Show Meaning",
                            style = MaterialTheme.typography.subtitle2,
                            color = Color(0xFFFFFFFF),
                            maxLines = 1
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun EnglishWordBackSide(
    flipController: FlippableController
) {
    Box(
        modifier = Modifier
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFECECEC),
            elevation = 16.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                ConstraintLayout(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val (txtShuffleWord, txtWord, txtMeaning, txtExample, txtExampleAnswer, btnDontKnow, btnKnow) = createRefs()

                    Text(
                        text = "culminate",
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .constrainAs(txtWord) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                            }
                            .padding(top = 16.dp, start = 16.dp)
                    )

                    Text(
                        text = "reproduce, duplicate",
                        style = MaterialTheme.typography.subtitle2,
                        maxLines = 1,
                        color = Color(0xFF696969),
                        modifier = Modifier
                            .constrainAs(txtMeaning) {
                                start.linkTo(txtWord.start)
                                top.linkTo(txtWord.bottom)
                            }
                            .padding(start = 16.dp)
                    )

                    Text(
                        text = "Example",
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .constrainAs(txtExample) {
                                top.linkTo(txtMeaning.bottom)
                                start.linkTo(txtMeaning.start)
                            }
                            .padding(top = 16.dp, start = 16.dp)
                    )

                    Text(
                        text = "Some word example sentence is written here to explain the meaning.",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0xFF696969),
                        modifier = Modifier
                            .constrainAs(txtExampleAnswer) {
                                start.linkTo(txtExample.start)
                                top.linkTo(txtExample.bottom)
                            }
                            .padding(start = 16.dp, end = 16.dp)
                    )

                    Text(
                        text = "I DON'T KNOW",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0xFF8B1818),
                        maxLines = 1,
                        modifier = Modifier
                            .constrainAs(btnDontKnow) {
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(bottom = 16.dp, start = 16.dp)
                            .clickable(
                                indication = rememberRipple(color = MaterialTheme.colors.primary),
                                interactionSource = remember { MutableInteractionSource() },
                            ) {
                                flipController.flip()
                            }
                    )

                    Text(
                        text = "I KNOW THIS WORD!",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0xFF3588C7),
                        maxLines = 1,
                        modifier = Modifier
                            .constrainAs(btnKnow) {
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(bottom = 16.dp, end = 16.dp)
                            .clickable(
                                indication = rememberRipple(color = MaterialTheme.colors.primary),
                                interactionSource = remember { MutableInteractionSource() },
                            ) {
                                flipController.flip()
                            }
                    )
                }
            }
        }
    }
}