package org.quixalert.br.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.quixalert.br.R
import org.quixalert.br.presentation.icons.NewsIcon

data class BarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun FloatingMenu(modifier: Modifier = Modifier, onReportClick: () -> Unit, onDocumentClick: () -> Unit, onEmergencyClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 56.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.background(Color.Transparent)
        ) {
            FloatingActionButton(
                onClick = onReportClick,
                containerColor = Color(0xFFB2DFDB)
            ) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.alert), "Floating action button.", tint = MaterialTheme.colorScheme.background)
            }
            Text(
                text = "Denúncias",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.offset(y = (-42).dp)
        ) {
            FloatingActionButton(
                onClick = onDocumentClick,
                containerColor = Color(0xFFB2DFDB)
            ) {
                Icon(ImageVector.vectorResource(R.drawable.file), "Floating action button.", tint = MaterialTheme.colorScheme.background)
            }
            Text(
                text = "Documentos",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            FloatingActionButton(
                onClick = onEmergencyClick,
                containerColor = Color(0xFFB2DFDB)
            ) {
                Icon(ImageVector.vectorResource(id = R.drawable.phone), "Floating action button.", tint = MaterialTheme.colorScheme.background)
            }
            Text(
                text = "Emergência",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }
}

@Composable
fun NavigationBarM3(onPlusClick: () -> Unit, onOtherCLick: (String) -> Unit) {
    var selectedItem by remember { mutableStateOf(0) }

    val barItems = listOf(
        BarItem(
            title = "Home",
            selectedIcon = Icons.Outlined.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "home"
        ),
        BarItem(
            title = "News",
            selectedIcon = NewsIcon,
            unselectedIcon = NewsIcon,
            route = "news"
        ),
        BarItem(
            title = "Add",
            selectedIcon = Icons.Filled.AddCircle,
            unselectedIcon = Icons.Outlined.AddCircle,
            route = "add"
        ),
        BarItem(
            title = "Animals",
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.paw),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.paw),
            route = "animals"
        ),
        BarItem(
            title = "Profile",
            selectedIcon = Icons.Outlined.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "profile"
        ),
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        barItems.forEachIndexed { index, barItem ->
            val selected = selectedItem == index
            NavigationBarItem(
                selected = selected,
                onClick = {
                    selectedItem = index
                    if (barItem.title == "Add") {
                        onPlusClick()
                    } else {
                        onOtherCLick(barItem.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) {
                            barItem.selectedIcon
                        } else barItem.unselectedIcon,
                        contentDescription = barItem.title,
                        tint = Color(0xFF000000)
                    )
                },
                alwaysShowLabel = selected,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFB2DFDB)
                )
            )
        }
    }
}

