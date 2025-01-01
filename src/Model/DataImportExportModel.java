package Model;

import java.io.*;
import java.util.*;

import DAO.DataImportExportDAOImpl;

public class DataImportExportModel {
    public DataImportExportDAOImpl dao;
    public DataImportExportModel(DataImportExportDAOImpl dao) {
        this.dao = dao;
    }
    private boolean checkFileExists(File file) {
        if(!file.exists()) {
            throw new IllegalArgumentException("File not found " + file.getPath());
        }
        return true;
    }
    private boolean checkIsFile(File file) {
        if(!file.isFile()) {
            throw new IllegalArgumentException("Not a file " + file.getPath());
        }
        return true;
    }
    private boolean checkIsReadable(File file) {
        if(!file.canRead()) {
            throw new IllegalArgumentException("File is not readable " + file.getPath());
        }
        return true;
    }
    public boolean importData(String fileName) throws IOException {
        boolean result = false;
        result = dao.importData(fileName);
        return result;
    }
    public void exportData(String fileName, List<Employee> data) throws IOException {
        dao.exportData(fileName, data);
    }
}