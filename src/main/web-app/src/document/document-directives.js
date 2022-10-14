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