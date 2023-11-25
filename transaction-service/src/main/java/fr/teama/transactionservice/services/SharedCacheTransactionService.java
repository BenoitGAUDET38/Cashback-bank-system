package fr.teama.transactionservice.services;

import fr.teama.transactionservice.models.CacheTransaction;
import fr.teama.transactionservice.repository.redis.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SharedCacheTransactionService {

    @Autowired
    private CacheRepository cacheRepository;

//    @Autowired
//    private RedisTemplate<Long, Integer> redisTemplate;

//    @Cacheable(value = "transactionCache", cacheManager = "cacheManager")
    public Integer getCachedData(Long bankAccountId) {
        Optional<CacheTransaction> cacheTransaction = cacheRepository.findById(bankAccountId);
        return cacheTransaction.map(CacheTransaction::getNumberOfTransactions).orElse(0);
//        Integer redisValue = redisTemplate.opsForValue().get(bankAccountId);
//        if (redisValue == null) {
//            return 0;
//        }
//        return redisValue + 1;
    }

//    @CachePut("transactionCache")
    public Integer addTransactionCachedData(Long bankAccountId) {
        Optional<CacheTransaction> cacheTransaction = cacheRepository.findById(bankAccountId);
        if (cacheTransaction.isEmpty()) {
            cacheRepository.save(new CacheTransaction(bankAccountId, 1));
            return 1;
        }
        cacheTransaction.get().incrementNumberOfTransactions();
        cacheRepository.save(cacheTransaction.get());
        return cacheTransaction.get().getNumberOfTransactions();
//        Integer redisValue = getCachedData(bankAccountId);
//        redisTemplate.opsForValue().set(bankAccountId, redisValue + 1);
    }
}
