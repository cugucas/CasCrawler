package ac.cn.iscas.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-17 10:15
 **/
public class CsvFilePipeline extends CsvFilePersistentBase implements Pipeline {
    public CsvFilePipeline() {

    }
    public CsvFilePipeline(String path) {
        setPath(path);
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.getAll().size() == 0) {
            return;
        }
        Map<String, Object> resultMap = resultItems.getAll();
        String header = resultMap.keySet().stream().collect(Collectors.joining(","));
        //String row = resultMap.values().stream().map(Object::toString).collect(Collectors.joining(","));
        Object[] row = resultMap.values().toArray(new Object[resultMap.size()]);




        if (csvPrinter == null) {
            String filepath=new StringBuilder().append(this.path).
                    append(PATH_SEPERATOR).
                    append(filenameByDate()).append(".csv").toString();
            try {
                init(filepath, header);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            csvPrinter.printRecord(row);
            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


//        String folder = String.format("%s%s", datasongConfig.getExportCsvFolder() + File.separator,
//                new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()));
//        new File(folder).mkdirs();
    }
}
