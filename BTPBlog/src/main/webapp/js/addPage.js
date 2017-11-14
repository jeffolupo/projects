$(document).ready(function() {
    $('#preview').click(function() {
        var content = tinymce.get('text-editor').getContent();
        $('#preview-container').html('<h4>Preview:</h4><hr/>' + content);
        return false;
    });
    $('#new-page-form').on('reset', function() {
        $('#preview-container').empty();
    });
    $('#new-page-form').on('submit', function() {
        addNewPage();
        return false;
    });
});

function addNewPage() {

    $('#success-alert-div').show();

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/BTPBlog/page',
        data: JSON.stringify({
            title: $('#title').val(),
            content: tinymce.get('text-editor').getContent()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function() {
            $('#success-alert-div').show();
        },
        error: function() {
            alert('Error creating new page. Please try again later.');
        }
    });
}