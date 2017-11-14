$(document).ready(function(){
    loadLatestSightings();
});

var sightingMap= new Map();

function loadLatestSightings(){
    $.ajax({
        type: 'GET',
        url: '/SuperHeroGame/latest/sightings',
        success: function(data, status){
            var sightingArea =  $('#sightingArea');
            sightingArea.empty();
            
            $.each(data, function(index, sighting){
                
                
                var heroes= sighting.heroes;
                var sightingId= sighting.sightingID;
                sightingMap.set(sightingId, sighting);
                var sightingDate = new Date(sighting.date).toDateString();
                var singleDiv = '<div class="col-sm-3 sightingDivs" id="' + sightingId 
                        + '" data-toggle="modal" onclick="showModal('+ sightingId +')">';
                singleDiv += '<p> Sighting Location: </p>';
                singleDiv += '<h4>' + sighting.location.locationName + '</h4>';
                singleDiv += '<p> # of SuperHumans Sighted: '+ heroes.length + '</p>';
                singleDiv += '<p> Date of Sighting: </p>';
                singleDiv += '<h5>' + sightingDate + '</h5>';
                singleDiv += '<button type="button" class="btn btn-primary" \n\
                    data-toggle="modal" data-target=".modalDisplay">show more info</button>'
                singleDiv += '</div>';   
                
                sightingArea.append(singleDiv);
            });    
        },
        error: function () {
            $('#errorMessages').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Error calling web service. Please try again later.'));
        }
    });
}

function showModal(id){
    $('#content').empty();
    var sighting = sightingMap.get(id);
    var heroPerSighting = sighting.heroes;
    var location = sighting.location.locationName;
    var sightingDate = new Date(sighting.date).toDateString();
    
    var content = '<div class="formattModal">';
    content += '<div class="panel panel-default">';
    content += '<div class="panel-heading">SuperHumans sighted at: '+ location +' on '+ sightingDate +'</div>';
    content += '<table class="table">';
    content += '<tr>';
    content += '<th>SuperHuman Name</th>';
    content += '<th>Super Power</th>';
    content += '<th>Attitude</th>';
    content += '<th>Known Associations</th>';
    content += '</tr>';
    
    heroPerSighting.forEach(function(hero){
        var organizations = hero.organization;
        content += '<tr>';
        content += '<td> ' +hero.heroName +'</td>';
        content += '<td> ' +hero.superPower.nameOfPower +'</td>';
        content += '<td> ' +hero.attitude.description +'</td>';
        content += '<td><select>'; 
        organizations.forEach(function(org){
            content += '<option>' + org.organizationName +'</option>';   
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

