angular.module('documentflowApp').factory('DocumentInteractionWithServer', function ($rootScope, DocumentService, DocumentHttpService) {
    return {
        save: async function (object) {
            let response = await DocumentHttpService.post(object).$promise;
            object = response;
            DocumentService.add(object);
            $rootScope.UpdateDocuments();
        },
        update: async function (object) {
            let response = await DocumentHttpService.update(object).$promise;
            object = response;
            DocumentService.update(object);
            $rootScope.UpdateDocuments();
            $rootScope.UpdateTab(object);
        },
        delete: async function (object) {
            let response = await DocumentHttpService.delete({id: object.id}).$promise;
            DocumentService.delete(object);
            $rootScope.CloseTab(object);
            $rootScope.UpdateDocuments();
        }
    }
});