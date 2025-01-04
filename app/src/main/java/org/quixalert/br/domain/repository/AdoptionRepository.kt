package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Adoption
import javax.inject.Inject

class AdoptionRepository  @Inject constructor() : FirebaseRepository<Adoption, String>(
    collectionName = "adoption",
    entityClass = Adoption::class.java
)