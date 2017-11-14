$(document).ready(function() {
    loadCategories();
    $('#hashtags').keypress(function(e) {
        if (e.which == 13) {
            var hashtag = $(this).val();
            $('#hashtag-list').append('<li id="' + hashtag + '"><span id="'
                    + hashtag + '" class="glyphicon glyphicon-remove"></span>&nbsp;'
                    + hashtag + '</li>');
            $(this).val('');
            return false;
        }
    });
    $('#hashtag-list').on('click', 'span', function() {
        $(this).parent().remove();
    });
    $('#preview').click(function() {
        var content = tinymce.get('text-editor').getContent();
        $('#preview-container').html('<h4>Preview:</h4>' + content);
        return false;
    });
    $('#new-post-form').on('reset', function() {
        $('#preview-container').empty();
        $('#hashtag-list').empty();
    });
    $('#new-post-form').on('submit', function() {
        addNewPost();
        return false;
    });
});

function loadCategories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/BTPBlog/categories',
        success: function(data, status) {
            $('#category-load-error').hide();
            $.each(data, function(index, category) {
                $('#category').append('<option value="' + category.categoryId
                        + '">' + category.categoryName + '</option>');
            });
        },
        error: function() {
            $('#category-load-error').show();
            $('#category-select-div').hide();
        }
    });
}

function addNewPost() {
    var hashtags = [];
    $('#hashtag-list').children().each(function() {
        hashtags.push({hashtagName: $(this).attr('id')});
    });
    var dateTimeNow = new Date(Date.now()).toISOString();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/BTPBlog/post',
        data: JSON.stringify({
            title: $('#title').val(),
            content: tinymce.get('text-editor').getContent(),
            hashtags: hashtags,
            categoryId: Number($('#category option:selected').val()),
            isHidden: $('#isHidden').is(':checked'),
            isApproved: false,
            originalTimestamp: dateTimeNow,
            updatedTimestamp: dateTimeNow
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function() {
            $('#success-alert-div').show();
        },
        error: function() {
            alert('Error creating new post. Please try again later.');
        }
    });
}