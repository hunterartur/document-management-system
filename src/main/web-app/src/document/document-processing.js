angular.module('documentflowApp').factory('ProcessingDocument', function (DocumentInteractionWithServer) {
    return {
        processingDocument: function (method, object) {
            switch (method) {
                case 'POST':
                    DocumentInteractionWithServer.save(object)
                        .catch(function (error) {
                            alert('Не удалось создать документа № ' + object.registrationNumber +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
                case 'PUT':
                    DocumentInteractionWithServer.update(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить обновление документа № ' + object.registrationNumber +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
                case 'DELETE':
                    DocumentInteractionWithServer.delete(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить удаление документа №' + object.registrationNumber +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
            }
        }
    }
});