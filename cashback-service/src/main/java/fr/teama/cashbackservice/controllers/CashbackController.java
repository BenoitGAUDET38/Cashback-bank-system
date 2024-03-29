package fr.teama.cashbackservice.controllers;

import fr.teama.cashbackservice.controllers.dto.PaymentDTO;
import fr.teama.cashbackservice.controllers.dto.TransactionRequestDTO;
import fr.teama.cashbackservice.interfaces.ICashbackManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@CrossOrigin
@RequestMapping(path = CashbackController.BASE_URI, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class CashbackController {

    public static final String BASE_URI = "/api/cashback";

    private final ICashbackManager cashbackManager;

    @Autowired
    CashbackController(ICashbackManager cashbackManager) {
        this.cashbackManager = cashbackManager;
    }

    @PostMapping(path = "/check-transaction")
    public ResponseEntity<Double> checkTransaction(@RequestBody TransactionRequestDTO requestDTO) {
        PaymentDTO payment = requestDTO.getPayment();
        Long bankAccountId = requestDTO.getBankAccountId();

        // Utilisez payment et bankAccountId comme nécessaire dans votre logique de cashback
        Double cashback = cashbackManager.addPotentialCashback(payment, bankAccountId);

        return ResponseEntity.ok(cashback);
    }
}
