package com.asura.fui.apps.sites;

import java.util.List;

import com.asura.tools.data.DataRecord;
import com.asura.tools.data.mongo.MongoHandler;
import com.asura.tools.sql.SelectSQL;
import com.asura.fui.service.data.IOneDataProvider;
import com.asura.fui.service.dispatch.FuiUrl;
import com.asura.fui.service.dispatch.urlstyle.IUrlStyle;
import com.asura.fui.FrontData;

public class SiteOneHomeDetailProvider implements IOneDataProvider {
	public void build(FrontData data, DataRecord record) {
		FuiUrl url = (FuiUrl) data.getValue("fui-url");
		String server = url.getServer();
		String host = data.getValueString("dbhost");
		String sub = FuiUrl.getSub(server);
		String domain = FuiUrl.getDomain(server);
		String db = "sites_" + domain.replace(".", "-");

		SelectSQL sql = new SelectSQL("site");
		sql.addWhereCondition("sub", sub);
		sql.setLimitCount(1);
		List temp = new MongoHandler(host).selectList(db, sql);

		if (temp.size() > 0) {
			DataRecord d = (DataRecord) temp.get(0);

			record.AddField("hometitle", d.getFieldValue("title"));
			record.AddField("hometitle1", d.getFieldValue("title1"));
			record.AddField("homekeyword", d.getFieldValue("keyword"));
			record.AddField("homedesc", d.getFieldValue("desc"));

			IUrlStyle style = (IUrlStyle) data.getValue("url-style");
			record.AddField("homeurl", style.getUrl(url, "home", d, 1));
		}
	}
}
