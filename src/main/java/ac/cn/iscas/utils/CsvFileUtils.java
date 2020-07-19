package ac.cn.iscas.utils;

import ac.cn.iscas.pipeline.CsvFilePersistentBase;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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

    public static List readCsvFile(File file, String[] fileHeaders, Integer num ) {
        BufferedReader br = null;
        CSVParser csvFileParser = null;
        List list = null;
        // 创建CSVFormat（header mapping）
        CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader(fileHeaders);
        try {
            // 初始化FileReader object
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));//解决乱码问题
            // 初始化 CSVParser object
            csvFileParser = new CSVParser(br, csvFileFormat);
            // CSV文件records
            List<CSVRecord> csvRecords = csvFileParser.getRecords();

            list = new ArrayList();
            for (int i = num; i < csvRecords.size(); i++) {
                CSVRecord record = csvRecords.get(i);
                List data = new ArrayList();
                for (int j = 0; j < fileHeaders.length; j++) {
                    data.add(record.get(fileHeaders[j]));
                }
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                csvFileParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
