function updateEmail() {

    var uniqueId = document.getElementById("uniqueId").value
    var email = document.getElementById("email").value
    var pass = document.getElementById("pass").value

    validateFormEmail(uniqueId, email, pass);

}

function updatePassword() {

    var uniqueId = document.getElementById("uniqueId").value
    var oldPassword = document.getElementById("oldPassword").value
    var newPassword = document.getElementById("newPassword").value
    var confirmPassword = document.getElementById("confirmPassword").value

    validateFormPassword(uniqueId, oldPassword, newPassword, confirmPassword);

}

function validateFormPassword(id, oldPassword, newPassword, confirmPassword) {
    var myJSONObject =
        {
            "id": id,
            "oldPassword": oldPassword,
            "newPassword": newPassword,
            "confirmPassword": confirmPassword
        };

    $.ajax({
        url: "/profile/updatePassword",
        type: "POST",
        data: myJSONObject,
        success: function (response) {
            alert(response)
        },
        error: function (e) {
            alert(e.responseText)
        }
    });
}

function validateFormEmail(id, email, password) {

    var myJSONObject =
        {
            "id": id,
            "email": email,
            "password": password
        };

    $.ajax({
        url: "/profile/updateEmail",
        type: "POST",
        data: myJSONObject,
        success: function (response) {
            alert(response)
        },
        error: function (e) {
            alert(e.responseText)
        }
    });
}