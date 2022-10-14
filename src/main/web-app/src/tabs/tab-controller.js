angular.module('documentflowApp').controller('tabsController', function ($rootScope, $timeout, DocumentService, EVENT_CLOSE_TAB, EVENT_OPEN_TAB, EVENT_UPDATE_TAB,
                                           EVENT_UPDATE_TAB, PostService, DepartmentService, TabsService) {
    let scope = this;
    scope.activeTabIndex = TabsService.getIndexActiveTab();
    scope.tabs = TabsService.getAll();
    scope.openTab = openTab;
    scope.closeTab = closeTab;
    scope.selectTab = selectTab;

    $rootScope.$on(EVENT_OPEN_TAB, function (event, newTab) {
        scope.openTab(newTab);
    });

    $rootScope.$on(EVENT_CLOSE_TAB, function (event, closedTab) {
        scope.closeTab(closedTab);
    });

    $rootScope.$on(EVENT_UPDATE_TAB, function (event, updateTab) {
        closeTab(updateTab);
        openTab(updateTab);
    });

    function openTab(newTab) {
        if (!newTab.registrationNumber) {
            scope.tabs.employees = TabsService.getAll().employees;
            newTab.post = PostService.getById(newTab.post.id);
            newTab.department = DepartmentService.getById(newTab.department.id);
            newTab.employeeDocuments = DocumentService.findByAuthor(newTab.id);
        } else {
            scope.tabs.documents = TabsService.getAll().documents;
        }

        if (TabsService.findTabById(newTab.id) === undefined) {
            TabsService.openTab(newTab);
            $timeout(function () {
                TabsService.setActiveTab(newTab.id);
                scope.activeTabIndex = TabsService.getIndexActiveTab();
            });
        } else {
            TabsService.setActiveTab(newTab.id);
            scope.activeTabIndex = TabsService.getIndexActiveTab();
        }
    };

    function closeTab(closedTab) {
        TabsService.closeTab(closedTab);
        scope.activeTabIndex = TabsService.getIndexActiveTab();
        scope.tabs = TabsService.getAll();
    }

    function selectTab(tab) {
        scope.employeeDocuments = DocumentService.findByAuthor(tab.id);
    }
});