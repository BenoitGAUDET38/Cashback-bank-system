package fr.teama.transactionservice.repository.redis;

import fr.teama.transactionservice.models.CacheTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends JpaRepository<CacheTransaction, Long> {
}
