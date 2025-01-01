package DAO;

import java.io.IOException;
import java.util.List;

public interface DataImportExportDAO<T> {
    public boolean importData(String fileName) throws IOException;
    public void exportData(String fileName, List<T> data) throws IOException;
}