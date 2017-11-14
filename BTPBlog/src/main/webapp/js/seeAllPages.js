$(document).ready(function () {

    fillPageTable();

});

function fillPageTable() {
    // we need to clear the previous content so we don't append to it
    clearPageTable();

    // grab the the tbody element that will hold the rows of contact information
    var contentRows = $('#contentRow');

    $.ajax({
        type: 'GET',
        url: '/BTPBlog/pages',
        success: function (data, status) {
            $.each(data, function (index, page) {
                var pageTitle = page.title;
                var id = page.pageId;
                var row = '<tr>';
                row += '<td>' + pageTitle + '</td>';
                row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
                row += '<td><a onclick="deletePage(' + id + ')">Delete</a></td>';
                row += '</tr>';
                contentRows.append(row);
            });
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });


}

function clearPageTable() {
    $('#contentRow').empty();
}

function deletePage(pageId) {
    $.ajax({
        type: 'DELETE',
        url: "/BTPBlog/page/" + pageId,
        success: function (status) {
            fillPageTable();
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function showEditForm(pageId) {
    // clear errorMessages
    $('#errorMessages').empty();
    // get the contact details from the server and then fill and show the
    // form on success
    $.ajax({
        type: 'GET',
        url: "/BTPBlog/page/" + pageId,
        success: function (data, status) {
            var content = data.content;
            $('#pageId').val(data.pageId);
            $('#pageTitle').val(data.title);
            tinymce.get('pageContent').setContent(content)

        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
    $('#allPagesTableDiv').hide();
    $('#editFormDiv').show();
}

$('#cancel').click(function () {
    $('#editFormDiv').hide();
    $('#allPagesTableDiv').show();
    $('#previewContainer').empty();
});

$('#preview').click(function () {
    var content = tinymce.get('pageContent').getContent();
    $('#previewContainer').html('<h4>Preview:</h4>' + content);
    return false;
});

$('#update').click(function (event) {
    $.ajax({
        type: 'PUT',
        url: "/BTPBlog/page/" + $('#pageId').val(),
        data: JSON.stringify({
            pageId: $('#pageId').val(),
            title: $('#pageTitle').val(),
            content: tinymce.get('pageContent').getContent(),
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function () {
            $('#pageId').val('');
            $('#pageTitle').val('');
            tinymce.get('pageContent').setContent('');
            $('#editFormDiv').hide();
            fillPageTable();
            $('#allPagesTableDiv').show();
            $('#previewContainer').empty();
            
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
});



