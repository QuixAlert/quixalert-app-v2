package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.AdoptionT
import javax.inject.Inject

class AdoptionRepository  @Inject constructor() : FirebaseRepository<AdoptionT, String>(
    collectionName = "adoption",
    entityClass = AdoptionT::class.java
)