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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-16 17:44
 **/
public class CasCrawler {
    public static void main(String[] args) throws IOException {
        new CasCrawler().crawSykyPages();
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

}
