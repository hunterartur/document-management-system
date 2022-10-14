angular.module('documentflowApp').controller('employeeController', function (DocumentService) {
    let scope = this;
    scope.documents = DocumentService.findByAuthor();
    scope.sortParam = 'registrationDate';
});