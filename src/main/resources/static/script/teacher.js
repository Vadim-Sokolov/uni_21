
$("#getTeachers").on("click", function (event) {
    event.preventDefault();
    $('#teachers tbody > tr').remove();
    $('#teachers').show();
    $('#hideTable').show();
    $.getJSON('http://localhost:8080/rest/teachers', function (data) {
        $(data).each(function (i, teacher) {
            $('#teachers').append($("<tr>")
                .append($("<td>").append(teacher.id))
                .append($("<td>").append(teacher.firstName + " " + teacher.lastName))
                .append($("<td>").append(teacher.faculty.name)));
        });
    });
});

$("#hideTable").on("click", function (event) {
    event.preventDefault();
    $('#teachers').hide();
    $('#hideTable').hide();
});


$("#addTeacher").on("submit", function (event) {

    event.preventDefault();
    $('#notAdded').hide();
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var facultyId = $("#facultyId").val();
    var teacher = { "firstName": firstName, "lastName": lastName, "facultyId": facultyId };

    $.ajax({
        url: 'http://localhost:8080/rest/teachers',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(teacher),
        error: function (response) {
            $('#notAdded').show();
            $("#notAdded").html(JSON.parse(response.responseText).message);
        }
    });
});

$("#updateTeacher").on("submit", function (event) {

    event.preventDefault();
    $('#notUpdated').hide();
    var id = $("#idForUpdate").val();
    var firstName = $("#firstNameForUpdate").val();
    var lastName = $("#lastNameForUpdate").val();
    var facultyId = $("#facultyIdForUpdate").val();
    var teacher = { "firstName": firstName, "lastName": lastName, "facultyId": facultyId };
    var url = "http://localhost:8080/rest/teachers/" + id;

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(teacher),
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
    $('#teacherById tbody > tr').remove();
    $('#teacherById').hide();
    $('#notFound').hide();
    var id = $("#idForSearch").val();
    var url = "http://localhost:8080/rest/teachers/" + id;

    $.getJSON(url, function (teacherDTO) {
        if (teacherDTO == null) {
            $('#notFound').show();
            $('html, body').animate({
                scrollTop: $("#notFound").offset().top
            });
        } else {
            $('#teacherById').show();
            $('#teacherById').append($("<tr>")
                .append($("<td>").append(teacherDTO.id))
                .append($("<td>").append(teacherDTO.firstName + " " + teacherDTO.lastName))
                .append($("<td>").append(teacherDTO.facultyId)));
            $('html, body').animate({
                scrollTop: $("#teacherById").offset().top
            });
        }
    });
});

$("#deleteById").on("submit", function (event) {

    event.preventDefault();
    $('#notDeleted').hide();
    var id = $("#idForDelete").val();
    var url = "http://localhost:8080/rest/teachers/" + id;

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
