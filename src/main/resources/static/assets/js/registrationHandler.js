$(document).ready(function()
{
    $('#deptname').on('change', function()
    {
        var endpoint = '/coursesDept/' + this.value + '/';

        axios.get(endpoint)
            .then(function(response)
            {
                output = response.data;

                $('#courseId').empty();
                $('#courseId').append('<option value selected disabled> Select Course </option>')

                $.each(output, function (i, course)
                {
                  $('#courseId').append($('<option></option>').val(course.id).html(course.name));
                });
            });
    });
});