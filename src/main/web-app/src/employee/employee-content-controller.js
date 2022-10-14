angular.module('documentflowApp').controller('employeeContentController', function ($rootScope, EmployeeService, EVENT_UPDATE_EMPLOYEES) {
    const scope = this;
    $rootScope.$on(EVENT_UPDATE_EMPLOYEES, function (event) {
        scope.employees = EmployeeService.getAll();
    });
});