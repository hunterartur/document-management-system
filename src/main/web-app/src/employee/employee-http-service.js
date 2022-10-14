angular.module('documentflowApp').factory('EmployeeHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH+'person/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'person/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'person/update'},
        'post': {method: 'POST', url: API_PATH + 'person/save'}
    });
});