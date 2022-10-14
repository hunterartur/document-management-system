package ru.citros.documentflow.sql;

/**
 * Хранит большие большие запросы к бд
 *
 * @author AIshmaev
 */
public class SqlLargeQueryConstants {

    /**
     * Выбирает из таблицы Document все записи включая дочерние таблицы
     */
    public static final String SELECT_ALL_QUERY_FROM_BASE_DOCUMENT =
            "SELECT ID, NAME, TEXT, REGISTRATION_NUMBER, DOCUMENT_TYPE, REGISTRATION_DATE, AUTHOR, I.SENDER_ID AS INCOMING_DOCUMENT_SENDER_ID, " +
                    "RECIPIENT_ID, OUTGOING_NUMBER, OUTGOING_REGISTRATION_DATE, OD.SENDER_ID AS OUTGOING_DOCUMENT_SENDER_ID, DELIVERY_METHOD, " +
                    "DATE_OF_ISSUE, TERM_OF_EXECUTION, EXECUTOR, CONTROL, CONTROLLER " +
                    "FROM DOCUMENT " +
                    "LEFT JOIN OUTGOING_DOCUMENT OD on DOCUMENT.ID = OD.DOCUMENT_ID " +
                    "LEFT JOIN TASK_DOCUMENT TD on DOCUMENT.ID = TD.DOCUMENT_ID " +
                    "LEFT JOIN INCOMING_DOCUMENT I on DOCUMENT.ID = I.DOCUMENT_ID";
    ;

    /**
     * Выбирает из таблицы IncomingDocument все записи включая записи из базового документа
     */
    public static final String SELECT_ALL_QUERY_FROM_INCOMING_DOCUMENT =
            "SELECT DOCUMENT_ID, SENDER_ID AS INCOMING_DOCUMENT_SENDER_ID, RECIPIENT_ID, OUTGOING_NUMBER, " +
                    "OUTGOING_REGISTRATION_DATE, ID, NAME, TEXT, REGISTRATION_NUMBER, DOCUMENT_TYPE, REGISTRATION_DATE, AUTHOR " +
                    "FROM INCOMING_DOCUMENT " +
                    "JOIN DOCUMENT D on INCOMING_DOCUMENT.DOCUMENT_ID = D.ID " +
                    "WHERE DOCUMENT_ID=?";

    /**
     * Выбирает из таблицы OutgoingDocument все записи включая записи из базового документа
     */
    public static final String SELECT_ALL_QUERY_FROM_OUTGOING_DOCUMENT =
            "SELECT DOCUMENT_ID, SENDER_ID AS OUTGOING_DOCUMENT_SENDER_ID, DELIVERY_METHOD, ID, NAME, TEXT, REGISTRATION_NUMBER, " +
                    "DOCUMENT_TYPE, REGISTRATION_DATE, AUTHOR FROM OUTGOING_DOCUMENT " +
                    "JOIN DOCUMENT D on OUTGOING_DOCUMENT.DOCUMENT_ID = D.ID " +
                    "WHERE DOCUMENT_ID=?";
}
