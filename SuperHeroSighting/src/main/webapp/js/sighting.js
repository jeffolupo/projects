$(document).ready(function () {
    loadSightingsTable();
    loadAllHeroesToAddSightingForm();
    loadAllLocationsToForm(addLocation);
});
var heroMap = new Map();
var sightingMap = new Map();
var locationMap = new Map();

var addLocation = '#addLocation';
var editLocation = '#editLocation';

function loadSightingsTable() {
    
    clearError();
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/sightings',
        success: function (data, status) {
            var contentRows = $('#contentRow');
            contentRows.empty();
            $.each(data, function (index, sighting) {
                sightingMap.set(sighting.sightingID, sighting);

                var sightingDate = new Date(sighting.date).toDateString();

                var id = sighting.sightingID;
                var row = '<tr>';
                row += '<td>' + sighting.location.locationName + '</td>';
                row += '<td>' + sightingDate + '</td>';
                row += '<td><a onclick="showEditForm(' + id + ')">Edit</a></td>';
                row += '<td><a onclick="deleteSighting(' + id + ')">Delete</a></td>';
                row += '</tr>';
                contentRows.append(row);
            });
        },
        error: function () {
            $('#errorMessages').empty();
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
}

function showEditForm(id) {
    $('#editSightingId').val('');
    $('#editDate').val('');
    $('#editHeroes').empty();
    $('#editLocation').empty();
    $('#editFormDiv').show();
    $('#sightingTableDiv').hide();
    $('#editSightingId').val(id);

    var sighting = sightingMap.get(id);
    var heroIds = [];
    var heroes = sighting.heroes;
    var locationID = sighting.location.locationID;
    $('#editDate').val(sighting.date);
    

    loadAllHeroesToEditPage();
    loadAllLocationsToForm(editLocation, locationID);


    for (i = 0; i < heroes.length; i++) {
        heroIds.push(heroes[i].heroID);
    }
    $('#editHeroes').val(heroIds);
    $('#editHeroes').selectpicker('refresh');
}

function deleteSighting(id) {
    
    clearError();
    $.ajax({
        type: 'DELETE',
        url: '/SuperHeroGame/sighting/' + id,
        success: function (data, status) {
            loadSightingsTable();
        },
        error: function () {
            $('#errorMessages').empty();
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
}




function loadAllLocationsToForm(addLocation, id) {
    
    clearError();

    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/locations',
        success: function (data, status) {
            $.each(data, function (index, location) {
                locationMap.set(location.locationID, location);
                var id = location.locationID;
                var name = location.locationName;
                var row = '<option value=' + id + '>';
                row += name;
                row += '</option>';
                $(addLocation).append(row);
            });
            $('#editLocation').val(id);
            $('#addLocation').val('');

        },
        error: function () {
            $('#errorMessages').empty();
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
}

function loadAllHeroesToAddSightingForm() {
    
    clearError();

    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/superheroes',
        success: function (data, status) {
            $.each(data, function (index, hero) {

                heroMap.set(hero.heroID, hero);

                var id = hero.heroID
                var name = hero.heroName;
                var row = '<option value=' + id + '>';
                row += name;
                row += '</option>';
                $('#addHeroes').append(row);

            });
            $('#addHeroes').val('');
            $('#addHeroes').selectpicker('refresh');

        },
        error: function () {
            $('#errorMessages').empty();
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
}

function loadAllHeroesToEditPage() {
    heroMap.forEach(function (hero, heroId, heroMap) {
        var hero = hero.heroName;
        var row = '<option value=' + heroId + '>';
        row += hero;
        row += '</option>';
        $('#editHeroes').append(row);
    });
}

$('#cancelEditButton').click(function () {
    $('#editFormDiv').hide();
    $('#sightingTableDiv').show();
});

$('#updateButton').click(function () {
    
    clearError();

    var selectHeroIds = $('#editHeroes').val();
    var heroObjects = [];
    var sightingId = $('#editSightingId').val();
    var sighting = sightingMap.get(parseInt(sightingId));
    var locationId = $('#editLocation').val();
    var location = locationMap.get(parseInt(locationId));

    for (i = 0; i < selectHeroIds.length; i++) {
        heroObjects.push(heroMap.get(parseInt(selectHeroIds[i])));
    }
    
    if(heroObjects.length > 0 && locationId > 0){
        
        $.ajax({
        type: 'PUT',
        url: '/SuperHeroGame/sighting/' + sightingId,
        data: JSON.stringify({
            sightingID: $('#editSightingId').val(),
            heroes: heroObjects,
            location: location,
            date: $('#editDate').val()
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function () {
            loadSightingsTable();
            $('#editFormDiv').hide();
            $('#sightingTableDiv').show();

        },
        error: function () {
            $('#errorMessages').empty();
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
        
    }

    
});

$('#addButton').click(function(){
    
    clearError();
    
    var locationId = $('#addLocation').val();
    var location = locationMap.get(parseInt(locationId));    
    var selectHeroIds = $('#addHeroes').val();
    var heroObjects = [];
    var date = new Date(Date.now()).toISOString();

    for (i = 0; i < selectHeroIds.length; i++) {
        heroObjects.push(heroMap.get(parseInt(selectHeroIds[i])));
    }
    
    if(heroObjects.length > 0 && locationId > 0){
        $.ajax({
        type: 'POST',
        url: '/SuperHeroGame/sighting',
        data: JSON.stringify({
            heroes: heroObjects,
            location: location,
            date: date
        }),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        
        success: function () {
            loadSightingsTable();
            $('#addHeroes').val('');
            $('#addLocation').val('');
        },
        error: function () {
            $('#errorMessages').empty();
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
    }
});

function clearError(){
    $('#errorMessages').empty();
}