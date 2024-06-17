package org.vamshi.hctbankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vamshi.hctbankapi.model.CustDetails;

@Repository
public interface CustDetailsRepo extends JpaRepository<CustDetails, Long> {
}
