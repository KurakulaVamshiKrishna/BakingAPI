package org.vamshi.hctbankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vamshi.hctbankapi.model.AccTransactions;

import java.util.List;

@Repository
public interface AccTransactionsRepo extends JpaRepository<AccTransactions, Long> {
    @Query(value = "select * from acc_transactions where transaction_refid=:refID"  ,nativeQuery = true)
    List<AccTransactions> getTrasactios(Long refID);

    @Query(value = "select * from acc_transactions where accountid=:id" ,nativeQuery = true)
    List<AccTransactions> getAccTransactionsByAccID(Long id);
}
