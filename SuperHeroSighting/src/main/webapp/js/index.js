function initMap(){
    loadLatestSightings();
}

var sightingMap = new Map();
var sightingLocationArray = [];

function loadLatestSightings() {
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/latest/sightings',
        success: function (data, status) {
            var sightingArea = $('#sightingArea');
            sightingLocationArray = [];
            sightingArea.empty();

//            This section is dynamically loading the latest sightings

            $.each(data, function (index, sighting) {
                var heroes = sighting.heroes;
                var sightingId = sighting.sightingID;
                sightingMap.set(sightingId, sighting);
                var sightingDate = new Date(sighting.date).toDateString();
                var singleDiv = '<div class="col-sm-3 sightingDivs" id="' + sightingId
                        + '" data-toggle="modal" onclick="showModal(' + sightingId + ')">';
                singleDiv += '<p> Sighting Location: </p>';
                singleDiv += '<h4>' + sighting.location.locationName + '</h4>';
                singleDiv += '<p> # of SuperHumans Sighted: ' + heroes.length + '</p>';
                singleDiv += '<p> Date of Sighting: </p>';
                singleDiv += '<h5>' + sightingDate + '</h5>';
                singleDiv += '<button type="button" class="btn btn-primary" \n\
                    data-toggle="modal" data-target=".modalDisplay">show more info</button>'
                singleDiv += '</div>';

                sightingArea.append(singleDiv);
            });

//            This section is creating a list of location objects to add to google map

            sightingMap.forEach(function (value, key, map) {
                var currentLocationObj = {
                    lat: value.location.latitude,
                    lng: value.location.longitude
                };
                sightingLocationArray.push(currentLocationObj);
            });

            placeMarkers(sightingLocationArray);

        },
        error: function () {
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
}

function showModal(id) {
    $('#content').empty();
    var sighting = sightingMap.get(id);
    var heroPerSighting = sighting.heroes;
    var location = sighting.location.locationName;
    var sightingDate = new Date(sighting.date).toDateString();

    var content = '<div class="formattModal">';
    content += '<div class="panel panel-default">';
    content += '<div class="panel-heading">SuperHumans sighted at: ' + location + ' on ' + sightingDate + '</div>';
    content += '<table class="table">';
    content += '<tr>';
    content += '<th>SuperHuman Name</th>';
    content += '<th>Super Power</th>';
    content += '<th>Attitude</th>';
    content += '<th>Known Associations</th>';
    content += '</tr>';

    heroPerSighting.forEach(function (hero) {
        var organizations = hero.organization;
        content += '<tr>';
        content += '<td> ' + hero.heroName + '</td>';
        content += '<td> ' + hero.superPower.nameOfPower + '</td>';
        content += '<td> ' + hero.attitude.description + '</td>';
        content += '<td><select>';
        organizations.forEach(function (org) {
            content += '<option>' + org.organizationName + '</option>';
        });
        content += '</select></td>';
        content += '</tr>';

    });
    content += '</table>';
    content += '</div>';
    content += '</div>';
    content += '</div>';
    $('#content').append(content);
}

function placeMarkers(locations) {

    var uluru = {lat: 44.9789694, lng: -93.271823};


    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 2,
        center: uluru
    });

    for (var i = 0; i < locations.length; i++) {
        var currObj = locations[i];
        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(currObj.lat, currObj.lng),
            map: map
        });
    }

    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(uluru.lat, uluru.lng),
        map: map
    });
}