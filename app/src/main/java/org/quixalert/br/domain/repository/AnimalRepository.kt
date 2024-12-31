package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Animal
import javax.inject.Inject

class AnimalRepository  @Inject constructor() : FirebaseRepository<Animal, String>(
    collectionName = "animal",
    entityClass = Animal::class.java
)