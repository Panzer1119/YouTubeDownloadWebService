/*
 *    Copyright 2019 - 2020 Paul Hagedorn (Panzer1119)
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
 *
 */

package de.codemakers.download.database;

import de.codemakers.base.Standard;
import de.codemakers.base.action.ClosingAction;
import de.codemakers.base.logger.Logger;
import de.codemakers.base.util.interfaces.Closeable;

import java.sql.*;

public abstract class AbstractConnector implements Closeable {
    
    protected transient Connection connection;
    
    public AbstractConnector() {
        this(null);
    }
    
    public AbstractConnector(Connection connection) {
        this.connection = connection;
    }
    
    public boolean isConnected() {
        return connection != null;
    }
    
    public boolean createConnection() {
        return createConnection(null, null);
    }
    
    public boolean createConnection(String username, byte[] password) {
        if (connection != null) {
            return true;
        }
        connection = createConnectionIntern(username, password);
        return connection != null;
    }
    
    abstract Connection createConnectionIntern(String username, byte[] password);
    
    private Connection createConnectionIntern() {
        return createConnectionIntern(null, null);
    }
    
    protected boolean closeConnection() {
        if (connection == null) {
            return true;
        }
        final boolean good = Standard.silentError(connection::close) == null;
        connection = null;
        return good;
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public Statement createStatement() throws SQLException {
        if (!isConnected()) {
            return null;
        }
        return connection.createStatement();
    }
    
    public ClosingAction<Statement> createAutoClosingStatement() {
        if (!isConnected()) {
            return null;
        }
        return new ClosingAction<>(connection::createStatement);
    }
    
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (!isConnected()) {
            return null;
        }
        return connection.prepareStatement(sql);
    }
    
    @Override
    public void closeIntern() throws Exception {
        closeConnection();
    }
    
    @Override
    public String toString() {
        return "AbstractConnector{" + "connection=" + connection + '}';
    }
    
    protected static Connection createConnection(String connectionString) {
        return createConnection(connectionString, null, null);
    }
    
    protected static Connection createConnection(String connectionString, String username, byte[] password) {
        try {
            return DriverManager.getConnection(connectionString, username == null ? "" : username, (password == null || password.length == 0) ? "" : new String(password));
        } catch (Exception ex) {
            Logger.handleError(ex);
            return null;
        }
    }
    
}
