export const app = angular.module('documentflowApp', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ngResource']);
app.config(['$resourceProvider', function($resourceProvider) {
    $resourceProvider.defaults.stripTrailingSlashes = false;
}]);
app.constant('API_PATH', "http://localhost:8080/v1.0/");
app.constant('EVENT_UPDATE_DOCUMENTS', "updateDocuments");
app.constant('EVENT_UPDATE_EMPLOYEES', "updateEmployees");
app.constant('EVENT_CLOSE_TAB', "closeTab");
app.constant('EVENT_OPEN_TAB', "openTab");
app.constant('EVENT_UPDATE_TAB', "updateTab");