angular.module('documentflowApp').factory('DepartmentHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH+'department/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'department/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'department/update'},
        'post': {method: 'POST', url: API_PATH + 'department/save'}
    });
});