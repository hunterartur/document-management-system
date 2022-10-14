angular.module('documentflowApp').factory('PostHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH+'post/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'post/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'post/update'},
        'post': {method: 'POST', url: API_PATH + 'post/save'}
    });
});
