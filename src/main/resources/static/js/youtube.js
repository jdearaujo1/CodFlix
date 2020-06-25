var player;

function onYouTubeIframeAPIReady() {
    player = new YT.Player('player', {
        events: {
            'onStateChange': onPlayerStateChange
        }
    });
}

function onPlayerStateChange(event) {

    switch (event.data) {
        case -1:
            if (player.getCurrentTime() === 0) {
                createInDatabase();
            }
            break;
        case 0:
            updateInDatabase();
            break;
        case 1:
            console.log("Playing " + player.getCurrentTime())
            break;
        case 2:
            console.log("Paused " + player.getCurrentTime())
            break;
        default:
            console.log(event.data)

    }
}

function createInDatabase() {
    var userId = document.getElementById("userId").value
    var mediaId = document.getElementById("mediaId").value

    var myJSONObject =
        {
            "userId": userId,
            "mediaId": mediaId
        };

    $.ajax({
        url: "/histories/create",
        type: "POST",
        data: myJSONObject,
    });
}

function updateInDatabase() {

    var userId = document.getElementById("userId").value
    var mediaId = document.getElementById("mediaId").value
    var watchDuration = player.getCurrentTime()

    var myJSONObject =
        {
            "userId": userId,
            "mediaId": mediaId,
            "watchDuration": watchDuration
        };

    $.ajax({
        url: "/histories/update",
        //ToDo: POST pour un update ?
        type: "POST",
        data: myJSONObject,
    });
}

window.onbeforeunload = function () {
    updateInDatabase();
};

// ToDo :stocker temps en bdd appel Ajax
//  si temps > temps total vidéo, temps total video + temps total video +1 etc..
//  Si temps < temps total video
//  -> ajouter le tag résume a la fin de la vidéo