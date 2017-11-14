$(document).ready(function() {
    loadVisiblePosts();
    $('#showMoreBtn').click(function() {
        showPosts();
    });
});

var numberOfPosts = 0;
var visiblePosts = 0;

function loadVisiblePosts() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/BTPBlog/posts/visible',
        success: function(data, status) {
            $.each(data, function(index, post) {
                numberOfPosts++;
                var id = post.postId;
                $('#displayPosts').append('<div class="post" id="' + id + '"></div>');
                $('#displayPosts div#' + id).append('<h3>' + post.title + '</h3>'
                        + '<div class="category" id="' + id + 'category"></div>');
                $.ajax({
                    type: 'GET',
                    url: 'http://localhost:8080/BTPBlog/category/' + post.categoryId,
                    success: function(category, status) {
                        $('#displayPosts div#' + id + 'category').append('<h5>'
                                + category.categoryName + '</h5>');
                    },
                    error: function() {
                        alert('Unable to load category information.');
                    }
                });
                $('#displayPosts div#' + id).append(post.content);
                if (post.hashtags.length > 0) {
                    $('#displayPosts div#' + id).append('<div class="hashtags"</div>');
                    $.each(post.hashtags, function(i, hashtag) {
                        $('#displayPosts div#' + id + ' div.hashtags').append(hashtag.hashtagName + ' ');
                    });
                }
                if (post.updatedTimestamp !== post.originalTimestamp) {
                    $('#displayPosts div#' + id).append('<h6>'
                            + new Date(post.originalTimestamp).toDateString()
                            + '<i> (Edited ' + new Date(post.updatedTimestamp).toDateString()
                            + ')</i></h6>');
                } else {
                    $('#displayPosts div#' + id).append('<h6>'
                            + new Date(post.originalTimestamp).toDateString()
                            + '</h6>');
                }
                $('#displayPosts div#' + id).append('<hr/>');
                $('#loadPostsError').hide();
            });
            $.each($('#displayPosts').children(), function() {
                $(this).addClass('hidden');
            });
            showPosts();
        },
        error: function() {
            $('#loadPostsError').show();
        }
    });
}

function showPosts() {
    var hiddenPosts = Number(numberOfPosts - visiblePosts);
    if (hiddenPosts > 3) {
        var count = 0;
        $.each($('#displayPosts').children(), function() {
            if (count < 3) {
                if ($(this).hasClass('hidden')) {
                    $(this).removeClass('hidden');
                    count++;
                    visiblePosts++;
                }
            }
        });
    } else {
        $.each($('#displayPosts').children(), function() {
            if ($(this).hasClass('hidden')) {
                $(this).removeClass('hidden');
                visiblePosts++;
            }
        });
    }
    if (visiblePosts < numberOfPosts) {
        $('#showMoreBtn').show();
    } else {
        $('#showMoreBtn').hide();
    }
}