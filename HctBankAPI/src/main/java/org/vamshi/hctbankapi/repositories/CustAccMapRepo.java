package org.vamshi.hctbankapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vamshi.hctbankapi.model.CustAccMap;

@Repository
public interface CustAccMapRepo extends JpaRepository<CustAccMap, Long> {
    @Query(value = "select acc_id from cust_acc_map where cust_id =:custID", nativeQuery = true)
    Long findByCustID(Long custID);

    @Query(value = "select cust_id from cust_acc_map where acc_id=:id", nativeQuery = true)
    Long findByAccID(Long id);
}
