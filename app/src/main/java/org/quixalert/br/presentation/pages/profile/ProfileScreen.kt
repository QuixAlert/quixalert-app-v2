package org.quixalert.br.presentation.pages.profile

import android.net.Uri
import android.webkit.WebView
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.quixalert.br.domain.model.AdoptionStatus
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.model.AnimalType
import org.quixalert.br.domain.model.Bidding
import org.quixalert.br.domain.model.Document
import org.quixalert.br.domain.model.Report
import org.quixalert.br.domain.model.User
import org.quixalert.br.presentation.icons.QuestionIcon
import org.quixalert.br.presentation.pages.documentsSolicitationScreen.MOCKED_DOCUMENTATION_PHOTO
import org.quixalert.br.services.FirebaseAuthService

val IconTint = Color(0xFF269996)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: User,
    biddings: List<Bidding>,
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    isDarkThemeEnabled: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    onBiddingClick: (Bidding) -> Unit,
    onReportClick: (Report) -> Unit,
    onAdoptionClick: (AdoptionT) -> Unit,
    onDocumentClick: (Document) -> Unit,
    onFaqCLick: () -> Unit,
    onExitClick: () -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    firebaseAuthService: FirebaseAuthService,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val MOCKED_DOCUMENTATION_PHOTO = "https://static.vecteezy.com/system/resources/thumbnails/047/201/337/small_2x/pile-of-documents-sat-on-desk-in-office-was-pile-of-documents-that-had-been-prepared-to-be-presented-at-meeting-as-summary-of-annual-operations-documents-prepared-for-storage-and-piled-on-the-desk-photo.jpg"

    val context = LocalContext.current
    val uiState by profileViewModel.uiState.collectAsState()
    val isMenuOpen = remember { mutableStateOf(false) }
    val darkThemeState = remember { mutableStateOf(isDarkThemeEnabled) }
    val currentUser = firebaseAuthService.getCurrentUser()

    LaunchedEffect(Unit) {
        currentUser?.uid?.let { uid ->
            profileViewModel.loadAdoptionsByUserId(uid)
            profileViewModel.loadReportByUserId(uid)
            profileViewModel.loadDocumentsByUserId(uid)
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onProfileImageChange(it) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Bar
            item {
                TopBar(
                    onBackClick = onBackClick,
                    onMenuClick = { isMenuOpen.value = !isMenuOpen.value }
                )
            }
            // Profile Header
            item {
                if (currentUser != null) {
                    ProfileHeader(
                        user = user,
                        onEditClick = onEditProfileClick,
                        imagePickerLauncher = imagePickerLauncher
                    )
                }
            }
            // Preferences Header
            item {
                Text(
                    text = "Preferências",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            // Dark Theme Toggle
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Modo Escuro",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Switch(
                        checked = darkThemeState.value,
                        onCheckedChange = {
                            darkThemeState.value = it
                            onThemeToggle(it)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = IconTint,
                            uncheckedThumbColor = IconTint
                        )
                    )
                }
            }
            // Reports Section
            item {
                Text(
                    text = "Minhas Denúncias",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item {
                when {
                    uiState.isLoadingReports -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    uiState.errorReports != null -> {
                        Text(
                            text = uiState.errorReports!!,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(uiState.reportsSolicitationByUser) { report ->
                                ReportItem(
                                    report = report,
                                    onReportClick = { clickedReport -> 
                                        onReportClick(clickedReport)
                                    }
                                )
                            }
                        }
                    }
                }
            }
            // Documents Section
            item {
                Text(
                    text = "Meus Documentos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item {
                when {
                    uiState.isLoadingDocuments -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    uiState.errorDocuments != null -> {
                        Text(
                            text = uiState.errorDocuments!!,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(vertical = 8.dp)
                        ) {
                            items(uiState.documentsSolicitationByUser) { document ->
                                DocumentItem(document = document, onDocumentClick = onDocumentClick)
                            }
                        }
                    }
                }
            }
            // Adoptions Section
            item {
                Text(
                    text = "Minhas Adoções",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            if (uiState.isLoadingAdoptions) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            } else if (uiState.errorAdoptions != null) {
                item {
                    Text(
                        text = uiState.errorAdoptions!!,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                if (uiState.adoptionsByUser.isEmpty()) {
                    item {
                        Text(
                            text = "Você não fez nenhuma solicitação de adoção",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                } else {
                    items(uiState.adoptionsByUser) { adoption ->
                        AdoptionItem(adoption = adoption, onAdoptionClick = onAdoptionClick)
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
        // Drawer overlay
        if (isMenuOpen.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { isMenuOpen.value = false }
            )
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                DrawerContent(
                    onExitClick = onExitClick,
                    onFaqCLick = onFaqCLick,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                        .fillMaxHeight()
                        .width(220.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
fun DrawerContent(
    onExitClick: () -> Unit,
    onFaqCLick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onFaqCLick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = QuestionIcon,
                contentDescription = "FAQ",
                modifier = Modifier.size(24.dp),
                tint = IconTint
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "FAQ", style = MaterialTheme.typography.bodyMedium)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onExitClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = "Sair",
                modifier = Modifier.size(24.dp),
                tint = IconTint
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sair", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = IconTint
            )
        }
        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = IconTint
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    user: User,
    onEditClick: () -> Unit,
    imagePickerLauncher: ManagedActivityResultLauncher<String, Uri?>
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .clickable { imagePickerLauncher.launch("image/*") }
            ) {
                AsyncImage(
                    model = user.profileImage,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .border(2.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun DocumentItem(document: org.quixalert.br.domain.model.Document, onDocumentClick: (Document) -> Unit) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onDocumentClick(document) }
    ) {
        Column {
            AsyncImage(
                model = MOCKED_DOCUMENTATION_PHOTO,
                contentDescription = document.id,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = document.reason,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun ReportItem(report: Report, onReportClick: (Report) -> Unit) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onReportClick(report) }
    ) {
        Column {
            AsyncImage(
                model = report.image,
                contentDescription = report.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = report.icon,
                    contentDescription = report.title,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = report.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun AdoptionItem(adoption: AdoptionT, onAdoptionClick: (AdoptionT) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onAdoptionClick(adoption) }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box {
                AsyncImage(
                    model = adoption.animal?.image,
                    contentDescription = adoption.animal?.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = if (adoption.status == AdoptionStatus.APPROVED) "Adotado" else "Em análise",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(
                            color = if (adoption.status == AdoptionStatus.APPROVED) Color(0xFF269996) else Color(0xFF0B5DA2),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = if (adoption.animal?.type == AnimalType.DOG)
                        "https://img.icons8.com/color/48/000000/dog.png"
                    else
                        "https://img.icons8.com/color/48/000000/cat.png",
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                adoption.animal?.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun BiddingItem(
    bidding: Bidding,
    onBiddingClick: (Bidding) -> Unit
) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onBiddingClick(bidding) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = bidding.image,
                contentDescription = bidding.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = IconTint
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = bidding.title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun BiddingPdfScreen(
    bidding: Bidding,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Voltar",
                    tint = IconTint
                )
            }
            Text(
                text = bidding.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(
                onClick = onShareClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartilhar PDF",
                    tint = IconTint
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        loadUrl(bidding.pdfUrl)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}