import com.asura.fui.component.bootstrap;

import com.cpkf.yyjd.tools.util.StringUtil;
import com.asura.core.component.AbstractUIComponent;
import com.asura.core.component.data.DataHeader;
import com.asura.core.component.data.IUIData;
import com.asura.core.component.layout.IUILayout;
import com.asura.core.html.HtmlDiv;
import com.asura.core.html.IHtmlElement;
import com.asura.core.html.SimpleHtml;
import com.asura.fui.FrontData;

public class BootHeader extends AbstractUIComponent {
	public IHtmlElement toHtml(IUIData data, IUILayout layout, FrontData paras) {
		DataHeader header = (DataHeader) data;

		HtmlDiv div = new HtmlDiv(paras);
		div.setClass("page-header");
		div.addStyle("overflow", "hidden");

		if (!(StringUtil.isNullOrEmpty(header.getBackPic()))) {
			div.addStyle("background", "url(" + header.getBackPic() + ") repeat top left scroll");
		}

		SimpleHtml h1 = new SimpleHtml("h1", header.getTitle(), paras);

		div.addChild(h1);

		h1.addChild(new SimpleHtml("br"));

		if (!(StringUtil.isNullOrEmpty(header.getDesc()))) {
			SimpleHtml s = new SimpleHtml("small", header.getDesc(), paras);
			h1.addChild(s);
		}

		return div;
	}
}
