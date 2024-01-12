package fr.teama.transactionservice.controllers;


import fr.teama.transactionservice.controllers.dto.PaymentDTO;
import fr.teama.transactionservice.exceptions.BankAccountNotFoundException;
import fr.teama.transactionservice.exceptions.BankAccountUnavailableException;
import fr.teama.transactionservice.exceptions.InvalidCardException;
import fr.teama.transactionservice.exceptions.PaymentFailedException;
import fr.teama.transactionservice.helpers.LoggerHelper;
import fr.teama.transactionservice.interfaces.IAccountProxy;
import fr.teama.transactionservice.interfaces.ITransactionManager;
import fr.teama.transactionservice.interfaces.ITransactionSaver;
import fr.teama.transactionservice.models.Card;
import fr.teama.transactionservice.models.transaction.Transaction;
import fr.teama.transactionservice.models.account.BankAccount;
import fr.teama.transactionservice.repository.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(path = TransactionController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class TransactionController {
    public static final String BASE_URI = "/api/transaction";

    @Autowired
    private ITransactionManager transactionManager;
    @Autowired
    private ITransactionSaver transactionSaver;

    @PostMapping("/pay")
    public ResponseEntity<Transaction> pay(@RequestBody PaymentDTO paymentDTO) throws InvalidCardException, PaymentFailedException, BankAccountNotFoundException {
        LoggerHelper.logInfo("Request received to pay " + paymentDTO);
        Card card = new Card(paymentDTO.getCardNumber(), paymentDTO.getExpirationDate(), paymentDTO.getCvv());
        BankAccount bankAccount = transactionManager.getBankAccountByCard(card);
        if (bankAccount.getId() == null) {
            throw new InvalidCardException();
        }
        Transaction transaction = transactionManager.pay(bankAccount.getId(), paymentDTO.getMid(), paymentDTO.getAmount());
        transactionSaver.debitAndSaveTransaction(transaction);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransaction() {
        LoggerHelper.logInfo("Request received to get all transactions");
        for (int i = 0; i < 100; i++) {
            LoggerHelper.logWarn("Ayoub ca marche, donc je ne sais pas ce que tu veux de plus  " + i);
        }
        return ResponseEntity.ok(transactionManager.getTransactions());
    }
}
