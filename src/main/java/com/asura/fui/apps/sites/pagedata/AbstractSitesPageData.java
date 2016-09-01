package com.asura.fui.apps.sites.pagedata;

import javax.servlet.http.HttpServletRequest;

import com.asura.tools.data.DataRecord;
import com.asura.tools.util.StringUtil;
import com.asura.fui.apps.sites.SiteCaches;
import com.asura.fui.apps.sites.urlstyle.UrlStyleCache;
import com.asura.fui.service.dispatch.FuiUrl;
import com.asura.fui.service.dispatch.urlstyle.IUrlStyle;
import com.asura.fui.service.page.IPageDataBuilder;
import com.asura.fui.service.page.PageData;
import com.asura.fui.FrontData;

public abstract class AbstractSitesPageData implements IPageDataBuilder {
	public PageData getBasePageData(FuiUrl url, FrontData data, HttpServletRequest request) {
		PageData pd = new PageData();

		DataRecord siteInfo = SiteCaches.getSiteInfos(url);

		String cats = SiteCaches.getCats(url, data);
		pd.addConstant("cats", cats);

		pd.addConstant("head", "首页=" + url.toUrlBase());

		pd.addConstant("htitle", siteInfo.getFieldValue("title"));
		pd.addConstant("htitle1", siteInfo.getFieldValue("title1"));

		if (StringUtil.isNullOrEmpty(siteInfo.getFieldValue("title1")))
			pd.addConstant("ctitle", siteInfo.getFieldValue("title"));
		else {
			pd.addConstant("ctitle", siteInfo.getFieldValue("title") + " | " + siteInfo.getFieldValue("title1"));
		}

		pd.addConstant("keyword", siteInfo.getFieldValue("keyword"));
		pd.addConstant("desc", siteInfo.getFieldValue("desc"));

		return pd;
	}

	public boolean canHandle(FuiUrl url, FrontData data) {
		DataRecord siteInfo = SiteCaches.getSiteInfos(url);

		IUrlStyle urlStyle = UrlStyleCache.getUrlStyle(siteInfo.getFieldValue("urlStyle"));

		return getPageType().equals(urlStyle.getUrlType(url));
	}

	public abstract String getPageType();
}
