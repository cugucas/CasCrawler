/**
 * 
 */
package ac.cn.iscas.keyword.textrank.tagger;

import java.util.List;

/**
 * @author zhangcheng
 *
 */
public interface Tagger {

	public List<Term> seg(String text);
}
