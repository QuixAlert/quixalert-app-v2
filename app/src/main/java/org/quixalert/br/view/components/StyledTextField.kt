package org.quixalert.br.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.view.ui.theme.poppinsFamily

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = poppinsFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 0.sp,
                color = Color(0xFF50555C)
            ),
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(24.dp),
                    spotColor = Color(0x1A000000)
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxSize(),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = TextStyle(
                            fontFamily = poppinsFamily(),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            color = Color(0x7A50555C),
                            lineHeight = 20.sp
                        )
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xF2D0D3D9),
                    unfocusedContainerColor = Color(0xF2D0D3D9),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color(0xFF50555C),
                    unfocusedTextColor = Color(0xFF50555C),
                    disabledContainerColor = Color(0xF2D0D3D9)
                ),
                textStyle = TextStyle(
                    fontFamily = poppinsFamily(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp
                ),
                singleLine = true,
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
} 