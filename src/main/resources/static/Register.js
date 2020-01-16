 const password = myUser.getPassword("password")
     , confirm_password = myUser.getRePassword("confirm_password");

 function validatePassword(){
     if(password.value !== confirm_password.value) {
         confirm_password.setCustomValidity("Passwords Don't Match");
     } else {
         confirm_password.setCustomValidity('');
     }
 }

 password.onchange = validatePassword;
 confirm_password.onkeyup = validatePassword;