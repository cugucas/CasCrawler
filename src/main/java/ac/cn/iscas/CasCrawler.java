package ac.cn.iscas;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-16 17:44
 **/
public class CasCrawler {
    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
    }

}
