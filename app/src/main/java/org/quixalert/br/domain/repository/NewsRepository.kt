package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.News
import javax.inject.Inject

class NewsRepository  @Inject constructor() : FirebaseRepository<News, String>(
    collectionName = "news",
    entityClass = News::class.java
)