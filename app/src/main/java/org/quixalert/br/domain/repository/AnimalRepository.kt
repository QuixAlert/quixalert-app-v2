package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Animal

class AnimalRepository : FirebaseRepository<Animal, String>(
    collectionName = "animal",
    entityClass = Animal::class.java
)