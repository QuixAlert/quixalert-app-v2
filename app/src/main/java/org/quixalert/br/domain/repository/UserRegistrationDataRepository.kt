package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.UserRegistrationData
import javax.inject.Inject

class UserRegistrationDataRepository  @Inject constructor() : FirebaseRepository<UserRegistrationData, String>(
    collectionName = "user_registration",
    entityClass = UserRegistrationData::class.java
)