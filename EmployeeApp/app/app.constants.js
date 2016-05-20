angular.module('app.constants', [])
    .constant('appConstants', {
        openAMCookie: 'iPlanetDirectoryPro',
        openAMURL: 'http://openam13.sc.com:8080/openam',
        apisURL: 'http://apis.sample.com:8010/history/',
        apisURLOpenIG: 'http://apis-ig.sample.com:9002/history/',
        travelAppURL: 'http://travel.sample.com:8012/travelApp/',
        travelAppURLOpenIG: 'http://travel-ig.sample.com:9002/travelApp/',
        clientID: 'employeeApp',
        redirectURI: 'http://employees-ig.sc.com:9000/employeeApp/oauth2_callback.html'
     });
