angular.module('app.constants', [])
    .constant('appConstants', {
        openAMCookie: 'iPlanetDirectoryPro',
        openAMURL: 'http://openam13.sc.com:8080/openam',
        apisURL: 'http://apis.sc.com:8010/history/',
        apisURLOpenIG: 'http://apis-ig.sc.com:9002/history/',
        clientID: 'employeeApp',
        redirectURI: 'http://employees-ig.sc.com:9000/employeeApp/oauth2_callback.html'
     });
