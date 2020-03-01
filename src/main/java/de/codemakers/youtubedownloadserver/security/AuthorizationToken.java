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

import de.codemakers.security.util.RandomUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class AuthorizationToken implements Comparable<AuthorizationToken> {
    
    public static final int TOKEN_LENGTH_ROOT = 512;
    public static final int TOKEN_LENGTH_GRANTER = 256;
    public static final int TOKEN_LENGTH_UNLIMITED = 128;
    public static final int TOKEN_LENGTH_SINGLE_USE = 64;
    
    private final String token;
    private final AuthorizationTokenLevel level;
    private final Instant created;
    private final Instant expiration;
    //
    private transient int used = 0;
    
    public AuthorizationToken(String token, AuthorizationTokenLevel level) {
        this(token, level, null);
    }
    
    public AuthorizationToken(String token, AuthorizationTokenLevel level, Duration validDuration) {
        this(token, level, Instant.now(), validDuration);
    }
    
    public AuthorizationToken(String token, AuthorizationTokenLevel level, Instant created, Duration validDuration) {
        this(token, level, created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public AuthorizationToken(String token, AuthorizationTokenLevel level, Instant created, Instant expiration) {
        this.token = Objects.requireNonNull(token, "token");
        this.level = level == null ? AuthorizationTokenLevel.UNKNOWN : level;
        this.created = Objects.requireNonNull(created, "created");
        this.expiration = expiration;
    }
    
    public String getToken() {
        return token;
    }
    
    public AuthorizationTokenLevel getLevel() {
        return level;
    }
    
    public Instant getCreated() {
        return created;
    }
    
    public Instant getExpiration() {
        return expiration;
    }
    
    public int getUsed() {
        return used;
    }
    
    public AuthorizationToken setUsed(int used) {
        this.used = used;
        return this;
    }
    
    public boolean isCreatedNow() {
        return isCreated(Instant.now());
    }
    
    public boolean isCreated(Instant timestamp) {
        return timestamp.isAfter(created);
    }
    
    public boolean isExpiredNow() {
        return isExpired(Instant.now());
    }
    
    public boolean isExpired(Instant timestamp) {
        return expiration != null && timestamp.isAfter(expiration);
    }
    
    public boolean isValidNow() {
        return isValid(Instant.now());
    }
    
    public boolean isValid(Instant timestamp) {
        return timestamp != null && isCreated(timestamp) && !isExpired(timestamp);
    }
    
    public AuthorizationToken createSingleUseToken(Duration validDuration) {
        return createSingleUseToken(validDuration == null ? null : Instant.now().plus(validDuration));
    }
    
    public AuthorizationToken createSingleUseToken(Instant expiration) {
        if (!level.isGranting()) {
            return null;
        }
        return new AuthorizationToken(generateRandomToken(TOKEN_LENGTH_SINGLE_USE), AuthorizationTokenLevel.SINGLE_USE, Instant.now(), expiration);
    }
    
    public AuthorizationToken createUnlimitedToken(Duration validDuration) {
        return createUnlimitedToken(validDuration == null ? null : Instant.now().plus(validDuration));
    }
    
    public AuthorizationToken createUnlimitedToken(Instant expiration) {
        if (!level.isGranting()) {
            return null;
        }
        return new AuthorizationToken(generateRandomToken(TOKEN_LENGTH_UNLIMITED), AuthorizationTokenLevel.UNLIMITED, Instant.now(), expiration);
    }
    
    public AuthorizationToken createGranterToken(Duration validDuration) {
        return createGranterToken(validDuration == null ? null : Instant.now().plus(validDuration));
    }
    
    public AuthorizationToken createGranterToken(Instant expiration) {
        if (level != AuthorizationTokenLevel.ROOT) {
            return null;
        }
        return new AuthorizationToken(generateRandomToken(TOKEN_LENGTH_GRANTER), AuthorizationTokenLevel.GRANTER, Instant.now(), expiration);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final AuthorizationToken that = (AuthorizationToken) other;
        return Objects.equals(token, that.token) && Objects.equals(level, that.level) && Objects.equals(created, that.created) && Objects.equals(expiration, that.expiration);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(token, level, created, expiration);
    }
    
    @Override
    public String toString() {
        return "AuthorizationToken{" + "token='" + token + '\'' + ", level=" + level + ", created=" + created + ", expiration=" + expiration + ", used=" + used + '}';
    }
    
    @Override
    public int compareTo(AuthorizationToken other) {
        return token.compareTo(other.token);
    }
    
    public static final String generateRandomToken(int length) {
        return RandomUtil.randomUrlBase64String(length);
    }
    
    public static AuthorizationToken createRootToken() {
        return createRootToken((Instant) null);
    }
    
    public static AuthorizationToken createRootToken(Duration validDuration) {
        return createRootToken(validDuration == null ? null : Instant.now().plus(validDuration));
    }
    
    public static AuthorizationToken createRootToken(Instant expiration) {
        return new AuthorizationToken(generateRandomToken(TOKEN_LENGTH_ROOT), AuthorizationTokenLevel.ROOT, Instant.now(), expiration);
    }
    
    public enum AuthorizationTokenLevel {
        UNKNOWN(-1, false, 0),
        ROOT(Integer.MAX_VALUE, true, -1),
        GRANTER(2, true, -1),
        UNLIMITED(1, false, -1),
        SINGLE_USE(0, false, 1);
        
        private final int level;
        private final boolean grants;
        private final int uses;
        
        AuthorizationTokenLevel(int level, boolean grants, int uses) {
            this.level = level;
            this.grants = grants;
            this.uses = uses;
        }
        
        public int getLevel() {
            return level;
        }
        
        public boolean isGranting() {
            return grants;
        }
        
        public int getUses() {
            return uses;
        }
        
        public boolean hasUnlimitedUses() {
            return uses == -1;
        }
        
        public boolean isThisHigher(AuthorizationTokenLevel level) {
            return level == null || this.level > level.level;
        }
        
        public boolean isThisHigherOrEqual(AuthorizationTokenLevel level) {
            return level == null || this.level >= level.level;
        }
        
        public boolean isEqual(AuthorizationTokenLevel level) {
            return level != null && this.level == level.level;
        }
        
        public boolean isThisLower(AuthorizationTokenLevel level) {
            return level != null && this.level < level.level;
        }
        
        public boolean isThisLowerOrEqual(AuthorizationTokenLevel level) {
            return level != null && this.level <= level.level;
        }
        
        public static final AuthorizationTokenLevel ofLevel(int level) {
            for (AuthorizationTokenLevel level_ : values()) {
                if (level_.level == level) {
                    return level_;
                }
            }
            return UNKNOWN;
        }
    }
    
}
