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