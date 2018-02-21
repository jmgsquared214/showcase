/*
 * Isomorphic SmartGWT web presentation layer
 * Copyright 2000 and beyond Isomorphic Software, Inc.
 *
 * OWNERSHIP NOTICE
 * Isomorphic Software owns and reserves all rights not expressly granted in this source code,
 * including all intellectual property rights to the structure, sequence, and format of this code
 * and to all designs, interfaces, algorithms, schema, protocols, and inventions expressed herein.
 *
 *  If you have any questions, please email <sourcecode@isomorphic.com>.
 *
 *  This entire comment must accompany any portion of Isomorphic Software source code that is
 *  copied or moved from this file.
 */

package com.smartgwt.sample.showcase.server.listener;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hsqldb.Server;

public class HSQLServletContextListener implements ServletContextListener {

    private static final String HSQLDB_DRIVER = "org.hsqldb.jdbcDriver";

    private static final String DEFAULT_CONFIG_FILE = "hsqlserver.properties";

    private static final String URL_PROPERTY = "hsql.url";
    private static final String USER_PROPERTY = "hsql.user";
    private static final String PORT_PROPERTY = "hsql.port";
    private static final String PASSWORD_PROPERTY = "hsql.password";
    private static final String DATA_DIR_PROPERTY = "hsql.data.dir";
    private static final String DATABASE_PROPERTY = "hsql.database";
    private static Properties properties;


    public void contextInitialized(ServletContextEvent sce) {
        properties = new Properties();
        try {
            ServletContext context = sce.getServletContext();

            String param = context.getInitParameter("HSQLDB_CONFIG");
            if (param == null || "".equals(param.trim())) {
                properties.load(this.getClass().getResourceAsStream(DEFAULT_CONFIG_FILE));
            } else {
                properties.load(new FileInputStream(new File(context.getRealPath(param))));
            }

            String databaseDir = properties.getProperty(DATA_DIR_PROPERTY);
            String database = properties.getProperty(DATABASE_PROPERTY);
            String port = properties.getProperty(PORT_PROPERTY);
            
            // Workaround: HSQLDB shifts dates according to timezone, even if the column is 
            // declared as a true "date" column, which should be timezoneless.  This code 
            // sets the local timezone to match the timezone where the sample DB was created.
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
            
            Server.main(new String[]{
                "-remote_open",
	"true",
	"-database.0", context.getRealPath(databaseDir + "/" + database),
                "-dbname.0", database,
                "--port", port != null ? port : "9001",
                "-no_system_exit", "true"
            });

        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = null;
        try {
            Class.forName(HSQLDB_DRIVER);
            connection = DriverManager.getConnection(
                    properties.getProperty(URL_PROPERTY),
                    properties.getProperty(USER_PROPERTY),
                    properties.getProperty(PASSWORD_PROPERTY));

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("SHUTDOWN;");
            stmt.close();
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.print("Cannot close database connection " + e.getMessage());
                }
            }
        }
    }
}