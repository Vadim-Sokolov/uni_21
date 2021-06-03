
$("#getLectures").on("click", function (event) {
    event.preventDefault();
    $('#lectures tbody > tr').remove();
    $('#lectures').show();
    $('#hideTable').show();
    $.getJSON('http://localhost:8080/rest/lectures', function (data) {
        $(data).each(function (i, lecture) {
            $('#lectures').append($("<tr>")
                .append($("<td>").append(lecture.id))
                .append($("<td>").append(lecture.course.name))
                .append($("<td>").append(lecture.auditorium.name))
                .append($("<td>").append(lecture.teacher.firstName + " " + lecture.teacher.lastName))
                .append($("<td>").append(lecture.group.name))
                .append($("<td>").append(lecture.time)));
        });
    });
});

$("#hideTable").on("click", function (event) {
    event.preventDefault();
    $('#lectures').hide();
    $('#hideTable').hide();
});


$("#addLecture").on("submit", function (event) {

    event.preventDefault();
    $('#notAdded').hide();
    var courseId = $("#courseId").val();
    var auditoriumId = $("#auditoriumId").val();
    var teacherId = $("#teacherId").val();
    var groupId = $("#groupId").val();
    var time = $("#time").val();
    var lecture = { "courseId": courseId, "auditoriumId": auditoriumId, "teacherId": teacherId, "groupId": groupId, "time": time };

    $.ajax({
        url: 'http://localhost:8080/rest/lectures',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(lecture),
        error: function (response) {
            $('#notAdded').show();
            $("#notAdded").html(JSON.parse(response.responseText).message);
            $('html, body').animate({
                scrollTop: $("#notAdded").offset().top - 400
            });
        }
    });
});

$("#updateLecture").on("submit", function (event) {

    event.preventDefault();
    $('#notUpdated').hide();
    var id = $("#idForUpdate").val();
    var courseId = $("#courseIdForUpdate").val();
    var auditoriumId = $("#auditoriumIdForUpdate").val();
    var teacherId = $("#teacherIdForUpdate").val();
    var groupId = $("#groupIdForUpdate").val();
    var time = $("#timeForUpdate").val();
    var lecture = { "courseId": courseId, "auditoriumId": auditoriumId, "teacherId": teacherId, "groupId": groupId, "time": time };
    var url = "http://localhost:8080/rest/lectures/" + id;

    $.ajax({
        url: url,
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(lecture),
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
    $('#lectureById tbody > tr').remove();
    $('#lectureById').hide();
    $('#notFound').hide();
    var id = $("#idForSearch").val();
    var url = "http://localhost:8080/rest/lectures/" + id;

    $.getJSON(url, function (lecture) {
        if (lecture == null) {
            $('#notFound').show();
            $('html, body').animate({
                scrollTop: $("#notFound").offset().top
            });
        } else {
            $('#lectureById').show();
            $('#lectureById').append($("<tr>")
                .append($("<td>").append(lecture.id))
                .append($("<td>").append(lecture.course.name))
                .append($("<td>").append(lecture.auditorium.name))
                .append($("<td>").append(lecture.teacher.firstName + " " + lecture.teacher.lastName))
                .append($("<td>").append(lecture.group.name))
                .append($("<td>").append(lecture.time)));
            $('html, body').animate({
                scrollTop: $("#lectureById").offset().top
            });
        }
    });
});

$("#deleteById").on("submit", function (event) {

    event.preventDefault();
    $('#notDeleted').hide();
    var id = $("#idForDelete").val();
    var url = "http://localhost:8080/rest/lectures/" + id;

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
