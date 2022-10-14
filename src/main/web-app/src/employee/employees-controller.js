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