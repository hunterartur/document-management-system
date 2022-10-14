/******/ (() => { // webpackBootstrap
/******/ 	var __webpack_modules__ = ({

/***/ "./src/department/department-http-service.js":
/*!***************************************************!*\
  !*** ./src/department/department-http-service.js ***!
  \***************************************************/
/***/ (() => {

angular.module('documentflowApp').factory('DepartmentHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH+'department/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'department/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'department/update'},
        'post': {method: 'POST', url: API_PATH + 'department/save'}
    });
});

/***/ }),

/***/ "./src/department/department-service.js":
/*!**********************************************!*\
  !*** ./src/department/department-service.js ***!
  \**********************************************/
/***/ (() => {

angular.module('documentflowApp').factory('DepartmentService', function () {
    let departments = [];

    return {
        getAll: function () {
            return departments;
        },
        add: function (department) {
            if (this.getById(department.id) == null) {
                departments.push(department);
            }
        },
        getById: function (id) {
            return departments.find(department => department.id === id);
        },
        setAll: function (departmentList) {
            departments = departmentList;
        },
        delete: function (department) {
            departments = departments.filter(d => d.id !== department.id);
        }
    };
});


/***/ }),

/***/ "./src/document/document-controllers.js":
/*!**********************************************!*\
  !*** ./src/document/document-controllers.js ***!
  \**********************************************/
/***/ (() => {

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

/***/ }),

/***/ "./src/document/document-directives.js":
/*!*********************************************!*\
  !*** ./src/document/document-directives.js ***!
  \*********************************************/
/***/ (() => {

angular.module('documentflowApp').directive('documentsTable', function () {
    return {
        restrict: 'E',
        transclude: true,
        templateUrl: 'src/document/documents-table.html'
    };
});

angular.module('documentflowApp').directive('documentTab', function () {
    return {
        restrict: 'E',
        transclude: true,
        scope: {document: '='},
        templateUrl: 'src/document/document-info.html'
    };
});

angular.module('documentflowApp').directive('documentForm', function () {
    return {
        restrict: 'E',
        transclude: false,
        templateUrl: 'src/document/document-form.html'
    }
});

/***/ }),

/***/ "./src/document/document-http-service.js":
/*!***********************************************!*\
  !*** ./src/document/document-http-service.js ***!
  \***********************************************/
/***/ (() => {

angular.module('documentflowApp').factory('DocumentHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH + 'document/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'document/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'document/update'},
        'post': {method: 'POST', url: API_PATH + 'document/save'},
        'getAllDeliveryMethods': {method: 'GET', url: API_PATH + 'document/delivery-method', isArray: true},
        'getAllDocumentTypes': {method: 'GET', url: API_PATH + 'document/document-type', isArray: true}
    });
});

/***/ }),

/***/ "./src/document/document-interaction-with-server.js":
/*!**********************************************************!*\
  !*** ./src/document/document-interaction-with-server.js ***!
  \**********************************************************/
/***/ (() => {

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

/***/ }),

/***/ "./src/document/document-processing.js":
/*!*********************************************!*\
  !*** ./src/document/document-processing.js ***!
  \*********************************************/
/***/ (() => {

angular.module('documentflowApp').factory('ProcessingDocument', function (DocumentInteractionWithServer) {
    return {
        processingDocument: function (method, object) {
            switch (method) {
                case 'POST':
                    DocumentInteractionWithServer.save(object)
                        .catch(function (error) {
                            alert('Не удалось создать документа № ' + object.registrationNumber +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
                case 'PUT':
                    DocumentInteractionWithServer.update(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить обновление документа № ' + object.registrationNumber +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
                case 'DELETE':
                    DocumentInteractionWithServer.delete(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить удаление документа №' + object.registrationNumber +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
            }
        }
    }
});

/***/ }),

/***/ "./src/document/document-service.js":
/*!******************************************!*\
  !*** ./src/document/document-service.js ***!
  \******************************************/
/***/ (() => {

angular.module('documentflowApp').factory('DocumentService', function (EmployeeService) {
    let documents = [];

    return {
        getAll: function () {
            return documents;
        },
        findByAuthor: function (idAuthor) {
            return documents.filter(document => document.author.id === idAuthor);
        },
        update: function (document) {
            let index = documents.findIndex(doc => doc.id === document.id);
            documents[index] = document;
        },
        delete: function (employee) {
            documents = documents.filter(document => document.id !== employee.id);
        },
        setAll: function (documentList) {
            documents = documentList;
            documents.forEach(doc => {
                doc.author = EmployeeService.getById(doc.author.id);
                if (doc.controller !== undefined) {
                    doc.controller = EmployeeService.getById(doc.controller.id);
                    doc.documentType = 'TASK_DOCUMENT';
                }
                if (doc.executor !== undefined) {
                    doc.executor = EmployeeService.getById(doc.executor.id);
                }
                if (doc.recipient !== undefined) {
                    doc.recipient = EmployeeService.getById(doc.recipient.id);
                    doc.documentType = 'INCOMING_DOCUMENT';
                }
                if (doc.sender !== undefined) {
                    doc.sender = EmployeeService.getById(doc.sender.id);
                }
                if (doc.deliveryMethod !== undefined) {
                    doc.documentType = 'OUTGOING_DOCUMENT';
                }
            });
        },
        getById: function (id) {
            return documents.find(document => document.id === id);
        },
        add: function (document) {
            if (this.getById(document.id) == null) {
                documents.push(document);
            }
        }
    };
});

angular.module('documentflowApp').factory('DeliveryMethodService', function () {
    let deliveryMethods = [];

    return {
        getAll: function () {
            return deliveryMethods;
        },
        setAll: function (deliveryMethodList) {
            deliveryMethods = deliveryMethodList;
        }
    };
});

angular.module('documentflowApp').factory('DocumentTypeService', function () {
    let documentType = [];

    return {
        getAll: function () {
            return documentType;
        },
        setAll: function (documentTypeList) {
            documentType = documentTypeList;
        }
    };
});

/***/ }),

/***/ "./src/employee/employee-content-controller.js":
/*!*****************************************************!*\
  !*** ./src/employee/employee-content-controller.js ***!
  \*****************************************************/
/***/ (() => {

angular.module('documentflowApp').controller('employeeContentController', function ($rootScope, EmployeeService, EVENT_UPDATE_EMPLOYEES) {
    const scope = this;
    $rootScope.$on(EVENT_UPDATE_EMPLOYEES, function (event) {
        scope.employees = EmployeeService.getAll();
    });
});

/***/ }),

/***/ "./src/employee/employee-controller.js":
/*!*********************************************!*\
  !*** ./src/employee/employee-controller.js ***!
  \*********************************************/
/***/ (() => {

angular.module('documentflowApp').controller('employeeController', function (DocumentService) {
    let scope = this;
    scope.documents = DocumentService.findByAuthor();
    scope.sortParam = 'registrationDate';
});

/***/ }),

/***/ "./src/employee/employee-directives.js":
/*!*********************************************!*\
  !*** ./src/employee/employee-directives.js ***!
  \*********************************************/
/***/ (() => {

angular.module('documentflowApp').directive('employeesTable', function () {
    return {
        restrict: 'E',
        transclude: true,
        templateUrl: 'src/employee/employees-table.html'
    };
});

angular.module('documentflowApp').directive('employeeInfo', function () {
    return {
        restrict: 'E',
        transclude: true,
        scope: {employee: '='},
        templateUrl: 'src/employee/employee-info.html'
    };
});

angular.module('documentflowApp').directive('employeeForm', function () {
    return {
        restrict: 'E',
        transclude: false,
        templateUrl: 'src/employee/employee-form.html'
    }
});

/***/ }),

/***/ "./src/employee/employee-http-service.js":
/*!***********************************************!*\
  !*** ./src/employee/employee-http-service.js ***!
  \***********************************************/
/***/ (() => {

angular.module('documentflowApp').factory('EmployeeHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH+'person/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'person/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'person/update'},
        'post': {method: 'POST', url: API_PATH + 'person/save'}
    });
});

/***/ }),

/***/ "./src/employee/employee-interaction-with-server.js":
/*!**********************************************************!*\
  !*** ./src/employee/employee-interaction-with-server.js ***!
  \**********************************************************/
/***/ (() => {

angular.module('documentflowApp').factory('employeeInteractionWithServer', function ($rootScope, EmployeeService, EmployeeHttpService) {
    return {
        readImage: function (image) {
            return new Promise((resolve, reject) => {
                let fileReader = new FileReader();
                fileReader.onload = x => resolve(fileReader.result);
                fileReader.readAsDataURL(image);
            })
        },
        isString: function (val) {
            return (typeof val === "string" || val instanceof String);
        },
        save: async function (object) {
            if (object.photo !== undefined) {
                object.photo = await this.readImage(object.photo);
            }
            let response = await EmployeeHttpService.post(object).$promise;
            object = response;
            EmployeeService.add(object);
            $rootScope.UpdateEmployees();
        },
        update: async function (object) {
            if (object.photo !== undefined && !(this.isString(object.photo))) {
                object.photo = await this.readImage(object.photo);
            }
            object.employeeDocuments = '';
            let response = await EmployeeHttpService.update(object).$promise;
            object = response;
            EmployeeService.update(object);
            $rootScope.UpdateEmployees();
            $rootScope.UpdateTab(object);
        },
        delete: async function (object) {
            let response = await EmployeeHttpService.delete({id: object.id}).$promise;
            EmployeeService.delete(object);
            $rootScope.CloseTab(object);
            $rootScope.UpdateEmployees();
        }
    }
});

/***/ }),

/***/ "./src/employee/employee-processing.js":
/*!*********************************************!*\
  !*** ./src/employee/employee-processing.js ***!
  \*********************************************/
/***/ (() => {

angular.module('documentflowApp').factory('ProcessingEmployee', function (employeeInteractionWithServer) {
    return {
        processingEmployee: function (method, object) {
            switch (method) {
                case 'POST':
                    employeeInteractionWithServer.save(object)
                        .catch(function (error) {
                            alert('Не удалось создать пользователя ' + object.lastName +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
                case 'PUT':
                    employeeInteractionWithServer.update(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить обновление пользователя ' + object.lastName +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + value);
                        });
                    break;
                case 'DELETE':
                    employeeInteractionWithServer.delete(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить удаление пользователя ' + object.lastName +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
            }
        }
    }
});

/***/ }),

/***/ "./src/employee/employee-service.js":
/*!******************************************!*\
  !*** ./src/employee/employee-service.js ***!
  \******************************************/
/***/ (() => {

angular.module('documentflowApp').factory('EmployeeService', function (PostService, DepartmentService) {
    let employees = [];

    return {
        getAll: function () {
            return employees;
        },
        update: function (employee) {
            let index = employees.findIndex(emp => emp.id === employee.id);
            employees[index] = employee;
        },
        delete: function (employee) {
            employees = employees.filter(emp => emp.id !== employee.id);
        },
        setAll: function (employeeList) {
            employees = employeeList;
            employees.forEach(emp => emp.post = PostService.getById(emp.post.id));
            employees.forEach(emp => emp.department = DepartmentService.getById(emp.department.id));
        },
        getById: function (id) {
            return employees.find(employee => employee.id === id);
        },
        add: function (employee) {
            if (this.getById(employee.id) == null) {
                employees.push(employee);
            }
        }
    };
});

/***/ }),

/***/ "./src/employee/employees-controller.js":
/*!**********************************************!*\
  !*** ./src/employee/employees-controller.js ***!
  \**********************************************/
/***/ (() => {

angular.module('documentflowApp').controller('employeesController', function ($http, $rootScope, EmployeeService, EVENT_UPDATE_EMPLOYEES, EVENT_OPEN_TAB) {
    const scope = this;

    $rootScope.OpenTab = function (employee) {
        $rootScope.$broadcast(EVENT_OPEN_TAB, employee);
    }
    scope.employees = EmployeeService.getAll();
    $rootScope.$on(EVENT_UPDATE_EMPLOYEES, function (event) {
        scope.employees = EmployeeService.getAll();
    });
});

/***/ }),

/***/ "./src/js/angular-main.js":
/*!********************************!*\
  !*** ./src/js/angular-main.js ***!
  \********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "app": () => (/* binding */ app)
/* harmony export */ });
const app = angular.module('documentflowApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ngResource']);
app.config(['$resourceProvider', function($resourceProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
}]);
app.constant('API_PATH', "http://localhost:8080/v1.0/");
app.constant('EVENT_UPDATE_DOCUMENTS', "updateDocuments");
app.constant('EVENT_UPDATE_EMPLOYEES', "updateEmployees");
app.constant('EVENT_CLOSE_TAB', "closeTab");
app.constant('EVENT_OPEN_TAB', "openTab");
app.constant('EVENT_UPDATE_TAB', "updateTab");

/***/ }),

/***/ "./src/js/controllers/initialization-controller.js":
/*!*********************************************************!*\
  !*** ./src/js/controllers/initialization-controller.js ***!
  \*********************************************************/
/***/ (() => {

angular.module('documentflowApp').controller('init', function (PostService, DepartmentService, PostHttpService, DepartmentHttpService,
                                 DeliveryMethodService, EVENT_UPDATE_DOCUMENTS, EVENT_UPDATE_EMPLOYEES, DocumentHttpService,
                                 DocumentTypeService, EmployeeService, EmployeeHttpService, DocumentService, $rootScope) {

    const scope = this;
    scope.downloadDocuments = downloadDocuments;


    $rootScope.UpdateDocuments = function () {
        $rootScope.$broadcast(EVENT_UPDATE_DOCUMENTS);
    }

    $rootScope.UpdateEmployees = function () {
        $rootScope.$broadcast(EVENT_UPDATE_EMPLOYEES);
    }

    async function downloadDocuments() {
        let posts = await PostHttpService.query().$promise;
        PostService.setAll(posts);
        let departments = await DepartmentHttpService.query().$promise;
        DepartmentService.setAll(departments);

        let deliveryMethods = await DocumentHttpService.getAllDeliveryMethods().$promise;
        DeliveryMethodService.setAll(deliveryMethods);

        let documentTypes = await DocumentHttpService.getAllDocumentTypes().$promise;
        DocumentTypeService.setAll(documentTypes);

        let employees = await EmployeeHttpService.query().$promise;
        EmployeeService.setAll(employees);
        $rootScope.UpdateEmployees();
        let documents = await DocumentHttpService.query().$promise;
        DocumentService.setAll(documents);
        $rootScope.UpdateDocuments();
    }

    scope.downloadDocuments().catch(function (error) {
        console.log(error)
    })

    scope.downloadDocuments();
});


/***/ }),

/***/ "./src/js/controllers/modal-instance-controller.js":
/*!*********************************************************!*\
  !*** ./src/js/controllers/modal-instance-controller.js ***!
  \*********************************************************/
/***/ (() => {

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

/***/ }),

/***/ "./src/js/controllers/modal-view-controller.js":
/*!*****************************************************!*\
  !*** ./src/js/controllers/modal-view-controller.js ***!
  \*****************************************************/
/***/ (() => {

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

/***/ }),

/***/ "./src/js/directives/directives.js":
/*!*****************************************!*\
  !*** ./src/js/directives/directives.js ***!
  \*****************************************/
/***/ (() => {

angular.module('documentflowApp').directive('welcomeTab', function () {
    return {
        restrict: 'E',
        transclude: true,
        templateUrl: 'src/tabs/welcome-tab.html'
    };
});

angular.module('documentflowApp').directive('modalWindow', function () {
    return {
        restrict: 'E',
        transclude: false,
        templateUrl: 'src/templates/modal-window.html'
    }
});

angular.module('documentflowApp').directive('confirmModalWindow', function () {
    return {
        restrict: 'E',
        transclude: false,
        templateUrl: 'src/templates/confirm-form.html'
    }
});

angular.module('documentflowApp').directive("selectImage", function() {
    return {
        require: "ngModel",
        link: function postLink(scope,elem,attrs,ngModel) {
            elem.on("change", function(e) {
                let file = elem[0].files[0];
                ngModel.$setViewValue(file);
            })
        }
    }
});

/***/ }),

/***/ "./src/post/post-http-service.js":
/*!***************************************!*\
  !*** ./src/post/post-http-service.js ***!
  \***************************************/
/***/ (() => {

angular.module('documentflowApp').factory('PostHttpService', function ($resource, API_PATH) {
    return $resource(API_PATH+'post/:id', {id: '@id'}, {
        'delete': {method: 'DELETE', url: API_PATH + 'post/delete/:id'},
        'update': {method: 'PUT', url: API_PATH + 'post/update'},
        'post': {method: 'POST', url: API_PATH + 'post/save'}
    });
});


/***/ }),

/***/ "./src/post/post-service.js":
/*!**********************************!*\
  !*** ./src/post/post-service.js ***!
  \**********************************/
