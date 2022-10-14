package ru.citros.documentflow.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Файл конфигурации общих/разных свойств
 *
 * @author AIshmaev
 */
@ConfigurationProperties(prefix = "documentflow")
public class CommonConfigurationProperties {

    /**
     * Длина генерируемой строки
     */
    private int lengthGeneratedString;

    /**
     * Интервал дат
     */
    private int dateInterval;

    /**
     * Количество документов
     */
    private int countDocuments;

    /**
     * Путь к директории с json файлами
     */
    private String pathToJsonDirectory;

    /**
     * Путь к xml файлу c сотрудниками
     */
    private String pathToPersonXmlFile;

    /**
     * Путь к xml файлу с отделами
     */
    private String pathToDepartmentXmlFile;

    /**
     * Путь к xml файлу с организациями
     */
    private String pathToOrganizationXmlFile;

    public int getLengthGeneratedString() {
        return lengthGeneratedString;
    }

    public void setLengthGeneratedString(int lengthGeneratedString) {
        this.lengthGeneratedString = lengthGeneratedString;
    }

    public int getDateInterval() {
        return dateInterval;
    }

    public void setDateInterval(int dateInterval) {
        this.dateInterval = dateInterval;
    }

    public int getCountDocuments() {
        return countDocuments;
    }

    public void setCountDocuments(int countDocuments) {
        this.countDocuments = countDocuments;
    }

    public String getPathToJsonDirectory() {
        return pathToJsonDirectory;
    }

    public void setPathToJsonDirectory(String pathToJsonDirectory) {
        this.pathToJsonDirectory = pathToJsonDirectory;
    }

    public String getPathToPersonXmlFile() {
        return pathToPersonXmlFile;
    }

    public void setPathToPersonXmlFile(String pathToPersonXmlFile) {
        this.pathToPersonXmlFile = pathToPersonXmlFile;
    }

    public String getPathToDepartmentXmlFile() {
        return pathToDepartmentXmlFile;
    }

    public void setPathToDepartmentXmlFile(String pathToDepartmentXmlFile) {
        this.pathToDepartmentXmlFile = pathToDepartmentXmlFile;
    }

    public String getPathToOrganizationXmlFile() {
        return pathToOrganizationXmlFile;
    }

    public void setPathToOrganizationXmlFile(String pathToOrganizationXmlFile) {
        this.pathToOrganizationXmlFile = pathToOrganizationXmlFile;
    }
}
