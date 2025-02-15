package org.quixalert.br.utils

import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.AnimalExtraInfo
import org.quixalert.br.domain.model.AnimalGender
import org.quixalert.br.domain.model.AnimalSize
import org.quixalert.br.domain.model.AnimalType
import org.quixalert.br.domain.model.Message
import org.quixalert.br.domain.model.News
import org.quixalert.br.domain.model.NewsType
import org.quixalert.br.domain.model.Notification
import java.text.SimpleDateFormat

fun populateAnimalData(): List<Animal> {
    val animals = listOf(
        Animal(
            name = "Rex",
            image = "https://i.pinimg.com/550x/fe/31/d7/fe31d7b41f988d922ae543b979bb0b91.jpg",
            gender = AnimalGender.MALE,
            type = AnimalType.DOG,
            age = 3,
            species = "Golden Retriever",
            size = AnimalSize.LARGE,
            extraInfo = AnimalExtraInfo(
                gallery = listOf(
                    "https://i.pinimg.com/736x/09/69/e5/0969e59bdf26244217b07490086158f3.jpg",
                    "https://s3.amazonaws.com/cdn-origin-etr.akc.org/wp-content/uploads/2020/07/09151754/Golden-Retriever-puppy-standing-outdoors.jpg"
                ),
                videos = listOf("https://youtu.be/Bs7GpD6ErTs?si=PTN4kKL0HeeY6qCT"),
                location = "Centro, Quixadá, Ceará, Brasil"
            )
        ),
        Animal(
            name = "Luna",
            image = "https://images.ctfassets.net/440y9b545yd9/5Q97k7qqGNOOMZ5xTvrgGp/06497c42ef8e94d761ea08e58530e283/Siberian850.jpg",
            gender = AnimalGender.FEMALE,
            type = AnimalType.CAT,
            age = 2,
            species = "Siberiano",
            size = AnimalSize.SMALL,
            extraInfo = AnimalExtraInfo(
                gallery = listOf(
                    "https://www.petplan.co.uk/images/breed-info/siberian/siberian-before-you-buy.jpg",
                    "https://d128mjo55rz53e.cloudfront.net/media/images/blog-breed-siberian-4.max-400x400.format-jpeg.jpg",
                    "https://images.squarespace-cdn.com/content/v1/63fec5652ac520758a255ca2/a4c42091-5672-473d-988b-eeac928333eb/bannerPXL_20230319_204241308.MP+-+Copy.jpg"
                ),
                videos = emptyList(),
                location = "Campo Novo, Quixadá, Ceará, Brasil"
            )
        ),
        Animal(
            name = "Max",
            image = "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg",
            gender = AnimalGender.MALE,
            type = AnimalType.DOG,
            age = 4,
            species = "Pastor Alemão",
            size = AnimalSize.LARGE,
            extraInfo = AnimalExtraInfo(
                gallery = listOf(
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d0/German_Shepherd_-_DSC_0346_%2810096362833%29.jpg/220px-German_Shepherd_-_DSC_0346_%2810096362833%29.jpg",
                    "https://images.squarespace-cdn.com/content/v1/5baffc59fb182025fee90fef/92442d27-b354-4240-b998-bcbe0f1cd91c/German-shepherd-training-main.jpg",
                    "https://kaisergsd.com/wp-content/uploads/2020/04/Germoprog5K-1.jpg",
                    "https://cdn.prod.website-files.com/61424e4d194b43f2e03c009c/66a1f751d7b3e7b4afd48760_20240725_blog_thumbnail1.jpg"
                ),
                videos = listOf("https://youtu.be/yxH9v_5jPhY"),
                location = "Alto São Francisco, Quixadá, Ceará, Brasil"
            )
        ),
        Animal(
            name = "Bella",
            image = "https://s2-vidadebicho.glbimg.com/h8oVRyAoFV37rdtlzBETB6a0MMI=/0x0:960x640/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_fb623579cd474803aedbbbbae014af68/internal_photos/bs/2022/c/W/RB0QfURSeCvJP48mA90g/2022-01-26-vidadebicho-gato-bengal-pixabay-6003100-960-720.jpeg",
            gender = AnimalGender.FEMALE,
            type = AnimalType.CAT,
            age = 5,
            species = "Gato Leopardo",
            size = AnimalSize.MEDIUM,
            extraInfo = AnimalExtraInfo(
                gallery = listOf(
                    "https://drbigodes.pt/wp-content/uploads/Raca-de-Gato-Bengal.jpg",
                    "https://www.patasdacasa.com.br/sites/default/files/noticias/2021/09/gato-de-bengala-quais-os-comportamentos-selvagens-que-a-raca-possui.jpg"
                ),
                videos = emptyList(),
                location = "Residencial Rachel de Queiroz, Quixadá, Ceará, Brasil"
            )
        ),
        Animal(
            name = "Rocky",
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/3/34/Labrador_on_Quantock_%282175262184%29.jpg/640px-Labrador_on_Quantock_%282175262184%29.jpg",
            gender = AnimalGender.MALE,
            type = AnimalType.DOG,
            age = 1,
            species = "Labrador Retriever",
            size = AnimalSize.LARGE,
            extraInfo = AnimalExtraInfo(
                gallery = listOf(
                    "https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=1200,height=675,fit=cover/animal/breed/dog/adult/66fd0ed8598bc632838841.jpg",
                    "https://www.hudsonanimalhospitalnyc.com/wp-content/uploads/2024/11/labrador-retriever-dog-breed-info.jpg"
                ),
                videos = listOf("https://youtu.be/9yOieDCUKO4"),
                location = "Centro Universitário, Quixadá, Ceará, Brasil"
            )
        ),
//        Animal(
//            name = "Thor",
//            image = "https://example.com/images/thor.jpg",
//            gender = AnimalGender.MALE,
//            type = AnimalType.DOG,
//            age = 3,
//            species = "Beagle",
//            size = AnimalSize.MEDIUM,
//            extraInfo = AnimalExtraInfo(
//                gallery = listOf(
//                    "https://example.com/images/thor1.jpg",
//                    "https://example.com/images/thor2.jpg"
//                ),
//                videos = listOf("https://example.com/videos/thor.mp4"),
//                location = "Lagoa, Quixadá, Ceará, Brasil"
//            )
//        ),
//        Animal(
//            name = "Mia",
//            image = "https://example.com/images/mia.jpg",
//            gender = AnimalGender.FEMALE,
//            type = AnimalType.CAT,
//            age = 2,
//            species = "Persian",
//            size = AnimalSize.SMALL,
//            extraInfo = AnimalExtraInfo(
//                gallery = listOf(
//                    "https://example.com/images/mia1.jpg",
//                    "https://example.com/images/mia2.jpg"
//                ),
//                videos = listOf("https://example.com/videos/mia.mp4"),
//                location = "São João, Quixadá, Ceará, Brasil"
//            )
//        ),
//        Animal(
//            name = "Zeca",
//            image = "https://example.com/images/zeca.jpg",
//            gender = AnimalGender.MALE,
//            type = AnimalType.DOG,
//            age = 4,
//            species = "Poodle",
//            size = AnimalSize.MEDIUM,
//            extraInfo = AnimalExtraInfo(
//                gallery = listOf(
//                    "https://example.com/images/zeca1.jpg",
//                    "https://example.com/images/zeca2.jpg"
//                ),
//                videos = listOf("https://example.com/videos/zeca.mp4"),
//                location = "Planalto Universitário, Quixadá, Ceará, Brasil"
//            )
//        ),
//        Animal(
//            name = "Lola",
//            image = "https://example.com/images/lola.jpg",
//            gender = AnimalGender.FEMALE,
//            type = AnimalType.CAT,
//            age = 4,
//            species = "Maine Coon",
//            size = AnimalSize.MEDIUM,
//            extraInfo = AnimalExtraInfo(
//                gallery = listOf(
//                    "https://example.com/images/lola1.jpg",
//                    "https://example.com/images/lola2.jpg"
//                ),
//                videos = listOf("https://example.com/videos/lola.mp4"),
//                location = "Campo Velho, Quixadá, Ceará, Brasil"
//            )
//        )
    )

    return animals
}

