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

import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.download.database.entities.impl.AuthorizationToken;

import java.time.Instant;

public abstract class AbstractDatabase<T extends AbstractDatabase, C extends AbstractConnector> {
    
    protected C connector;
    
    public AbstractDatabase(C connector) {
        this.connector = connector;
    }
    
    public T setConnector(C connector) {
        this.connector = connector;
        return (T) this;
    }
    
    public abstract boolean isConnected();
    
    public boolean start() {
        return start(null, null);
    }
    
    public abstract boolean start(String username, byte[] password);
    
    public abstract boolean stop();
    
    // SQL Gets
    
    public AuthorizationToken getAuthorizationTokenByToken(String token) {
        throw new NotYetImplementedRuntimeException();
    }
    
    // TODO
    // SQL Adds
    // TODO
    // SQL Sets
    
    public boolean setAuthorizationTokenTimesUsedByToken(String token, int timesUsed) {
        throw new NotYetImplementedRuntimeException();
    }
    
    // TODO
    // SQL Removes
    // TODO
    //
    
    public boolean isTokenValid(AuthorizationToken token) {
        return isTokenValid(token, Instant.now());
    }
    
    public boolean isTokenValid(AuthorizationToken token, Instant timestamp) {
        return token != null && isTokenValid(token.getToken(), timestamp);
    }
    
    public boolean isTokenValid(String token) {
        return isTokenValid(token, Instant.now());
    }
    
    public boolean isTokenValid(String token, Instant timestamp) {
        if (token == null || token.isEmpty() || !isConnected()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getAuthorizationTokenByToken(token);
        return authorizationToken != null && authorizationToken.isValid(timestamp);
    }
    
    public boolean useTokenOnce(AuthorizationToken token) {
        return useTokenOnce(token, Instant.now());
    }
    
    public boolean useTokenOnce(AuthorizationToken token, Instant timestamp) {
        return token != null && useTokenOnce(token.getToken(), timestamp);
    }
    
    public boolean useTokenOnce(String token) {
        return useTokenOnce(token, Instant.now());
    }
    
    public boolean useTokenOnce(String token, Instant timestamp) {
        if (token == null || token.isEmpty() || !isConnected()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getAuthorizationTokenByToken(token);
        if (authorizationToken == null || !authorizationToken.isValid(timestamp)) {
            return false;
        }
        return setAuthorizationTokenTimesUsedByToken(token, authorizationToken.getTimesUsed() + 1);
    }
    
    @Override
    public String toString() {
        return "AbstractDatabase{" + "connector=" + connector + '}';
    }
    
}
