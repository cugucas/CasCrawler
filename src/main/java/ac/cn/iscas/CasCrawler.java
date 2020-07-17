package ac.cn.iscas;

import ac.cn.iscas.domain.KyjzBean;
import ac.cn.iscas.page.CasPageProcess;
import ac.cn.iscas.pipeline.CsvFilePipeline;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-16 17:44
 **/
public class CasCrawler {
    public static void main(String[] args) {
        List<String> sykyPageListUrls = new ArrayList<>();
        String[] sykyPageUrls = new String[sykyPageListUrls.size()];
        sykyPageListUrls.add("http://www.cas.cn/syky/index.shtml");
        for (int i = 1; i <= 2; i++) {
            sykyPageListUrls.add(String.format("http://www.cas.cn/syky/index_%d.shtml", i));
        }
        sykyPageUrls = sykyPageListUrls.toArray(sykyPageUrls);
        Spider.create(new CasPageProcess()).addUrl(sykyPageUrls).addPipeline(new CsvFilePipeline("D:\\")).thread(1).run();
    }

}
