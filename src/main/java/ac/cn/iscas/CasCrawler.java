package ac.cn.iscas;

import ac.cn.iscas.domain.InfoCenter;
import ac.cn.iscas.domain.KyjzBean;
import ac.cn.iscas.domain.StatisticInfo;
import ac.cn.iscas.page.CasPageProcess;
import ac.cn.iscas.pipeline.CsvFilePipeline;
import ac.cn.iscas.utils.CsvFileUtils;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-16 17:44
 **/
public class CasCrawler {
    public static void main(String[] args) throws IOException {
        File downloadFile = new File("D:\\科研进展\\20200718_statistic.csv");
        String[] header = new String[] {"单位名称", "标题", "发布时间", "链接", "关键字", "发表篇数"};
        List<List<String>> rows = CsvFileUtils.readCsvFile(downloadFile, header, 1);
        List<StatisticInfo> objRows = new ArrayList<>();
        int pubSum = 0;
        for (List<String> row : rows) {
            StatisticInfo statisticInfo = new StatisticInfo();
            statisticInfo.setUnit(row.get(0));
            statisticInfo.setTitle(row.get(1));
            statisticInfo.setTime(row.get(2));
            statisticInfo.setUrl(row.get(3));
            statisticInfo.setKeyword(row.get(4));
            statisticInfo.setPubNum(Integer.parseInt(row.get(5)));
            pubSum += Integer.parseInt(row.get(5));
            objRows.add(statisticInfo);
        }
        Collections.sort(objRows, Collections.reverseOrder());
        Map<String, StatisticInfo> map = new LinkedHashMap<>();
        for (StatisticInfo info: objRows) {
            map.put(info.getUnit(), info);
        }

        int rank = 1;
        int order = 1;
        int latestPubNum = 0;
        String[] statheader = new String[] {"排名", "单位", "发表篇数"};
        List<String[]> statRows = new ArrayList<>();

        for(Map.Entry<String, StatisticInfo> entry : map.entrySet()) {
            String unitName = entry.getKey();
            Integer pubNum = entry.getValue().getPubNum();

            if (pubNum == latestPubNum) {
            } else {
                rank = order;
            }
            String row = String.format("[%s]\t排名第%d位\t发表文章%d篇", unitName, rank, pubNum);
            statRows.add(new String[]{String.valueOf(rank), unitName, String.valueOf(pubNum)});
            System.out.println(row);
            order++;
            latestPubNum = pubNum;
        }
        String totalTime = String.format("总计");
        String totalUnit = String.format("2020-01-01 至 2020-07-15有%d家单位发表科研进展类文章", map.size());
        String totalPaper = String.format("%d篇", objRows.size());
        statRows.add(new String[]{totalTime, totalUnit, totalPaper});
        CsvFileUtils.writeCsv(statheader, statRows, "D:\\科研进展\\统计");

        //new CasCrawler().crawSykyPages();
    }

    public void crawSykyPages() throws IOException {
        List<String> sykyPageListUrls = new ArrayList<>();
        String[] sykyPageUrls = new String[sykyPageListUrls.size()];
        sykyPageListUrls.add("http://www.cas.cn/syky/index.shtml");
        for (int i = 1; i <= 130; i++) {
            sykyPageListUrls.add(String.format("http://www.cas.cn/syky/index_%d.shtml", i));
        }
        sykyPageUrls = sykyPageListUrls.toArray(sykyPageUrls);
        Spider.create(new CasPageProcess()).addUrl(sykyPageUrls).addPipeline(new CsvFilePipeline("D:\\")).thread(1).run();

        String[] header = new String[] {"单位名称", "标题", "发布时间", "链接", "关键字", "发表篇数"};
        List<String[]> rows = new ArrayList<>();
        for (Map.Entry<String, List<StatisticInfo>> entry : InfoCenter.get().entrySet()) {
            String unitName = entry.getKey();
            List<StatisticInfo> statisticInfos = entry.getValue();
            Collections.sort(statisticInfos, Collections.reverseOrder());
            //statisticInfos.sort((o1,o2) -> o1.getTime().compareTo(o2.getTime()));
            for (StatisticInfo statisticInfo : entry.getValue()) {
                String title = statisticInfo.getTitle();
                String time = statisticInfo.getTime();
                String url = statisticInfo.getUrl();
                String keywords = statisticInfo.getKeyword();
                String pubNumber = String.valueOf(entry.getValue().size());
                String[] row = new String[] {unitName, title, time, url, keywords, pubNumber};
                rows.add(row);
            }
        }

        CsvFileUtils.writeCsv(header, rows, "D:\\科研进展");
    }

//    public int getRange(String key) {
//        List<>
//    }

}
