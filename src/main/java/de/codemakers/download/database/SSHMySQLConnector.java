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

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import de.codemakers.base.Standard;
import de.codemakers.base.logger.Logger;
import de.codemakers.base.util.interfaces.Closeable;
import de.codemakers.io.file.AdvancedFile;

import java.sql.Connection;

public class SSHMySQLConnector extends MySQLConnector implements Closeable {
    
    private transient final JSch jSch = new JSch();
    private transient Session session = null;
    private transient int forwardedPort = -1;
    
    public SSHMySQLConnector(String host) {
        super(host);
    }
    
    public SSHMySQLConnector(Connection connection, String host) {
        super(connection, host);
    }
    
    public SSHMySQLConnector(String host, int port) {
        super(host, port);
    }
    
    public SSHMySQLConnector(Connection connection, String host, int port) {
        super(connection, host, port);
    }
    
    public SSHMySQLConnector(String host, String database) {
        super(host, database);
    }
    
    public SSHMySQLConnector(Connection connection, String host, String database) {
        super(connection, host, database);
    }
    
    public SSHMySQLConnector(String host, int port, String database) {
        super(host, port, database);
    }
    
    public SSHMySQLConnector(Connection connection, String host, int port, String database) {
        super(connection, host, port, database);
    }
    
    public Session getSession() {
        return session;
    }
    
    public SSHMySQLConnector setSession(Session session) {
        this.session = session;
        return this;
    }
    
    public int getForwardedPort() {
        return forwardedPort;
    }
    
    public SSHMySQLConnector setForwardedPort(int forwardedPort) {
        this.forwardedPort = forwardedPort;
        return this;
    }
    
    public boolean addPrivateKeyFile(AdvancedFile advancedFile, byte[] passphrase) {
        return Standard.silentError(() -> jSch.addIdentity(advancedFile.getAbsolutePath(), passphrase)) == null;
    }
    
    public boolean setKnownHosts(AdvancedFile advancedFile) {
        return Standard.silentError(() -> jSch.setKnownHosts(advancedFile.getAbsolutePath())) == null;
    }
    
    public Session createAndPrepareSession(String jumpServerUsername, String jumpServerHost) {
        setSession(Standard.silentError(() -> jSch.getSession(jumpServerUsername, jumpServerHost)));
        prepareSession(getSession());
        return session;
    }
    
    public Session createAndPrepareSession(String jumpServerUsername, String jumpServerHost, int jumpServerPort) {
        setSession(Standard.silentError(() -> jSch.getSession(jumpServerUsername, jumpServerHost, jumpServerPort)));
        prepareSession(getSession());
        return session;
    }
    
    private void prepareSession(Session session) {
        try {
            session.setConfig("server_host_key","ecdsa-sha2-nistp256"); //FIXME Hmmm, and what if the host key is ssh-rsa? (Seems, that most have switched to ecdsa-sha2-nistp256)
            session.connect();
            setForwardedPort(session.setPortForwardingL(0, getHost(), getPort()));
        } catch (Exception ex) {
            Logger.handleError(ex);
        }
    }
    
    @Override
    public void closeIntern() throws Exception {
        Standard.silentError(super::closeIntern);
        if (session != null) {
            session.disconnect();
        }
    }
    
    @Override
    Connection createConnectionIntern(String username, byte[] password) {
        return createConnection(String.format(TEMPLATE_CONNECTION_STRING, host, forwardedPort, database), username, password);
    }
    
    @Override
    public String toString() {
        return "SSHMySQLConnector{" + "jSch=" + jSch + ", session=" + session + ", forwardedPort=" + forwardedPort + ", host='" + host + '\'' + ", port=" + port + ", database='" + database + '\'' + ", connection=" + connection + '}';
    }
    
}