/***/ (() => {

angular.module('documentflowApp').factory('PostService', function () {
    let posts = [];

    return {
        getAll: function () {
            return posts;
        },
        delete: function (post) {
            posts = posts.filter(p => p.id !== post.id);
        },
        setAll: function (postList) {
            posts = postList;
        },
        add: function (post) {
            if (this.getById(post.id) == null) {
                posts.push(post);
            }
        },
        getById: function (id) {
            return posts.find(post => post.id === id);
        }
    };
});

/***/ }),

/***/ "./src/tabs/tab-controller.js":
/*!************************************!*\
  !*** ./src/tabs/tab-controller.js ***!
  \************************************/
/***/ (() => {

angular.module('documentflowApp').controller('tabsController', function ($rootScope, $timeout, DocumentService, EVENT_CLOSE_TAB, EVENT_OPEN_TAB, EVENT_UPDATE_TAB,
                                           EVENT_UPDATE_TAB, PostService, DepartmentService, TabsService) {
    let scope = this;
    scope.activeTabIndex = TabsService.getIndexActiveTab();
    scope.tabs = TabsService.getAll();
    scope.openTab = openTab;
    scope.closeTab = closeTab;
    scope.selectTab = selectTab;

    $rootScope.$on(EVENT_OPEN_TAB, function (event, newTab) {
        scope.openTab(newTab);
    });

    $rootScope.$on(EVENT_CLOSE_TAB, function (event, closedTab) {
        scope.closeTab(closedTab);
    });

    $rootScope.$on(EVENT_UPDATE_TAB, function (event, updateTab) {
        closeTab(updateTab);
        openTab(updateTab);
    });

    function openTab(newTab) {
        if (!newTab.registrationNumber) {
            scope.tabs.employees = TabsService.getAll().employees;
            newTab.post = PostService.getById(newTab.post.id);
            newTab.department = DepartmentService.getById(newTab.department.id);
            newTab.employeeDocuments = DocumentService.findByAuthor(newTab.id);
        } else {
            scope.tabs.documents = TabsService.getAll().documents;
        }

        if (TabsService.findTabById(newTab.id) === undefined) {
            TabsService.openTab(newTab);
            $timeout(function () {
                TabsService.setActiveTab(newTab.id);
                scope.activeTabIndex = TabsService.getIndexActiveTab();
            });
        } else {
            TabsService.setActiveTab(newTab.id);
            scope.activeTabIndex = TabsService.getIndexActiveTab();
        }
    };

    function closeTab(closedTab) {
        TabsService.closeTab(closedTab);
        scope.activeTabIndex = TabsService.getIndexActiveTab();
        scope.tabs = TabsService.getAll();
    }

    function selectTab(tab) {
        scope.employeeDocuments = DocumentService.findByAuthor(tab.id);
    }
});

/***/ }),

/***/ "./src/tabs/tab-service.js":
/*!*********************************!*\
  !*** ./src/tabs/tab-service.js ***!
  \*********************************/
/***/ (() => {

angular.module('documentflowApp').factory('TabsService', function () {

    let tabs = {employees: [], documents: []};

    let activeTabIndex = -1;

    return {
        getAll: function () {
            return tabs;
        },
        openTab: function (tab) {
            if (!tab.registrationNumber) {
                tabs.employees.push(tab);
            } else {
                tabs.documents.push(tab);
            }
        },
        getIndexActiveTab: function () {
            return activeTabIndex;
        },
        setActiveTab: function (index) {
            activeTabIndex = index;
        },
        findTabById: function (id) {
            let employeeTab = tabs.employees.find(tab => tab.id === id);
            if (employeeTab !== undefined) {
                return employeeTab;
            } else {
                return tabs.documents.find(tab => tab.id === id);
            }
        },
        closeTab: function (closedTab) {
            if (!closedTab.registrationNumber) {
                let index = tabs.employees.findIndex(emp => emp.id === closedTab.id);
                tabs.employees.splice(index, 1);
                if (tabs.employees.length !== 0) {
                    if (tabs.employees.length - 1 >= index) {
                        this.setActiveTab(tabs.employees[index].id);
                    } else {
                        this.setActiveTab(tabs.employees[tabs.employees.length - 1].id);
                    }
                } else if (tabs.documents.length !== 0) {
                    this.setActiveTab(tabs.documents[0].id);
                } else {
                    this.setActiveTab(-1);
                }
            } else {
                let index = tabs.documents.findIndex(doc => doc.id === closedTab.id);
                tabs.documents.splice(index, 1);
                if (tabs.documents.length !== 0) {
                    if (tabs.documents.length - 1 >= index) {
                        this.setActiveTab(tabs.documents[index].id);
                    } else {
                        this.setActiveTab(tabs.documents[tabs.documents.length - 1].id);
                    }
                } else if (tabs.employees.length !== 0) {
                    this.setActiveTab(tabs.employees[tabs.employees.length - 1].id);
                } else {
                    this.setActiveTab(-1);
                }
            }
        }
    };
});

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/compat get default export */
/******/ 	(() => {
/******/ 		// getDefaultExport function for compatibility with non-harmony modules
/******/ 		__webpack_require__.n = (module) => {
/******/ 			var getter = module && module.__esModule ?
/******/ 				() => (module['default']) :
/******/ 				() => (module);
/******/ 			__webpack_require__.d(getter, { a: getter });
/******/ 			return getter;
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/define property getters */
/******/ 	(() => {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = (exports, definition) => {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	(() => {
/******/ 		__webpack_require__.o = (obj, prop) => (Object.prototype.hasOwnProperty.call(obj, prop))
/******/ 	})();
/******/ 	
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/ 	
/************************************************************************/
var __webpack_exports__ = {};
// This entry need to be wrapped in an IIFE because it need to be in strict mode.
(() => {
"use strict";
/*!**********************!*\
  !*** ./src/index.js ***!
  \**********************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _js_angular_main_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./js/angular-main.js */ "./src/js/angular-main.js");
/* harmony import */ var _employee_employees_controller_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./employee/employees-controller.js */ "./src/employee/employees-controller.js");
/* harmony import */ var _employee_employees_controller_js__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_employee_employees_controller_js__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _employee_employee_service_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./employee/employee-service.js */ "./src/employee/employee-service.js");
/* harmony import */ var _employee_employee_service_js__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_employee_employee_service_js__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _employee_employee_content_controller_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./employee/employee-content-controller.js */ "./src/employee/employee-content-controller.js");
/* harmony import */ var _employee_employee_content_controller_js__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(_employee_employee_content_controller_js__WEBPACK_IMPORTED_MODULE_3__);
/* harmony import */ var _employee_employee_controller_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./employee/employee-controller.js */ "./src/employee/employee-controller.js");
/* harmony import */ var _employee_employee_controller_js__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(_employee_employee_controller_js__WEBPACK_IMPORTED_MODULE_4__);
/* harmony import */ var _employee_employee_http_service_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./employee/employee-http-service.js */ "./src/employee/employee-http-service.js");
/* harmony import */ var _employee_employee_http_service_js__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(_employee_employee_http_service_js__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var _employee_employee_directives_js__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./employee/employee-directives.js */ "./src/employee/employee-directives.js");
/* harmony import */ var _employee_employee_directives_js__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(_employee_employee_directives_js__WEBPACK_IMPORTED_MODULE_6__);
/* harmony import */ var _employee_employee_interaction_with_server_js__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./employee/employee-interaction-with-server.js */ "./src/employee/employee-interaction-with-server.js");
/* harmony import */ var _employee_employee_interaction_with_server_js__WEBPACK_IMPORTED_MODULE_7___default = /*#__PURE__*/__webpack_require__.n(_employee_employee_interaction_with_server_js__WEBPACK_IMPORTED_MODULE_7__);
/* harmony import */ var _employee_employee_processing_js__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./employee/employee-processing.js */ "./src/employee/employee-processing.js");
/* harmony import */ var _employee_employee_processing_js__WEBPACK_IMPORTED_MODULE_8___default = /*#__PURE__*/__webpack_require__.n(_employee_employee_processing_js__WEBPACK_IMPORTED_MODULE_8__);
/* harmony import */ var _document_document_controllers_js__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./document/document-controllers.js */ "./src/document/document-controllers.js");
/* harmony import */ var _document_document_controllers_js__WEBPACK_IMPORTED_MODULE_9___default = /*#__PURE__*/__webpack_require__.n(_document_document_controllers_js__WEBPACK_IMPORTED_MODULE_9__);
/* harmony import */ var _document_document_service_js__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./document/document-service.js */ "./src/document/document-service.js");
/* harmony import */ var _document_document_service_js__WEBPACK_IMPORTED_MODULE_10___default = /*#__PURE__*/__webpack_require__.n(_document_document_service_js__WEBPACK_IMPORTED_MODULE_10__);
/* harmony import */ var _document_document_http_service_js__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./document/document-http-service.js */ "./src/document/document-http-service.js");
/* harmony import */ var _document_document_http_service_js__WEBPACK_IMPORTED_MODULE_11___default = /*#__PURE__*/__webpack_require__.n(_document_document_http_service_js__WEBPACK_IMPORTED_MODULE_11__);
/* harmony import */ var _document_document_directives_js__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./document/document-directives.js */ "./src/document/document-directives.js");
/* harmony import */ var _document_document_directives_js__WEBPACK_IMPORTED_MODULE_12___default = /*#__PURE__*/__webpack_require__.n(_document_document_directives_js__WEBPACK_IMPORTED_MODULE_12__);
/* harmony import */ var _document_document_interaction_with_server_js__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! ./document/document-interaction-with-server.js */ "./src/document/document-interaction-with-server.js");
/* harmony import */ var _document_document_interaction_with_server_js__WEBPACK_IMPORTED_MODULE_13___default = /*#__PURE__*/__webpack_require__.n(_document_document_interaction_with_server_js__WEBPACK_IMPORTED_MODULE_13__);
/* harmony import */ var _document_document_processing_js__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! ./document/document-processing.js */ "./src/document/document-processing.js");
/* harmony import */ var _document_document_processing_js__WEBPACK_IMPORTED_MODULE_14___default = /*#__PURE__*/__webpack_require__.n(_document_document_processing_js__WEBPACK_IMPORTED_MODULE_14__);
/* harmony import */ var _js_controllers_modal_instance_controller_js__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! ./js/controllers/modal-instance-controller.js */ "./src/js/controllers/modal-instance-controller.js");
/* harmony import */ var _js_controllers_modal_instance_controller_js__WEBPACK_IMPORTED_MODULE_15___default = /*#__PURE__*/__webpack_require__.n(_js_controllers_modal_instance_controller_js__WEBPACK_IMPORTED_MODULE_15__);
/* harmony import */ var _js_controllers_initialization_controller_js__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! ./js/controllers/initialization-controller.js */ "./src/js/controllers/initialization-controller.js");
/* harmony import */ var _js_controllers_initialization_controller_js__WEBPACK_IMPORTED_MODULE_16___default = /*#__PURE__*/__webpack_require__.n(_js_controllers_initialization_controller_js__WEBPACK_IMPORTED_MODULE_16__);
/* harmony import */ var _tabs_tab_controller_js__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ./tabs/tab-controller.js */ "./src/tabs/tab-controller.js");
/* harmony import */ var _tabs_tab_controller_js__WEBPACK_IMPORTED_MODULE_17___default = /*#__PURE__*/__webpack_require__.n(_tabs_tab_controller_js__WEBPACK_IMPORTED_MODULE_17__);
/* harmony import */ var _tabs_tab_service_js__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! ./tabs/tab-service.js */ "./src/tabs/tab-service.js");
/* harmony import */ var _tabs_tab_service_js__WEBPACK_IMPORTED_MODULE_18___default = /*#__PURE__*/__webpack_require__.n(_tabs_tab_service_js__WEBPACK_IMPORTED_MODULE_18__);
/* harmony import */ var _js_controllers_modal_view_controller_js__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! ./js/controllers/modal-view-controller.js */ "./src/js/controllers/modal-view-controller.js");
/* harmony import */ var _js_controllers_modal_view_controller_js__WEBPACK_IMPORTED_MODULE_19___default = /*#__PURE__*/__webpack_require__.n(_js_controllers_modal_view_controller_js__WEBPACK_IMPORTED_MODULE_19__);
/* harmony import */ var _js_directives_directives_js__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! ./js/directives/directives.js */ "./src/js/directives/directives.js");
/* harmony import */ var _js_directives_directives_js__WEBPACK_IMPORTED_MODULE_20___default = /*#__PURE__*/__webpack_require__.n(_js_directives_directives_js__WEBPACK_IMPORTED_MODULE_20__);
/* harmony import */ var _post_post_service_js__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! ./post/post-service.js */ "./src/post/post-service.js");
/* harmony import */ var _post_post_service_js__WEBPACK_IMPORTED_MODULE_21___default = /*#__PURE__*/__webpack_require__.n(_post_post_service_js__WEBPACK_IMPORTED_MODULE_21__);
/* harmony import */ var _post_post_http_service_js__WEBPACK_IMPORTED_MODULE_22__ = __webpack_require__(/*! ./post/post-http-service.js */ "./src/post/post-http-service.js");
/* harmony import */ var _post_post_http_service_js__WEBPACK_IMPORTED_MODULE_22___default = /*#__PURE__*/__webpack_require__.n(_post_post_http_service_js__WEBPACK_IMPORTED_MODULE_22__);
/* harmony import */ var _department_department_service_js__WEBPACK_IMPORTED_MODULE_23__ = __webpack_require__(/*! ./department/department-service.js */ "./src/department/department-service.js");
/* harmony import */ var _department_department_service_js__WEBPACK_IMPORTED_MODULE_23___default = /*#__PURE__*/__webpack_require__.n(_department_department_service_js__WEBPACK_IMPORTED_MODULE_23__);
/* harmony import */ var _department_department_http_service_js__WEBPACK_IMPORTED_MODULE_24__ = __webpack_require__(/*! ./department/department-http-service.js */ "./src/department/department-http-service.js");
/* harmony import */ var _department_department_http_service_js__WEBPACK_IMPORTED_MODULE_24___default = /*#__PURE__*/__webpack_require__.n(_department_department_http_service_js__WEBPACK_IMPORTED_MODULE_24__);

























})();

