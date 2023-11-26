package fr.teama.cashbackservice.controllers.dto;

import fr.teama.cashbackservice.models.Cashback;

public class CashbackDTO {

    private double amountSpent;

    private double amountReturned;

    private String siret;

    private Long transactionId;

    public CashbackDTO(double amountSpent, double amountReturned, String siret, Long transactionId) {
        this.amountSpent = amountSpent;
        this.amountReturned = amountReturned;
        this.siret = siret;
        this.transactionId = transactionId;
    }

    public CashbackDTO() {
    }

    public CashbackDTO(Cashback cashback) {
        this.amountSpent = cashback.getAmountSpent();
        this.amountReturned = cashback.getAmountReturned();
        this.siret = cashback.getSiret();
        this.transactionId = cashback.getTransactionId();
    }

    public double getAmountReturned() {
        return amountReturned;
    }

    public void setAmountReturned(double amount) {
        this.amountReturned = amount;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "CashbackDTO{" +
                "amountSpent=" + amountSpent +
                ", amountReturned=" + amountReturned +
                ", siret=" + siret +
                ", transactionId=" + transactionId +
                '}';
    }
}