$(document).ready(function () {
    loadAllSuperHeroes();
    listAllAttitudesToAddFrom();
    listAllPowersToAddForm();
    listAllOrganizationsToAddForm();
});
function loadAllSuperHeroes() {

    clearHeroTable();
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/superheroes',
        success: function (data, status) {
            var contentRows = $('#contentRow');

            $.each(data, function (index, hero) {

                var id = hero.heroID;
                var row = '<tr>';
                row += '<td>' + hero.heroName + '</td>';
                row += '<td>' + hero.superPower.nameOfPower + '</td>';
                row += '<td>' + hero.attitude.description + '</td>';
                row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
                row += '<td><a onclick="deleteHero(' + id + ')">Delete</a></td>';
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

function clearHeroTable() {
    clearError();
    $('#contentRow').empty();
}

function deleteHero(id) {
    $.ajax({
        type: 'DELETE',
        url: '/SuperHeroGame/superhero/' + id,
        success: function (status) {
            loadAllSuperHeroes();
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function showEditForm(id) {
    clearError();
    $('#editFormDiv').show();
    $('#heroTableDiv').hide();

    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/superhero/' + id,
        success: function (hero, status) {
            var org = hero.organization;
            var ids = [];
            $('#editHeroId').val(hero.heroID);
            $('#editHeroName').val(hero.heroName);
            listAllAttitudesToEditFrom(hero.attitude.attitudeID);
            listAllPowersToEditForm(hero.superPower.superPowerID);
            listAllOrganizationsToEditForm(hero);


        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });


}

function listAllPowersToEditForm(id) {
    clearError();
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/powers',
        success: function (data, status) {

            $('#editPower').empty();
            $.each(data, function (index, power) {
                var content = power.nameOfPower;
                var row = '<option value=' + power.superPowerID + '>';
                row += content;
                row += '</option>';
                $('#editPower').append(row);
            });
            $('#editPower').val(id);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function listAllAttitudesToEditFrom(id) {
    clearError();
    $('#editAttitude').empty();
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/attitudes',
        success: function (data, status) {

            $('#editAtitude').empty();
            $.each(data, function (index, attitude) {
                var content = attitude.description;
                var row = '<option value=' + attitude.attitudeID + '>';
                row += content;
                row += '</option>';
                $('#editAttitude').append(row);
            });
            $('#editAttitude').val(id);
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function listAllOrganizationsToEditForm(hero) {
    clearError();
    $('#editOrganization').empty();
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/organizations',
        success: function (data, status) {

            var org = hero.organization;
            var ids = [];
            $('#editOrganization').empty();
            $.each(data, function (index, org) {
                var content = org.organizationName;
                var row = '<option value=' + org.organizationID + '>';
                row += content;
                row += '</option>';
                $('#editOrganization').append(row);
            });


            for (i = 0; i < org.length; i++) {
                ids.push(org[i].organizationID);
            }

            $('#editOrganization').val(ids);
            $('#editOrganization').selectpicker('refresh');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

$('#cancelEditButton').click(function () {
    $('#editFormDiv').hide();
    $('#heroTableDiv').show();
});


$('#updateButton').click(function () {
    
    clearError();

    var name = $('#editHeroName').val();
    var attitude = {attitudeID: $('#editAttitude').val()};
    var power = {superPowerID: $('#editPower').val()};
    var org = $('#editOrganization').val();
    var orgs = [];

    for (i = 0; i < org.length; i++) {
        orgs.push({organizationID: org[i]});
    }


    if ( power != null && attitude != null && name.length > 0){

        $.ajax({
            type: 'PUT',
            url: '/SuperHeroGame/superhero/' + $('#editHeroId').val(),
            data: JSON.stringify({
                heroName: $('#editHeroName').val(),
                superPower: power,
                attitude: attitude,
                heroID: $('#editHeroId').val(),
                organization: orgs
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function () {

                $('#editFormDiv').hide();
                loadAllSuperHeroes();
                $('#heroTableDiv').show();
            },
            error: function () {
                $('#errorMessages')
                        .append($('<li>')
                                .attr({class: 'list-group-item list-group-item-danger'})
                                .text('Error calling web service.  Please try again later.'));
            }
        });

  }

});



///// Add Hero Form


function listAllPowersToAddForm() {
    clearError();
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/powers',
        success: function (data, status) {

            $('#newPower').empty();
            $.each(data, function (index, power) {
                var content = power.nameOfPower;
                var row = '<option value=' + power.superPowerID + '>';
                row += content;
                row += '</option>';
                $('#newSuperPower').append(row);
            });
            $('#newSuperPower').val('');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function listAllAttitudesToAddFrom() {
    clearError();
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/attitudes',
        success: function (data, status) {


            $.each(data, function (index, attitude) {
                var content = attitude.description;
                var row = '<option value=' + attitude.attitudeID + '>';
                row += content;
                row += '</option>';
                $('#newAttitude').append(row);
            });
            $('#newAttitude').val('');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

function listAllOrganizationsToAddForm() {
    clearError();

    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/organizations',
        success: function (data, status) {
            $('#newOrganization').empty();
            $.each(data, function (index, org) {
                var content = org.organizationName;
                var row = '<option value=' + org.organizationID + '>';
                row += content;
                row += '</option>';
                $('#newOrganization').append(row);
            });
            $('#newOrganization').val('');
            $('#newOrganization').selectpicker('refresh');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }
    });
}

$('#addSuperHero').click(function () {
    
    clearError();


    var name = $('#addHeroName').val();
    var power = {superPowerID: $('#newSuperPower').val()};
    var attitude = {attitudeID: $('#newAttitude').val()};
    var org = $('#newOrganization').val();
    var orgs = [];

    for (i = 0; i < org.length; i++) {
        orgs.push({organizationID: org[i]});
    }
    
    if ( power != null && attitude != null && name.length > 0){
        
        $.ajax({
        type: 'POST',
        url: '/SuperHeroGame/superhero',
        data: JSON.stringify({
            heroName: $('#addHeroName').val(),
            superPower: power,
            attitude: attitude,
            organization: orgs
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },

        success: function () {
            loadAllSuperHeroes();
            $('#newSuperPower').val('');
            $('#newAttitude').val('');
            $('#newOrganization').val('');
            $('#addHeroName').val('');
            $('#newOrganization').selectpicker('refresh');
        },
        error: function () {
            $('#errorMessages')
                    .append($('<li>')
                            .attr({class: 'list-group-item list-group-item-danger'})
                            .text('Error calling web service.  Please try again later.'));
        }

    });
        
    }

    
});

$('#cancelAddButton').click(function () {
    $('#newSuperPower').val('');
    $('#newAttitude').val('');
    $('#newOrganization').val('');
    $('#addHeroName').val('');
});


function clearError(){
    $('#errorMessages').empty();
}