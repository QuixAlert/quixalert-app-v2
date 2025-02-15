package org.quixalert.br.services

import kotlinx.coroutines.Deferred
import org.quixalert.br.domain.model.Donation
import org.quixalert.br.domain.repository.DonationRepository
import javax.inject.Inject

class DonationService @Inject constructor(
    private val donationRepository: DonationRepository
): IGenericService<Donation, String> {

    override fun add(entity: Donation) {
        donationRepository.add(entity)
    }

    override fun updateById(entityId: String, entity: Donation) {
        donationRepository.updateById(entityId, entity)
    }

    override fun deleteById(entityId: String) {
        donationRepository.deleteById(entityId)
    }

    override fun getAll(): Deferred<List<Donation>> {
        return donationRepository.getAll()
    }

    override fun getById(entityId: String): Deferred<Donation?> {
        return donationRepository.getById(entityId)
    }
}