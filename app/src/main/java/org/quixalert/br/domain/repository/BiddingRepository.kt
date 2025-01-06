package org.quixalert.br.domain.repository

import org.quixalert.br.domain.model.Bidding
import javax.inject.Inject

class BiddingRepository  @Inject constructor() : FirebaseRepository<Bidding, String>(
    collectionName = "bidding",
    entityClass = Bidding::class.java
)