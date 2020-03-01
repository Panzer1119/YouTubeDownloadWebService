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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthorizationUtil {

    private static final Map<String, AuthorizationToken> AUTHORIZATION_TOKENS = new ConcurrentHashMap<>();
    
    public static final boolean hasToken(AuthorizationToken token) {
        return token != null && hasToken(token.getToken());
    }
    
    public static final boolean hasToken(String token) {
        return AUTHORIZATION_TOKENS.containsKey(token);
    }
    
    public static final AuthorizationToken getToken(String token) {
        return AUTHORIZATION_TOKENS.get(token);
    }
    
    public static final boolean addToken(AuthorizationToken token) {
        if (token == null) {
            return false;
        }
        return AUTHORIZATION_TOKENS.put(token.getToken(), token) != token;
    }
    
    public static final boolean isValidToken(AuthorizationToken token) {
        return token != null && isValidToken(token.getToken());
    }
    
    public static final boolean isValidToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getToken(token);
        if (authorizationToken == null) {
            return false;
        }
        return authorizationToken.isValidNow();
    }

}
