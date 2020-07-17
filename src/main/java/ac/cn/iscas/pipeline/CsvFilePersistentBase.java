package ac.cn.iscas.pipeline;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-17 10:32
 **/
public class CsvFilePersistentBase {
    protected String path;
    public static String PATH_SEPERATOR = "/";
    public CSVPrinter csvPrinter;

    static {
        String property = System.getProperties().getProperty("file.separator");
        if (property != null) {
            PATH_SEPERATOR = property;
        }

    }

    public void init(String path, String header) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withHeader(header);
        Writer writer = Files.newBufferedWriter(Paths.get(path));
        this.csvPrinter = new CSVPrinter(writer, format);
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        if (!path.endsWith(PATH_SEPERATOR)) {
            path = (new StringBuilder()).append(path).append(PATH_SEPERATOR)
                    .toString();
        }
        this.path = path;
    }
    /**
     * 创建文件
     * @param fullName
     * @return
     */
    public File getFile(String fullName) {
        checkAndMakeParentDirecotry(fullName);
        return new File(fullName);
    }
    /**
     * 创建路径
     * 检查改文件所在路径是否存在
     * @param fullName
     */
    public void checkAndMakeParentDirecotry(String fullName) {
        int index = fullName.lastIndexOf(PATH_SEPERATOR);
        if (index > 0) {
            String path = fullName.substring(0, index);
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }


    public String getPath() {
        return path;
    }
    private String filename="";
    private DateFormat bf=null;
    private Date date=null;
    /**
     * 日期格式化，作为文件名
     * @return
     */
    public String filenameByDate() {
        bf = new SimpleDateFormat("yyyy-MM-dd");
        date=new Date();
        filename=bf.format(date).replaceAll("-", "");
        return filename;
    }
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        String test=new CsvFilePersistentBase().filenameByDate();
        System.out.println(test);
    }

}
