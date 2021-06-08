
$("#getFacultys").on("click", function (event) {
    event.preventDefault();
    $('#facultys tbody > tr').remove();
    $('#facultys').show();
    $('#hideTable').show();
    $.getJSON('http://localhost:8080/rest/faculties', function (data) {
        $(data).each(function (i, faculty) {
            $('#facultys').append($("<tr>")
                .append($("<td>").append(faculty.id))
                .append($("<td>").append(faculty.name)));
        });
    });
});

$("#hideTable").on("click", function (event) {
    event.preventDefault();
    $('#facultys').hide();
    $('#hideTable').hide();
});


$("#addFaculty").on("submit", function (event) {

    event.preventDefault();
    var name = $("#name").val();
    var faculty = { "name": name };

    $.ajax({
        url: 'http://localhost:8080/rest/faculties',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(faculty)
    });
});

$("#updateFaculty").on("submit", function (event) {

    event.preventDefault();
    $('#notUpdated').hide();
    var id = $("#idForUpdate").val();
    var name = $("#nameForUpdate").val();
    var faculty = { "name": name };
    var url = "http://localhost:8080/rest/faculties/" + id;

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(faculty),
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
    $('#facultyById tbody > tr').remove();
    $('#notFound').hide();
    var id = $("#idForSearch").val();
    var url = "http://localhost:8080/rest/faculties/" + id;

    $.getJSON(url, function (facultyDTO) {
        if (facultyDTO == null) {
            $('#notFound').show();
            $('html, body').animate({
                scrollTop: $("#notFound").offset().top
            });
        } else {
            $('#facultyById').show();
            $('#facultyById').append($("<tr>")
                .append($("<td>").append(facultyDTO.id))
                .append($("<td>").append(facultyDTO.name)));
            $('html, body').animate({
                scrollTop: $("#facultyById").offset().top
            });
        }
    });
});

$("#deleteById").on("submit", function (event) {

    event.preventDefault();
    $('#notDeleted').hide();
    var id = $("#idForDelete").val();
    var url = "http://localhost:8080/rest/faculties/" + id;

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