fun populateNews(): List<News> {
    val news = listOf(
        News(
            title = "Alagamentos e Chuvas Fortes em Quixadá",
            imageUrl = "https://reporterceara.com.br/wp-content/uploads/2024/03/WhatsApp-Image-2024-03-17-at-16.37.04.jpeg",
            iconUrl = "https://reporterceara.com.br/wp-content/uploads/2024/02/logo-300x204.png",
            type = NewsType.LOCAL,
        ),
        News(
            title = "AMMA Planeja Criar Unidades de Conservação",
            imageUrl = "https://www.sema.ce.gov.br/wp-content/uploads/sites/36/2024/08/WhatsApp-Image-2024-08-28-at-13.37.22.jpeg",
            iconUrl = "https://brasao.org/wp-content/uploads/2018/10/brasao-do-ceara.png",
            type = NewsType.LOCAL,
        ),
        News(
            title = "AMMA Recebe Prêmio de Empatia Animal",
            imageUrl = "https://quixada.ce.gov.br/fotos/745/Capa745.jpg",
            iconUrl = "https://brasao.org/wp-content/uploads/2018/10/brasao-do-ceara.png",
            type = NewsType.LOCAL,
        ),
        News(
            title = "Acidente de ônibus nos Andes peruanos deixa seis mortos e seis desaparecidos",
            imageUrl = "https://s2-g1.glbimg.com/GGDz-2duFNRjZtuMQhY7GAarN1o=/0x0:3500x2333/1000x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2025/A/b/opmVQnSf6FELMnseI2hA/000-36rz7xm.jpg",
            iconUrl = "https://psicologafabiola.com.br/wp-content/uploads/2023/10/Entrevista-para-G1-sobre-Divorcio-1.jpg",
            type = NewsType.GLOBAL,
        ),
        News(
            title = "Suíça inaugura o teleférico mais íngreme do mundo",
            imageUrl = "https://newr7-r7-prod.web.arc-cdn.net/resizer/v2/https%3A%2F%2Fd22yf0mxeu0gma.cloudfront.net%2F01-03-2025%2Ft_234af9a756bc4e6aa98bd8c8cda05efb_name_imagem_R7_padr_o__1_.png?auth=965727644e6ec8b2f62b8843edb8b75db258cbf19fcfb6d13956f757476985e0&smart=true&width=348&height=195",
            iconUrl = "https://img.r7.com/images/record-tv-06112023200331159",
            type = NewsType.GLOBAL,
        ),
        News(
            title = "Edmundo González se reúne com Javier Milei na Argentina",
            imageUrl = "https://www.cnnbrasil.com.br/wp-content/uploads/sites/12/2025/01/IMG-20250104-WA0005-e1736004818686.jpg?w=1220&h=674&crop=1&quality=85",
            iconUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/CNN_International_logo.svg/1200px-CNN_International_logo.svg.png",
            type = NewsType.GLOBAL,
        )
    )
    return news
}

