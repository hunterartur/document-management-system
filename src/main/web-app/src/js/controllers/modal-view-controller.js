angular.module('documentflowApp').controller('modalView', function ($uibModal, $rootScope, $log, $document, PostService, DepartmentService) {
    const scope = this;
    scope.animationsEnabled = true;
    scope.object = {};
    scope.visible = true;

    scope.open = function (typeObject, object, method, parentSelector) {
        if (method === 'PUT') {
            scope.title = 'Обновление ';
        } else {
            scope.title = 'Создание ';
        }
        if (typeObject === "employee") {
            scope.visible = true;
            scope.title += 'сотрудника';
        } else {
            scope.visible = false;
            scope.title += 'документа';
        }
        scope.object = object ? object : {};
        scope.detachObject = Object.assign({}, scope.object);
        scope.method = method ? method : 'POST';
        let parentElem = parentSelector ?
            angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
        let modalInstance = $uibModal.open({
            animation: scope.animationsEnabled,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'createViewModal.html',
            controller: 'ModalInstanceCtrl',
            controllerAs: 'modalView',
            appendTo: parentElem,
            resolve: {
                params: function () {
                    return {
                        posts: PostService.getAll(),
                        departments: DepartmentService.getAll(),
                        detachObject: scope.detachObject,
                        method: scope.method,
                        typeObject: typeObject,
                        visible: scope.visible,
                        title: scope.title
                    };
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            scope.selected = selectedItem;
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });

        modalInstance.opened.then(function () {
            if (typeObject === "employee" && scope.detachObject.birthday !== undefined) {
                scope.detachObject.birthday = new Date(scope.detachObject.birthday);
            } else {
                scope.detachObject.registrationDate = scope.detachObject.registrationDate !== undefined ? new Date(scope.detachObject.registrationDate) : scope.detachObject.registrationDate;
                scope.detachObject.dateOfIssue = scope.detachObject.dateOfIssue !== undefined ? new Date(scope.detachObject.dateOfIssue) : scope.detachObject.dateOfIssue;
                scope.detachObject.outgoingRegistrationDate = scope.detachObject.outgoingRegistrationDate !== undefined ? new Date(scope.detachObject.outgoingRegistrationDate) : scope.detachObject.outgoingRegistrationDate;
            }
        });
    };

    scope.openConfirmModals = function (typeObject, object, method, parentSelector) {
        scope.object = object ? object : {};
        scope.method = method ? method : 'POST';
        let parentElem = parentSelector ?
            angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
        let modalInstance = $uibModal.open({
            animation: scope.animationsEnabled,
            ariaLabelledBy: 'modal-title-confirm',
            ariaDescribedBy: 'modal-body-confirm',
            templateUrl: 'confirmForm.html',
            size: 'sm',
            controller: 'ModalInstanceCtrl',
            controllerAs: 'modalView',
            appendTo: parentElem,
            resolve: {
                params: function () {
                    return {detachObject: scope.object, method: scope.method};
                }
            }
        });
    };
});