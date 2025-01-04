package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Donation
import javax.inject.Inject


class DonationRepository  @Inject constructor() : FirebaseRepository<Donation, String>(
    collectionName = "donation",
    entityClass = Donation::class.java
)