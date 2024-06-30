package top.fsfsfs.basic.mybatisplus.utils;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库工具类
 *
 * @author tangyh
 * @since 2021/11/3 18:32
 */
@Slf4j
public class DbPlusUtil {
    private static final Pattern SQL_SERVER_PATTERN = Pattern.compile("jdbc(:p6spy)?:(?<db>\\w+):.*((//)|@)(?<host>.+):(?<port>\\d+)(/|(;databasename=)|:)(?<dbName>\\w+)\\??.*");
    private static final Map<String, DbType> JDBC_DB_TYPE_CACHE = new ConcurrentHashMap<>();

    @SneakyThrows
    public static String getSqlServerDbName(String url) {
        Matcher m = SQL_SERVER_PATTERN.matcher(url.toLowerCase());
        if (m.find()) {
            return m.group("dbName");
        }
        return null;
    }

    /**
     * 截取jdbc地址中的数据库名
     *
     * @param jdbcUrl jdbc 链接地址
     * @return 数据库类型
     */
    public static String getDataBaseNameByUrl(String jdbcUrl) {
        String database = null;
        int pos, pos1;
        String connUri;

        if (StrUtil.isBlank(jdbcUrl)) {
            throw new IllegalArgumentException("Invalid JDBC url.");
        }

        jdbcUrl = jdbcUrl.toLowerCase();

        if (jdbcUrl.startsWith("jdbc:impala")) {
            jdbcUrl = jdbcUrl.replace(":impala", "");
        }

        if (jdbcUrl.startsWith("jdbc:p6spy")) {
            jdbcUrl = jdbcUrl.replace(":p6spy", "");
        }

        if (!jdbcUrl.startsWith("jdbc:")
                || (pos1 = jdbcUrl.indexOf(':', 5)) == -1) {
            throw new IllegalArgumentException("Invalid JDBC url.");
        }

        final String name = ReUtil.getGroup1("jdbc:(.*?):", jdbcUrl);


        if (name.contains("mysql") || name.contains("cobar")) {
            connUri = jdbcUrl.substring(pos1 + 1);

            if (connUri.startsWith("//")) {
                if ((pos = connUri.indexOf('/', 2)) != -1) {
                    database = connUri.substring(pos + 1);
                }
            } else {
                database = connUri;
            }

            assert database != null;

            if (database.contains("?")) {
                database = database.substring(0, database.indexOf("?"));
            }

            if (database.contains(";")) {
                database = database.substring(0, database.indexOf(";"));
            }
        } else if (name.contains("sqlserver") || name.contains("microsoft")) {
            database = getSqlServerDbName(jdbcUrl);
        }

        if (StrUtil.isBlank(database)) {
            throw new IllegalArgumentException("Invalid JDBC url.");
        }
        return database;
    }


    /**
     * 不关闭 Connection,因为是从事务里获取的,sqlSession会负责关闭
     *
     * @param executor Executor
     * @return DbType
     */
    public static DbType getDbType(DataSource executor) {
        try {
            Connection conn = executor.getConnection();
            return JDBC_DB_TYPE_CACHE.computeIfAbsent(conn.getMetaData().getURL(), JdbcUtils::getDbType);
        } catch (SQLException e) {
            throw ExceptionUtils.mpe(e);
        }
    }

    /**
     * 根据连接地址判断数据库类型
     *
     * @param jdbcUrl 连接地址
     * @return ignore
     */
    public static DbType getDbType(String jdbcUrl) {
        Assert.isFalse(StringUtils.isBlank(jdbcUrl), "Error: The jdbcUrl is Null, Cannot read database type");
        String url = jdbcUrl.toLowerCase();
        if (url.contains(":mysql:") || url.contains(":cobar:")) {
            return DbType.MYSQL;
        } else if (url.contains(":mariadb:")) {
            return DbType.MARIADB;
        } else if (url.contains(":oracle:")) {
            return DbType.ORACLE;
        } else if (url.contains(":sqlserver:") || url.contains(":microsoft:")) {
            return DbType.SQL_SERVER2005;
        } else if (url.contains(":sqlserver2012:")) {
            return DbType.SQL_SERVER;
        } else if (url.contains(":postgresql:")) {
            return DbType.POSTGRE_SQL;
        } else if (url.contains(":hsqldb:")) {
            return DbType.HSQL;
        } else if (url.contains(":db2:")) {
            return DbType.DB2;
        } else if (url.contains(":sqlite:")) {
            return DbType.SQLITE;
        } else if (url.contains(":h2:")) {
            return DbType.H2;
        } else if (regexFind(":dm\\d*:", url)) {
            return DbType.DM;
        } else if (url.contains(":xugu:")) {
            return DbType.XU_GU;
        } else if (regexFind(":kingbase\\d*:", url)) {
            return DbType.KINGBASE_ES;
        } else if (url.contains(":phoenix:")) {
            return DbType.PHOENIX;
        } else if (url.contains(":zenith:")) {
            return DbType.GAUSS;
        } else if (url.contains(":gbase:")) {
            return DbType.GBASE;
        } else if (url.contains(":gbasedbt-sqli:") || url.contains(":informix-sqli:")) {
            return DbType.GBASE_8S;
        } else if (url.contains(":clickhouse:")) {
            return DbType.CLICK_HOUSE;
        } else if (url.contains(":oscar:")) {
            return DbType.OSCAR;
        } else if (url.contains(":sybase:")) {
            return DbType.SYBASE;
        } else if (url.contains(":oceanbase:")) {
            return DbType.OCEAN_BASE;
        } else if (url.contains(":highgo:")) {
            return DbType.HIGH_GO;
        } else if (url.contains(":cubrid:")) {
            return DbType.CUBRID;
        } else if (url.contains(":sap:")) {
            return DbType.SAP_HANA;
        } else if (url.contains(":impala:")) {
            return DbType.IMPALA;
        } else if (url.contains(":vertica:")) {
            return DbType.VERTICA;
        } else if (url.contains(":xcloud:")) {
            return DbType.XCloud;
        } else if (url.contains(":firebirdsql:")) {
            return DbType.FIREBIRD;
        } else {
            log.warn("The jdbcUrl is " + jdbcUrl + ", Mybatis Plus Cannot Read Database type or The Database's Not Supported!");
            return DbType.OTHER;
        }
    }


    /**
     * 正则匹配
     *
     * @param regex 正则
     * @param input 字符串
     * @return 验证成功返回 true，验证失败返回 false
     */
    public static boolean regexFind(String regex, CharSequence input) {
        if (null == input) {
            return false;
        }
        return Pattern.compile(regex).matcher(input).find();
    }
}
