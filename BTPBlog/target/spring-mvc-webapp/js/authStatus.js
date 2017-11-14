$(document).ready(function() {
    displayLogoutButtonIfLoggedIn();
});

function displayLogoutButtonIfLoggedIn() {
    $.get('http://localhost:8080/BTPBlog/login/username')
            .done(function(response) {
                console.log(response);
                if (response) {
                    $('#logout').show();
                }
            });
}