fun populateNotifications(): List<Notification> {
    val notifications = listOf(
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("15/12/2024"),
            title = "Dicas de sustentabilidade",
            message = "Todas as vezes que usamos uma folha de papel indevidamente, matamos praticamente uma árvore. Use papel de forma consciente.",
            readCheck = false,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon1.png?alt=media&token=e20f74a6-5988-4c96-b83b-e4e8810e194a"
        ),
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("12/12/2024"),
            title = "Vacinação de animais",
            message = "A partir do dia 30 de junho, teremos vacinação gratuita para gatos e cachorros. Você pode ir até qualquer posto de vacinação com o seu pet.",
            readCheck = true,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon1.png?alt=media&token=e20f74a6-5988-4c96-b83b-e4e8810e194a"
        ),
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("12/12/2024"),
            title = "Ibama está na cidade",
            message = "O Ibama está na cidade para promover o quinto ciclo de palestras sobre preservação ambiental. Será no auditório Raquel de Queiroz às 10h.",
            readCheck = true,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon2.png?alt=media&token=3118c4ca-1e53-4aa8-a643-f6ee33ee3a6a"
        ),
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
            title = "Castração de gatos gratuita",
            message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
            readCheck = true,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon4.png?alt=media&token=5de623e2-bed7-48c0-a836-c523509c739f"
        ),
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
            title = "Castração de gatos gratuita",
            message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
            readCheck = true,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon4.png?alt=media&token=5de623e2-bed7-48c0-a836-c523509c739f"
        ),
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
            title = "Castração de gatos gratuita",
            message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
            readCheck = true,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon4.png?alt=media&token=5de623e2-bed7-48c0-a836-c523509c739f"
        ),
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
            title = "Castração de gatos gratuita",
            message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
            readCheck = true,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon4.png?alt=media&token=5de623e2-bed7-48c0-a836-c523509c739f"
        ),
        Notification(
            data = SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
            title = "Castração de gatos gratuita",
            message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
            readCheck = true,
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/quixalert.appspot.com/o/notifications_icons%2Fnotification_icon4.png?alt=media&token=5de623e2-bed7-48c0-a836-c523509c739f"
        )
    )

    return notifications
}

fun populateMessagesByAdoptionId(adoptionId: String): List<Message> {
    val messages = listOf(
        Message(
            description = "Olá, gostaria de adotar o Rex!",
            timestamp = System.currentTimeMillis() - 3_600_000,
            userId = "1",
            adoptionId = adoptionId,
            isFromAttendant = false
        ),
        Message(
            description = "Olá! Aqui é a atendente. Vamos iniciar o processo de adoção.",
            timestamp = System.currentTimeMillis() - 3_500_000,
            userId = "attendant",
            adoptionId = adoptionId,
            isFromAttendant = true
        ),
        Message(
            description = "Por favor, envie seus documentos para continuarmos.",
            timestamp = System.currentTimeMillis() - 3_400_000,
            userId = "attendant",
            adoptionId = adoptionId,
            isFromAttendant = true
        ),
        Message(
            description = "Enviei todos os documentos por email.",
            timestamp = System.currentTimeMillis() - 3_300_000,
            userId = "1",
            adoptionId = adoptionId,
            isFromAttendant = false
        ),
        Message(
            description = "Perfeito! Analisarei e retorno em breve.",
            timestamp = System.currentTimeMillis() - 3_200_000,
            userId = "attendant",
            adoptionId = adoptionId,
            isFromAttendant = true
        )
    )

    return messages
}