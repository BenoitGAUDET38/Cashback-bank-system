package fr.teama.transactionservice.controllers;


import fr.teama.transactionservice.controllers.dto.PaymentDTO;
import fr.teama.transactionservice.exceptions.BankAccountUnavailableException;
import fr.teama.transactionservice.exceptions.InvalidCardException;
import fr.teama.transactionservice.exceptions.PaymentFailedException;
import fr.teama.transactionservice.helpers.LoggerHelper;
import fr.teama.transactionservice.interfaces.IAccountProxy;
import fr.teama.transactionservice.interfaces.ITransactionManager;
import fr.teama.transactionservice.interfaces.ITransactionSaver;
import fr.teama.transactionservice.models.Card;
import fr.teama.transactionservice.models.Transaction;
import fr.teama.transactionservice.services.SharedCacheTransactionService;
import fr.teama.transactionservice.services.SharedCacheTransactionService;
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
    @Autowired
    private IAccountProxy bankAccountProxy;
    @Autowired
    private SharedCacheTransactionService sharedCacheTransactionService;

    @PostMapping("/pay")
    public ResponseEntity<Transaction> pay(@RequestBody PaymentDTO paymentDTO) throws InvalidCardException, PaymentFailedException, BankAccountUnavailableException {
        LoggerHelper.logInfo("Request received to pay " + paymentDTO);
        Card card = new Card(paymentDTO.getCardNumber(), paymentDTO.getExpirationDate(), paymentDTO.getCvv());
        Long bankAccountId = bankAccountProxy.getBankAccountIdByCard(card);
        if (bankAccountId == null) {
            throw new InvalidCardException();
        }
        Transaction transaction = transactionManager.pay(bankAccountId, paymentDTO.getMid(), paymentDTO.getAmount());
        transactionSaver.debitAndSaveTransaction(transaction);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransaction() {
        LoggerHelper.logInfo("Request received to get all transactions");
        return ResponseEntity.ok(transactionManager.getTransactions());
    }

    @GetMapping("/bankAccount/{bankAccountId}")
    public ResponseEntity<Integer> getNbOfTrans(@PathVariable Long bankAccountId) {
        LoggerHelper.logInfo("Request received to get all transactions by bank account id");
        return ResponseEntity.ok(sharedCacheTransactionService.getCachedData(bankAccountId));
    }

    @PostMapping("/bankAccount/{bankAccountId}")
    public ResponseEntity<Integer> newTrans(@PathVariable Long bankAccountId) {
        LoggerHelper.logInfo("Request received to add a transaction by bank account id");
        return ResponseEntity.ok(sharedCacheTransactionService.addTransactionCachedData(bankAccountId));
    }
}
