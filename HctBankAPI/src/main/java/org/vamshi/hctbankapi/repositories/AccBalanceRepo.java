package org.vamshi.hctbankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vamshi.hctbankapi.model.AccBalance;

@Repository
public interface AccBalanceRepo extends JpaRepository<AccBalance, Long> {
}
