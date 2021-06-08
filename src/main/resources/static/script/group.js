
$("#getGroups").on("click", function (event) {
    event.preventDefault();
    $('#groups tbody > tr').remove();
    $('#groups').show();
    $('#hideTable').show();
    $.getJSON('http://localhost:8080/rest/groups', function (data) {
        $(data).each(function (i, group) {
            $('#groups').append($("<tr>")
                .append($("<td>").append(group.id))
                .append($("<td>").append(group.name))
                .append($("<td>").append(group.faculty.name)));
        });
    });
});

$("#hideTable").on("click", function (event) {
    event.preventDefault();
    $('#groups').hide();
    $('#hideTable').hide();
});


$("#addGroup").on("submit", function (event) {

    event.preventDefault();
    $('#notAdded').hide();
    var name = $("#name").val();
    var facultyId = $("#facultyId").val();
    var group = { "name": name, "facultyId": facultyId };

    $.ajax({
        url: 'http://localhost:8080/rest/groups',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(group),
        error: function (response) {
            $('#notAdded').show();
            $("#notAdded").html(JSON.parse(response.responseText).message);
            $('html, body').animate({
                scrollTop: $("#notAdded").offset().top
            });
        }
    });
});

$("#updateGroup").on("submit", function (event) {

    event.preventDefault();
    $('#notUpdated').hide();
    var id = $("#idForUpdate").val();
    var name = $("#nameForUpdate").val();
    var facultyId = $("#facultyIdForUpdate").val();
    var group = { "name": name, "facultyId": facultyId };
    var url = "http://localhost:8080/rest/groups/" + id;

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(group),
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
    $('#groupById tbody > tr').remove();
    $('#groupById').hide();
    $('#notFound').hide();
    var id = $("#idForSearch").val();
    var url = "http://localhost:8080/rest/groups/" + id;

    $.getJSON(url, function (groupDTO) {
        if (groupDTO == null) {
            $('#notFound').show();
            $('html, body').animate({
                scrollTop: $("#notFound").offset().top
            });
        } else {
            $('#groupById').show();
            $('#groupById').append($("<tr>")
                .append($("<td>").append(groupDTO.id))
                .append($("<td>").append(groupDTO.name))
                .append($("<td>").append(groupDTO.faculty.name)));
            $('html, body').animate({
                scrollTop: $("#groupById").offset().top
            });
        }
    });
});

$("#deleteById").on("submit", function (event) {

    event.preventDefault();
    $('#notDeleted').hide();
    var id = $("#idForDelete").val();
    var url = "http://localhost:8080/rest/groups/" + id;

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

