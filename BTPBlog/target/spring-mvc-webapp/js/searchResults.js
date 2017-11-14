$(document).ready(function () {

    hashtagDropScreen();
    categoryDropScreen();

});
var contentRows = $("contentRows");

function hashtagDropScreen(event) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/BTPBlog/hashtags',
        success: function (data, status) {
            $.each(data, function (index, hashtag) {

                var hashtagId = hashtag.hashtagId;
                var name = hashtag.hashtagName;
                $('#hashtags')
                        .append('<option value="' + hashtagId + '">' + name + '</option>');
            });
        },
        error: function () {
            $('#errorMessages')
                    .append('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service.  Please try again later.');
        }
    });

}

function onHashtagSearch() {
    var hashtagId = $('#hashtags').val();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/BTPBlog/posts/hashtag/' + hashtagId,
        success: function (data, status) {
            $('#displayPosts').empty();
            console.log(hashtagId);
            $.each(data, function (index, post) {
                var title = post.title;
                var content = post.content;
                var date = new Date(post.updatedTimestamp).toDateString();
                $('#displayPosts')
                        .append('<h3>' + title + '</h3>' +
                                '<h6>' + date + '</h6>' +
                                '<p>' + content + '</p>');
                $.each(post.hashtags, function (i, hashtag) {
                    console.log(hashtag.hashtagName);
                    $('#displayPosts').append('<p>' + hashtag.hashtagName + '</p>');
                });

            });
        },
        error: function () {
            $('#errorMessages')
                    .append('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service.  Please try again later.');
        }
    });
}
$('#hashtags').change(onHashtagSearch);

function categoryDropScreen(event) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/BTPBlog/categories',
        success: function (data, status) {
            $.each(data, function (index, category) {

                var categoryId = category.categoryId;
                var name = category.categoryName;
                $('#categories')
                        .append('<option value=' + categoryId + '>' + name + '</option>');
            });
        },
        error: function () {
            $('#errorMessages')
                    .append('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service.  Please try again later.');
        }
    });
}

function onCategorySearch() {
    var categoryId = $('#categories').val();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/BTPBlog/posts/category/' + categoryId,
        success: function (data, status) {
            $('#displayPosts').empty();
            $.each(data, function (index, post) {
                var title = post.title;
                var content = post.content;
                var date = new Date(post.updatedTimestamp).toDateString();
                var hashtag = post.hashtags;
                $('#displayPosts')
                        .append('<h3>' + title + '</h3>' +
                                '<h6>' + date + '</h6>' +
                                '<p>' + content + '</p>');
                $.each(post.hashtags, function (i, hashtag) {
                    console.log(hashtag.hashtagName);
                    $('#displayPosts').append('<p>' + hashtag.hashtagName + '</p>');
                });
            });
        },
        error: function () {
            $('#errorMessages')
                    .append('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service.  Please try again later.');
        }
    });
}


$('#categories').change(onCategorySearch);

