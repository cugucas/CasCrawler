package ac.cn.iscas.page;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-16 17:46
 **/
public class CasPageProcess implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(500);

    @Override
    public void process(Page page) {
        //文章页信息提取 http://www.cas.cn/syky/202007/t20200715_4753229.shtml
        if (page.getUrl().regex("^http:\\/\\/www\\.cas\\.cn\\/syky\\/2020[a-zA-Z0-9\\/\\_\\.]+$").match()) {
            page.putField("title", page.getHtml().xpath("/html/head/meta[@name=\"ArticleTitle\"]/@content").toString());
            page.putField("time", page.getHtml().xpath("/html/head/meta[@name=\"PubDate\"]/@content").toString());
            page.putField("unit", page.getHtml().xpath("/html/head/meta[@name=\"ContentSource\"]/@content").toString());
            page.putField("url", page.getUrl());
            page.putField("context",
                    page.getHtml().xpath("//div[@id='_content']/text()").all().toString());
        }
        // 列表页提取当前页内全部文章链接 http://www.cas.cn/syky/index_1.shtml
        else if (page.getUrl().regex("^http:\\/\\/www\\.cas\\.cn\\/syky\\/index[a-zA-Z0-9\\/\\_\\.]+$").match()) {
            // 文章url
            page.addTargetRequests(
                    page.getHtml().xpath("//div[@id='content']/li/a/@href").all());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
