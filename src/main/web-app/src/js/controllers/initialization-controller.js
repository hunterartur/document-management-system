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
