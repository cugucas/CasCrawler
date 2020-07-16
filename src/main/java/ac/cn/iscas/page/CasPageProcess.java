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
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

//    @Override
//    public void process(Page page) {
//        //String context = page.getHtml().xpath(".//div[@id='content']/text()").toString();
//        page.addTargetRequests(page.getHtml().xpath("//div[@id='content']/li/a/text()").all());
//        //page.addTargetRequests(page.getHtml().select(".//div[@id='content']/text()").links().regex("(https://github\\.com/\\w+/\\w+)").all());
//        //page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
//    }
    @Override
    public void process(Page page) {
        //http://www.cas.cn/syky/202007/t20200715_4753229.shtml
        if (page.getUrl().regex("http://www.cas.cn/syky/20200[1-9][a-zA-Z0-9]$").match()) {
            page.putField("Title", page.getHtml().xpath("/html/body/div[4]/div[1]/div[1]/h1/text()").toString());
            page.putField("Content",
                    page.getHtml().xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/p/text()").all().toString());
        }
        // 列表页
        //http://www.cas.cn/syky/index_1.shtml
        else if (page.getUrl().regex("^http://www\\.cas\\.cn/syky/index_[1-9][0-9]{0,2}$").match()) {
            // 文章url
            page.addTargetRequests(
                    page.getHtml().xpath("//div[@id='content']/li/a/@href").all());
            // 翻页url
            page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[5]/div[2]/div/a[8]/@href").all());
        }else {
            page.addTargetRequests(
                    page.getHtml().xpath("//div[@id='content']/li/a/@href").all());
            //page.addTargetRequest();
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
