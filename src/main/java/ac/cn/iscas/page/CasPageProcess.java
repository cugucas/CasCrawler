package ac.cn.iscas.page;

import ac.cn.iscas.domain.InfoCenter;
import ac.cn.iscas.domain.StatisticInfo;
import ac.cn.iscas.keyword.textrank.KeywordsExtractor;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.stream.Collectors;

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
            String title = page.getHtml().xpath("/html/head/meta[@name=\"ArticleTitle\"]/@content").toString();
            String time = page.getHtml().xpath("/html/head/meta[@name=\"PubDate\"]/@content").toString();
            if (!time.contains("2020")) {
                return;
            }
            String unit = page.getHtml().xpath("/html/head/meta[@name=\"ContentSource\"]/@content").toString().replace(",中国科学院", "");
            String url = page.getUrl().toString();
            String keywords = "";
            String context = "";
            try {
                context = page.getHtml().xpath("//div[@id='_content']/text()").all().toString();
                keywords = KeywordsExtractor.getKeywords(context).stream().collect(Collectors.joining("|"));

            } catch (Exception e) {
                e.printStackTrace();
            }
            page.putField("title", title);
            page.putField("time", time);
            page.putField("unit", unit);
            page.putField("url", url);
            page.putField("context",context);
            page.putField("keywords",keywords);

            StatisticInfo statisticInfo = new StatisticInfo();
            statisticInfo.setTitle(title);
            statisticInfo.setTime(time);
            statisticInfo.setContext(context);
            statisticInfo.setUnit(unit);
            statisticInfo.setUrl(url);
            statisticInfo.setKeyword(keywords);
            InfoCenter.put(unit, statisticInfo);
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
