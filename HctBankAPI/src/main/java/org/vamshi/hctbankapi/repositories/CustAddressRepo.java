package org.vamshi.hctbankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vamshi.hctbankapi.model.CustAddress;

@Repository
public interface CustAddressRepo extends JpaRepository<CustAddress, Long> {
}
