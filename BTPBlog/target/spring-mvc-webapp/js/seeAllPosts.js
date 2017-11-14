$(document).ready(function () {
    loadPostTable();
});

function loadPostTable() {
    clearPostTable();
    var contentRows = $('#contentRow');
    $.ajax({
        type: 'GET',
        url: '/BTPBlog/posts',
        success: function (data, status) {
            $.each(data, function (index, post) {
                var approved;
                var hidden;
                
                if (post.isApproved == true){
                    approved = "yes";
                } else {
                    approved = "no";
                }
                
                if (post.isHidden == true){
                    hidden = "yes";
                } else {
                    hidden = "no";
                }
                
                var postTitle = post.title;
                var id = post.postId;
                var catName = post.category.categoryName;
                var row = '<tr>';
                row += '<td>' + postTitle + '</td>';
                row += '<td>' + catName + '</td>';
                row += '<td>' + approved + '</td>';
                row += '<td>' + hidden + '</td>';
                row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
                row += '<td><a onclick="deletePost(' + id + ')">Delete</a></td>';
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

function clearPostTable() {
    $('#contentRow').empty();
}

function deletePost(id) {
    $.ajax({
        type: 'DELETE',
        url: '/BTPBlog/post/' + id,
        success: function (status) {
            loadPostTable();
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function showEditForm(postId) {
    $('#errorMessages').empty();

    $.ajax({
        type: 'GET',
        url: '/BTPBlog/post/' + postId,
        success: function (data, status) {
         
            var updateTimestamp = new Date(Date.now()).toISOString();
            
            $('#postId').val(data.postId);
            $('#postTitle').val(data.title);
            $('#postCategory').val(data.category);
            tinymce.get('postContent').setContent(data.content);
            $('#hashtags').val(data.hashtags);
            $('#originalTime').val(data.originalTimestamp);
            $('#updateTime').val(updateTimestamp);
            $('#isHidden').prop('checked', data.isHidden);
            $('#isApproved').prop('checked', data.isApproved);
            listAllCategoriesToEditForm(data);
            listAllHashtagsToEditForm(data);
            
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function listAllCategoriesToEditForm(ord) {
    
    $('#allPostsTableDiv').hide();
    $('#editFormDiv').show();
    $.ajax({
        type: 'GET',
        url: '/BTPBlog/categories',
        success: function (data, status) {
            $('#postCategory').empty();
            $.each(data, function (index, category) {
                var content = category.categoryName;
                var row = '<option value=' + category.categoryId + '>';
                row += content;
                row += '</option>';
                $('#postCategory').append(row);
            });    
            $('#postCategory').val(ord.categoryId);
            $('#postCategory').selectpicker('refresh');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('3Error calling web service.  Please try again later.'));
        }
    });

}

function listAllHashtagsToEditForm(ord) {
  
    $.ajax({
        type: 'GET',
        url: '/BTPBlog/hashtags',
        dataType: 'json',
        success: function (data, status) {
            $('#hashtags').empty();
            $.each(data, function (index, hashtag) {
                var content = hashtag.hashtagName;
                var row = '<option value=' + hashtag.hashtagId + '>';
                row += content;
                row += '</option>';
                $('#hashtags').append(row);
                
            });
            hashtagIds = selectAllPostsHashtags(ord);

//            $('#hashtags').val(hashtagIds);
//            $('#hashtags').selectpicker('refresh');

        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('33Error calling web service.  Please try again later.'));
        }
    });
}

function selectAllPostsHashtags(ord){
    
    var hashtagIds =[];
    
    $.ajax({
        type: 'GET',
        url: '/BTPBlog/hashtags/post/' + ord.postId,
        dataType: 'json',
        success: function (data, status) {
            $.each(data, function (index, hashtag) {
                hashtagIds.push(hashtag.hashtagId);
            });
            $('#hashtags').val(hashtagIds);
            $('#hashtags').selectpicker('refresh');

        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('33Error calling web service.  Please try again later.'));
        }
    });
}

$('#cancel').click(function () {
    $('#editFormDiv').hide();
    $('#allPostsTableDiv').show();
    $('#previewContainer').empty();
});

$('#preview').click(function () {
    var content = tinymce.get('postContent').getContent();
    $('#previewContainer').html('<h4>Preview:</h4>' + content);
    return false;
});

$('#update').click(function (event) {
    var tagsFromSelect = $('#hashtags').val();
    var tags = [];
    
    for(i = 0; i <tagsFromSelect.length; i++){    
       tags.push({hashtagId: tagsFromSelect[i]});   
    }

    $.ajax({
        type: 'PUT',
        url: "/BTPBlog/post/" + $('#postId').val(),
        data: JSON.stringify({
            postId: $('#postId').val(),
            title: $('#postTitle').val(),
            content: tinymce.get('postContent').getContent(),
            hashtags: tags,
            categoryId: $('#postCategory').val(),
            isHidden: $('#isHidden').is(':checked'),
            isApproved: $('#isApproved').is(':checked'),
            updatedTimestamp: $('#updateTime').val(),
            originalTimestamp: $('#originalTime').val()
            
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        
        success: function () {
            $('#updateTime').val('')
            $('#postId').val('');
            $('#postTitle').val('');
            tinymce.get('postContent').setContent('');
            $('#editFormDiv').hide();
            loadPostTable();
            $('#allPostsTableDiv').show();
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


