package org.quixalert.br.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun NavgationBarWithScaffold() {
    Scaffold (
        bottomBar = { NavigationBarM3() }
    ){ padding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues( 16.dp)
        ) {
            items(50) {
                ListItem(
                    headlineContent = {Text("Item $it")},
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null
                        )
                    }
                )
            }

        }

    }

}

@Composable
fun NavigationBarM3(){
    var selectedItem by remember { mutableStateOf(0) }
    val barItems = listOf(
        BarItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "home"
        ),
        BarItem(
            title = "News",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
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
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,
            route = "animals"
        ),
        BarItem(
            title = "Perfil",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "perfil"
        ),
    )

    NavigationBar{
        barItems.forEachIndexed { index, barItem ->
            val selected = selectedItem == index
            NavigationBarItem(
                selected =selectedItem == index,
                onClick = {
                    selectedItem = index
                },
                icon = {
                   Icon(
                       imageVector = if(selected){
                           barItem.selectedIcon
                       }else barItem.unselectedIcon,
                       contentDescription = barItem.title,
                       tint = if (selected){
                           MaterialTheme.colorScheme.primary
                       }else{
                           MaterialTheme.colorScheme.onBackground
                       }
                   )
                },
                alwaysShowLabel = selected
            )
        }

    }
}

data class BarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
