$(document).ready(function() {
    loadAllPages();
    $('#navbar').on('click', 'li.static-page', function(e) {
        var pageId = $(this).attr('id');
        e.preventDefault();
        window.location.href = 'page.html?id=' + pageId;
    });
    var query = window.location.search;
    if (query.includes('?id=')) {
        var pageId = query.substr(4);
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/BTPBlog/page/public/' + pageId,
            success: function(page, status) {
                console.log(page);
                $('#content').append('<h1>' + page.title + '</h1>' + page.content);
            },
            error: function() {
                alert('ERROR: Unable to load page.');
            }
        });
    }
});

function loadAllPages() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/BTPBlog/pages',
        success: function(data, status) {
            $.each(data, function(index, page) {
                $('#navbar').append('<li class="static-page" id="' + page.pageId
                        + '"><a class="navword" href="page.html">' + page.title + '</a></li>')
            });
        },
        error: function() {
            alert('ERROR: Unable to load pages.');
        }
    });
}