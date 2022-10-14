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