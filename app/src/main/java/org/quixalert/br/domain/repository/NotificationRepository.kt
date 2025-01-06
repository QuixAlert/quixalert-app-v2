package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Notification
import javax.inject.Inject

class NotificationRepository  @Inject constructor() : FirebaseRepository<Notification, String>(
    collectionName = "notification",
    entityClass = Notification::class.java
)