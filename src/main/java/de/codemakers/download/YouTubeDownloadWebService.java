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

package de.codemakers.download;

import de.codemakers.base.Standard;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.download.database.MySQLConnector;
import de.codemakers.download.database.YouTubeDatabase;

public class YouTubeDownloadWebService {
    
    private static volatile YouTubeDatabase<MySQLConnector> DATABASE = null;
    
    private static final void setDatabase(YouTubeDatabase<MySQLConnector> database) {
        YouTubeDownloadWebService.DATABASE = database;
    }
    
    public static final boolean hasDatabase() {
        return DATABASE != null;
    }
    
    public static final boolean isConnected() {
        return hasDatabase() && getDatabase().isConnected();
    }
    
    public static final YouTubeDatabase<MySQLConnector> getDatabase() {
        return DATABASE;
    }
    
    public static final void awaitDatabase() {
        while (!hasDatabase()) {
            Standard.silentSleep(100);
        }
    }
    
    public static final YouTubeDatabase<MySQLConnector> getDatabaseOrWait() {
        if (!hasDatabase()) {
            awaitDatabase();
        }
        return getDatabase();
    }
    
    public static final YouTubeDatabase<MySQLConnector> connectToDatabase(String host, String database, String username, byte[] password) {
        setDatabase(new YouTubeDatabase<>(new MySQLConnector(host, database)));
        getDatabase().start(username, password);
        return getDatabase();
    }
    
    public static final <R> R useDatabaseOrNull(ToughFunction<YouTubeDatabase<MySQLConnector>, R> function) {
        return useDatabase(function, null);
    }
    
    public static final boolean useDatabaseOrFalse(ToughFunction<YouTubeDatabase<MySQLConnector>, Boolean> function) {
        return useDatabase(function, false);
    }
    
    public static final <R> R useDatabase(ToughFunction<YouTubeDatabase<MySQLConnector>, R> function, R defaultValue) {
        if (function == null) {
            return defaultValue;
        }
        final YouTubeDatabase<MySQLConnector> database = getDatabase();
        if (database == null) {
            return defaultValue;
        }
        return function.applyWithoutException(database);
    }
    
}
