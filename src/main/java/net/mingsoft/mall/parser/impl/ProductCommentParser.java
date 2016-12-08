package net.mingsoft.mall.parser.impl;

import com.mingsoft.parser.IParser;


/**
 * 商品评论标签（单标签）
 * {ms:field.sale}
 * @author 史爱华
 * 技术支持：景德镇铭飞科技
 * 官网：www.ming-soft.com
 */
public class ProductCommentParser extends IParser {
	/**
	 * 商品评论
	 */
	private final static String PRODUCT_FIELD_COMMENT="\\{ms:field.comment/\\}";
	
	/**
	 * 构造标签的属性
	 * @param htmlContent 原HTML代码
	 * @param newContent 替换的内容
	 */
	public ProductCommentParser(String htmlContent,String newContent){
		super.htmlCotent = htmlContent;
		super.newCotent = newContent;
	}
	@Override
	public String parse() {
		// TODO Auto-generated method stub
		return replaceAll(PRODUCT_FIELD_COMMENT);
	}
	
	
	
}
