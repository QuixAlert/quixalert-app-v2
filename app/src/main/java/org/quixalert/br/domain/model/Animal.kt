package org.quixalert.br.domain.model

data class Animal(
    override var id: String = "",
    val name: String = "",
    val image: String = "",
    val gender: AnimalGender = AnimalGender.MALE,
    val type: AnimalType = AnimalType.DOG,
    val age: Int = 0,
    val species: String = "",
    val description: String = "",
    val size: AnimalSize = AnimalSize.MEDIUM,
    val extraInfo: AnimalExtraInfo = AnimalExtraInfo()
) : BaseModel(id)
