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
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = poppinsFamily(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                letterSpacing = (-0.333333).sp,
                color = Color(0xFF50555C)
            ),
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(15.dp),
                    spotColor = Color(0x40000000)
                )
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(15.dp)
                ),
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontFamily = poppinsFamily(),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        letterSpacing = (-0.333333).sp,
                        color = Color(0x7A50555C), // opacity 0.48
                        lineHeight = 20.sp
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xE6D0D3D9),
                unfocusedContainerColor = Color(0xE6D0D3D9),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTextColor = Color(0xFF50555C),
                unfocusedTextColor = Color(0xFF50555C)
            ),
            textStyle = TextStyle(
                fontFamily = poppinsFamily(),
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                letterSpacing = (-0.333333).sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(15.dp)
        )
    }
} 