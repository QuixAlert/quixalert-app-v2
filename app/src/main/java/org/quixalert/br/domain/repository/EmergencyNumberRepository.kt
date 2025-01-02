package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.EmergencyNumber
import javax.inject.Inject

class EmergencyNumberRepository  @Inject constructor() : FirebaseRepository<EmergencyNumber, String>(
    collectionName = "emergency_number",
    entityClass = EmergencyNumber::class.java
)