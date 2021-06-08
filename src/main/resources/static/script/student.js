
$("#getStudents").on("click", function (event) {
    event.preventDefault();
    $('#students tbody > tr').remove();
    $('#students').show();
    $('#hideTable').show();
    $.getJSON('http://localhost:8080/rest/students', function (data) {
        $(data).each(function (i, student) {
            $('#students').append($("<tr>")
                .append($("<td>").append(student.id))
                .append($("<td>").append(student.firstName + " " + student.lastName))
                .append($("<td>").append(student.studentCardNumber))
                .append($("<td>").append(student.group.name)));
        });
    });
});

$("#hideTable").on("click", function (event) {
    event.preventDefault();
    $('#students').hide();
    $('#hideTable').hide();
});


$("#addStudent").on("submit", function (event) {

    event.preventDefault();
    $('#notAdded').hide();
    var firstName = $("#firstName").val();
    var lastName = $("#lastName").val();
    var cardNumber = $("#cardNumber").val();
    var groupId = $("#groupId").val();
    var student = { "firstName": firstName, "lastName": lastName, "studentCardNumber": cardNumber, "groupId": groupId };

    $.ajax({
        url: 'http://localhost:8080/rest/students',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(student),
        error: function (response) {
            $('#notAdded').show();
            $("#notAdded").html(JSON.parse(response.responseText).message);
        }
    });
});

$("#updateStudent").on("submit", function (event) {

    event.preventDefault();
    $('#notUpdated').hide();
    var id = $("#idForUpdate").val();
    var firstName = $("#firstNameForUpdate").val();
    var lastName = $("#lastNameForUpdate").val();
    var cardNumber = $("#cardNumberForUpdate").val();
    var groupId = $("#groupIdForUpdate").val();
    var student = { "firstName": firstName, "lastName": lastName, "studentCardNumber": cardNumber, "groupId": groupId };
    var url = "http://localhost:8080/rest/students/" + id;

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(student),
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
    $('#studentById tbody > tr').remove();
    $('#studentById').hide();
    $('#notFound').hide();
    var id = $("#idForSearch").val();
    var url = "http://localhost:8080/rest/students/" + id;

    $.getJSON(url, function (studentDTO) {
        if (studentDTO == null) {
            $('#notFound').show();
            $('html, body').animate({
                scrollTop: $("#notFound").offset().top
            });
        } else {
            $('#studentById').show();
            $('#studentById').append($("<tr>")
                .append($("<td>").append(studentDTO.id))
                .append($("<td>").append(studentDTO.firstName + " " + studentDTO.lastName))
                .append($("<td>").append(studentDTO.studentCardNumber))
                .append($("<td>").append(studentDTO.groupId)));
            $('html, body').animate({
                scrollTop: $("#studentById").offset().top
            });
        }
    });
});

$("#deleteById").on("submit", function (event) {

    event.preventDefault();
    $('#notDeleted').hide();
    var id = $("#idForDelete").val();
    var url = "http://localhost:8080/rest/students/" + id;

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

