angular.module('documentflowApp').factory('DocumentHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH + 'document/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'document/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'document/update'},
        'post': {method: 'POST', url: API_PATH + 'document/save'},
        'getAllDeliveryMethods': {method: 'GET', url: API_PATH + 'document/delivery-method', isArray: true},
        'getAllDocumentTypes': {method: 'GET', url: API_PATH + 'document/document-type', isArray: true}
    });
});