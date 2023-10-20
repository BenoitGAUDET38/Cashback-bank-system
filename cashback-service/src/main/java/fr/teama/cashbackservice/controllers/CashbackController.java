package fr.teama.cashbackservice.controllers;

import fr.teama.cashbackservice.repository.CashbackDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@CrossOrigin
@RequestMapping(path = CashbackController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CashbackController {
    public static final String BASE_URI = "/api/cashback";

    @Autowired
    CashbackDataRepository cashbackDataRepository;

    @GetMapping
    public ResponseEntity<String> getCashback() {
        return ResponseEntity.ok("Cashback");
    }
}
