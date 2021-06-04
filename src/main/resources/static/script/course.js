
$("#getCourses").on("click", function (event) {
    event.preventDefault();
    $('#courses tbody > tr').remove();
    $('#courses').show();
    $('#hideTable').show();
    $.getJSON('http://localhost:8080/rest/courses', function (data) {
        $(data).each(function (i, course) {
            $('#courses').append($("<tr>")
                .append($("<td>").append(course.id))
                .append($("<td>").append(course.name))
                .append($("<td>").append(course.numberOfWeeks))
                .append($("<td>").append(course.description)));
        });
    });
});

$("#hideTable").on("click", function (event) {
    event.preventDefault();
    $('#courses').hide();
    $('#hideTable').hide();
});


$("#addCourse").on("submit", function (event) {

    event.preventDefault();
    var name = $("#name").val();
    var numberOfWeeks = $("#numberOfWeeks").val();
    var description = $("#description").val();
    var course = { "name": name, "numberOfWeeks": numberOfWeeks, "description": description };

    $.ajax({
        url: 'http://localhost:8080/rest/courses',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(course)
    });
});

$("#updateCourse").on("submit", function (event) {

    event.preventDefault();
    $('#notUpdated').hide();
    var id = $("#idForUpdate").val();
    var name = $("#nameForUpdate").val();
    var numberOfWeeks = $("#weeksForUpdate").val();
    var description = $("#descriptionForUpdate").val();
    var course = { "name": name, "numberOfWeeks": numberOfWeeks, "description": description };
    var url = "http://localhost:8080/rest/courses/" + id;

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(course),
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
    $('#courseById tbody > tr').remove();
    $('#courseById').hide();
    $('#notFound').hide();
    var id = $("#idForSearch").val();
    var url = "http://localhost:8080/rest/courses/" + id;

    $.getJSON(url, function (courseDTO) {
        if (courseDTO == null) {
            $('#notFound').show();
            $('html, body').animate({
                scrollTop: $("#notFound").offset().top
            });
        } else {
            $('#courseById').show();
            $('#courseById').append($("<tr>")
                .append($("<td>").append(courseDTO.id))
                .append($("<td>").append(courseDTO.name))
                .append($("<td>").append(courseDTO.numberOfWeeks))
                .append($("<td>").append(courseDTO.description)));
            $('html, body').animate({
                scrollTop: $("#courseById").offset().top
            });
        }
    });
});

$("#deleteById").on("submit", function (event) {

    event.preventDefault();
    $('#notDeleted').hide();
    var id = $("#idForDelete").val();
    var url = "http://localhost:8080/rest/courses/" + id;

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