/******/ })()
;
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiLi9kaXN0L21haW4uanMiLCJtYXBwaW5ncyI6Ijs7Ozs7Ozs7O0FBQUE7QUFDQSxpREFBaUQsVUFBVTtBQUMzRCxtQkFBbUIsMERBQTBEO0FBQzdFLG1CQUFtQixtREFBbUQ7QUFDdEUsaUJBQWlCO0FBQ2pCLEtBQUs7QUFDTCxDQUFDOzs7Ozs7Ozs7O0FDTkQ7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7Ozs7Ozs7Ozs7O0FDdEJEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsS0FBSztBQUNMO0FBQ0E7QUFDQSxDQUFDOzs7Ozs7Ozs7O0FDYkQ7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQztBQUNEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxnQkFBZ0IsY0FBYztBQUM5QjtBQUNBO0FBQ0EsQ0FBQztBQUNEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQzs7Ozs7Ozs7OztBQ3ZCRDtBQUNBLGlEQUFpRCxVQUFVO0FBQzNELG1CQUFtQix3REFBd0Q7QUFDM0UsbUJBQW1CLGlEQUFpRDtBQUNwRSxpQkFBaUIsZ0RBQWdEO0FBQ2pFLGtDQUFrQyx5RUFBeUU7QUFDM0csZ0NBQWdDO0FBQ2hDLEtBQUs7QUFDTCxDQUFDOzs7Ozs7Ozs7O0FDUkQ7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0EsNkRBQTZELGNBQWM7QUFDM0U7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7Ozs7Ozs7Ozs7QUN0QkQ7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSx5QkFBeUI7QUFDekI7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSx5QkFBeUI7QUFDekI7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSx5QkFBeUI7QUFDekI7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDOzs7Ozs7Ozs7O0FDL0JEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLGFBQWE7QUFDYixTQUFTO0FBQ1Q7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDO0FBQ0Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDO0FBQ0Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDOzs7Ozs7Ozs7O0FDM0VEO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsS0FBSztBQUNMLENBQUM7Ozs7Ozs7Ozs7QUNMRDtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7Ozs7Ozs7Ozs7QUNKRDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDO0FBQ0Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLGdCQUFnQixjQUFjO0FBQzlCO0FBQ0E7QUFDQSxDQUFDO0FBQ0Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDOzs7Ozs7Ozs7O0FDdkJEO0FBQ0EsNkNBQTZDLFVBQVU7QUFDdkQsbUJBQW1CLHNEQUFzRDtBQUN6RSxtQkFBbUIsK0NBQStDO0FBQ2xFLGlCQUFpQjtBQUNqQixLQUFLO0FBQ0wsQ0FBQzs7Ozs7Ozs7OztBQ05EO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsYUFBYTtBQUNiLFNBQVM7QUFDVDtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQSw2REFBNkQsY0FBYztBQUMzRTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQzs7Ozs7Ozs7OztBQ3ZDRDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLHlCQUF5QjtBQUN6QjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLHlCQUF5QjtBQUN6QjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLHlCQUF5QjtBQUN6QjtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7Ozs7Ozs7Ozs7QUMvQkQ7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQzs7Ozs7Ozs7OztBQzVCRDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxLQUFLO0FBQ0wsQ0FBQzs7Ozs7Ozs7Ozs7Ozs7O0FDVk07QUFDUDtBQUNBO0FBQ0EsQ0FBQztBQUNEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTs7Ozs7Ozs7OztBQ1RBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxLQUFLO0FBQ0w7QUFDQTtBQUNBLENBQUM7Ozs7Ozs7Ozs7O0FDekNEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsVUFBVTtBQUNWO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDOzs7Ozs7Ozs7O0FDM0NEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFVBQVU7QUFDVjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsVUFBVTtBQUNWO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsNkNBQTZDO0FBQzdDO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQSxjQUFjO0FBQ2Q7QUFDQTtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsNEJBQTRCO0FBQzVCO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQSxDQUFDOzs7Ozs7Ozs7O0FDckZEO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7QUFDRDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7QUFDRDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLENBQUM7QUFDRDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsYUFBYTtBQUNiO0FBQ0E7QUFDQSxDQUFDOzs7Ozs7Ozs7O0FDbENEO0FBQ0EsMkNBQTJDLFVBQVU7QUFDckQsbUJBQW1CLG9EQUFvRDtBQUN2RSxtQkFBbUIsNkNBQTZDO0FBQ2hFLGlCQUFpQjtBQUNqQixLQUFLO0FBQ0wsQ0FBQzs7Ozs7Ozs7Ozs7QUNORDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQzs7Ozs7Ozs7OztBQ3RCRDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsS0FBSztBQUNMO0FBQ0E7QUFDQTtBQUNBLEtBQUs7QUFDTDtBQUNBO0FBQ0E7QUFDQTtBQUNBLEtBQUs7QUFDTDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFVBQVU7QUFDVjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsYUFBYTtBQUNiLFVBQVU7QUFDVjtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0EsQ0FBQzs7Ozs7Ozs7OztBQ3JERDtBQUNBO0FBQ0EsZ0JBQWdCO0FBQ2hCO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0E7QUFDQSxjQUFjO0FBQ2Q7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0EsU0FBUztBQUNUO0FBQ0E7QUFDQSxTQUFTO0FBQ1Q7QUFDQTtBQUNBO0FBQ0E7QUFDQSxjQUFjO0FBQ2Q7QUFDQTtBQUNBLFNBQVM7QUFDVDtBQUNBO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLHNCQUFzQjtBQUN0QjtBQUNBO0FBQ0Esa0JBQWtCO0FBQ2xCO0FBQ0Esa0JBQWtCO0FBQ2xCO0FBQ0E7QUFDQSxjQUFjO0FBQ2Q7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLHNCQUFzQjtBQUN0QjtBQUNBO0FBQ0Esa0JBQWtCO0FBQ2xCO0FBQ0Esa0JBQWtCO0FBQ2xCO0FBQ0E7QUFDQTtBQUNBO0FBQ0E7QUFDQSxDQUFDOzs7Ozs7VUMvREQ7VUFDQTs7VUFFQTtVQUNBO1VBQ0E7VUFDQTtVQUNBO1VBQ0E7VUFDQTtVQUNBO1VBQ0E7VUFDQTtVQUNBO1VBQ0E7VUFDQTs7VUFFQTtVQUNBOztVQUVBO1VBQ0E7VUFDQTs7Ozs7V0N0QkE7V0FDQTtXQUNBO1dBQ0E7V0FDQTtXQUNBLGlDQUFpQyxXQUFXO1dBQzVDO1dBQ0E7Ozs7O1dDUEE7V0FDQTtXQUNBO1dBQ0E7V0FDQSx5Q0FBeUMsd0NBQXdDO1dBQ2pGO1dBQ0E7V0FDQTs7Ozs7V0NQQTs7Ozs7V0NBQTtXQUNBO1dBQ0E7V0FDQSx1REFBdUQsaUJBQWlCO1dBQ3hFO1dBQ0EsZ0RBQWdELGFBQWE7V0FDN0Q7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7QUNOOEI7QUFDYztBQUNKO0FBQ1c7QUFDUjtBQUNFO0FBQ0Y7QUFDYTtBQUNiO0FBQ0M7QUFDSjtBQUNLO0FBQ0Y7QUFDYTtBQUNiO0FBQ1k7QUFDQTtBQUNyQjtBQUNIO0FBQ29CO0FBQ1o7QUFDUDtBQUNLO0FBQ08iLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly9kb2N1bWVudGZsb3cvLi9zcmMvZGVwYXJ0bWVudC9kZXBhcnRtZW50LWh0dHAtc2VydmljZS5qcyIsIndlYnBhY2s6Ly9kb2N1bWVudGZsb3cvLi9zcmMvZGVwYXJ0bWVudC9kZXBhcnRtZW50LXNlcnZpY2UuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2RvY3VtZW50L2RvY3VtZW50LWNvbnRyb2xsZXJzLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9kb2N1bWVudC9kb2N1bWVudC1kaXJlY3RpdmVzLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9kb2N1bWVudC9kb2N1bWVudC1odHRwLXNlcnZpY2UuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2RvY3VtZW50L2RvY3VtZW50LWludGVyYWN0aW9uLXdpdGgtc2VydmVyLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9kb2N1bWVudC9kb2N1bWVudC1wcm9jZXNzaW5nLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9kb2N1bWVudC9kb2N1bWVudC1zZXJ2aWNlLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9lbXBsb3llZS9lbXBsb3llZS1jb250ZW50LWNvbnRyb2xsZXIuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2VtcGxveWVlL2VtcGxveWVlLWNvbnRyb2xsZXIuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2VtcGxveWVlL2VtcGxveWVlLWRpcmVjdGl2ZXMuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2VtcGxveWVlL2VtcGxveWVlLWh0dHAtc2VydmljZS5qcyIsIndlYnBhY2s6Ly9kb2N1bWVudGZsb3cvLi9zcmMvZW1wbG95ZWUvZW1wbG95ZWUtaW50ZXJhY3Rpb24td2l0aC1zZXJ2ZXIuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2VtcGxveWVlL2VtcGxveWVlLXByb2Nlc3NpbmcuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2VtcGxveWVlL2VtcGxveWVlLXNlcnZpY2UuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2VtcGxveWVlL2VtcGxveWVlcy1jb250cm9sbGVyLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9qcy9hbmd1bGFyLW1haW4uanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2pzL2NvbnRyb2xsZXJzL2luaXRpYWxpemF0aW9uLWNvbnRyb2xsZXIuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2pzL2NvbnRyb2xsZXJzL21vZGFsLWluc3RhbmNlLWNvbnRyb2xsZXIuanMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2pzL2NvbnRyb2xsZXJzL21vZGFsLXZpZXctY29udHJvbGxlci5qcyIsIndlYnBhY2s6Ly9kb2N1bWVudGZsb3cvLi9zcmMvanMvZGlyZWN0aXZlcy9kaXJlY3RpdmVzLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9wb3N0L3Bvc3QtaHR0cC1zZXJ2aWNlLmpzIiwid2VicGFjazovL2RvY3VtZW50Zmxvdy8uL3NyYy9wb3N0L3Bvc3Qtc2VydmljZS5qcyIsIndlYnBhY2s6Ly9kb2N1bWVudGZsb3cvLi9zcmMvdGFicy90YWItY29udHJvbGxlci5qcyIsIndlYnBhY2s6Ly9kb2N1bWVudGZsb3cvLi9zcmMvdGFicy90YWItc2VydmljZS5qcyIsIndlYnBhY2s6Ly9kb2N1bWVudGZsb3cvd2VicGFjay9ib290c3RyYXAiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93L3dlYnBhY2svcnVudGltZS9jb21wYXQgZ2V0IGRlZmF1bHQgZXhwb3J0Iiwid2VicGFjazovL2RvY3VtZW50Zmxvdy93ZWJwYWNrL3J1bnRpbWUvZGVmaW5lIHByb3BlcnR5IGdldHRlcnMiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93L3dlYnBhY2svcnVudGltZS9oYXNPd25Qcm9wZXJ0eSBzaG9ydGhhbmQiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93L3dlYnBhY2svcnVudGltZS9tYWtlIG5hbWVzcGFjZSBvYmplY3QiLCJ3ZWJwYWNrOi8vZG9jdW1lbnRmbG93Ly4vc3JjL2luZGV4LmpzIl0sInNvdXJjZXNDb250ZW50IjpbImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5mYWN0b3J5KCdEZXBhcnRtZW50SHR0cFNlcnZpY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlLCBBUElfUEFUSCkge1xyXG4gICAgcmV0dXJuICRyZXNvdXJjZShBUElfUEFUSCsnZGVwYXJ0bWVudC86aWQnLCB7aWQ6ICdAaWQnfSwge1xyXG4gICAgICAgICdkZWxldGUnOiB7bWV0aG9kOiAnREVMRVRFJywgdXJsOiBBUElfUEFUSCArICdkZXBhcnRtZW50L2RlbGV0ZS86aWQnfSxcclxuICAgICAgICAndXBkYXRlJzoge21ldGhvZDogJ1BVVCcsIHVybDogQVBJX1BBVEggKyAnZGVwYXJ0bWVudC91cGRhdGUnfSxcclxuICAgICAgICAncG9zdCc6IHttZXRob2Q6ICdQT1NUJywgdXJsOiBBUElfUEFUSCArICdkZXBhcnRtZW50L3NhdmUnfVxyXG4gICAgfSk7XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5mYWN0b3J5KCdEZXBhcnRtZW50U2VydmljZScsIGZ1bmN0aW9uICgpIHtcclxuICAgIGxldCBkZXBhcnRtZW50cyA9IFtdO1xyXG5cclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgZ2V0QWxsOiBmdW5jdGlvbiAoKSB7XHJcbiAgICAgICAgICAgIHJldHVybiBkZXBhcnRtZW50cztcclxuICAgICAgICB9LFxyXG4gICAgICAgIGFkZDogZnVuY3Rpb24gKGRlcGFydG1lbnQpIHtcclxuICAgICAgICAgICAgaWYgKHRoaXMuZ2V0QnlJZChkZXBhcnRtZW50LmlkKSA9PSBudWxsKSB7XHJcbiAgICAgICAgICAgICAgICBkZXBhcnRtZW50cy5wdXNoKGRlcGFydG1lbnQpO1xyXG4gICAgICAgICAgICB9XHJcbiAgICAgICAgfSxcclxuICAgICAgICBnZXRCeUlkOiBmdW5jdGlvbiAoaWQpIHtcclxuICAgICAgICAgICAgcmV0dXJuIGRlcGFydG1lbnRzLmZpbmQoZGVwYXJ0bWVudCA9PiBkZXBhcnRtZW50LmlkID09PSBpZCk7XHJcbiAgICAgICAgfSxcclxuICAgICAgICBzZXRBbGw6IGZ1bmN0aW9uIChkZXBhcnRtZW50TGlzdCkge1xyXG4gICAgICAgICAgICBkZXBhcnRtZW50cyA9IGRlcGFydG1lbnRMaXN0O1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgZGVsZXRlOiBmdW5jdGlvbiAoZGVwYXJ0bWVudCkge1xyXG4gICAgICAgICAgICBkZXBhcnRtZW50cyA9IGRlcGFydG1lbnRzLmZpbHRlcihkID0+IGQuaWQgIT09IGRlcGFydG1lbnQuaWQpO1xyXG4gICAgICAgIH1cclxuICAgIH07XHJcbn0pO1xyXG4iLCJhbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuY29udHJvbGxlcignZG9jdW1lbnRzQ29udHJvbGxlcicsIGZ1bmN0aW9uICgkcm9vdFNjb3BlLCBEb2N1bWVudFNlcnZpY2UsIEVWRU5UX1VQREFURV9ET0NVTUVOVFMsIEVWRU5UX09QRU5fVEFCKSB7XHJcbiAgICBjb25zdCBzY29wZSA9IHRoaXM7XHJcblxyXG4gICAgJHJvb3RTY29wZS5PcGVuVGFiID0gZnVuY3Rpb24gKGRvY3VtZW50KSB7XHJcbiAgICAgICAgJHJvb3RTY29wZS4kYnJvYWRjYXN0KEVWRU5UX09QRU5fVEFCLCBkb2N1bWVudCk7XHJcbiAgICB9XHJcbiAgICBzY29wZS5kb2N1bWVudHMgPSBEb2N1bWVudFNlcnZpY2UuZ2V0QWxsKCk7XHJcblxyXG4gICAgJHJvb3RTY29wZS4kb24oRVZFTlRfVVBEQVRFX0RPQ1VNRU5UUywgZnVuY3Rpb24gKGV2ZW50KSB7XHJcbiAgICAgICAgc2NvcGUuZG9jdW1lbnRzID0gRG9jdW1lbnRTZXJ2aWNlLmdldEFsbCgpO1xyXG4gICAgfSk7XHJcblxyXG4gICAgc2NvcGUuc29ydFBhcmFtID0gJ3JlZ2lzdHJhdGlvbkRhdGUnO1xyXG59KTsiLCJhbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZGlyZWN0aXZlKCdkb2N1bWVudHNUYWJsZScsIGZ1bmN0aW9uICgpIHtcclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgcmVzdHJpY3Q6ICdFJyxcclxuICAgICAgICB0cmFuc2NsdWRlOiB0cnVlLFxyXG4gICAgICAgIHRlbXBsYXRlVXJsOiAnc3JjL2RvY3VtZW50L2RvY3VtZW50cy10YWJsZS5odG1sJ1xyXG4gICAgfTtcclxufSk7XHJcblxyXG5hbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZGlyZWN0aXZlKCdkb2N1bWVudFRhYicsIGZ1bmN0aW9uICgpIHtcclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgcmVzdHJpY3Q6ICdFJyxcclxuICAgICAgICB0cmFuc2NsdWRlOiB0cnVlLFxyXG4gICAgICAgIHNjb3BlOiB7ZG9jdW1lbnQ6ICc9J30sXHJcbiAgICAgICAgdGVtcGxhdGVVcmw6ICdzcmMvZG9jdW1lbnQvZG9jdW1lbnQtaW5mby5odG1sJ1xyXG4gICAgfTtcclxufSk7XHJcblxyXG5hbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZGlyZWN0aXZlKCdkb2N1bWVudEZvcm0nLCBmdW5jdGlvbiAoKSB7XHJcbiAgICByZXR1cm4ge1xyXG4gICAgICAgIHJlc3RyaWN0OiAnRScsXHJcbiAgICAgICAgdHJhbnNjbHVkZTogZmFsc2UsXHJcbiAgICAgICAgdGVtcGxhdGVVcmw6ICdzcmMvZG9jdW1lbnQvZG9jdW1lbnQtZm9ybS5odG1sJ1xyXG4gICAgfVxyXG59KTsiLCJhbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZmFjdG9yeSgnRG9jdW1lbnRIdHRwU2VydmljZScsIGZ1bmN0aW9uICgkcmVzb3VyY2UsIEFQSV9QQVRIKSB7XHJcbiAgICByZXR1cm4gJHJlc291cmNlKEFQSV9QQVRIICsgJ2RvY3VtZW50LzppZCcsIHtpZDogJ0BpZCd9LCB7XHJcbiAgICAgICAgJ2RlbGV0ZSc6IHttZXRob2Q6ICdERUxFVEUnLCB1cmw6IEFQSV9QQVRIICsgJ2RvY3VtZW50L2RlbGV0ZS86aWQnfSxcclxuICAgICAgICAndXBkYXRlJzoge21ldGhvZDogJ1BVVCcsIHVybDogQVBJX1BBVEggKyAnZG9jdW1lbnQvdXBkYXRlJ30sXHJcbiAgICAgICAgJ3Bvc3QnOiB7bWV0aG9kOiAnUE9TVCcsIHVybDogQVBJX1BBVEggKyAnZG9jdW1lbnQvc2F2ZSd9LFxyXG4gICAgICAgICdnZXRBbGxEZWxpdmVyeU1ldGhvZHMnOiB7bWV0aG9kOiAnR0VUJywgdXJsOiBBUElfUEFUSCArICdkb2N1bWVudC9kZWxpdmVyeS1tZXRob2QnLCBpc0FycmF5OiB0cnVlfSxcclxuICAgICAgICAnZ2V0QWxsRG9jdW1lbnRUeXBlcyc6IHttZXRob2Q6ICdHRVQnLCB1cmw6IEFQSV9QQVRIICsgJ2RvY3VtZW50L2RvY3VtZW50LXR5cGUnLCBpc0FycmF5OiB0cnVlfVxyXG4gICAgfSk7XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5mYWN0b3J5KCdEb2N1bWVudEludGVyYWN0aW9uV2l0aFNlcnZlcicsIGZ1bmN0aW9uICgkcm9vdFNjb3BlLCBEb2N1bWVudFNlcnZpY2UsIERvY3VtZW50SHR0cFNlcnZpY2UpIHtcclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgc2F2ZTogYXN5bmMgZnVuY3Rpb24gKG9iamVjdCkge1xyXG4gICAgICAgICAgICBsZXQgcmVzcG9uc2UgPSBhd2FpdCBEb2N1bWVudEh0dHBTZXJ2aWNlLnBvc3Qob2JqZWN0KS4kcHJvbWlzZTtcclxuICAgICAgICAgICAgb2JqZWN0ID0gcmVzcG9uc2U7XHJcbiAgICAgICAgICAgIERvY3VtZW50U2VydmljZS5hZGQob2JqZWN0KTtcclxuICAgICAgICAgICAgJHJvb3RTY29wZS5VcGRhdGVEb2N1bWVudHMoKTtcclxuICAgICAgICB9LFxyXG4gICAgICAgIHVwZGF0ZTogYXN5bmMgZnVuY3Rpb24gKG9iamVjdCkge1xyXG4gICAgICAgICAgICBsZXQgcmVzcG9uc2UgPSBhd2FpdCBEb2N1bWVudEh0dHBTZXJ2aWNlLnVwZGF0ZShvYmplY3QpLiRwcm9taXNlO1xyXG4gICAgICAgICAgICBvYmplY3QgPSByZXNwb25zZTtcclxuICAgICAgICAgICAgRG9jdW1lbnRTZXJ2aWNlLnVwZGF0ZShvYmplY3QpO1xyXG4gICAgICAgICAgICAkcm9vdFNjb3BlLlVwZGF0ZURvY3VtZW50cygpO1xyXG4gICAgICAgICAgICAkcm9vdFNjb3BlLlVwZGF0ZVRhYihvYmplY3QpO1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgZGVsZXRlOiBhc3luYyBmdW5jdGlvbiAob2JqZWN0KSB7XHJcbiAgICAgICAgICAgIGxldCByZXNwb25zZSA9IGF3YWl0IERvY3VtZW50SHR0cFNlcnZpY2UuZGVsZXRlKHtpZDogb2JqZWN0LmlkfSkuJHByb21pc2U7XHJcbiAgICAgICAgICAgIERvY3VtZW50U2VydmljZS5kZWxldGUob2JqZWN0KTtcclxuICAgICAgICAgICAgJHJvb3RTY29wZS5DbG9zZVRhYihvYmplY3QpO1xyXG4gICAgICAgICAgICAkcm9vdFNjb3BlLlVwZGF0ZURvY3VtZW50cygpO1xyXG4gICAgICAgIH1cclxuICAgIH1cclxufSk7IiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmZhY3RvcnkoJ1Byb2Nlc3NpbmdEb2N1bWVudCcsIGZ1bmN0aW9uIChEb2N1bWVudEludGVyYWN0aW9uV2l0aFNlcnZlcikge1xyXG4gICAgcmV0dXJuIHtcclxuICAgICAgICBwcm9jZXNzaW5nRG9jdW1lbnQ6IGZ1bmN0aW9uIChtZXRob2QsIG9iamVjdCkge1xyXG4gICAgICAgICAgICBzd2l0Y2ggKG1ldGhvZCkge1xyXG4gICAgICAgICAgICAgICAgY2FzZSAnUE9TVCc6XHJcbiAgICAgICAgICAgICAgICAgICAgRG9jdW1lbnRJbnRlcmFjdGlvbldpdGhTZXJ2ZXIuc2F2ZShvYmplY3QpXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIC5jYXRjaChmdW5jdGlvbiAoZXJyb3IpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIGFsZXJ0KCfQndC1INGD0LTQsNC70L7RgdGMINGB0L7Qt9C00LDRgtGMINC00L7QutGD0LzQtdC90YLQsCDihJYgJyArIG9iamVjdC5yZWdpc3RyYXRpb25OdW1iZXIgK1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICdcXG7QodC+0L7QsdGJ0LXQvdC40LU6ICcgKyBlcnJvci5tZXNzYWdlICsgJ1xcbtCf0L7QtNGA0L7QsdC90LXQtSDRgdC80L7RgtGA0Lgg0LIg0LvQvtCz0LDRhScpO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgY29uc29sZS5sb2coJ2Vycm9yczogJyArIGVycm9yKTtcclxuICAgICAgICAgICAgICAgICAgICAgICAgfSk7XHJcbiAgICAgICAgICAgICAgICAgICAgYnJlYWs7XHJcbiAgICAgICAgICAgICAgICBjYXNlICdQVVQnOlxyXG4gICAgICAgICAgICAgICAgICAgIERvY3VtZW50SW50ZXJhY3Rpb25XaXRoU2VydmVyLnVwZGF0ZShvYmplY3QpXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIC5jYXRjaChmdW5jdGlvbiAoZXJyb3IpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIGFsZXJ0KCfQndC1INGD0LTQsNC70L7RgdGMINCy0YvQv9C+0LvQvdC40YLRjCDQvtCx0L3QvtCy0LvQtdC90LjQtSDQtNC+0LrRg9C80LXQvdGC0LAg4oSWICcgKyBvYmplY3QucmVnaXN0cmF0aW9uTnVtYmVyICtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAnXFxu0KHQvtC+0LHRidC10L3QuNC1OiAnICsgZXJyb3IubWVzc2FnZSArICdcXG7Qn9C+0LTRgNC+0LHQvdC10LUg0YHQvNC+0YLRgNC4INCyINC70L7Qs9Cw0YUnKTtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIGNvbnNvbGUubG9nKCdlcnJvcnM6ICcgKyBlcnJvcik7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgIH0pO1xyXG4gICAgICAgICAgICAgICAgICAgIGJyZWFrO1xyXG4gICAgICAgICAgICAgICAgY2FzZSAnREVMRVRFJzpcclxuICAgICAgICAgICAgICAgICAgICBEb2N1bWVudEludGVyYWN0aW9uV2l0aFNlcnZlci5kZWxldGUob2JqZWN0KVxyXG4gICAgICAgICAgICAgICAgICAgICAgICAuY2F0Y2goZnVuY3Rpb24gKGVycm9yKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICBhbGVydCgn0J3QtSDRg9C00LDQu9C+0YHRjCDQstGL0L/QvtC70L3QuNGC0Ywg0YPQtNCw0LvQtdC90LjQtSDQtNC+0LrRg9C80LXQvdGC0LAg4oSWJyArIG9iamVjdC5yZWdpc3RyYXRpb25OdW1iZXIgK1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICdcXG7QodC+0L7QsdGJ0LXQvdC40LU6ICcgKyBlcnJvci5tZXNzYWdlICsgJ1xcbtCf0L7QtNGA0L7QsdC90LXQtSDRgdC80L7RgtGA0Lgg0LIg0LvQvtCz0LDRhScpO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgY29uc29sZS5sb2coJ2Vycm9yczogJyArIGVycm9yKTtcclxuICAgICAgICAgICAgICAgICAgICAgICAgfSk7XHJcbiAgICAgICAgICAgICAgICAgICAgYnJlYWs7XHJcbiAgICAgICAgICAgIH1cclxuICAgICAgICB9XHJcbiAgICB9XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5mYWN0b3J5KCdEb2N1bWVudFNlcnZpY2UnLCBmdW5jdGlvbiAoRW1wbG95ZWVTZXJ2aWNlKSB7XHJcbiAgICBsZXQgZG9jdW1lbnRzID0gW107XHJcblxyXG4gICAgcmV0dXJuIHtcclxuICAgICAgICBnZXRBbGw6IGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgcmV0dXJuIGRvY3VtZW50cztcclxuICAgICAgICB9LFxyXG4gICAgICAgIGZpbmRCeUF1dGhvcjogZnVuY3Rpb24gKGlkQXV0aG9yKSB7XHJcbiAgICAgICAgICAgIHJldHVybiBkb2N1bWVudHMuZmlsdGVyKGRvY3VtZW50ID0+IGRvY3VtZW50LmF1dGhvci5pZCA9PT0gaWRBdXRob3IpO1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgdXBkYXRlOiBmdW5jdGlvbiAoZG9jdW1lbnQpIHtcclxuICAgICAgICAgICAgbGV0IGluZGV4ID0gZG9jdW1lbnRzLmZpbmRJbmRleChkb2MgPT4gZG9jLmlkID09PSBkb2N1bWVudC5pZCk7XHJcbiAgICAgICAgICAgIGRvY3VtZW50c1tpbmRleF0gPSBkb2N1bWVudDtcclxuICAgICAgICB9LFxyXG4gICAgICAgIGRlbGV0ZTogZnVuY3Rpb24gKGVtcGxveWVlKSB7XHJcbiAgICAgICAgICAgIGRvY3VtZW50cyA9IGRvY3VtZW50cy5maWx0ZXIoZG9jdW1lbnQgPT4gZG9jdW1lbnQuaWQgIT09IGVtcGxveWVlLmlkKTtcclxuICAgICAgICB9LFxyXG4gICAgICAgIHNldEFsbDogZnVuY3Rpb24gKGRvY3VtZW50TGlzdCkge1xyXG4gICAgICAgICAgICBkb2N1bWVudHMgPSBkb2N1bWVudExpc3Q7XHJcbiAgICAgICAgICAgIGRvY3VtZW50cy5mb3JFYWNoKGRvYyA9PiB7XHJcbiAgICAgICAgICAgICAgICBkb2MuYXV0aG9yID0gRW1wbG95ZWVTZXJ2aWNlLmdldEJ5SWQoZG9jLmF1dGhvci5pZCk7XHJcbiAgICAgICAgICAgICAgICBpZiAoZG9jLmNvbnRyb2xsZXIgIT09IHVuZGVmaW5lZCkge1xyXG4gICAgICAgICAgICAgICAgICAgIGRvYy5jb250cm9sbGVyID0gRW1wbG95ZWVTZXJ2aWNlLmdldEJ5SWQoZG9jLmNvbnRyb2xsZXIuaWQpO1xyXG4gICAgICAgICAgICAgICAgICAgIGRvYy5kb2N1bWVudFR5cGUgPSAnVEFTS19ET0NVTUVOVCc7XHJcbiAgICAgICAgICAgICAgICB9XHJcbiAgICAgICAgICAgICAgICBpZiAoZG9jLmV4ZWN1dG9yICE9PSB1bmRlZmluZWQpIHtcclxuICAgICAgICAgICAgICAgICAgICBkb2MuZXhlY3V0b3IgPSBFbXBsb3llZVNlcnZpY2UuZ2V0QnlJZChkb2MuZXhlY3V0b3IuaWQpO1xyXG4gICAgICAgICAgICAgICAgfVxyXG4gICAgICAgICAgICAgICAgaWYgKGRvYy5yZWNpcGllbnQgIT09IHVuZGVmaW5lZCkge1xyXG4gICAgICAgICAgICAgICAgICAgIGRvYy5yZWNpcGllbnQgPSBFbXBsb3llZVNlcnZpY2UuZ2V0QnlJZChkb2MucmVjaXBpZW50LmlkKTtcclxuICAgICAgICAgICAgICAgICAgICBkb2MuZG9jdW1lbnRUeXBlID0gJ0lOQ09NSU5HX0RPQ1VNRU5UJztcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgICAgIGlmIChkb2Muc2VuZGVyICE9PSB1bmRlZmluZWQpIHtcclxuICAgICAgICAgICAgICAgICAgICBkb2Muc2VuZGVyID0gRW1wbG95ZWVTZXJ2aWNlLmdldEJ5SWQoZG9jLnNlbmRlci5pZCk7XHJcbiAgICAgICAgICAgICAgICB9XHJcbiAgICAgICAgICAgICAgICBpZiAoZG9jLmRlbGl2ZXJ5TWV0aG9kICE9PSB1bmRlZmluZWQpIHtcclxuICAgICAgICAgICAgICAgICAgICBkb2MuZG9jdW1lbnRUeXBlID0gJ09VVEdPSU5HX0RPQ1VNRU5UJztcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgfSk7XHJcbiAgICAgICAgfSxcclxuICAgICAgICBnZXRCeUlkOiBmdW5jdGlvbiAoaWQpIHtcclxuICAgICAgICAgICAgcmV0dXJuIGRvY3VtZW50cy5maW5kKGRvY3VtZW50ID0+IGRvY3VtZW50LmlkID09PSBpZCk7XHJcbiAgICAgICAgfSxcclxuICAgICAgICBhZGQ6IGZ1bmN0aW9uIChkb2N1bWVudCkge1xyXG4gICAgICAgICAgICBpZiAodGhpcy5nZXRCeUlkKGRvY3VtZW50LmlkKSA9PSBudWxsKSB7XHJcbiAgICAgICAgICAgICAgICBkb2N1bWVudHMucHVzaChkb2N1bWVudCk7XHJcbiAgICAgICAgICAgIH1cclxuICAgICAgICB9XHJcbiAgICB9O1xyXG59KTtcclxuXHJcbmFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5mYWN0b3J5KCdEZWxpdmVyeU1ldGhvZFNlcnZpY2UnLCBmdW5jdGlvbiAoKSB7XHJcbiAgICBsZXQgZGVsaXZlcnlNZXRob2RzID0gW107XHJcblxyXG4gICAgcmV0dXJuIHtcclxuICAgICAgICBnZXRBbGw6IGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgcmV0dXJuIGRlbGl2ZXJ5TWV0aG9kcztcclxuICAgICAgICB9LFxyXG4gICAgICAgIHNldEFsbDogZnVuY3Rpb24gKGRlbGl2ZXJ5TWV0aG9kTGlzdCkge1xyXG4gICAgICAgICAgICBkZWxpdmVyeU1ldGhvZHMgPSBkZWxpdmVyeU1ldGhvZExpc3Q7XHJcbiAgICAgICAgfVxyXG4gICAgfTtcclxufSk7XHJcblxyXG5hbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZmFjdG9yeSgnRG9jdW1lbnRUeXBlU2VydmljZScsIGZ1bmN0aW9uICgpIHtcclxuICAgIGxldCBkb2N1bWVudFR5cGUgPSBbXTtcclxuXHJcbiAgICByZXR1cm4ge1xyXG4gICAgICAgIGdldEFsbDogZnVuY3Rpb24gKCkge1xyXG4gICAgICAgICAgICByZXR1cm4gZG9jdW1lbnRUeXBlO1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgc2V0QWxsOiBmdW5jdGlvbiAoZG9jdW1lbnRUeXBlTGlzdCkge1xyXG4gICAgICAgICAgICBkb2N1bWVudFR5cGUgPSBkb2N1bWVudFR5cGVMaXN0O1xyXG4gICAgICAgIH1cclxuICAgIH07XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5jb250cm9sbGVyKCdlbXBsb3llZUNvbnRlbnRDb250cm9sbGVyJywgZnVuY3Rpb24gKCRyb290U2NvcGUsIEVtcGxveWVlU2VydmljZSwgRVZFTlRfVVBEQVRFX0VNUExPWUVFUykge1xyXG4gICAgY29uc3Qgc2NvcGUgPSB0aGlzO1xyXG4gICAgJHJvb3RTY29wZS4kb24oRVZFTlRfVVBEQVRFX0VNUExPWUVFUywgZnVuY3Rpb24gKGV2ZW50KSB7XHJcbiAgICAgICAgc2NvcGUuZW1wbG95ZWVzID0gRW1wbG95ZWVTZXJ2aWNlLmdldEFsbCgpO1xyXG4gICAgfSk7XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5jb250cm9sbGVyKCdlbXBsb3llZUNvbnRyb2xsZXInLCBmdW5jdGlvbiAoRG9jdW1lbnRTZXJ2aWNlKSB7XHJcbiAgICBsZXQgc2NvcGUgPSB0aGlzO1xyXG4gICAgc2NvcGUuZG9jdW1lbnRzID0gRG9jdW1lbnRTZXJ2aWNlLmZpbmRCeUF1dGhvcigpO1xyXG4gICAgc2NvcGUuc29ydFBhcmFtID0gJ3JlZ2lzdHJhdGlvbkRhdGUnO1xyXG59KTsiLCJhbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZGlyZWN0aXZlKCdlbXBsb3llZXNUYWJsZScsIGZ1bmN0aW9uICgpIHtcclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgcmVzdHJpY3Q6ICdFJyxcclxuICAgICAgICB0cmFuc2NsdWRlOiB0cnVlLFxyXG4gICAgICAgIHRlbXBsYXRlVXJsOiAnc3JjL2VtcGxveWVlL2VtcGxveWVlcy10YWJsZS5odG1sJ1xyXG4gICAgfTtcclxufSk7XHJcblxyXG5hbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZGlyZWN0aXZlKCdlbXBsb3llZUluZm8nLCBmdW5jdGlvbiAoKSB7XHJcbiAgICByZXR1cm4ge1xyXG4gICAgICAgIHJlc3RyaWN0OiAnRScsXHJcbiAgICAgICAgdHJhbnNjbHVkZTogdHJ1ZSxcclxuICAgICAgICBzY29wZToge2VtcGxveWVlOiAnPSd9LFxyXG4gICAgICAgIHRlbXBsYXRlVXJsOiAnc3JjL2VtcGxveWVlL2VtcGxveWVlLWluZm8uaHRtbCdcclxuICAgIH07XHJcbn0pO1xyXG5cclxuYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmRpcmVjdGl2ZSgnZW1wbG95ZWVGb3JtJywgZnVuY3Rpb24gKCkge1xyXG4gICAgcmV0dXJuIHtcclxuICAgICAgICByZXN0cmljdDogJ0UnLFxyXG4gICAgICAgIHRyYW5zY2x1ZGU6IGZhbHNlLFxyXG4gICAgICAgIHRlbXBsYXRlVXJsOiAnc3JjL2VtcGxveWVlL2VtcGxveWVlLWZvcm0uaHRtbCdcclxuICAgIH1cclxufSk7IiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmZhY3RvcnkoJ0VtcGxveWVlSHR0cFNlcnZpY2UnLCBmdW5jdGlvbiAoJHJlc291cmNlLCBBUElfUEFUSCkge1xyXG4gICAgcmV0dXJuICRyZXNvdXJjZShBUElfUEFUSCsncGVyc29uLzppZCcsIHtpZDogJ0BpZCd9LCB7XHJcbiAgICAgICAgJ2RlbGV0ZSc6IHttZXRob2Q6ICdERUxFVEUnLCB1cmw6IEFQSV9QQVRIICsgJ3BlcnNvbi9kZWxldGUvOmlkJ30sXHJcbiAgICAgICAgJ3VwZGF0ZSc6IHttZXRob2Q6ICdQVVQnLCB1cmw6IEFQSV9QQVRIICsgJ3BlcnNvbi91cGRhdGUnfSxcclxuICAgICAgICAncG9zdCc6IHttZXRob2Q6ICdQT1NUJywgdXJsOiBBUElfUEFUSCArICdwZXJzb24vc2F2ZSd9XHJcbiAgICB9KTtcclxufSk7IiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmZhY3RvcnkoJ2VtcGxveWVlSW50ZXJhY3Rpb25XaXRoU2VydmVyJywgZnVuY3Rpb24gKCRyb290U2NvcGUsIEVtcGxveWVlU2VydmljZSwgRW1wbG95ZWVIdHRwU2VydmljZSkge1xyXG4gICAgcmV0dXJuIHtcclxuICAgICAgICByZWFkSW1hZ2U6IGZ1bmN0aW9uIChpbWFnZSkge1xyXG4gICAgICAgICAgICByZXR1cm4gbmV3IFByb21pc2UoKHJlc29sdmUsIHJlamVjdCkgPT4ge1xyXG4gICAgICAgICAgICAgICAgbGV0IGZpbGVSZWFkZXIgPSBuZXcgRmlsZVJlYWRlcigpO1xyXG4gICAgICAgICAgICAgICAgZmlsZVJlYWRlci5vbmxvYWQgPSB4ID0+IHJlc29sdmUoZmlsZVJlYWRlci5yZXN1bHQpO1xyXG4gICAgICAgICAgICAgICAgZmlsZVJlYWRlci5yZWFkQXNEYXRhVVJMKGltYWdlKTtcclxuICAgICAgICAgICAgfSlcclxuICAgICAgICB9LFxyXG4gICAgICAgIGlzU3RyaW5nOiBmdW5jdGlvbiAodmFsKSB7XHJcbiAgICAgICAgICAgIHJldHVybiAodHlwZW9mIHZhbCA9PT0gXCJzdHJpbmdcIiB8fCB2YWwgaW5zdGFuY2VvZiBTdHJpbmcpO1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgc2F2ZTogYXN5bmMgZnVuY3Rpb24gKG9iamVjdCkge1xyXG4gICAgICAgICAgICBpZiAob2JqZWN0LnBob3RvICE9PSB1bmRlZmluZWQpIHtcclxuICAgICAgICAgICAgICAgIG9iamVjdC5waG90byA9IGF3YWl0IHRoaXMucmVhZEltYWdlKG9iamVjdC5waG90byk7XHJcbiAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgbGV0IHJlc3BvbnNlID0gYXdhaXQgRW1wbG95ZWVIdHRwU2VydmljZS5wb3N0KG9iamVjdCkuJHByb21pc2U7XHJcbiAgICAgICAgICAgIG9iamVjdCA9IHJlc3BvbnNlO1xyXG4gICAgICAgICAgICBFbXBsb3llZVNlcnZpY2UuYWRkKG9iamVjdCk7XHJcbiAgICAgICAgICAgICRyb290U2NvcGUuVXBkYXRlRW1wbG95ZWVzKCk7XHJcbiAgICAgICAgfSxcclxuICAgICAgICB1cGRhdGU6IGFzeW5jIGZ1bmN0aW9uIChvYmplY3QpIHtcclxuICAgICAgICAgICAgaWYgKG9iamVjdC5waG90byAhPT0gdW5kZWZpbmVkICYmICEodGhpcy5pc1N0cmluZyhvYmplY3QucGhvdG8pKSkge1xyXG4gICAgICAgICAgICAgICAgb2JqZWN0LnBob3RvID0gYXdhaXQgdGhpcy5yZWFkSW1hZ2Uob2JqZWN0LnBob3RvKTtcclxuICAgICAgICAgICAgfVxyXG4gICAgICAgICAgICBvYmplY3QuZW1wbG95ZWVEb2N1bWVudHMgPSAnJztcclxuICAgICAgICAgICAgbGV0IHJlc3BvbnNlID0gYXdhaXQgRW1wbG95ZWVIdHRwU2VydmljZS51cGRhdGUob2JqZWN0KS4kcHJvbWlzZTtcclxuICAgICAgICAgICAgb2JqZWN0ID0gcmVzcG9uc2U7XHJcbiAgICAgICAgICAgIEVtcGxveWVlU2VydmljZS51cGRhdGUob2JqZWN0KTtcclxuICAgICAgICAgICAgJHJvb3RTY29wZS5VcGRhdGVFbXBsb3llZXMoKTtcclxuICAgICAgICAgICAgJHJvb3RTY29wZS5VcGRhdGVUYWIob2JqZWN0KTtcclxuICAgICAgICB9LFxyXG4gICAgICAgIGRlbGV0ZTogYXN5bmMgZnVuY3Rpb24gKG9iamVjdCkge1xyXG4gICAgICAgICAgICBsZXQgcmVzcG9uc2UgPSBhd2FpdCBFbXBsb3llZUh0dHBTZXJ2aWNlLmRlbGV0ZSh7aWQ6IG9iamVjdC5pZH0pLiRwcm9taXNlO1xyXG4gICAgICAgICAgICBFbXBsb3llZVNlcnZpY2UuZGVsZXRlKG9iamVjdCk7XHJcbiAgICAgICAgICAgICRyb290U2NvcGUuQ2xvc2VUYWIob2JqZWN0KTtcclxuICAgICAgICAgICAgJHJvb3RTY29wZS5VcGRhdGVFbXBsb3llZXMoKTtcclxuICAgICAgICB9XHJcbiAgICB9XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5mYWN0b3J5KCdQcm9jZXNzaW5nRW1wbG95ZWUnLCBmdW5jdGlvbiAoZW1wbG95ZWVJbnRlcmFjdGlvbldpdGhTZXJ2ZXIpIHtcclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgcHJvY2Vzc2luZ0VtcGxveWVlOiBmdW5jdGlvbiAobWV0aG9kLCBvYmplY3QpIHtcclxuICAgICAgICAgICAgc3dpdGNoIChtZXRob2QpIHtcclxuICAgICAgICAgICAgICAgIGNhc2UgJ1BPU1QnOlxyXG4gICAgICAgICAgICAgICAgICAgIGVtcGxveWVlSW50ZXJhY3Rpb25XaXRoU2VydmVyLnNhdmUob2JqZWN0KVxyXG4gICAgICAgICAgICAgICAgICAgICAgICAuY2F0Y2goZnVuY3Rpb24gKGVycm9yKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICBhbGVydCgn0J3QtSDRg9C00LDQu9C+0YHRjCDRgdC+0LfQtNCw0YLRjCDQv9C+0LvRjNC30L7QstCw0YLQtdC70Y8gJyArIG9iamVjdC5sYXN0TmFtZSArXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgJ1xcbtCh0L7QvtCx0YnQtdC90LjQtTogJyArIGVycm9yLm1lc3NhZ2UgKyAnXFxu0J/QvtC00YDQvtCx0L3QtdC1INGB0LzQvtGC0YDQuCDQsiDQu9C+0LPQsNGFJyk7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZygnZXJyb3JzOiAnICsgZXJyb3IpO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICB9KTtcclxuICAgICAgICAgICAgICAgICAgICBicmVhaztcclxuICAgICAgICAgICAgICAgIGNhc2UgJ1BVVCc6XHJcbiAgICAgICAgICAgICAgICAgICAgZW1wbG95ZWVJbnRlcmFjdGlvbldpdGhTZXJ2ZXIudXBkYXRlKG9iamVjdClcclxuICAgICAgICAgICAgICAgICAgICAgICAgLmNhdGNoKGZ1bmN0aW9uIChlcnJvcikge1xyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgYWxlcnQoJ9Cd0LUg0YPQtNCw0LvQvtGB0Ywg0LLRi9C/0L7Qu9C90LjRgtGMINC+0LHQvdC+0LLQu9C10L3QuNC1INC/0L7Qu9GM0LfQvtCy0LDRgtC10LvRjyAnICsgb2JqZWN0Lmxhc3ROYW1lICtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAnXFxu0KHQvtC+0LHRidC10L3QuNC1OiAnICsgZXJyb3IubWVzc2FnZSArICdcXG7Qn9C+0LTRgNC+0LHQvdC10LUg0YHQvNC+0YLRgNC4INCyINC70L7Qs9Cw0YUnKTtcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIGNvbnNvbGUubG9nKCdlcnJvcnM6ICcgKyB2YWx1ZSk7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgIH0pO1xyXG4gICAgICAgICAgICAgICAgICAgIGJyZWFrO1xyXG4gICAgICAgICAgICAgICAgY2FzZSAnREVMRVRFJzpcclxuICAgICAgICAgICAgICAgICAgICBlbXBsb3llZUludGVyYWN0aW9uV2l0aFNlcnZlci5kZWxldGUob2JqZWN0KVxyXG4gICAgICAgICAgICAgICAgICAgICAgICAuY2F0Y2goZnVuY3Rpb24gKGVycm9yKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICBhbGVydCgn0J3QtSDRg9C00LDQu9C+0YHRjCDQstGL0L/QvtC70L3QuNGC0Ywg0YPQtNCw0LvQtdC90LjQtSDQv9C+0LvRjNC30L7QstCw0YLQtdC70Y8gJyArIG9iamVjdC5sYXN0TmFtZSArXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgJ1xcbtCh0L7QvtCx0YnQtdC90LjQtTogJyArIGVycm9yLm1lc3NhZ2UgKyAnXFxu0J/QvtC00YDQvtCx0L3QtdC1INGB0LzQvtGC0YDQuCDQsiDQu9C+0LPQsNGFJyk7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICBjb25zb2xlLmxvZygnZXJyb3JzOiAnICsgZXJyb3IpO1xyXG4gICAgICAgICAgICAgICAgICAgICAgICB9KTtcclxuICAgICAgICAgICAgICAgICAgICBicmVhaztcclxuICAgICAgICAgICAgfVxyXG4gICAgICAgIH1cclxuICAgIH1cclxufSk7IiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmZhY3RvcnkoJ0VtcGxveWVlU2VydmljZScsIGZ1bmN0aW9uIChQb3N0U2VydmljZSwgRGVwYXJ0bWVudFNlcnZpY2UpIHtcclxuICAgIGxldCBlbXBsb3llZXMgPSBbXTtcclxuXHJcbiAgICByZXR1cm4ge1xyXG4gICAgICAgIGdldEFsbDogZnVuY3Rpb24gKCkge1xyXG4gICAgICAgICAgICByZXR1cm4gZW1wbG95ZWVzO1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgdXBkYXRlOiBmdW5jdGlvbiAoZW1wbG95ZWUpIHtcclxuICAgICAgICAgICAgbGV0IGluZGV4ID0gZW1wbG95ZWVzLmZpbmRJbmRleChlbXAgPT4gZW1wLmlkID09PSBlbXBsb3llZS5pZCk7XHJcbiAgICAgICAgICAgIGVtcGxveWVlc1tpbmRleF0gPSBlbXBsb3llZTtcclxuICAgICAgICB9LFxyXG4gICAgICAgIGRlbGV0ZTogZnVuY3Rpb24gKGVtcGxveWVlKSB7XHJcbiAgICAgICAgICAgIGVtcGxveWVlcyA9IGVtcGxveWVlcy5maWx0ZXIoZW1wID0+IGVtcC5pZCAhPT0gZW1wbG95ZWUuaWQpO1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgc2V0QWxsOiBmdW5jdGlvbiAoZW1wbG95ZWVMaXN0KSB7XHJcbiAgICAgICAgICAgIGVtcGxveWVlcyA9IGVtcGxveWVlTGlzdDtcclxuICAgICAgICAgICAgZW1wbG95ZWVzLmZvckVhY2goZW1wID0+IGVtcC5wb3N0ID0gUG9zdFNlcnZpY2UuZ2V0QnlJZChlbXAucG9zdC5pZCkpO1xyXG4gICAgICAgICAgICBlbXBsb3llZXMuZm9yRWFjaChlbXAgPT4gZW1wLmRlcGFydG1lbnQgPSBEZXBhcnRtZW50U2VydmljZS5nZXRCeUlkKGVtcC5kZXBhcnRtZW50LmlkKSk7XHJcbiAgICAgICAgfSxcclxuICAgICAgICBnZXRCeUlkOiBmdW5jdGlvbiAoaWQpIHtcclxuICAgICAgICAgICAgcmV0dXJuIGVtcGxveWVlcy5maW5kKGVtcGxveWVlID0+IGVtcGxveWVlLmlkID09PSBpZCk7XHJcbiAgICAgICAgfSxcclxuICAgICAgICBhZGQ6IGZ1bmN0aW9uIChlbXBsb3llZSkge1xyXG4gICAgICAgICAgICBpZiAodGhpcy5nZXRCeUlkKGVtcGxveWVlLmlkKSA9PSBudWxsKSB7XHJcbiAgICAgICAgICAgICAgICBlbXBsb3llZXMucHVzaChlbXBsb3llZSk7XHJcbiAgICAgICAgICAgIH1cclxuICAgICAgICB9XHJcbiAgICB9O1xyXG59KTsiLCJhbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuY29udHJvbGxlcignZW1wbG95ZWVzQ29udHJvbGxlcicsIGZ1bmN0aW9uICgkaHR0cCwgJHJvb3RTY29wZSwgRW1wbG95ZWVTZXJ2aWNlLCBFVkVOVF9VUERBVEVfRU1QTE9ZRUVTLCBFVkVOVF9PUEVOX1RBQikge1xyXG4gICAgY29uc3Qgc2NvcGUgPSB0aGlzO1xyXG5cclxuICAgICRyb290U2NvcGUuT3BlblRhYiA9IGZ1bmN0aW9uIChlbXBsb3llZSkge1xyXG4gICAgICAgICRyb290U2NvcGUuJGJyb2FkY2FzdChFVkVOVF9PUEVOX1RBQiwgZW1wbG95ZWUpO1xyXG4gICAgfVxyXG4gICAgc2NvcGUuZW1wbG95ZWVzID0gRW1wbG95ZWVTZXJ2aWNlLmdldEFsbCgpO1xyXG4gICAgJHJvb3RTY29wZS4kb24oRVZFTlRfVVBEQVRFX0VNUExPWUVFUywgZnVuY3Rpb24gKGV2ZW50KSB7XHJcbiAgICAgICAgc2NvcGUuZW1wbG95ZWVzID0gRW1wbG95ZWVTZXJ2aWNlLmdldEFsbCgpO1xyXG4gICAgfSk7XHJcbn0pOyIsImV4cG9ydCBjb25zdCBhcHAgPSBhbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJywgWyduZ0FuaW1hdGUnLCAnbmdTYW5pdGl6ZScsICd1aS5ib290c3RyYXAnLCAnbmdSZXNvdXJjZSddKTtcclxuYXBwLmNvbmZpZyhbJyRyZXNvdXJjZVByb3ZpZGVyJywgZnVuY3Rpb24oJHJlc291cmNlUHJvdmlkZXIpIHtcclxuICAgICRyZXNvdXJjZVByb3ZpZGVyLmRlZmF1bHRzLnN0cmlwVHJhaWxpbmdTbGFzaGVzID0gZmFsc2U7XHJcbn1dKTtcclxuYXBwLmNvbnN0YW50KCdBUElfUEFUSCcsIFwiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3YxLjAvXCIpO1xyXG5hcHAuY29uc3RhbnQoJ0VWRU5UX1VQREFURV9ET0NVTUVOVFMnLCBcInVwZGF0ZURvY3VtZW50c1wiKTtcclxuYXBwLmNvbnN0YW50KCdFVkVOVF9VUERBVEVfRU1QTE9ZRUVTJywgXCJ1cGRhdGVFbXBsb3llZXNcIik7XHJcbmFwcC5jb25zdGFudCgnRVZFTlRfQ0xPU0VfVEFCJywgXCJjbG9zZVRhYlwiKTtcclxuYXBwLmNvbnN0YW50KCdFVkVOVF9PUEVOX1RBQicsIFwib3BlblRhYlwiKTtcclxuYXBwLmNvbnN0YW50KCdFVkVOVF9VUERBVEVfVEFCJywgXCJ1cGRhdGVUYWJcIik7IiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmNvbnRyb2xsZXIoJ2luaXQnLCBmdW5jdGlvbiAoUG9zdFNlcnZpY2UsIERlcGFydG1lbnRTZXJ2aWNlLCBQb3N0SHR0cFNlcnZpY2UsIERlcGFydG1lbnRIdHRwU2VydmljZSxcclxuICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgRGVsaXZlcnlNZXRob2RTZXJ2aWNlLCBFVkVOVF9VUERBVEVfRE9DVU1FTlRTLCBFVkVOVF9VUERBVEVfRU1QTE9ZRUVTLCBEb2N1bWVudEh0dHBTZXJ2aWNlLFxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBEb2N1bWVudFR5cGVTZXJ2aWNlLCBFbXBsb3llZVNlcnZpY2UsIEVtcGxveWVlSHR0cFNlcnZpY2UsIERvY3VtZW50U2VydmljZSwgJHJvb3RTY29wZSkge1xyXG5cclxuICAgIGNvbnN0IHNjb3BlID0gdGhpcztcclxuICAgIHNjb3BlLmRvd25sb2FkRG9jdW1lbnRzID0gZG93bmxvYWREb2N1bWVudHM7XHJcblxyXG5cclxuICAgICRyb290U2NvcGUuVXBkYXRlRG9jdW1lbnRzID0gZnVuY3Rpb24gKCkge1xyXG4gICAgICAgICRyb290U2NvcGUuJGJyb2FkY2FzdChFVkVOVF9VUERBVEVfRE9DVU1FTlRTKTtcclxuICAgIH1cclxuXHJcbiAgICAkcm9vdFNjb3BlLlVwZGF0ZUVtcGxveWVlcyA9IGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAkcm9vdFNjb3BlLiRicm9hZGNhc3QoRVZFTlRfVVBEQVRFX0VNUExPWUVFUyk7XHJcbiAgICB9XHJcblxyXG4gICAgYXN5bmMgZnVuY3Rpb24gZG93bmxvYWREb2N1bWVudHMoKSB7XHJcbiAgICAgICAgbGV0IHBvc3RzID0gYXdhaXQgUG9zdEh0dHBTZXJ2aWNlLnF1ZXJ5KCkuJHByb21pc2U7XHJcbiAgICAgICAgUG9zdFNlcnZpY2Uuc2V0QWxsKHBvc3RzKTtcclxuICAgICAgICBsZXQgZGVwYXJ0bWVudHMgPSBhd2FpdCBEZXBhcnRtZW50SHR0cFNlcnZpY2UucXVlcnkoKS4kcHJvbWlzZTtcclxuICAgICAgICBEZXBhcnRtZW50U2VydmljZS5zZXRBbGwoZGVwYXJ0bWVudHMpO1xyXG5cclxuICAgICAgICBsZXQgZGVsaXZlcnlNZXRob2RzID0gYXdhaXQgRG9jdW1lbnRIdHRwU2VydmljZS5nZXRBbGxEZWxpdmVyeU1ldGhvZHMoKS4kcHJvbWlzZTtcclxuICAgICAgICBEZWxpdmVyeU1ldGhvZFNlcnZpY2Uuc2V0QWxsKGRlbGl2ZXJ5TWV0aG9kcyk7XHJcblxyXG4gICAgICAgIGxldCBkb2N1bWVudFR5cGVzID0gYXdhaXQgRG9jdW1lbnRIdHRwU2VydmljZS5nZXRBbGxEb2N1bWVudFR5cGVzKCkuJHByb21pc2U7XHJcbiAgICAgICAgRG9jdW1lbnRUeXBlU2VydmljZS5zZXRBbGwoZG9jdW1lbnRUeXBlcyk7XHJcblxyXG4gICAgICAgIGxldCBlbXBsb3llZXMgPSBhd2FpdCBFbXBsb3llZUh0dHBTZXJ2aWNlLnF1ZXJ5KCkuJHByb21pc2U7XHJcbiAgICAgICAgRW1wbG95ZWVTZXJ2aWNlLnNldEFsbChlbXBsb3llZXMpO1xyXG4gICAgICAgICRyb290U2NvcGUuVXBkYXRlRW1wbG95ZWVzKCk7XHJcbiAgICAgICAgbGV0IGRvY3VtZW50cyA9IGF3YWl0IERvY3VtZW50SHR0cFNlcnZpY2UucXVlcnkoKS4kcHJvbWlzZTtcclxuICAgICAgICBEb2N1bWVudFNlcnZpY2Uuc2V0QWxsKGRvY3VtZW50cyk7XHJcbiAgICAgICAgJHJvb3RTY29wZS5VcGRhdGVEb2N1bWVudHMoKTtcclxuICAgIH1cclxuXHJcbiAgICBzY29wZS5kb3dubG9hZERvY3VtZW50cygpLmNhdGNoKGZ1bmN0aW9uIChlcnJvcikge1xyXG4gICAgICAgIGNvbnNvbGUubG9nKGVycm9yKVxyXG4gICAgfSlcclxuXHJcbiAgICBzY29wZS5kb3dubG9hZERvY3VtZW50cygpO1xyXG59KTtcclxuIiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmNvbnRyb2xsZXIoJ01vZGFsSW5zdGFuY2VDdHJsJywgZnVuY3Rpb24gKCR1aWJNb2RhbEluc3RhbmNlLCBwYXJhbXMsIEVtcGxveWVlU2VydmljZSwgJHJvb3RTY29wZSwgRG9jdW1lbnRTZXJ2aWNlLCBQcm9jZXNzaW5nRW1wbG95ZWUsXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBEb2N1bWVudEh0dHBTZXJ2aWNlLCBFbXBsb3llZUh0dHBTZXJ2aWNlLCBEZWxpdmVyeU1ldGhvZFNlcnZpY2UsIEVWRU5UX1VQREFURV9ET0NVTUVOVFMsXHJcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICBQcm9jZXNzaW5nRG9jdW1lbnQsIEVWRU5UX1VQREFURV9FTVBMT1lFRVMsIEVWRU5UX0NMT1NFX1RBQiwgRVZFTlRfVVBEQVRFX1RBQikge1xyXG4gICAgY29uc3Qgc2NvcGUgPSB0aGlzO1xyXG4gICAgc2NvcGUucG9zdHMgPSBwYXJhbXMucG9zdHM7XHJcbiAgICBzY29wZS5kZXBhcnRtZW50cyA9IHBhcmFtcy5kZXBhcnRtZW50cztcclxuICAgIHNjb3BlLm9iamVjdCA9IHBhcmFtcy5kZXRhY2hPYmplY3Q7XHJcbiAgICBzY29wZS5kZWxpdmVyeU1ldGhvZHMgPSBEZWxpdmVyeU1ldGhvZFNlcnZpY2UuZ2V0QWxsKCk7XHJcbiAgICBzY29wZS52aXNpYmxlID0gcGFyYW1zLnZpc2libGU7XHJcbiAgICBzY29wZS50eXBlT2JqZWN0ID0gcGFyYW1zLnR5cGVPYmplY3RcclxuICAgIHNjb3BlLmVtcGxveWVlcyA9IEVtcGxveWVlU2VydmljZS5nZXRBbGwoKTtcclxuICAgIHNjb3BlLm9rID0gb2s7XHJcbiAgICBzY29wZS5jYW5jZWwgPSBjYW5jZWw7XHJcbiAgICBzY29wZS50aXRsZSA9IHBhcmFtcy50aXRsZTtcclxuXHJcbiAgICAkcm9vdFNjb3BlLkNsb3NlVGFiID0gZnVuY3Rpb24gKGVtcGxveWVlKSB7XHJcbiAgICAgICAgJHJvb3RTY29wZS4kYnJvYWRjYXN0KEVWRU5UX0NMT1NFX1RBQiwgZW1wbG95ZWUpO1xyXG4gICAgfVxyXG5cclxuICAgICRyb290U2NvcGUuVXBkYXRlRW1wbG95ZWVzID0gZnVuY3Rpb24gKCkge1xyXG4gICAgICAgICRyb290U2NvcGUuJGJyb2FkY2FzdChFVkVOVF9VUERBVEVfRU1QTE9ZRUVTKTtcclxuICAgIH1cclxuXHJcbiAgICAkcm9vdFNjb3BlLlVwZGF0ZVRhYiA9IGZ1bmN0aW9uICh1cGRhdGVUYWIpIHtcclxuICAgICAgICAkcm9vdFNjb3BlLiRicm9hZGNhc3QoRVZFTlRfVVBEQVRFX1RBQiwgdXBkYXRlVGFiKTtcclxuICAgIH1cclxuXHJcbiAgICAkcm9vdFNjb3BlLlVwZGF0ZURvY3VtZW50cyA9IGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAkcm9vdFNjb3BlLiRicm9hZGNhc3QoRVZFTlRfVVBEQVRFX0RPQ1VNRU5UUyk7XHJcbiAgICB9XHJcblxyXG4gICAgZnVuY3Rpb24gb2sob2JqZWN0KSB7XHJcbiAgICAgICAgaWYgKCFvYmplY3QucmVnaXN0cmF0aW9uTnVtYmVyKSB7XHJcbiAgICAgICAgICAgIFByb2Nlc3NpbmdFbXBsb3llZS5wcm9jZXNzaW5nRW1wbG95ZWUocGFyYW1zLm1ldGhvZCwgb2JqZWN0KTtcclxuICAgICAgICB9IGVsc2Uge1xyXG4gICAgICAgICAgICBQcm9jZXNzaW5nRG9jdW1lbnQucHJvY2Vzc2luZ0RvY3VtZW50KHBhcmFtcy5tZXRob2QsIG9iamVjdCk7XHJcbiAgICAgICAgfVxyXG4gICAgICAgICR1aWJNb2RhbEluc3RhbmNlLmNsb3NlKCk7XHJcbiAgICB9O1xyXG5cclxuICAgIGZ1bmN0aW9uIGNhbmNlbCgpIHtcclxuICAgICAgICAkdWliTW9kYWxJbnN0YW5jZS5kaXNtaXNzKCdjYW5jZWwnKTtcclxuICAgIH07XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5jb250cm9sbGVyKCdtb2RhbFZpZXcnLCBmdW5jdGlvbiAoJHVpYk1vZGFsLCAkcm9vdFNjb3BlLCAkbG9nLCAkZG9jdW1lbnQsIFBvc3RTZXJ2aWNlLCBEZXBhcnRtZW50U2VydmljZSkge1xyXG4gICAgY29uc3Qgc2NvcGUgPSB0aGlzO1xyXG4gICAgc2NvcGUuYW5pbWF0aW9uc0VuYWJsZWQgPSB0cnVlO1xyXG4gICAgc2NvcGUub2JqZWN0ID0ge307XHJcbiAgICBzY29wZS52aXNpYmxlID0gdHJ1ZTtcclxuXHJcbiAgICBzY29wZS5vcGVuID0gZnVuY3Rpb24gKHR5cGVPYmplY3QsIG9iamVjdCwgbWV0aG9kLCBwYXJlbnRTZWxlY3Rvcikge1xyXG4gICAgICAgIGlmIChtZXRob2QgPT09ICdQVVQnKSB7XHJcbiAgICAgICAgICAgIHNjb3BlLnRpdGxlID0gJ9Ce0LHQvdC+0LLQu9C10L3QuNC1ICc7XHJcbiAgICAgICAgfSBlbHNlIHtcclxuICAgICAgICAgICAgc2NvcGUudGl0bGUgPSAn0KHQvtC30LTQsNC90LjQtSAnO1xyXG4gICAgICAgIH1cclxuICAgICAgICBpZiAodHlwZU9iamVjdCA9PT0gXCJlbXBsb3llZVwiKSB7XHJcbiAgICAgICAgICAgIHNjb3BlLnZpc2libGUgPSB0cnVlO1xyXG4gICAgICAgICAgICBzY29wZS50aXRsZSArPSAn0YHQvtGC0YDRg9C00L3QuNC60LAnO1xyXG4gICAgICAgIH0gZWxzZSB7XHJcbiAgICAgICAgICAgIHNjb3BlLnZpc2libGUgPSBmYWxzZTtcclxuICAgICAgICAgICAgc2NvcGUudGl0bGUgKz0gJ9C00L7QutGD0LzQtdC90YLQsCc7XHJcbiAgICAgICAgfVxyXG4gICAgICAgIHNjb3BlLm9iamVjdCA9IG9iamVjdCA/IG9iamVjdCA6IHt9O1xyXG4gICAgICAgIHNjb3BlLmRldGFjaE9iamVjdCA9IE9iamVjdC5hc3NpZ24oe30sIHNjb3BlLm9iamVjdCk7XHJcbiAgICAgICAgc2NvcGUubWV0aG9kID0gbWV0aG9kID8gbWV0aG9kIDogJ1BPU1QnO1xyXG4gICAgICAgIGxldCBwYXJlbnRFbGVtID0gcGFyZW50U2VsZWN0b3IgP1xyXG4gICAgICAgICAgICBhbmd1bGFyLmVsZW1lbnQoJGRvY3VtZW50WzBdLnF1ZXJ5U2VsZWN0b3IoJy5tb2RhbC1kZW1vICcgKyBwYXJlbnRTZWxlY3RvcikpIDogdW5kZWZpbmVkO1xyXG4gICAgICAgIGxldCBtb2RhbEluc3RhbmNlID0gJHVpYk1vZGFsLm9wZW4oe1xyXG4gICAgICAgICAgICBhbmltYXRpb246IHNjb3BlLmFuaW1hdGlvbnNFbmFibGVkLFxyXG4gICAgICAgICAgICBhcmlhTGFiZWxsZWRCeTogJ21vZGFsLXRpdGxlJyxcclxuICAgICAgICAgICAgYXJpYURlc2NyaWJlZEJ5OiAnbW9kYWwtYm9keScsXHJcbiAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnY3JlYXRlVmlld01vZGFsLmh0bWwnLFxyXG4gICAgICAgICAgICBjb250cm9sbGVyOiAnTW9kYWxJbnN0YW5jZUN0cmwnLFxyXG4gICAgICAgICAgICBjb250cm9sbGVyQXM6ICdtb2RhbFZpZXcnLFxyXG4gICAgICAgICAgICBhcHBlbmRUbzogcGFyZW50RWxlbSxcclxuICAgICAgICAgICAgcmVzb2x2ZToge1xyXG4gICAgICAgICAgICAgICAgcGFyYW1zOiBmdW5jdGlvbiAoKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgcmV0dXJuIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgcG9zdHM6IFBvc3RTZXJ2aWNlLmdldEFsbCgpLFxyXG4gICAgICAgICAgICAgICAgICAgICAgICBkZXBhcnRtZW50czogRGVwYXJ0bWVudFNlcnZpY2UuZ2V0QWxsKCksXHJcbiAgICAgICAgICAgICAgICAgICAgICAgIGRldGFjaE9iamVjdDogc2NvcGUuZGV0YWNoT2JqZWN0LFxyXG4gICAgICAgICAgICAgICAgICAgICAgICBtZXRob2Q6IHNjb3BlLm1ldGhvZCxcclxuICAgICAgICAgICAgICAgICAgICAgICAgdHlwZU9iamVjdDogdHlwZU9iamVjdCxcclxuICAgICAgICAgICAgICAgICAgICAgICAgdmlzaWJsZTogc2NvcGUudmlzaWJsZSxcclxuICAgICAgICAgICAgICAgICAgICAgICAgdGl0bGU6IHNjb3BlLnRpdGxlXHJcbiAgICAgICAgICAgICAgICAgICAgfTtcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgfVxyXG4gICAgICAgIH0pO1xyXG5cclxuICAgICAgICBtb2RhbEluc3RhbmNlLnJlc3VsdC50aGVuKGZ1bmN0aW9uIChzZWxlY3RlZEl0ZW0pIHtcclxuICAgICAgICAgICAgc2NvcGUuc2VsZWN0ZWQgPSBzZWxlY3RlZEl0ZW07XHJcbiAgICAgICAgfSwgZnVuY3Rpb24gKCkge1xyXG4gICAgICAgICAgICAkbG9nLmluZm8oJ01vZGFsIGRpc21pc3NlZCBhdDogJyArIG5ldyBEYXRlKCkpO1xyXG4gICAgICAgIH0pO1xyXG5cclxuICAgICAgICBtb2RhbEluc3RhbmNlLm9wZW5lZC50aGVuKGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgaWYgKHR5cGVPYmplY3QgPT09IFwiZW1wbG95ZWVcIiAmJiBzY29wZS5kZXRhY2hPYmplY3QuYmlydGhkYXkgIT09IHVuZGVmaW5lZCkge1xyXG4gICAgICAgICAgICAgICAgc2NvcGUuZGV0YWNoT2JqZWN0LmJpcnRoZGF5ID0gbmV3IERhdGUoc2NvcGUuZGV0YWNoT2JqZWN0LmJpcnRoZGF5KTtcclxuICAgICAgICAgICAgfSBlbHNlIHtcclxuICAgICAgICAgICAgICAgIHNjb3BlLmRldGFjaE9iamVjdC5yZWdpc3RyYXRpb25EYXRlID0gc2NvcGUuZGV0YWNoT2JqZWN0LnJlZ2lzdHJhdGlvbkRhdGUgIT09IHVuZGVmaW5lZCA/IG5ldyBEYXRlKHNjb3BlLmRldGFjaE9iamVjdC5yZWdpc3RyYXRpb25EYXRlKSA6IHNjb3BlLmRldGFjaE9iamVjdC5yZWdpc3RyYXRpb25EYXRlO1xyXG4gICAgICAgICAgICAgICAgc2NvcGUuZGV0YWNoT2JqZWN0LmRhdGVPZklzc3VlID0gc2NvcGUuZGV0YWNoT2JqZWN0LmRhdGVPZklzc3VlICE9PSB1bmRlZmluZWQgPyBuZXcgRGF0ZShzY29wZS5kZXRhY2hPYmplY3QuZGF0ZU9mSXNzdWUpIDogc2NvcGUuZGV0YWNoT2JqZWN0LmRhdGVPZklzc3VlO1xyXG4gICAgICAgICAgICAgICAgc2NvcGUuZGV0YWNoT2JqZWN0Lm91dGdvaW5nUmVnaXN0cmF0aW9uRGF0ZSA9IHNjb3BlLmRldGFjaE9iamVjdC5vdXRnb2luZ1JlZ2lzdHJhdGlvbkRhdGUgIT09IHVuZGVmaW5lZCA/IG5ldyBEYXRlKHNjb3BlLmRldGFjaE9iamVjdC5vdXRnb2luZ1JlZ2lzdHJhdGlvbkRhdGUpIDogc2NvcGUuZGV0YWNoT2JqZWN0Lm91dGdvaW5nUmVnaXN0cmF0aW9uRGF0ZTtcclxuICAgICAgICAgICAgfVxyXG4gICAgICAgIH0pO1xyXG4gICAgfTtcclxuXHJcbiAgICBzY29wZS5vcGVuQ29uZmlybU1vZGFscyA9IGZ1bmN0aW9uICh0eXBlT2JqZWN0LCBvYmplY3QsIG1ldGhvZCwgcGFyZW50U2VsZWN0b3IpIHtcclxuICAgICAgICBzY29wZS5vYmplY3QgPSBvYmplY3QgPyBvYmplY3QgOiB7fTtcclxuICAgICAgICBzY29wZS5tZXRob2QgPSBtZXRob2QgPyBtZXRob2QgOiAnUE9TVCc7XHJcbiAgICAgICAgbGV0IHBhcmVudEVsZW0gPSBwYXJlbnRTZWxlY3RvciA/XHJcbiAgICAgICAgICAgIGFuZ3VsYXIuZWxlbWVudCgkZG9jdW1lbnRbMF0ucXVlcnlTZWxlY3RvcignLm1vZGFsLWRlbW8gJyArIHBhcmVudFNlbGVjdG9yKSkgOiB1bmRlZmluZWQ7XHJcbiAgICAgICAgbGV0IG1vZGFsSW5zdGFuY2UgPSAkdWliTW9kYWwub3Blbih7XHJcbiAgICAgICAgICAgIGFuaW1hdGlvbjogc2NvcGUuYW5pbWF0aW9uc0VuYWJsZWQsXHJcbiAgICAgICAgICAgIGFyaWFMYWJlbGxlZEJ5OiAnbW9kYWwtdGl0bGUtY29uZmlybScsXHJcbiAgICAgICAgICAgIGFyaWFEZXNjcmliZWRCeTogJ21vZGFsLWJvZHktY29uZmlybScsXHJcbiAgICAgICAgICAgIHRlbXBsYXRlVXJsOiAnY29uZmlybUZvcm0uaHRtbCcsXHJcbiAgICAgICAgICAgIHNpemU6ICdzbScsXHJcbiAgICAgICAgICAgIGNvbnRyb2xsZXI6ICdNb2RhbEluc3RhbmNlQ3RybCcsXHJcbiAgICAgICAgICAgIGNvbnRyb2xsZXJBczogJ21vZGFsVmlldycsXHJcbiAgICAgICAgICAgIGFwcGVuZFRvOiBwYXJlbnRFbGVtLFxyXG4gICAgICAgICAgICByZXNvbHZlOiB7XHJcbiAgICAgICAgICAgICAgICBwYXJhbXM6IGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgICAgICAgICByZXR1cm4ge2RldGFjaE9iamVjdDogc2NvcGUub2JqZWN0LCBtZXRob2Q6IHNjb3BlLm1ldGhvZH07XHJcbiAgICAgICAgICAgICAgICB9XHJcbiAgICAgICAgICAgIH1cclxuICAgICAgICB9KTtcclxuICAgIH07XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5kaXJlY3RpdmUoJ3dlbGNvbWVUYWInLCBmdW5jdGlvbiAoKSB7XHJcbiAgICByZXR1cm4ge1xyXG4gICAgICAgIHJlc3RyaWN0OiAnRScsXHJcbiAgICAgICAgdHJhbnNjbHVkZTogdHJ1ZSxcclxuICAgICAgICB0ZW1wbGF0ZVVybDogJ3NyYy90YWJzL3dlbGNvbWUtdGFiLmh0bWwnXHJcbiAgICB9O1xyXG59KTtcclxuXHJcbmFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5kaXJlY3RpdmUoJ21vZGFsV2luZG93JywgZnVuY3Rpb24gKCkge1xyXG4gICAgcmV0dXJuIHtcclxuICAgICAgICByZXN0cmljdDogJ0UnLFxyXG4gICAgICAgIHRyYW5zY2x1ZGU6IGZhbHNlLFxyXG4gICAgICAgIHRlbXBsYXRlVXJsOiAnc3JjL3RlbXBsYXRlcy9tb2RhbC13aW5kb3cuaHRtbCdcclxuICAgIH1cclxufSk7XHJcblxyXG5hbmd1bGFyLm1vZHVsZSgnZG9jdW1lbnRmbG93QXBwJykuZGlyZWN0aXZlKCdjb25maXJtTW9kYWxXaW5kb3cnLCBmdW5jdGlvbiAoKSB7XHJcbiAgICByZXR1cm4ge1xyXG4gICAgICAgIHJlc3RyaWN0OiAnRScsXHJcbiAgICAgICAgdHJhbnNjbHVkZTogZmFsc2UsXHJcbiAgICAgICAgdGVtcGxhdGVVcmw6ICdzcmMvdGVtcGxhdGVzL2NvbmZpcm0tZm9ybS5odG1sJ1xyXG4gICAgfVxyXG59KTtcclxuXHJcbmFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5kaXJlY3RpdmUoXCJzZWxlY3RJbWFnZVwiLCBmdW5jdGlvbigpIHtcclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgcmVxdWlyZTogXCJuZ01vZGVsXCIsXHJcbiAgICAgICAgbGluazogZnVuY3Rpb24gcG9zdExpbmsoc2NvcGUsZWxlbSxhdHRycyxuZ01vZGVsKSB7XHJcbiAgICAgICAgICAgIGVsZW0ub24oXCJjaGFuZ2VcIiwgZnVuY3Rpb24oZSkge1xyXG4gICAgICAgICAgICAgICAgbGV0IGZpbGUgPSBlbGVtWzBdLmZpbGVzWzBdO1xyXG4gICAgICAgICAgICAgICAgbmdNb2RlbC4kc2V0Vmlld1ZhbHVlKGZpbGUpO1xyXG4gICAgICAgICAgICB9KVxyXG4gICAgICAgIH1cclxuICAgIH1cclxufSk7IiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmZhY3RvcnkoJ1Bvc3RIdHRwU2VydmljZScsIGZ1bmN0aW9uICgkcmVzb3VyY2UsIEFQSV9QQVRIKSB7XHJcbiAgICByZXR1cm4gJHJlc291cmNlKEFQSV9QQVRIKydwb3N0LzppZCcsIHtpZDogJ0BpZCd9LCB7XHJcbiAgICAgICAgJ2RlbGV0ZSc6IHttZXRob2Q6ICdERUxFVEUnLCB1cmw6IEFQSV9QQVRIICsgJ3Bvc3QvZGVsZXRlLzppZCd9LFxyXG4gICAgICAgICd1cGRhdGUnOiB7bWV0aG9kOiAnUFVUJywgdXJsOiBBUElfUEFUSCArICdwb3N0L3VwZGF0ZSd9LFxyXG4gICAgICAgICdwb3N0Jzoge21ldGhvZDogJ1BPU1QnLCB1cmw6IEFQSV9QQVRIICsgJ3Bvc3Qvc2F2ZSd9XHJcbiAgICB9KTtcclxufSk7XHJcbiIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5mYWN0b3J5KCdQb3N0U2VydmljZScsIGZ1bmN0aW9uICgpIHtcclxuICAgIGxldCBwb3N0cyA9IFtdO1xyXG5cclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgZ2V0QWxsOiBmdW5jdGlvbiAoKSB7XHJcbiAgICAgICAgICAgIHJldHVybiBwb3N0cztcclxuICAgICAgICB9LFxyXG4gICAgICAgIGRlbGV0ZTogZnVuY3Rpb24gKHBvc3QpIHtcclxuICAgICAgICAgICAgcG9zdHMgPSBwb3N0cy5maWx0ZXIocCA9PiBwLmlkICE9PSBwb3N0LmlkKTtcclxuICAgICAgICB9LFxyXG4gICAgICAgIHNldEFsbDogZnVuY3Rpb24gKHBvc3RMaXN0KSB7XHJcbiAgICAgICAgICAgIHBvc3RzID0gcG9zdExpc3Q7XHJcbiAgICAgICAgfSxcclxuICAgICAgICBhZGQ6IGZ1bmN0aW9uIChwb3N0KSB7XHJcbiAgICAgICAgICAgIGlmICh0aGlzLmdldEJ5SWQocG9zdC5pZCkgPT0gbnVsbCkge1xyXG4gICAgICAgICAgICAgICAgcG9zdHMucHVzaChwb3N0KTtcclxuICAgICAgICAgICAgfVxyXG4gICAgICAgIH0sXHJcbiAgICAgICAgZ2V0QnlJZDogZnVuY3Rpb24gKGlkKSB7XHJcbiAgICAgICAgICAgIHJldHVybiBwb3N0cy5maW5kKHBvc3QgPT4gcG9zdC5pZCA9PT0gaWQpO1xyXG4gICAgICAgIH1cclxuICAgIH07XHJcbn0pOyIsImFuZ3VsYXIubW9kdWxlKCdkb2N1bWVudGZsb3dBcHAnKS5jb250cm9sbGVyKCd0YWJzQ29udHJvbGxlcicsIGZ1bmN0aW9uICgkcm9vdFNjb3BlLCAkdGltZW91dCwgRG9jdW1lbnRTZXJ2aWNlLCBFVkVOVF9DTE9TRV9UQUIsIEVWRU5UX09QRU5fVEFCLCBFVkVOVF9VUERBVEVfVEFCLFxyXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgRVZFTlRfVVBEQVRFX1RBQiwgUG9zdFNlcnZpY2UsIERlcGFydG1lbnRTZXJ2aWNlLCBUYWJzU2VydmljZSkge1xyXG4gICAgbGV0IHNjb3BlID0gdGhpcztcclxuICAgIHNjb3BlLmFjdGl2ZVRhYkluZGV4ID0gVGFic1NlcnZpY2UuZ2V0SW5kZXhBY3RpdmVUYWIoKTtcclxuICAgIHNjb3BlLnRhYnMgPSBUYWJzU2VydmljZS5nZXRBbGwoKTtcclxuICAgIHNjb3BlLm9wZW5UYWIgPSBvcGVuVGFiO1xyXG4gICAgc2NvcGUuY2xvc2VUYWIgPSBjbG9zZVRhYjtcclxuICAgIHNjb3BlLnNlbGVjdFRhYiA9IHNlbGVjdFRhYjtcclxuXHJcbiAgICAkcm9vdFNjb3BlLiRvbihFVkVOVF9PUEVOX1RBQiwgZnVuY3Rpb24gKGV2ZW50LCBuZXdUYWIpIHtcclxuICAgICAgICBzY29wZS5vcGVuVGFiKG5ld1RhYik7XHJcbiAgICB9KTtcclxuXHJcbiAgICAkcm9vdFNjb3BlLiRvbihFVkVOVF9DTE9TRV9UQUIsIGZ1bmN0aW9uIChldmVudCwgY2xvc2VkVGFiKSB7XHJcbiAgICAgICAgc2NvcGUuY2xvc2VUYWIoY2xvc2VkVGFiKTtcclxuICAgIH0pO1xyXG5cclxuICAgICRyb290U2NvcGUuJG9uKEVWRU5UX1VQREFURV9UQUIsIGZ1bmN0aW9uIChldmVudCwgdXBkYXRlVGFiKSB7XHJcbiAgICAgICAgY2xvc2VUYWIodXBkYXRlVGFiKTtcclxuICAgICAgICBvcGVuVGFiKHVwZGF0ZVRhYik7XHJcbiAgICB9KTtcclxuXHJcbiAgICBmdW5jdGlvbiBvcGVuVGFiKG5ld1RhYikge1xyXG4gICAgICAgIGlmICghbmV3VGFiLnJlZ2lzdHJhdGlvbk51bWJlcikge1xyXG4gICAgICAgICAgICBzY29wZS50YWJzLmVtcGxveWVlcyA9IFRhYnNTZXJ2aWNlLmdldEFsbCgpLmVtcGxveWVlcztcclxuICAgICAgICAgICAgbmV3VGFiLnBvc3QgPSBQb3N0U2VydmljZS5nZXRCeUlkKG5ld1RhYi5wb3N0LmlkKTtcclxuICAgICAgICAgICAgbmV3VGFiLmRlcGFydG1lbnQgPSBEZXBhcnRtZW50U2VydmljZS5nZXRCeUlkKG5ld1RhYi5kZXBhcnRtZW50LmlkKTtcclxuICAgICAgICAgICAgbmV3VGFiLmVtcGxveWVlRG9jdW1lbnRzID0gRG9jdW1lbnRTZXJ2aWNlLmZpbmRCeUF1dGhvcihuZXdUYWIuaWQpO1xyXG4gICAgICAgIH0gZWxzZSB7XHJcbiAgICAgICAgICAgIHNjb3BlLnRhYnMuZG9jdW1lbnRzID0gVGFic1NlcnZpY2UuZ2V0QWxsKCkuZG9jdW1lbnRzO1xyXG4gICAgICAgIH1cclxuXHJcbiAgICAgICAgaWYgKFRhYnNTZXJ2aWNlLmZpbmRUYWJCeUlkKG5ld1RhYi5pZCkgPT09IHVuZGVmaW5lZCkge1xyXG4gICAgICAgICAgICBUYWJzU2VydmljZS5vcGVuVGFiKG5ld1RhYik7XHJcbiAgICAgICAgICAgICR0aW1lb3V0KGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgICAgIFRhYnNTZXJ2aWNlLnNldEFjdGl2ZVRhYihuZXdUYWIuaWQpO1xyXG4gICAgICAgICAgICAgICAgc2NvcGUuYWN0aXZlVGFiSW5kZXggPSBUYWJzU2VydmljZS5nZXRJbmRleEFjdGl2ZVRhYigpO1xyXG4gICAgICAgICAgICB9KTtcclxuICAgICAgICB9IGVsc2Uge1xyXG4gICAgICAgICAgICBUYWJzU2VydmljZS5zZXRBY3RpdmVUYWIobmV3VGFiLmlkKTtcclxuICAgICAgICAgICAgc2NvcGUuYWN0aXZlVGFiSW5kZXggPSBUYWJzU2VydmljZS5nZXRJbmRleEFjdGl2ZVRhYigpO1xyXG4gICAgICAgIH1cclxuICAgIH07XHJcblxyXG4gICAgZnVuY3Rpb24gY2xvc2VUYWIoY2xvc2VkVGFiKSB7XHJcbiAgICAgICAgVGFic1NlcnZpY2UuY2xvc2VUYWIoY2xvc2VkVGFiKTtcclxuICAgICAgICBzY29wZS5hY3RpdmVUYWJJbmRleCA9IFRhYnNTZXJ2aWNlLmdldEluZGV4QWN0aXZlVGFiKCk7XHJcbiAgICAgICAgc2NvcGUudGFicyA9IFRhYnNTZXJ2aWNlLmdldEFsbCgpO1xyXG4gICAgfVxyXG5cclxuICAgIGZ1bmN0aW9uIHNlbGVjdFRhYih0YWIpIHtcclxuICAgICAgICBzY29wZS5lbXBsb3llZURvY3VtZW50cyA9IERvY3VtZW50U2VydmljZS5maW5kQnlBdXRob3IodGFiLmlkKTtcclxuICAgIH1cclxufSk7IiwiYW5ndWxhci5tb2R1bGUoJ2RvY3VtZW50Zmxvd0FwcCcpLmZhY3RvcnkoJ1RhYnNTZXJ2aWNlJywgZnVuY3Rpb24gKCkge1xyXG5cclxuICAgIGxldCB0YWJzID0ge2VtcGxveWVlczogW10sIGRvY3VtZW50czogW119O1xyXG5cclxuICAgIGxldCBhY3RpdmVUYWJJbmRleCA9IC0xO1xyXG5cclxuICAgIHJldHVybiB7XHJcbiAgICAgICAgZ2V0QWxsOiBmdW5jdGlvbiAoKSB7XHJcbiAgICAgICAgICAgIHJldHVybiB0YWJzO1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgb3BlblRhYjogZnVuY3Rpb24gKHRhYikge1xyXG4gICAgICAgICAgICBpZiAoIXRhYi5yZWdpc3RyYXRpb25OdW1iZXIpIHtcclxuICAgICAgICAgICAgICAgIHRhYnMuZW1wbG95ZWVzLnB1c2godGFiKTtcclxuICAgICAgICAgICAgfSBlbHNlIHtcclxuICAgICAgICAgICAgICAgIHRhYnMuZG9jdW1lbnRzLnB1c2godGFiKTtcclxuICAgICAgICAgICAgfVxyXG4gICAgICAgIH0sXHJcbiAgICAgICAgZ2V0SW5kZXhBY3RpdmVUYWI6IGZ1bmN0aW9uICgpIHtcclxuICAgICAgICAgICAgcmV0dXJuIGFjdGl2ZVRhYkluZGV4O1xyXG4gICAgICAgIH0sXHJcbiAgICAgICAgc2V0QWN0aXZlVGFiOiBmdW5jdGlvbiAoaW5kZXgpIHtcclxuICAgICAgICAgICAgYWN0aXZlVGFiSW5kZXggPSBpbmRleDtcclxuICAgICAgICB9LFxyXG4gICAgICAgIGZpbmRUYWJCeUlkOiBmdW5jdGlvbiAoaWQpIHtcclxuICAgICAgICAgICAgbGV0IGVtcGxveWVlVGFiID0gdGFicy5lbXBsb3llZXMuZmluZCh0YWIgPT4gdGFiLmlkID09PSBpZCk7XHJcbiAgICAgICAgICAgIGlmIChlbXBsb3llZVRhYiAhPT0gdW5kZWZpbmVkKSB7XHJcbiAgICAgICAgICAgICAgICByZXR1cm4gZW1wbG95ZWVUYWI7XHJcbiAgICAgICAgICAgIH0gZWxzZSB7XHJcbiAgICAgICAgICAgICAgICByZXR1cm4gdGFicy5kb2N1bWVudHMuZmluZCh0YWIgPT4gdGFiLmlkID09PSBpZCk7XHJcbiAgICAgICAgICAgIH1cclxuICAgICAgICB9LFxyXG4gICAgICAgIGNsb3NlVGFiOiBmdW5jdGlvbiAoY2xvc2VkVGFiKSB7XHJcbiAgICAgICAgICAgIGlmICghY2xvc2VkVGFiLnJlZ2lzdHJhdGlvbk51bWJlcikge1xyXG4gICAgICAgICAgICAgICAgbGV0IGluZGV4ID0gdGFicy5lbXBsb3llZXMuZmluZEluZGV4KGVtcCA9PiBlbXAuaWQgPT09IGNsb3NlZFRhYi5pZCk7XHJcbiAgICAgICAgICAgICAgICB0YWJzLmVtcGxveWVlcy5zcGxpY2UoaW5kZXgsIDEpO1xyXG4gICAgICAgICAgICAgICAgaWYgKHRhYnMuZW1wbG95ZWVzLmxlbmd0aCAhPT0gMCkge1xyXG4gICAgICAgICAgICAgICAgICAgIGlmICh0YWJzLmVtcGxveWVlcy5sZW5ndGggLSAxID49IGluZGV4KSB7XHJcbiAgICAgICAgICAgICAgICAgICAgICAgIHRoaXMuc2V0QWN0aXZlVGFiKHRhYnMuZW1wbG95ZWVzW2luZGV4XS5pZCk7XHJcbiAgICAgICAgICAgICAgICAgICAgfSBlbHNlIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgdGhpcy5zZXRBY3RpdmVUYWIodGFicy5lbXBsb3llZXNbdGFicy5lbXBsb3llZXMubGVuZ3RoIC0gMV0uaWQpO1xyXG4gICAgICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgICAgIH0gZWxzZSBpZiAodGFicy5kb2N1bWVudHMubGVuZ3RoICE9PSAwKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgdGhpcy5zZXRBY3RpdmVUYWIodGFicy5kb2N1bWVudHNbMF0uaWQpO1xyXG4gICAgICAgICAgICAgICAgfSBlbHNlIHtcclxuICAgICAgICAgICAgICAgICAgICB0aGlzLnNldEFjdGl2ZVRhYigtMSk7XHJcbiAgICAgICAgICAgICAgICB9XHJcbiAgICAgICAgICAgIH0gZWxzZSB7XHJcbiAgICAgICAgICAgICAgICBsZXQgaW5kZXggPSB0YWJzLmRvY3VtZW50cy5maW5kSW5kZXgoZG9jID0+IGRvYy5pZCA9PT0gY2xvc2VkVGFiLmlkKTtcclxuICAgICAgICAgICAgICAgIHRhYnMuZG9jdW1lbnRzLnNwbGljZShpbmRleCwgMSk7XHJcbiAgICAgICAgICAgICAgICBpZiAodGFicy5kb2N1bWVudHMubGVuZ3RoICE9PSAwKSB7XHJcbiAgICAgICAgICAgICAgICAgICAgaWYgKHRhYnMuZG9jdW1lbnRzLmxlbmd0aCAtIDEgPj0gaW5kZXgpIHtcclxuICAgICAgICAgICAgICAgICAgICAgICAgdGhpcy5zZXRBY3RpdmVUYWIodGFicy5kb2N1bWVudHNbaW5kZXhdLmlkKTtcclxuICAgICAgICAgICAgICAgICAgICB9IGVsc2Uge1xyXG4gICAgICAgICAgICAgICAgICAgICAgICB0aGlzLnNldEFjdGl2ZVRhYih0YWJzLmRvY3VtZW50c1t0YWJzLmRvY3VtZW50cy5sZW5ndGggLSAxXS5pZCk7XHJcbiAgICAgICAgICAgICAgICAgICAgfVxyXG4gICAgICAgICAgICAgICAgfSBlbHNlIGlmICh0YWJzLmVtcGxveWVlcy5sZW5ndGggIT09IDApIHtcclxuICAgICAgICAgICAgICAgICAgICB0aGlzLnNldEFjdGl2ZVRhYih0YWJzLmVtcGxveWVlc1t0YWJzLmVtcGxveWVlcy5sZW5ndGggLSAxXS5pZCk7XHJcbiAgICAgICAgICAgICAgICB9IGVsc2Uge1xyXG4gICAgICAgICAgICAgICAgICAgIHRoaXMuc2V0QWN0aXZlVGFiKC0xKTtcclxuICAgICAgICAgICAgICAgIH1cclxuICAgICAgICAgICAgfVxyXG4gICAgICAgIH1cclxuICAgIH07XHJcbn0pOyIsIi8vIFRoZSBtb2R1bGUgY2FjaGVcbnZhciBfX3dlYnBhY2tfbW9kdWxlX2NhY2hlX18gPSB7fTtcblxuLy8gVGhlIHJlcXVpcmUgZnVuY3Rpb25cbmZ1bmN0aW9uIF9fd2VicGFja19yZXF1aXJlX18obW9kdWxlSWQpIHtcblx0Ly8gQ2hlY2sgaWYgbW9kdWxlIGlzIGluIGNhY2hlXG5cdHZhciBjYWNoZWRNb2R1bGUgPSBfX3dlYnBhY2tfbW9kdWxlX2NhY2hlX19bbW9kdWxlSWRdO1xuXHRpZiAoY2FjaGVkTW9kdWxlICE9PSB1bmRlZmluZWQpIHtcblx0XHRyZXR1cm4gY2FjaGVkTW9kdWxlLmV4cG9ydHM7XG5cdH1cblx0Ly8gQ3JlYXRlIGEgbmV3IG1vZHVsZSAoYW5kIHB1dCBpdCBpbnRvIHRoZSBjYWNoZSlcblx0dmFyIG1vZHVsZSA9IF9fd2VicGFja19tb2R1bGVfY2FjaGVfX1ttb2R1bGVJZF0gPSB7XG5cdFx0Ly8gbm8gbW9kdWxlLmlkIG5lZWRlZFxuXHRcdC8vIG5vIG1vZHVsZS5sb2FkZWQgbmVlZGVkXG5cdFx0ZXhwb3J0czoge31cblx0fTtcblxuXHQvLyBFeGVjdXRlIHRoZSBtb2R1bGUgZnVuY3Rpb25cblx0X193ZWJwYWNrX21vZHVsZXNfX1ttb2R1bGVJZF0obW9kdWxlLCBtb2R1bGUuZXhwb3J0cywgX193ZWJwYWNrX3JlcXVpcmVfXyk7XG5cblx0Ly8gUmV0dXJuIHRoZSBleHBvcnRzIG9mIHRoZSBtb2R1bGVcblx0cmV0dXJuIG1vZHVsZS5leHBvcnRzO1xufVxuXG4iLCIvLyBnZXREZWZhdWx0RXhwb3J0IGZ1bmN0aW9uIGZvciBjb21wYXRpYmlsaXR5IHdpdGggbm9uLWhhcm1vbnkgbW9kdWxlc1xuX193ZWJwYWNrX3JlcXVpcmVfXy5uID0gKG1vZHVsZSkgPT4ge1xuXHR2YXIgZ2V0dGVyID0gbW9kdWxlICYmIG1vZHVsZS5fX2VzTW9kdWxlID9cblx0XHQoKSA9PiAobW9kdWxlWydkZWZhdWx0J10pIDpcblx0XHQoKSA9PiAobW9kdWxlKTtcblx0X193ZWJwYWNrX3JlcXVpcmVfXy5kKGdldHRlciwgeyBhOiBnZXR0ZXIgfSk7XG5cdHJldHVybiBnZXR0ZXI7XG59OyIsIi8vIGRlZmluZSBnZXR0ZXIgZnVuY3Rpb25zIGZvciBoYXJtb255IGV4cG9ydHNcbl9fd2VicGFja19yZXF1aXJlX18uZCA9IChleHBvcnRzLCBkZWZpbml0aW9uKSA9PiB7XG5cdGZvcih2YXIga2V5IGluIGRlZmluaXRpb24pIHtcblx0XHRpZihfX3dlYnBhY2tfcmVxdWlyZV9fLm8oZGVmaW5pdGlvbiwga2V5KSAmJiAhX193ZWJwYWNrX3JlcXVpcmVfXy5vKGV4cG9ydHMsIGtleSkpIHtcblx0XHRcdE9iamVjdC5kZWZpbmVQcm9wZXJ0eShleHBvcnRzLCBrZXksIHsgZW51bWVyYWJsZTogdHJ1ZSwgZ2V0OiBkZWZpbml0aW9uW2tleV0gfSk7XG5cdFx0fVxuXHR9XG59OyIsIl9fd2VicGFja19yZXF1aXJlX18ubyA9IChvYmosIHByb3ApID0+IChPYmplY3QucHJvdG90eXBlLmhhc093blByb3BlcnR5LmNhbGwob2JqLCBwcm9wKSkiLCIvLyBkZWZpbmUgX19lc01vZHVsZSBvbiBleHBvcnRzXG5fX3dlYnBhY2tfcmVxdWlyZV9fLnIgPSAoZXhwb3J0cykgPT4ge1xuXHRpZih0eXBlb2YgU3ltYm9sICE9PSAndW5kZWZpbmVkJyAmJiBTeW1ib2wudG9TdHJpbmdUYWcpIHtcblx0XHRPYmplY3QuZGVmaW5lUHJvcGVydHkoZXhwb3J0cywgU3ltYm9sLnRvU3RyaW5nVGFnLCB7IHZhbHVlOiAnTW9kdWxlJyB9KTtcblx0fVxuXHRPYmplY3QuZGVmaW5lUHJvcGVydHkoZXhwb3J0cywgJ19fZXNNb2R1bGUnLCB7IHZhbHVlOiB0cnVlIH0pO1xufTsiLCJpbXBvcnQgJy4vanMvYW5ndWxhci1tYWluLmpzJztcclxuaW1wb3J0ICcuL2VtcGxveWVlL2VtcGxveWVlcy1jb250cm9sbGVyLmpzJztcclxuaW1wb3J0ICcuL2VtcGxveWVlL2VtcGxveWVlLXNlcnZpY2UuanMnO1xyXG5pbXBvcnQgJy4vZW1wbG95ZWUvZW1wbG95ZWUtY29udGVudC1jb250cm9sbGVyLmpzJztcclxuaW1wb3J0ICcuL2VtcGxveWVlL2VtcGxveWVlLWNvbnRyb2xsZXIuanMnO1xyXG5pbXBvcnQgJy4vZW1wbG95ZWUvZW1wbG95ZWUtaHR0cC1zZXJ2aWNlLmpzJztcclxuaW1wb3J0ICcuL2VtcGxveWVlL2VtcGxveWVlLWRpcmVjdGl2ZXMuanMnO1xyXG5pbXBvcnQgJy4vZW1wbG95ZWUvZW1wbG95ZWUtaW50ZXJhY3Rpb24td2l0aC1zZXJ2ZXIuanMnO1xyXG5pbXBvcnQgJy4vZW1wbG95ZWUvZW1wbG95ZWUtcHJvY2Vzc2luZy5qcyc7XHJcbmltcG9ydCAnLi9kb2N1bWVudC9kb2N1bWVudC1jb250cm9sbGVycy5qcyc7XHJcbmltcG9ydCAnLi9kb2N1bWVudC9kb2N1bWVudC1zZXJ2aWNlLmpzJztcclxuaW1wb3J0ICcuL2RvY3VtZW50L2RvY3VtZW50LWh0dHAtc2VydmljZS5qcyc7XHJcbmltcG9ydCAnLi9kb2N1bWVudC9kb2N1bWVudC1kaXJlY3RpdmVzLmpzJztcclxuaW1wb3J0ICcuL2RvY3VtZW50L2RvY3VtZW50LWludGVyYWN0aW9uLXdpdGgtc2VydmVyLmpzJztcclxuaW1wb3J0ICcuL2RvY3VtZW50L2RvY3VtZW50LXByb2Nlc3NpbmcuanMnO1xyXG5pbXBvcnQgJy4vanMvY29udHJvbGxlcnMvbW9kYWwtaW5zdGFuY2UtY29udHJvbGxlci5qcyc7XHJcbmltcG9ydCAnLi9qcy9jb250cm9sbGVycy9pbml0aWFsaXphdGlvbi1jb250cm9sbGVyLmpzJztcclxuaW1wb3J0ICcuL3RhYnMvdGFiLWNvbnRyb2xsZXIuanMnO1xyXG5pbXBvcnQgJy4vdGFicy90YWItc2VydmljZS5qcyc7XHJcbmltcG9ydCAnLi9qcy9jb250cm9sbGVycy9tb2RhbC12aWV3LWNvbnRyb2xsZXIuanMnO1xyXG5pbXBvcnQgJy4vanMvZGlyZWN0aXZlcy9kaXJlY3RpdmVzLmpzJztcclxuaW1wb3J0ICcuL3Bvc3QvcG9zdC1zZXJ2aWNlLmpzJztcclxuaW1wb3J0ICcuL3Bvc3QvcG9zdC1odHRwLXNlcnZpY2UuanMnO1xyXG5pbXBvcnQgJy4vZGVwYXJ0bWVudC9kZXBhcnRtZW50LXNlcnZpY2UuanMnO1xyXG5pbXBvcnQgJy4vZGVwYXJ0bWVudC9kZXBhcnRtZW50LWh0dHAtc2VydmljZS5qcyc7Il0sIm5hbWVzIjpbXSwic291cmNlUm9vdCI6IiJ9