//check invalid field
(function () {
    'use strict';
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation');
    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }

                    form.classList.add('was-validated');
                }, false);
            });
})();

const forget = document.getElementById('forget-form');
const register = document.getElementById('register');
const login = document.getElementById('login');
const btn = document.getElementById('btn');
const btn1 = document.getElementById('btn1');
const logo = document.getElementById('logo');
btn.addEventListener('click', function handleClick() {
    if (register.style.display === 'none') {
        register.style.display = 'block';
        login.style.display = 'none';
        logo.style.display = 'none';
    } else {
        register.style.display = 'none';
    }
});
btn1.addEventListener('click', function handleClick() {
    if (login.style.display === 'none') {
        login.style.display = 'block';
        register.style.display = 'none';
        logo.style.display = 'block';
    } else {
        register.style.display = 'none';
    }
});


//chck validate passowrd
function validate_password() {

    var pass = document.getElementById('register_password').value;
    var confirm_pass = document.getElementById('register_confirm_password').value;
    if (pass != confirm_pass) {
        $('#alert-pass').html('Not Matching!').css('color', '#dc3545');
        document.getElementById('create-account1').disabled = true;
        document.getElementById('create-account1').style.opacity = (0.4);
    } else {
        $('#alert-pass').html('Matching!').css('color', 'green');
        document.getElementById('create-account1').disabled = false;
        document.getElementById('create-account1').style.opacity = (1);
    }
}
function validate_password1() {

    var pass = document.getElementById('password_forget').value;
    var confirm_pass = document.getElementById('confirm_password_forget').value;
    if(pass == ""){
        $('#alert-pass2').html('Cannot be blank!').css('color', '#dc3545');
        document.getElementById('check-user').disabled = true;
        document.getElementById('check-user').style.opacity = (0.4);
    }
    if(confirm_pass == ""){
        $('#alert-pass1').html('Cannot be blank!').css('color', '#dc3545');
        document.getElementById('check-user').disabled = true;
        document.getElementById('check-user').style.opacity = (0.4);
    }
    if (pass != confirm_pass) {
        $('#alert-pass1').html('Not Matching!').css('color', '#dc3545');
        document.getElementById('check-user').disabled = true;
        document.getElementById('check-user').style.opacity = (0.4);
    } else {
        $('#alert-pass1').html('Matching!').css('color', 'green');
        document.getElementById('check-user').disabled = false;
        document.getElementById('check-user').style.opacity = (1);
    }
}
