
$("#getAuditoriums").on("click", function (event) {
    event.preventDefault();
    $('#auditoriums tbody > tr').remove();
    $('#auditoriums').show();
    $('#hideTable').show();
    $.getJSON('http://localhost:8080/rest/auditoriums', function (data) {
        $(data).each(function (i, auditorium) {
            $('#auditoriums').append($("<tr>")
                .append($("<td>").append(auditorium.id))
                .append($("<td>").append(auditorium.name))
                .append($("<td>").append(auditorium.capacity)));
        });
    });
});

$("#hideTable").on("click", function (event) {
    event.preventDefault();
    $('#auditoriums').hide();
    $('#hideTable').hide();
});


$("#addAuditorium").on("submit", function (event) {

    event.preventDefault();
    var name = $("#name").val();
    var capacity = $("#capacity").val();
    var auditorium = { "name": name, "capacity": capacity };

    $.ajax({
        url: 'http://localhost:8080/rest/auditoriums',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(auditorium)
    });
});

$("#updateAuditorium").on("submit", function (event) {

    event.preventDefault();
    $('#notUpdated').hide();
    var id = $("#idForUpdate").val();
    var name = $("#nameForUpdate").val();
    var capacity = $("#capacityForUpdate").val();
    var auditorium = { "name": name, "capacity": capacity };
    var url = "http://localhost:8080/rest/auditoriums/" + id;

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(auditorium),
        error: function (response) {
            $('#notUpdated').show();
            $("#notUpdated").html(JSON.parse(response.responseText).message);
            $('html, body').animate({
                scrollTop: $("#notUpdated").offset().top
            });
        }
    });
});

$("#findById").on("submit", function (event) {

    event.preventDefault();
    $('#auditoriumById tbody > tr').remove();
    $('#auditoriumById').hide();
    $('#notFound').hide();
    var id = $("#idForSearch").val();
    var url = "http://localhost:8080/rest/auditoriums/" + id;

    $.getJSON(url, function (auditorium) {
        if (auditorium == null) {
            $('#notFound').show();
            $('html, body').animate({
                scrollTop: $("#notFound").offset().top
            });
        } else {
            $('#auditoriumById').show();
            $('#auditoriumById').append($("<tr>")
                .append($("<td>").append(auditorium.id))
                .append($("<td>").append(auditorium.name))
                .append($("<td>").append(auditorium.capacity)));
            $('html, body').animate({
                scrollTop: $("#auditoriumById").offset().top
            });
        }
    });
});

$("#deleteById").on("submit", function (event) {

    event.preventDefault();
    $('#notDeleted').hide();
    var id = $("#idForDelete").val();
    var url = "http://localhost:8080/rest/auditoriums/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        error: function () {
            $('#notDeleted').show();
            $('html, body').animate({
                scrollTop: $("#notDeleted").offset().top
            });
        }
    });
});

