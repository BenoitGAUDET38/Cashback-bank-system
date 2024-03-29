package fr.teama.cashbackservice.controllers;

import fr.teama.cashbackservice.exceptions.AffiliatedStoreAlreadyExist;
import fr.teama.cashbackservice.helpers.LoggerHelper;
import fr.teama.cashbackservice.interfaces.IAffiliatedStoreCatalog;
import fr.teama.cashbackservice.interfaces.IAffiliatedStoreManager;
import fr.teama.cashbackservice.models.AffiliatedStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@CrossOrigin
@RequestMapping(path = AffiliatedStoreController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class AffiliatedStoreController {
    public static final String BASE_URI = "/api/store";

    @Autowired
    IAffiliatedStoreManager affiliatedStoreManager;

    @Autowired
    IAffiliatedStoreCatalog affiliatedStoreCatalog;

    // rest methode to create a new store, and find new store by id, name, and all stores
    @PostMapping(path = "/create")
    public ResponseEntity<String> createAffiliatedStore(@RequestBody AffiliatedStore affiliatedStore) {
        try {
            LoggerHelper.logInfo("Request received to create new affiliated store : " + affiliatedStore.toString());
            return ResponseEntity.ok(affiliatedStoreManager.createAffiliatedStore(affiliatedStore).toString());
        } catch (AffiliatedStoreAlreadyExist e) {
            return ResponseEntity.status(401).body("Affiliated store with name '" + affiliatedStore.getName() + "' already exists.");
        }

    }

    @PostMapping(path = "/id")
    public ResponseEntity<AffiliatedStore> getAffiliatedStoreById(@RequestBody Long id) {
        LoggerHelper.logInfo("Request received to get affiliated store by id : " + id);
        return ResponseEntity.ok(affiliatedStoreCatalog.getAffiliatedStoreById(id));
    }

    @PostMapping(path = "/name")
    public ResponseEntity<AffiliatedStore> getAffiliatedStoreByName(@RequestBody String name) {
        LoggerHelper.logInfo("Request received to get affiliated store by name : " + name);
        return ResponseEntity.ok(affiliatedStoreCatalog.getAffiliatedStoreByName(name));
    }

    @GetMapping(path = "/siret/{siret}")
    public ResponseEntity<AffiliatedStore> getAffiliatedStoreBySiret(@PathVariable("siret") String siret) {
        LoggerHelper.logInfo("Request received to get affiliated store with siret : " + siret);
        AffiliatedStore affiliatedStore = affiliatedStoreCatalog.getAffiliatedStoreBySiret(siret);
        if (affiliatedStore == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(affiliatedStore);
    }

    @GetMapping
    public ResponseEntity<List<AffiliatedStore>> getAllAffiliatedStores() {
        LoggerHelper.logInfo("Request received to get all affiliated stores");
        return ResponseEntity.ok(affiliatedStoreCatalog.getAllAffiliatedStores());
    }

    @PostMapping(path= "cancelled-cashback-transactions")
    public ResponseEntity<String> manuallyCheckCancelledCashbackTransactions() {
        LoggerHelper.logInfo("Request received to manually check cancelled cashback transactions for affiliated stores");

        affiliatedStoreManager.manuallyCheckCancelledCashbackTransactions();

        return ResponseEntity.ok("Cashback removed of bank accounts with cancelled cashback transactions.");
    }
}
