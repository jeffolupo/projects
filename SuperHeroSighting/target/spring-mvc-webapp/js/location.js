$(document).ready(function () {
    loadLocationTable();
});

var locationMap = new Map();


function loadLocationTable() {

    clearError();

    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/locations',
        success: function (data, status) {
            $('#contentRow').empty();
            var contentRows = $('#contentRow');
            $.each(data, function (index, location) {

                locationMap.set(location.locationID, location);

                var id = location.locationID;
                var row = '<tr>';
                row += '<td>' + location.locationName + '</td>';
                row += '<td>' + location.address + '</td>';
                row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
                row += '<td><a onclick="deleteLocation(' + id + ')">Delete</a></td>';
                row += '</tr>';
                contentRows.append(row);
            });
        },
        error: function () {
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
}

function showEditForm(id) {
    $('#locationTableDiv').hide();
    $('#editFormDiv').show();

    var location = locationMap.get(id);
    $('#editLocationId').val(location.locationID);
    $('#editLocationName').val(location.locationName);
    $('#editAddress').val(location.address);
    $('#editLatitude').val(location.latitude);
    $('#editLongitude').val(location.longitude);

}

function deleteLocation(id) {

    clearError();
    $.ajax({
        type: 'DELETE',
        url: '/SuperHeroGame/location/' + id,
        success: function (status) {
            loadLocationTable();
        },
        error: function () {
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });

}

$('#updateButton').click(function () {

    clearError();
    
    var name = $('#editLocationName').val();
    var address = $('#editAddress').val();
    var latitude = $('#editLatitude').val();
    var longitude = $('#editLongitude').val();
    
    
    if (name.length > 0 && address.length > 0 && latitude.length > 0 && longitude.length > 0){
        
        $.ajax({
        type: 'PUT',
        url: '/SuperHeroGame/location/' + $('#editLocationId').val(),
        data: JSON.stringify({
            locationID: $('#editLocationId').val(),
            locationName: $('#editLocationName').val(),
            address: $('#editAddress').val(),
            latitude: $('#editLatitude').val(),
            longitude: $('#editLongitude').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function () {
            $('#editFormDiv').hide();
            loadLocationTable();
            $('#locationTableDiv').show();
        },
        error: function () {
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }

    });
        
        
    }

});

$('#cancelEditButton').click(function () {
    $('#locationTableDiv').show();
    $('#editFormDiv').hide();

    $('#editLocationId').val('');
    $('#editLocationName').val('');
    $('#editAddress').val('');
    $('#editLatitude').val('');
    $('#editLongitude').val('');
});

$('#cancelAddButton').click(function () {
    $('#addLocationName').val('');
    $('#addAddress').val('');
    $('#addLatitude').val('');
    $('#addLongitude').val('');
});

$('#addButton').click(function () {

    clearError();

    var name = $('#addLocationName').val();
    var address = $('#addAddress').val();
    var latitude = $('#addLatitude').val();
    var longitude = $('#addLongitude').val();

    if (name.length > 0 && address.length > 0 && latitude.length > 0 && longitude.length > 0) {
        $.ajax({
            type: 'POST',
            url: '/SuperHeroGame/location',
            data: JSON.stringify({
                locationName: $('#addLocationName').val(),
                address: $('#addAddress').val(),
                latitude: $('#addLatitude').val(),
                longitude: $('#addLongitude').val()
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function () {
                loadLocationTable();
                $('#addLocationName').val('');
                $('#addAddress').val('');
                $('#addLatitude').val('');
                $('#addLongitude').val('');
            },
            error: function () {
                $('#errorMessages').append($('<li>')
                        .attr({class: 'list-group-item list-group-item-danger'})
                        .text('Error calling web service. Please try again later.'));
            }
        });

    }

});

function clearError() {
    $('#errorMessages').empty();
}