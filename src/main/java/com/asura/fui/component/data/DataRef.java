package com.asura.fui.component.data;

import com.cpkf.yyjd.tools.util.StringUtil;
import com.asura.fui.component.layout.IUILayout;
import com.asura.fui.html.HtmlDiv;
import com.asura.fui.html.IHtmlElement;
import com.asura.fui.service.dispatch.PageCache;
import com.asura.fui.util.ParamterUtil;
import com.asura.fui.FrontData;


public class DataRef extends AbstractUIData {
	private String ref;

	public String getRef() {
		return this.ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public IHtmlElement toElement(IUILayout layout, FrontData paras) {
		String r = ParamterUtil.getValue(this.ref, paras);

		String[] ss = StringUtil.split(r, ",");

		IUIData data = null;
		if (ss.length == 2)
			data = PageCache.getDataRef(ss[0].trim(), ss[1].trim());
		else {
			data = PageCache.getDataRef("", r);
		}

		if (data == null) {
			return new HtmlDiv();
		}

		return data.toElement(layout, paras);
	}
}