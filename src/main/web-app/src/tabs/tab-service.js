angular.module('documentflowApp').factory('TabsService', function () {

    let tabs = {employees: [], documents: []};

    let activeTabIndex = -1;

    return {
        getAll: function () {
            return tabs;
        },
        openTab: function (tab) {
            if (!tab.registrationNumber) {
                tabs.employees.push(tab);
            } else {
                tabs.documents.push(tab);
            }
        },
        getIndexActiveTab: function () {
            return activeTabIndex;
        },
        setActiveTab: function (index) {
            activeTabIndex = index;
        },
        findTabById: function (id) {
            let employeeTab = tabs.employees.find(tab => tab.id === id);
            if (employeeTab !== undefined) {
                return employeeTab;
            } else {
                return tabs.documents.find(tab => tab.id === id);
            }
        },
        closeTab: function (closedTab) {
            if (!closedTab.registrationNumber) {
                let index = tabs.employees.findIndex(emp => emp.id === closedTab.id);
                tabs.employees.splice(index, 1);
                if (tabs.employees.length !== 0) {
                    if (tabs.employees.length - 1 >= index) {
                        this.setActiveTab(tabs.employees[index].id);
                    } else {
                        this.setActiveTab(tabs.employees[tabs.employees.length - 1].id);
                    }
                } else if (tabs.documents.length !== 0) {
                    this.setActiveTab(tabs.documents[0].id);
                } else {
                    this.setActiveTab(-1);
                }
            } else {
                let index = tabs.documents.findIndex(doc => doc.id === closedTab.id);
                tabs.documents.splice(index, 1);
                if (tabs.documents.length !== 0) {
                    if (tabs.documents.length - 1 >= index) {
                        this.setActiveTab(tabs.documents[index].id);
                    } else {
                        this.setActiveTab(tabs.documents[tabs.documents.length - 1].id);
                    }
                } else if (tabs.employees.length !== 0) {
                    this.setActiveTab(tabs.employees[tabs.employees.length - 1].id);
                } else {
                    this.setActiveTab(-1);
                }
            }
        }
    };
});