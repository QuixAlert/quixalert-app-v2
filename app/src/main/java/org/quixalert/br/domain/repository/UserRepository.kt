package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.User
import javax.inject.Inject

class UserRepository  @Inject constructor() : FirebaseRepository<User, String>(
    collectionName = "users",
    entityClass = User::class.java
)