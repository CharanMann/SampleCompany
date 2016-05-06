angular.module('app.constants', [])
    .constant('appConstants', {
        openAMCookie: 'iplanetDirectoryPro',
        openAMURL: 'http://openam13.sc.com:8080/openam',
        openAMOAuth2AccessTokenURL: 'http://openam13.sc.com:8080/openam/oauth2/employees/access_token',
        openAMOAuth2AuthURL: 'http://openam13.sc.com:8080/openam/oauth2/employees/authorize',
        openAMOAuth2TokenInfoURL: 'http://openam13.sc.com:8080/openam/oauth2/employees/tokeninfo',
        apisURL: 'http://apis.sc.com:8010/history/',
        apisURLOpenIG: 'http://apis-ig.sc.com:9002/history/'
     });