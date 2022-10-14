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
