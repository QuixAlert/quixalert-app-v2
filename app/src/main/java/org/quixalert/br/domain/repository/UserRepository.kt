package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.User
import javax.inject.Inject

class UserRepository  @Inject constructor() : FirebaseRepository<User, String>(
    collectionName = "user",
    entityClass = User::class.java
)