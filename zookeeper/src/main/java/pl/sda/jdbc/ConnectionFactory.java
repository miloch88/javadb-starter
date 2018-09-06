package pl.sda.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    private MysqlDataSource dataSource;

    private ConnectionFactory(String filenmae){

        try{

            Properties properties = getDataBaseProperties(filenmae);

            dataSource = new MysqlDataSource();
            dataSource.setServerName(properties.getProperty("pl.sda.jdbc.db.server"));
            dataSource.setDatabaseName(properties.getProperty("pl.sda.jdbc.db.name"));
            dataSource.setUser(properties.getProperty("pl.sda.jdbc.db.user"));
            dataSource.setPassword(properties.getProperty("pl.sda.jdbc.db.password"));
            String ps = properties.getProperty("pl.sda.jdbc.db.port");
            dataSource.setPort(Integer.parseInt(ps));
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
            dataSource.setAllowMultiQueries(false);

        } catch (SQLException e) {
            logger.error("Error during creating MysqlDataSource", e);
            return;
        }
    }


    public ConnectionFactory() {
        this("/zoo_keeper.datebase.properties");
    }

    private Properties getDataBaseProperties(String filenmae) {
        Properties properties = new Properties();
        try{
            InputStream is = ConnectionFactory.class.getResourceAsStream(filenmae);
            if(is == null){
                throw new IllegalArgumentException("Can't find file: " + filenmae);
            }
            properties.load(is);
        } catch (IOException e) {
            logger.error("Erroe during fetching properties for datebase", e);
            return null;
        }
        return properties;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
