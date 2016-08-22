angular.module('app.constants', [])
    .constant('appConstants', {
        openAMCookie: 'iPlanetDirectoryPro',
        openAMURL: 'http://openam.example.com:18080/openam',
        apisURL: 'http://apis.example.net:8010/history/',
        apisURLOpenIG: 'http://apis-ig.example.net:9002/history/',
        travelAppURL: 'http://travel.example.net:8012/travelApp/',
        travelAppURLOpenIG: 'http://travel-ig.example.net:9002/travelApp/',
        benefitsAppURL: 'http://benefits.example.com:8014/benefitsApp/',
        benefitsAppURLOpenIG: 'http://benefits-ig.example.com:9002/benefitsApp/',
        clientID: 'employeeApp',
        redirectURI: 'http://employees-ig.example.com:9000/employeeApp/oauth2_callback.html'
     });
