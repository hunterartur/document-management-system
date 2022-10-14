angular.module('documentflowApp').factory('DocumentService', function (EmployeeService) {
    let documents = [];

    return {
        getAll: function () {
            return documents;
        },
        findByAuthor: function (idAuthor) {
            return documents.filter(document => document.author.id === idAuthor);
        },
        update: function (document) {
            let index = documents.findIndex(doc => doc.id === document.id);
            documents[index] = document;
        },
        delete: function (employee) {
            documents = documents.filter(document => document.id !== employee.id);
        },
        setAll: function (documentList) {
            documents = documentList;
            documents.forEach(doc => {
                doc.author = EmployeeService.getById(doc.author.id);
                if (doc.controller !== undefined) {
                    doc.controller = EmployeeService.getById(doc.controller.id);
                    doc.documentType = 'TASK_DOCUMENT';
                }
                if (doc.executor !== undefined) {
                    doc.executor = EmployeeService.getById(doc.executor.id);
                }
                if (doc.recipient !== undefined) {
                    doc.recipient = EmployeeService.getById(doc.recipient.id);
                    doc.documentType = 'INCOMING_DOCUMENT';
                }
                if (doc.sender !== undefined) {
                    doc.sender = EmployeeService.getById(doc.sender.id);
                }
                if (doc.deliveryMethod !== undefined) {
                    doc.documentType = 'OUTGOING_DOCUMENT';
                }
            });
        },
        getById: function (id) {
            return documents.find(document => document.id === id);
        },
        add: function (document) {
            if (this.getById(document.id) == null) {
                documents.push(document);
            }
        }
    };
});

angular.module('documentflowApp').factory('DeliveryMethodService', function () {
    let deliveryMethods = [];

    return {
        getAll: function () {
            return deliveryMethods;
        },
        setAll: function (deliveryMethodList) {
            deliveryMethods = deliveryMethodList;
        }
    };
});

angular.module('documentflowApp').factory('DocumentTypeService', function () {
    let documentType = [];

    return {
        getAll: function () {
            return documentType;
        },
        setAll: function (documentTypeList) {
            documentType = documentTypeList;
        }
    };
});