package net.mingsoft.mall.action.people;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mall.biz.IProductImpressionBiz;
import net.mingsoft.mall.entity.ProductImpressionEntity;

/**
 * 商品印象管理控制层
 * 
 * @author 铭飞开发团队
 * @version 版本号：1.0.0<br/>
 *          创建日期：<br/>
 *          历史修订：<br/>
 */
@Controller("peopleProductImpressionAction")
@RequestMapping("/people/mall/productImpression")
public class ProductImpressionAction extends net.mingsoft.people.action.BaseAction {

	/**
	 * 注入商品印象业务层
	 */
	@Autowired
	private IProductImpressionBiz productImpressionBiz;

	/**
	 * 查询商品印象列表
	 * 
	 * @param productImpression
	 *            商品印象实体 <i>productImpression参数包含字段信息参考：</i><br/>
	 *            piBasicId 商品编号<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            [<br/>
	 *            { <br/>
	 *            piId: <br/>
	 *            piBasicId: 商品编号<br/>
	 *            piTitle: 印象<br/>
	 *            piPeopleId: 添加用户<br/>
	 *            piAmount: 数量<br/>
	 *            piDatetime: 添加时间<br/>
	 *            }<br/>
	 *            ]<br/>
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute ProductImpressionEntity productImpression, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) {
		// 验证商品编号的值是否合法
		if (StringUtil.isBlank(productImpression.getPiBasicId())) {
			this.outJson(response, null, false, getResString("err.empty", this.getResString("pi.basic.id")));
			return;
		}
		if (!StringUtil.checkLength(productImpression.getPiBasicId() + "", 1, 10)) {
			this.outJson(response, null, false,
					getResString("err.length", this.getResString("pi.basic.id"), "1", "10"));
			return;
		}
		BasicUtil.startPage();
		List productImpressionList = productImpressionBiz.query(productImpression);
		BasicUtil.endPage(productImpressionList);
		this.outJson(response, JSONArray.toJSONStringWithDateFormat(productImpressionList, "yyyy-MM-dd"));
	}

	/**
	 * 获取商品印象
	 * 
	 * @param productImpression
	 *            商品印象实体 <i>productImpression参数包含字段信息参考：</i><br/>
	 *            piId <br/>
	 *            piBasicId 商品编号<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            { <br/>
	 *            piId: <br/>
	 *            piBasicId: 商品编号<br/>
	 *            piTitle: 印象<br/>
	 *            piPeopleId: 添加用户<br/>
	 *            piAmount: 数量<br/>
	 *            piDatetime: 添加时间<br/>
	 *            }<br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute ProductImpressionEntity productImpression, HttpServletResponse response,
			HttpServletRequest request, ModelMap model) {
		if (productImpression.getPiId() <= 0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("pi.id")));
			return;
		}
		ProductImpressionEntity _productImpression = (ProductImpressionEntity) productImpressionBiz
				.getEntity(productImpression.getPiId());
		if (_productImpression.getPiPeopleId() != this.getPeopleBySession().getPeopleId()) {
			this.outJson(response, false);
		}
		model.addAttribute("productImpression", _productImpression);
		this.outJson(response, _productImpression);
	}

	/**
	 * 保存商品印象实体
	 * 
	 * @param productImpression
	 *            商品印象实体 <i>productImpression参数包含字段信息参考：</i><br/>
	 *            piBasicId 商品编号<br/>
	 *            piTitle 印象<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            { <br/>
	 *            piBasicId: 商品编号<br/>
	 *            piTitle: 印象<br/>
	 *            }<br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute ProductImpressionEntity productImpression, HttpServletResponse response,
			HttpServletRequest request) {
		// 验证商品编号的值是否合法
		if (StringUtil.isBlank(productImpression.getPiBasicId())) {
			this.outJson(response, null, false, getResString("err.empty", this.getResString("pi.basic.id")));
			return;
		}
		if (!StringUtil.checkLength(productImpression.getPiBasicId() + "", 1, 10)) {
			this.outJson(response, null, false,
					getResString("err.length", this.getResString("pi.basic.id"), "1", "10"));
			return;
		}
		// 验证印象的值是否合法
		if (StringUtil.isBlank(productImpression.getPiTitle())) {
			this.outJson(response, null, false, getResString("err.empty", this.getResString("pi.title")));
			return;
		}
		if (!StringUtil.checkLength(productImpression.getPiTitle() + "", 1, 255)) {
			this.outJson(response, null, false, getResString("err.length", this.getResString("pi.title"), "1", "255"));
			return;
		}
		productImpression.setPiPeopleId(this.getPeopleBySession().getPeopleId());
		productImpression.setPiDatetime(new Date());
		productImpressionBiz.saveEntity(productImpression);
		this.outJson(response, productImpression);
	}

	/**
	 * @param productImpression
	 *            商品印象实体 <i>productImpression参数包含字段信息参考：</i><br/>
	 *            piId:自增长编号 批量删除商品印象
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@ModelAttribute ProductImpressionEntity productImpression, HttpServletResponse response,
			HttpServletRequest request) {
		ProductImpressionEntity _productImpression = (ProductImpressionEntity) productImpressionBiz
				.getEntity(productImpression.getPiId());
		// 不是自己发布的印象或印象已经存在评价不能被删除
		if (_productImpression.getPiPeopleId() != this.getPeopleBySession().getPeopleId()
				|| _productImpression.getPiAmount() > 0) {
			this.outJson(response, false);
		}
		productImpressionBiz.deleteEntity(productImpression.getPiId());
		this.outJson(response, true);
	}

	/**
	 * 更新商品印象信息商品印象
	 * 
	 * @param productImpression
	 *            商品印象实体 <i>productImpression参数包含字段信息参考：</i><br/>
	 *            piBasicId 商品编号<br/>
	 *            piTitle 印象<br/>
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            { <br/>
	 *            piBasicId: 商品编号<br/>
	 *            piTitle: 印象<br/>
	 *            }<br/>
	 */
	@PostMapping("/update")
	@ResponseBody
	public void update(@ModelAttribute ProductImpressionEntity productImpression, HttpServletResponse response,
			HttpServletRequest request) {
		// 验证商品编号的值是否合法
		if (StringUtil.isBlank(productImpression.getPiBasicId())) {
			this.outJson(response, null, false, getResString("err.empty", this.getResString("pi.basic.id")));
			return;
		}
		if (!StringUtil.checkLength(productImpression.getPiBasicId() + "", 1, 10)) {
			this.outJson(response, null, false,
					getResString("err.length", this.getResString("pi.basic.id"), "1", "10"));
			return;
		}
		// 验证印象的值是否合法
		if (StringUtil.isBlank(productImpression.getPiTitle())) {
			this.outJson(response, null, false, getResString("err.empty", this.getResString("pi.title")));
			return;
		}
		if (!StringUtil.checkLength(productImpression.getPiTitle() + "", 1, 255)) {
			this.outJson(response, null, false, getResString("err.length", this.getResString("pi.title"), "1", "255"));
			return;
		}
		// 验证添加用户的值是否合法
		if (StringUtil.isBlank(productImpression.getPiPeopleId())) {
			this.outJson(response, null, false, getResString("err.empty", this.getResString("pi.people.id")));
			return;
		}
		if (!StringUtil.checkLength(productImpression.getPiPeopleId() + "", 1, 10)) {
			this.outJson(response, null, false,
					getResString("err.length", this.getResString("pi.people.id"), "1", "10"));
			return;
		}
		productImpression.setPiPeopleId(this.getPeopleBySession().getPeopleId());
		productImpression.setPiDatetime(new Date());
		productImpressionBiz.updateEntity(productImpression);
		this.outJson(response, productImpression);
	}

}