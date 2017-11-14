$(document).ready(function () {

    fillOrganizationTable();
    listAllLocationsToAddNewOrg();
});

function fillOrganizationTable() {

    clearError();
    // we need to clear the previous content so we don't append to it
    clearPageTable();

    // grab the the tbody element that will hold the rows of contact information
    var contentRows = $('#contentRow');

    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/organizations',
        success: function (data, status) {
            $.each(data, function (index, organization) {
                var organizationName = organization.organizationName;
                var id = organization.organizationID;
                var row = '<tr>';
                row += '<td>' + organizationName + '</td>';
                row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
                row += '<td><a onclick="deleteOrganization(' + id + ')">Delete</a></td>';
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

function deleteOrganization(organizationId) {

    clearError();
    $.ajax({
        type: 'DELETE',
        url: "/SuperHeroGame/organization/" + organizationId,
        success: function (status) {
            fillOrganizationTable();
        }
    });
}

function showEditForm(organizationId) {

    clearError();
    // clear errorMessages
    $('#errorMessages').empty();
    // get the contact details from the server and then fill and show the
    // form on success
    $.ajax({
        type: 'GET',
        url: "/SuperHeroGame/organization/" + organizationId,
        success: function (data, status) {
            var content = data.content;
            $('#editOrganizationId').val(data.organizationID);
            $('#editOrganizationName').val(data.organizationName);
            $('#editOrganizationDescription').val(data.description);
            $('#organizationLocations').val(data.location);
            $('#organizationTableDiv').hide();
            listAllLocationsToEditForm(data);
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

$('#cancelEditButton').click(function () {
    $('#editFormDiv').hide();
    $('#organizationTableDiv').show();
});

function hideAndRefreshOrgTable() {
    $('#editFormDiv').hide();
    fillOrganizationTable();
    $('#organizationTableDiv').show();

}



function listAllLocationsToEditForm(ord) {

    clearError();

    $.ajax({
        type: 'GET',
        url: "/SuperHeroGame/locations",
        success: function (data, status) {

            $('#organizationLocations').empty();
            $.each(data, function (index, location) {
                var content = location.locationName;

                var row = '<option value=' + location.locationID + '>';
                row += content;
                row += '</option>';
                $('#organizationLocations').append(row);
            });
            $('#organizationLocations').val(ord.location.locationID);

        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });

}

function listAllLocationsToAddNewOrg() {

    clearError();

    $.ajax({
        type: 'GET',
        url: "/SuperHeroGame/locations",
        success: function (data, status) {

            $('#newOrganizationLocations').empty();
            $.each(data, function (index, location) {
                var content = location.locationName;

                var row = '<option value=' + location.locationID + '>';
                row += content;
                row += '</option>';
                $('#newOrganizationLocations').append(row);
            });
            $('#newOrganizationLocations').val('');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });

}


$('#updateButton').click(function (event) {

    clearError();

    var loc = $('#organizationLocations').val();
    var name = $('#editOrganizationName').val();
    var des = $('#editOrganizationDescription').val();
    var locationObj;

    if (loc.length > 0 && name.length > 0 && des.length > 0) {

        $.ajax({
            type: 'GET',
            url: '/SuperHeroGame/location/' + $('#organizationLocations').val(),
            success: function (data, status) {
                locationObj = data;
                $.ajax({
                    type: 'PUT',
                    url: '/SuperHeroGame/organization/' + $('#editOrganizationId').val(),
                    data: JSON.stringify({
                        organizationID: $('#editOrganizationId').val(),
                        organizationName: $('#editOrganizationName').val(),
                        location: locationObj,
                        description: $('#editOrganizationDescription').val()
                    }),
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    'dataType': 'json',
                    success: function () {
                        hideAndRefreshOrgTable();
                    },
                    error: function () {
                        $('#errorMessages')
                                .append($('<li>')
                                        .attr({class: 'list-group-item list-group-item-danger'})
                                        .text('Error calling web service.  Please try again later.'));
                    }

                });

            },
            error: function () {
                $('#errorMessages')
                        .append($('<li>')
                                .attr({class: 'list-group-item list-group-item-danger'})
                                .text('Error calling web service for location object.  Please try again later.'));
            }
        });

    }

});

$('#addOrganizationButton').click(function () {

    clearError();

    var loc = $('#newOrganizationLocations').val();
    var name = $('#addOrganizationName').val();
    var des = $('#addOrganizationDescription').val();

    var locationObj;

    if (loc != null && name !=null && des != null) {

        $.ajax({
            type: 'GET',
            url: '/SuperHeroGame/location/' + loc,
            success: function (data, status) {
                locationObj = data;
                $.ajax({
                    type: 'POST',
                    url: '/SuperHeroGame/organization',
                    data: JSON.stringify({
                        organizationName: name,
                        location: locationObj,
                        description: des
                    }),
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
//                    'dataType': 'json',
                    success: function (data2, status) {
                        // clear errorMessages
                        $('#errorMessages').empty();
                        // Clear the form and reload the table
                        $('#addOrganizationName').val('');
                        $('#newOrganizationLocations').val('');
                        $('#addOrganizationDescription').val('');
                        fillOrganizationTable();

                    },
                    error: function (e) {
                        $('#errorMessages')
                                .append($('<li>')
                                        .attr({class: 'list-group-item list-group-item-danger'})
                                        .text('Error calling web service.  Please try again later.'));
                    }
                });

            },

            error: function () {
                $('#errorMessages')
                        .append($('<li>')
                                .attr({class: 'list-group-item list-group-item-danger'})
                                .text('Error calling web service for location object.  Please try again later.'));
            }
        });
    }

});

$('#cancelAddButton').click(function () {
    $('#errorMessages').empty();
    // Clear the form and reload the table
    $('#addOrganizationName').val('');
    $('#newOrganizationLocations').val('');
    $('#addOrganizationDescription').val('');

});

function clearError() {
    $('#errorMessages').empty();
}