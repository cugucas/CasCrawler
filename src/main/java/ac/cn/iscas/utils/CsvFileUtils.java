package ac.cn.iscas.utils;

import ac.cn.iscas.pipeline.CsvFilePersistentBase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class CsvFileUtils {
    public static void writeCsv(String[] header, List<String[]> rows, String filePath) throws IOException {

        CSVFormat format = CSVFormat.EXCEL.withHeader(header);
        String fullFilePath=new StringBuilder().append(filePath).
                append(File.separator).
                append(new CsvFilePersistentBase().filenameByDate()).append("_statistic.csv").toString();


        CSVPrinter csvPrinter = null;
        FileWriter fileWriter = null;
        File file = new File(fullFilePath);
        try {
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            fileWriter = new FileWriter(fullFilePath);
            //BOM的UTF-8 头 防止Excel打开乱码
            byte[] uft8bom={(byte)0xef,(byte)0xbb,(byte)0xbf};
            fileWriter.write(new String(uft8bom));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            csvPrinter = new CSVPrinter(fileWriter, format);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            csvPrinter.printRecords(rows);
            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
