package fr.teama.transactionservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CacheTransaction")
public class CacheTransaction {
    @Id
    private Long id;
    private int numberOfTransactions;

    public CacheTransaction(Long id, int numberOfTransactions) {
        this.id = id;
        this.numberOfTransactions = numberOfTransactions;
    }

    public CacheTransaction() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public void incrementNumberOfTransactions() {
        this.numberOfTransactions++;
    }
}
