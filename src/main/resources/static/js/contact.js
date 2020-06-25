function sendEmail() {
    Email.send({
        SecureToken: '/',
        To: '/',
        From: '/',
        Subject: document.getElementById("subject").value,
        Body: document.getElementById("message").value,
    }).then(
        alert("Nous avons bien reçu votre demande de contact !\n" +
            "Vous recevrez une réponse dans les meilleurs délais.")
    );
}