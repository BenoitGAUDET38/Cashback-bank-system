package fr.teama.bankservice.interfaces;

import fr.teama.bankservice.controllers.dto.TransactionDTO;
import fr.teama.bankservice.exceptions.NotEnoughMoneyException;
import fr.teama.bankservice.exceptions.PaymentFailedException;
import fr.teama.bankservice.models.Card;
import fr.teama.bankservice.models.Transaction;

import java.util.List;

public interface ITransaction {
    Transaction pay(Card card, String MID, double amount) throws PaymentFailedException;
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getTransactions();

    List<TransactionDTO> getCashbackTransactionsByStore(String siret);

    List<TransactionDTO> getCashbackTransactions();

    void cancelCashback(Long transactionId);
}
