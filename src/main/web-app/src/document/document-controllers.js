angular.module('documentflowApp').controller('documentsController', function ($rootScope, DocumentService, EVENT_UPDATE_DOCUMENTS, EVENT_OPEN_TAB) {
    const scope = this;

    $rootScope.OpenTab = function (document) {
        $rootScope.$broadcast(EVENT_OPEN_TAB, document);
    }
    scope.documents = DocumentService.getAll();

    $rootScope.$on(EVENT_UPDATE_DOCUMENTS, function (event) {
        scope.documents = DocumentService.getAll();
    });

    scope.sortParam = 'registrationDate';
});