$(document).ready(function() {
    var query = window.location.search;
    if (query.includes('?login_error=1')) {
        $('#login-error-alert').show();
    }
});

