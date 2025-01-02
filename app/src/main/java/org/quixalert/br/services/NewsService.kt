package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Adoption
import org.quixalert.br.domain.model.News
import org.quixalert.br.domain.repository.NewsRepository
import javax.inject.Inject

class NewsService @Inject constructor(
    private val newsRepository: NewsRepository
): IGenericService<News, String> {

    override fun add(entity: News) {
        newsRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: News) {
        newsRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        newsRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<News>> {
        return newsRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<News?> {
        return newsRepository.getById(entityId)
    }
}