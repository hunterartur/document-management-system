create table POST
(
    ID   VARCHAR(64) not null
        constraint PK_POST
            primary key,
    NAME VARCHAR(50) not null
);
create table ORGANIZATION
(
    ID             VARCHAR(64)  not null
        constraint PK_ORGANIZATION
            primary key,
    FULL_NAME      VARCHAR(100) not null,
    SHORT_NAME     VARCHAR(50)  not null,
    CONTACT_NUMBER VARCHAR(255),
    director_id VARCHAR(64)
);
create table DEPARTMENT
(
    ID              VARCHAR(64)  not null
        constraint PK_DEPARTMENT
            primary key,
    FULL_NAME       VARCHAR(100) not null,
    SHORT_NAME      VARCHAR(50)  not null,
    ORGANIZATION_ID VARCHAR(64)
        constraint ORGANIZATION_DEPARTMENT_FK
            references ORGANIZATION,
    CONTACT_NUMBER VARCHAR(255),
    director_id VARCHAR(64)
);
create table PERSON
(
    ID            VARCHAR(64) not null
        constraint PK_PERSON
            primary key,
    FIRST_NAME    VARCHAR(50) not null,
    LAST_NAME     VARCHAR(50) not null,
    PATRONYMIC    VARCHAR(70) not null,
    PHOTO         VARCHAR(255),
    BIRTHDAY      DATE        not null,
    PHONE_NUMBER  VARCHAR(20),
    POST_ID       VARCHAR(64)
        constraint POST_PERSON_FK
            references POST,
    DEPARTMENT_ID VARCHAR(64)
        constraint DEPARTMENT_PERSON_FK
            references DEPARTMENT
);
create table DOCUMENT
(
    ID                  VARCHAR(64)  not null
        constraint PK_DOCUMENT
            primary key,
    NAME                VARCHAR(100) not null,
    TEXT                VARCHAR(255) not null,
    REGISTRATION_NUMBER VARCHAR(10)  not null
        unique,
    DOCUMENT_TYPE       VARCHAR(255) not null,
    REGISTRATION_DATE   DATE         not null,
    AUTHOR              VARCHAR(64)
        constraint PERSON_DOCUMENT_FK
            references PERSON
);
create table INCOMING_DOCUMENT
(
    DOCUMENT_ID                VARCHAR(64)
        constraint DOCUMENT_INCOMING_FK
            references DOCUMENT,
    SENDER_ID                  VARCHAR(64)
        constraint SENDER_INCOMING_DOCUMENT_FK
            references PERSON,
    RECIPIENT_ID               VARCHAR(64)
        constraint RECIPIENT_INCOMING_DOCUMENT_FK
            references PERSON,
    OUTGOING_NUMBER            VARCHAR(10) not null,
    OUTGOING_REGISTRATION_DATE DATE        not null
);
create table OUTGOING_DOCUMENT
(
    DOCUMENT_ID     VARCHAR(64)
        constraint DOCUMENT_OUTGOING_FK
            references DOCUMENT,
    SENDER_ID       VARCHAR(64)
        constraint SENDER_OUTGOING_DOCUMENT_FK
            references PERSON,
    DELIVERY_METHOD VARCHAR(50) not null
);
create table TASK_DOCUMENT
(
    DOCUMENT_ID       VARCHAR(64)
        constraint DOCUMENT_TASK_FK
            references DOCUMENT,
    DATE_OF_ISSUE     DATE    not null,
    TERM_OF_EXECUTION INTEGER not null,
    EXECUTOR          VARCHAR(64)
        constraint EXECUTOR_TASK_DOCUMENT_FK
            references PERSON,
    CONTROL           BOOLEAN not null,
    CONTROLLER        VARCHAR(64)
        constraint CONTROLLER_TASK_DOCUMENT_FK
            references PERSON
);
ALTER TABLE ORGANIZATION ADD FOREIGN KEY (director_id) REFERENCES PERSON(ID);
ALTER TABLE DEPARTMENT ADD FOREIGN KEY (director_id) REFERENCES PERSON(ID);





