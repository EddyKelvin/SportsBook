var stompClient = null;

$(document).ready(function() {
    connect();
});

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/sports-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/newUpdate', function (res) {
            var newUpdateEvent = JSON.parse(res.body)
            showEventUpdate(newUpdateEvent.name + " --> " + newUpdateEvent.score);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    let eventName =  $('#event').val();
    var eventScore = $('#score').val();

    $.ajax({
        url: 'http://localhost:8080/event',
        type: 'put',
        data: {
            'name' : eventName,
            'score': eventScore
        },
        success: function(res) {
            stompClient.send("/broadcast/newUpdate", {}, JSON.stringify(res));
        }
    })

}

function showEventUpdate(update) {
    $("#greetings").append("<tr><td>" + update + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
