function sendEmail() {
    Email.send({
        SecureToken: '/',
        To: '/',
        From: '/',
        Subject: document.getElementById("subject").value,
        Body: document.getElementById("message").value,
    }).then(
        alert("Email Sent")
    );
}