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