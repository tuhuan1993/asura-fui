import com.asura.fui.service.data;

import java.util.List;

import com.cpkf.yyjd.tools.data.DataRecord;
import com.cpkf.yyjd.tools.data.mongo.MongoHandler;
import com.cpkf.yyjd.tools.data.newmysql.ConnectionInformation;
import com.cpkf.yyjd.tools.data.newmysql.MysqlHandler;
import com.cpkf.yyjd.tools.sql.SelectSQL;
import com.cpkf.yyjd.tools.util.StringUtil;
import com.cpkf.yyjd.tools.util.cache.SimpleCache;
import com.asura.core.service.dispatch.FuiUrl;

public class DataSourceProvider {
	private static SimpleCache<String, String> cache = new SimpleCache(1000000);

	public static MysqlHandler getMysql(String server, String key) {
		String config = getConfig(server, key, "mysql");
		if (StringUtil.isNullOrEmpty(config)) {
			config = getConfig(FuiUrl.getDomain(server), key, "mysql");
		}

		if (!(StringUtil.isNullOrEmpty(config))) {
			return new MysqlHandler(ConnectionInformation.fromXml(config));
		}

		return null;
	}

	public static String getMysqlXml(String server, String key) {
		String config = getConfig(server, key, "mysql");
		if (StringUtil.isNullOrEmpty(config)) {
			config = getConfig(FuiUrl.getDomain(server), key, "mysql");
		}

		if (!(StringUtil.isNullOrEmpty(config))) {
			return config;
		}

		return null;
	}

	private static String getConfig(String server, String key, String type) {
		String s = server + key + type;
		if (!(cache.iscached(s))) {
			SelectSQL sql = new SelectSQL("datasource");
			if (!(StringUtil.isNullOrEmpty(key))) {
				sql.addWhereCondition("key", key);
			}
			sql.addWhereCondition("type", type);
			if (!(StringUtil.isNullOrEmpty(server))) {
				sql.addWhereCondition("server", server);
			}
			List list = new MysqlHandler().selectList(sql);

			if (list.size() > 0)
				cache.cache(s, ((DataRecord) list.get(0)).getFieldValue("config"), 3600);
			else {
				cache.cache(s, "");
			}
		}

		return ((String) cache.get(s));
	}

	public static String getMongoHost(String server) {
		return getMongoHost(server, "");
	}

	public static String getMongoHost(String server, String key) {
		String config = getConfig(server, key, "mongo");
		if (StringUtil.isNullOrEmpty(config)) {
			config = getConfig(FuiUrl.getDomain(server), key, "mongo");
		}

		if (!(StringUtil.isNullOrEmpty(config))) {
			return config;
		}
		config = getConfig("localhost", key, "mongo");

		return config;
	}

	public static MongoHandler getMongo(String server) {
		return getMongo(server, "");
	}

	public static MongoHandler getMongo(String server, String key) {
		String config = getConfig(server, key, "mongo");
		if (StringUtil.isNullOrEmpty(config)) {
			config = getConfig(FuiUrl.getDomain(server), key, "mongo");
		}

		if (!(StringUtil.isNullOrEmpty(config))) {
			return new MongoHandler(config);
		}
		config = getConfig("localhost", key, "mongo");

		if (!(StringUtil.isNullOrEmpty(config))) {
			return new MongoHandler(config);
		}

		return null;
	}
}
