package org.quixalert.br.domain.model

data class Animal(
    override var id: String = "",
    val name: String = "",
    val image: String = "",
    val gender: AnimalGender = AnimalGender.MALE,
    val type: AnimalType = AnimalType.DOG,
    val age: Int = 0,
    val species: String = "",
    val size: AnimalSize = AnimalSize.MEDIUM
) : BaseModel(id)
