angular.module('documentflowApp').factory('ProcessingEmployee', function (employeeInteractionWithServer) {
    return {
        processingEmployee: function (method, object) {
            switch (method) {
                case 'POST':
                    employeeInteractionWithServer.save(object)
                        .catch(function (error) {
                            alert('Не удалось создать пользователя ' + object.lastName +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
                case 'PUT':
                    employeeInteractionWithServer.update(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить обновление пользователя ' + object.lastName +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + value);
                        });
                    break;
                case 'DELETE':
                    employeeInteractionWithServer.delete(object)
                        .catch(function (error) {
                            alert('Не удалось выполнить удаление пользователя ' + object.lastName +
                                '\nСообщение: ' + error.message + '\nПодробнее смотри в логах');
                            console.log('errors: ' + error);
                        });
                    break;
            }
        }
    }
});