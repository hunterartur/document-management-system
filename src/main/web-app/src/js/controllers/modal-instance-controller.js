angular.module('documentflowApp').controller('ModalInstanceCtrl', function ($uibModalInstance, params, EmployeeService, $rootScope, DocumentService, ProcessingEmployee,
                                              DocumentHttpService, EmployeeHttpService, DeliveryMethodService, EVENT_UPDATE_DOCUMENTS,
                                              ProcessingDocument, EVENT_UPDATE_EMPLOYEES, EVENT_CLOSE_TAB, EVENT_UPDATE_TAB) {
    const scope = this;
    scope.posts = params.posts;
    scope.departments = params.departments;
    scope.object = params.detachObject;
    scope.deliveryMethods = DeliveryMethodService.getAll();
    scope.visible = params.visible;
    scope.typeObject = params.typeObject
    scope.employees = EmployeeService.getAll();
    scope.ok = ok;
    scope.cancel = cancel;
    scope.title = params.title;

    $rootScope.CloseTab = function (employee) {
        $rootScope.$broadcast(EVENT_CLOSE_TAB, employee);
    }

    $rootScope.UpdateEmployees = function () {
        $rootScope.$broadcast(EVENT_UPDATE_EMPLOYEES);
    }

    $rootScope.UpdateTab = function (updateTab) {
        $rootScope.$broadcast(EVENT_UPDATE_TAB, updateTab);
    }

    $rootScope.UpdateDocuments = function () {
        $rootScope.$broadcast(EVENT_UPDATE_DOCUMENTS);
    }

    function ok(object) {
        if (!object.registrationNumber) {
            ProcessingEmployee.processingEmployee(params.method, object);
        } else {
            ProcessingDocument.processingDocument(params.method, object);
        }
        $uibModalInstance.close();
    };

    function cancel() {
        $uibModalInstance.dismiss('cancel');
    };
});