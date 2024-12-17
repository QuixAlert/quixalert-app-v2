package org.quixalert.br

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.quixalert.br.App
import org.quixalert.br.model.Adoption
import org.quixalert.br.model.AdoptionStatus
import org.quixalert.br.model.Bidding
import org.quixalert.br.model.Gender
import org.quixalert.br.model.News
import org.quixalert.br.model.Pet
import org.quixalert.br.model.PetType
import org.quixalert.br.model.Report
import org.quixalert.br.model.User

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

// Mock Data
public val mockUser = User(
    name = "Mara Lopes",
    greeting = "Bom dia",
    profileImage = "https://picsum.photos/seed/user/300"
)

public val mockLocalNews = listOf(
    News(
        id = "1",
        title = "Novo Parque será inaugurado no centro da cidade",
        image = "https://picsum.photos/seed/local1/300",
        icon = "https://picsum.photos/seed/user/300",
        isLocal = true
    ),
    News(
        id = "2",
        title = "Festival de Gastronomia acontece neste fim de semana",
        image = "https://picsum.photos/seed/local2/300",
        icon = "https://picsum.photos/seed/user/300",
        isLocal = true
    ),
    News(
        id = "3",
        title = "Prefeitura anuncia reforma de escola municipal",
        image = "https://picsum.photos/seed/local3/300",
        icon = "https://picsum.photos/seed/user/300",
        isLocal = true
    )
)

public val mockGlobalNews = listOf(
    News(
        id = "4",
        title = "Nova descoberta científica promete revolucionar medicina",
        image = "https://picsum.photos/seed/global1/300",
        icon = "https://picsum.photos/seed/user/300",
    ),
    News(
        id = "5",
        title = "Avanços tecnológicos na exploração espacial",
        image = "https://picsum.photos/seed/global2/300",
        icon = "https://picsum.photos/seed/user/300",
    ),
    News(
        id = "6",
        title = "Acordo internacional sobre mudanças climáticas",
        image = "https://picsum.photos/seed/global3/300",
        icon = "https://picsum.photos/seed/user/300",
    )
)

public val mockPets = listOf(
    Pet(
        id = "1",
        name = "Thor",
        image = "https://picsum.photos/seed/dog1/300",
        gender = Gender.MALE,
        type = PetType.DOG
    ),
    Pet(
        id = "2",
        name = "Luna",
        image = "https://picsum.photos/seed/cat1/300",
        gender = Gender.FEMALE,
        type = PetType.CAT
    ),
    Pet(
        id = "3",
        name = "Max",
        image = "https://picsum.photos/seed/dog2/300",
        gender = Gender.MALE,
        type = PetType.DOG
    ),
    Pet(
        id = "4",
        name = "Nina",
        image = "https://picsum.photos/seed/cat2/300",
        gender = Gender.FEMALE,
        type = PetType.CAT
    )
)

object MockData {
    val user = User(
        name = "João Silva",
        profileImage = "https://randomuser.me/api/portraits/men/1.jpg",
        greeting = "Ola"
    )

    val reports = listOf(
        Report(
            id = "1",
            title = "Buraco na calçada",
            image = "https://images.unsplash.com/photo-1584483766114-2cea6facdf57?ixlib=rb-4.0.3",
            icon = "https://img.icons8.com/color/48/000000/light.png",
            date = "2024-03-15"
        ),
        Report(
            id = "2",
            title = "Lâmpada queimada",
            image = "https://images.unsplash.com/photo-1563461661026-49631dd5d68e?ixlib=rb-4.0.3",
            icon = "https://img.icons8.com/color/48/000000/light.png",
            date = "2024-03-14"
        ),
        Report(
            id = "3",
            title = "Lixo acumulado",
            image = "https://images.unsplash.com/photo-1605600659908-0ef719419d41?ixlib=rb-4.0.3",
            icon = "https://img.icons8.com/color/48/000000/trash.png",
            date = "2024-03-13"
        )
    )

    val biddings = listOf(
        Bidding(
            id = "1",
            title = "Reforma da Praça Central",
            pdfUrl = "https://example.com/bidding1.pdf",
            image = "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?ixlib=rb-4.0.3",
            date = "2024-03-15"
        ),
        Bidding(
            id = "2",
            title = "Construção de Escola",
            pdfUrl = "https://example.com/bidding2.pdf",
            image = "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?ixlib=rb-4.0.3",
            date = "2024-03-14"
        ),
        Bidding(
            id = "3",
            title = "Pavimentação de Rua",
            pdfUrl = "https://example.com/bidding3.pdf",
            image = "https://images.unsplash.com/photo-1584483766114-2cea6facdf57?ixlib=rb-4.0.3",
            date = "2024-03-13"
        )
    )

    val adoptions = listOf(
        Adoption(
            id = "1",
            petName = "Rex",
            petImage = "https://images.unsplash.com/photo-1543466835-00a7907e9de1?ixlib=rb-4.0.3",
            petIcon = "https://img.icons8.com/color/48/000000/dog.png",
            status = AdoptionStatus.APPROVED
        ),
        Adoption(
            id = "2",
            petName = "Luna",
            petImage = "https://images.unsplash.com/photo-1543852786-1cf6624b9987?ixlib=rb-4.0.3",
            petIcon = "https://img.icons8.com/color/48/000000/cat.png",
            status = AdoptionStatus.PENDING
        ),
        Adoption(
            id = "3",
            petName = "Max",
            petImage = "https://images.unsplash.com/photo-1537151625747-768eb6cf92b2?ixlib=rb-4.0.3",
            petIcon = "https://img.icons8.com/color/48/000000/dog.png",
            status = AdoptionStatus.APPROVED
        )
    )
}

// Uso:
// val user = MockData.user
// val reports = MockData.reports
// val biddings = MockData.biddings
// val adoptions = MockData.adoptions
