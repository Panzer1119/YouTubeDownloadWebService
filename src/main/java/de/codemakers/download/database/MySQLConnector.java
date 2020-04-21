/*
 *    Copyright 2020 Paul Hagedorn (Panzer1119)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.codemakers.download.database;

import java.sql.Connection;

public class MySQLConnector extends AbstractConnector {
    
    public static final String CLASS_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String TEMPLATE_CONNECTION_STRING = "jdbc:mysql://%s:%d/%s";
    
    public static final int DEFAULT_PORT = 3306;
    
    protected String host;
    protected int port = DEFAULT_PORT;
    protected String database;
    
    public MySQLConnector(String host, String database) {
        this.host = host;
        this.database = database;
    }
    
    public MySQLConnector(String host, int port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
    }
    
    public String getHost() {
        return host;
    }
    
    public MySQLConnector setHost(String host) {
        this.host = host;
        return this;
    }
    
    public int getPort() {
        return port;
    }
    
    public MySQLConnector setPort(int port) {
        this.port = port;
        return this;
    }
    
    public String getDatabase() {
        return database;
    }
    
    public MySQLConnector setDatabase(String database) {
        this.database = database;
        return this;
    }
    
    @Override
    Connection createConnectionIntern(String username, byte[] password) {
        return createConnection(String.format(TEMPLATE_CONNECTION_STRING, host, port, database), username, password);
    }
    
    @Override
    public String toString() {
        return "MySQLConnector{" + "host='" + host + '\'' + ", port=" + port + ", database='" + database + '\'' + ", connection=" + connection + '}';
    }
    
}
