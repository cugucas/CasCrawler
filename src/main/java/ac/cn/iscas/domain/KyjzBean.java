package ac.cn.iscas.domain;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @program: CasCrawler
 * @description:
 * @author: LiangJian
 * @create: 2020-07-16 18:59
 **/
@TargetUrl("http://www.cas.cn/syky/")
@ExtractBy(value = "//div[@id='content']",multi = true)
public class KyjzBean {
    @ExtractBy("//li/text()")
    private String title;
//    @ExtractBy("//a[@class=grey3]/text()")
//    private String author;
//
//    @ExtractBy("//a[@class=grey2]/text()")
//    private String type;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
