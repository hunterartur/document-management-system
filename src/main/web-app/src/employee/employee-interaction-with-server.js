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