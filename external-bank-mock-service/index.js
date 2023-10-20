const express = require('express');
const bodyParser = require('body-parser');
const axios = require('axios');

const app = express();
const port = 8080;

//const apiURL = "http://bank-service:3000";
const apiURL = "http://localhost:3000";

app.use(bodyParser.json());

app.post('/create-bank-transfer', (req, res) => {
    // Récupérez les données IBAN et valeur de la requête
    const iban = req.body.iban;
    const amount = req.body.amount;

    // Créez un objet similaire à BankTransferDTO
    const bankTransfer = {
        iban: iban,
        amount: amount
    };

    // Effectuez une requête POST vers /api/balance/add avec l'objet en tant que corps
    axios.post(apiURL + '/api/balance/add', bankTransfer)
        .then(response => {
            console.log(response.data);
            res.status(200).send('Balance added');
        })
        .catch(error => {
            console.error(error);
            res.status(500).send('Error while adding balance');
        });
});

app.listen(port, () => {
    console.log(`Node.js server listening on port ${port}`);
});
