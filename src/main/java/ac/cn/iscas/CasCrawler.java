package ac.cn.iscas;

import ac.cn.iscas.domain.KyjzBean;
import ac.cn.iscas.page.CasPageProcess;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-16 17:44
 **/
public class CasCrawler {
    public static void main(String[] args) {
        Spider.create(new CasPageProcess()).addUrl("http://www.cas.cn/syky/index.shtml").thread(1).run();
//        OOSpider ooSpider = OOSpider.create(Site.me().setCharset("utf-8"), new ConsolePageModelPipeline(), KyjzBean.class);
//
//        KyjzBean qidian= ooSpider.get("http://www.cas.cn/syky/index.shtml");
//
//        System.out.println(qidian.getTitle());

    }

}
