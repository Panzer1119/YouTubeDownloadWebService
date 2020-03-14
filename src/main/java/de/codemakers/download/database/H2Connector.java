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

import de.codemakers.base.logger.Logger;
import de.codemakers.io.file.AdvancedFile;

import java.sql.Connection;
import java.util.Objects;

public class H2Connector extends AbstractConnector {
    
    public static final String CLASS_H2_DRIVER = "org.h2.Driver";
    public static final String TEMPLATE_CONNECTION_STRING = "jdbc:h2:%s";
    
    static {
        try {
            Class.forName(CLASS_H2_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.handleError(ex);
        }
    }
    
    protected AdvancedFile databaseDirectory;
    
    public H2Connector(AdvancedFile databaseDirectory) {
        this.databaseDirectory = Objects.requireNonNull(databaseDirectory, "databaseDirectory");
    }
    
    public AdvancedFile getDatabaseDirectory() {
        return databaseDirectory;
    }
    
    public H2Connector setDatabaseDirectory(AdvancedFile databaseDirectory) {
        this.databaseDirectory = databaseDirectory;
        return this;
    }
    
    @Override
    Connection createConnectionIntern(String username, byte[] password) {
        return createConnectionFromPath(getDatabaseDirectory().getAbsolutePath(), username, password);
    }
    
    @Override
    public String toString() {
        return "H2Connector{" + "databaseDirectory=" + databaseDirectory + ", connection=" + connection + '}';
    }
    
    protected static Connection createConnectionFromPath(String path) {
        return createConnectionFromPath(path, null, null);
    }
    
    protected static Connection createConnectionFromPath(String path, String username, byte[] password) {
        return createConnection(String.format(TEMPLATE_CONNECTION_STRING, path), username, password);
    }
    
}
