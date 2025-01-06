package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Bidding
import org.quixalert.br.domain.repository.BiddingRepository
import javax.inject.Inject

class BiddingService @Inject constructor(
    private val biddingRepository: BiddingRepository
): IGenericService<Bidding, String> {

    override fun add(entity: Bidding) {
        biddingRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Bidding) {
        biddingRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        biddingRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Bidding>> {
        return biddingRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<Bidding?> {
        return biddingRepository.getById(entityId)
    }
}