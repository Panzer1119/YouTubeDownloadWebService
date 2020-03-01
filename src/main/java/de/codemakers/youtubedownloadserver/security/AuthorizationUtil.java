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

package de.codemakers.youtubedownloadserver.security;

import de.codemakers.base.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Instant;

public class AuthorizationUtil {
    
    protected static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS = "authorizationToken";
    protected static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN = "token";
    protected static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_LEVEL = "level";
    protected static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_CREATED = "created";
    protected static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION = "expiration";
    protected static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_USED = "used";
    protected static final String QUERY_TABLE_AUTHORIZATION_TOKENS_SELECT_BY_TOKEN = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN);
    protected static final String QUERY_TABLE_AUTHORIZATION_TOKENS_INSERT = String.format("INSERT INTO * (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_LEVEL, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_CREATED, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION);
    protected static final String QUERY_TABLE_AUTHORIZATION_TOKENS_UPDATE_USED_BY_TOKEN = String.format("UPDATE * SET %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_USED, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN);
    
    private static PreparedStatement PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN = null;
    private static PreparedStatement PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD = null;
    private static PreparedStatement PREPARED_STATEMENT_AUTHORIZATION_TOKENS_SET_USED = null;
    
    protected static void initPreparedStatements() {
        //TODO Use the AbstractDatabase here to prepare the statements
    }
    
    public static final boolean hasToken(AuthorizationToken token) {
        return token != null && hasToken(token.getToken());
    }
    
    public static final boolean hasToken(String token) {
        synchronized (PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN) {
            if (PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN == null) {
                return false;
            }
            //TODO Improve this via the database?
            try {
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN.setString(1, token);
                final ResultSet resultSet = PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN.executeQuery();
                return resultSet.next();
            } catch (Exception ex) {
                Logger.handleError(ex);
                return false;
            }
        }
    }
    
    public static final AuthorizationToken getToken(String token) {
        synchronized (PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN) {
            if (PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN == null) {
                return null;
            }
            //TODO Improve this via the database?
            try {
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN.setString(1, token);
                final ResultSet resultSet = PREPARED_STATEMENT_AUTHORIZATION_TOKENS_GET_BY_TOKEN.executeQuery();
                if (!resultSet.next()) {
                    return null;
                }
                final AuthorizationToken authorizationToken = new AuthorizationToken(resultSet.getString(IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN), AuthorizationToken.AuthorizationTokenLevel.ofLevel(resultSet.getInt(IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_LEVEL)), Instant.ofEpochMilli(resultSet.getLong(IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_CREATED)), resultSet.getLong(IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION) == 0 ? null : Instant.ofEpochMilli(resultSet.getLong(IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION)));
                authorizationToken.setUsed(resultSet.getInt(IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_USED));
                return authorizationToken;
            } catch (Exception ex) {
                Logger.handleError(ex);
                return null;
            }
        }
    }
    
    public static final boolean addToken(AuthorizationToken token) {
        synchronized (PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD) {
            if (token == null || PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD == null) {
                return false;
            }
            //TODO Improve this via the database?
            try {
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD.setString(1, token.getToken());
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD.setInt(2, token.getLevel().getLevel());
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD.setLong(3, token.getCreated().toEpochMilli());
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD.setLong(4, token.getExpiration() == null ? 0 : token.getExpiration().toEpochMilli());
                final int affected = PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD.executeUpdate();
                return affected > 0;
            } catch (Exception ex) {
                Logger.handleError(ex);
                return false;
            }
        }
    }
    
    public static final boolean isValidToken(AuthorizationToken token) {
        return token != null && isValidToken(token.getToken());
    }
    
    public static final boolean isValidToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getToken(token);
        if (authorizationToken == null || !authorizationToken.isValidNow()) {
            return false;
        }
        return authorizationToken.getLevel().hasUnlimitedUses() || authorizationToken.getUsed() < authorizationToken.getLevel().getUses();
    }
    
    public static final boolean useToken(AuthorizationToken token) {
        return token != null && useToken(token.getToken());
    }
    
    public static final boolean useToken(String token) {
        if (token == null || token.isEmpty() || PREPARED_STATEMENT_AUTHORIZATION_TOKENS_SET_USED == null) {
            return false;
        }
        final AuthorizationToken authorizationToken = getToken(token);
        if (authorizationToken == null || !authorizationToken.isValidNow()) {
            return false;
        }
        synchronized (PREPARED_STATEMENT_AUTHORIZATION_TOKENS_SET_USED) {
            if (PREPARED_STATEMENT_AUTHORIZATION_TOKENS_SET_USED == null) {
                return false;
            }
            //TODO Improve this via the database?
            try {
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_SET_USED.setInt(1, authorizationToken.getUsed() + 1);
                PREPARED_STATEMENT_AUTHORIZATION_TOKENS_SET_USED.setString(2, authorizationToken.getToken());
                final int affected = PREPARED_STATEMENT_AUTHORIZATION_TOKENS_ADD.executeUpdate();
                return affected > 0;
            } catch (Exception ex) {
                Logger.handleError(ex);
                return false;
            }
        }
    }
    
}
