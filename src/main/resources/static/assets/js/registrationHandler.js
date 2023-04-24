$(document).ready(function()
{
    $('#deptname').on('change', function()
    {
        alert(this.value);
        console.log("inside Registration Handler");

        var endpoint = '/coursesdept/';
        
        var data = {
            idDept: this.value
        }

        console.log('data = ' + data);
        console.log('data.toString() = ' + data.toString());

        axios.post(endpoint, data)
            .then(function(response) {
                output = response.data;
                console.log('response : ' + output);            
            })
            .catch(function(error) {
                console.log(error);
                swal('Oops!', 'Registration Failed! Please try again later.', 'error');

                btn.val('Save');
                btn.removeClass('disabled')
            });
    });
});