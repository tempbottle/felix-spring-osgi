package org.ertuo.douche.db.hsql;

import org.hsqldb.Server;
import org.hsqldb.ServerConfiguration;
import org.hsqldb.ServerConstants;
import org.hsqldb.lib.FileUtil;
import org.hsqldb.persist.HsqlProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * hsql嵌入式启动
 * @author mo.duanm
 *
 */
@Service
public class HSQLServer extends Server implements InitializingBean {
	public static void start(String[] args) {
		String propsPath = FileUtil.getDefaultInstance()
				.canonicalOrAbsolutePath("server");
		HsqlProperties fileProps = ServerConfiguration
				.getPropertiesFromFile(propsPath);
		HsqlProperties props = fileProps == null ? new HsqlProperties()
				: fileProps;
		HsqlProperties stringProps = HsqlProperties.argArrayToProps(args,
				ServerConstants.SC_KEY_PREFIX);

		if (stringProps != null) {
			if (stringProps.getErrorKeys().length != 0) {
				printHelp("server.help");
				return;
			}
			props.addProperties(stringProps);
		}
		ServerConfiguration.translateDefaultDatabaseProperty(props);

		ServerConfiguration.translateDefaultNoSystemExitProperty(props);

		Server server = new Server();

		server.setProperties(props);

		server.start();
	}

	/**
	 * 启动 Hsqldb 服务的方法。
	 * 
	 * @param dbPath
	 *            数据库路径
	 * @param dbName
	 *            数据库名称
	 * @param port
	 *            端口号
	 */
	private static void startServer(String dbPath, String dbName, int port) {
		 
		Server server = new Server();// 它可是hsqldb.jar里面的类啊。
		server.setDatabaseName(0, dbName);
		server.setDatabasePath(0, dbPath + dbName);
		if (port != -1) {
			server.setPort(port);
		}
		server.setSilent(true);
		server.start();

	}

	public static void main(String[] arg) {

		// HSQLServer.startServer("F:/douche/db/myhsql", "douche", 9999);

		String[] args = new String[] { "-database.0", "file:mydb", "-dbname.0",
				"douche" };
		HSQLServer.start(args);
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		//HSQLServer.startServer("", "douche", 9999);
		/*String[] args = new String[] { "-database.0 mydb", "file:mydb", "-dbname.0 mydb",
				"douche" };
		HSQLServer.start(args);*/
		//System.out.println("启动HSQL完毕");

	}
}
