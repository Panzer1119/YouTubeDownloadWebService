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

package de.codemakers.download.database.entities.impl;

import de.codemakers.download.entities.impl.BasicToken;
import de.codemakers.security.util.RandomUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class AuthorizationToken extends BasicToken implements Comparable<AuthorizationToken> {
    
    protected int id;
    protected final TokenLevel level;
    //
    private transient int timesUsed = 0;
    
    public AuthorizationToken(int id, String token, TokenLevel level) {
        this(id, token, level, null);
    }
    
    public AuthorizationToken(int id, String token, TokenLevel level, Duration validDuration) {
        this(id, token, level, Instant.now(), validDuration);
    }
    
    public AuthorizationToken(int id, String token, TokenLevel level, Instant created, Duration validDuration) {
        this(id, token, level, created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public AuthorizationToken(int id, String token, TokenLevel level, Instant created, Instant expiration) {
        super(token, created, expiration);
        this.id = id;
        this.level = level == null ? TokenLevel.UNKNOWN : level;
    }
    
    public int getId() {
        return id;
    }
    
    public AuthorizationToken setId(int id) {
        this.id = id;
        return this;
    }
    
    public TokenLevel getLevel() {
        return level;
    }
    
    public AuthorizationToken setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
        return this;
    }
    
    public int getTimesUsed() {
        return timesUsed;
    }
    
    @Override
    public boolean isValid(Instant timestamp) {
        if (!super.isValid(timestamp)) {
            return false;
        }
        return getLevel().hasUnlimitedUses() || getTimesUsed() < getLevel().getUses();
    }
    
    @Override
    public String toString() {
        return "AuthorizationToken{" + "id=" + id + ", level=" + level + ", timesUsed=" + timesUsed + ", created=" + created + ", expiration=" + expiration + ", token='" + token + '\'' + '}';
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final AuthorizationToken that = (AuthorizationToken) other;
        return level == that.level;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), level);
    }
    
    @Override
    public int compareTo(AuthorizationToken other) {
        return token.compareTo(other.token);
    }
    
    // // //
    // //
    //
    
    public static final AuthorizationToken generateSingleUseToken(Instant created, Duration validDuration) {
        return generateSingleUseToken(created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public static final AuthorizationToken generateSingleUseToken(Instant created, Instant expiration) {
        return new AuthorizationToken(-1, generateRandomToken(TokenLevel.SINGLE_USE), TokenLevel.SINGLE_USE, created, expiration);
    }
    
    //
    
    public static final AuthorizationToken generateUnlimitedUseToken(Instant created, Duration validDuration) {
        return generateUnlimitedUseToken(created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public static final AuthorizationToken generateUnlimitedUseToken(Instant created, Instant expiration) {
        return new AuthorizationToken(-2, generateRandomToken(TokenLevel.UNLIMITED_USE), TokenLevel.UNLIMITED_USE, created, expiration);
    }
    
    //
    
    public static final AuthorizationToken generateGranterToken(Instant created, Duration validDuration) {
        return generateGranterToken(created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public static final AuthorizationToken generateGranterToken(Instant created, Instant expiration) {
        return new AuthorizationToken(-2, generateRandomToken(TokenLevel.GRANTER), TokenLevel.GRANTER, created, expiration);
    }
    
    //
    
    public static final AuthorizationToken generateSuperGranterToken(Instant created, Duration validDuration) {
        return generateSuperGranterToken(created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public static final AuthorizationToken generateSuperGranterToken(Instant created, Instant expiration) {
        return new AuthorizationToken(-2, generateRandomToken(TokenLevel.SUPER_GRANTER), TokenLevel.SUPER_GRANTER, created, expiration);
    }
    
    //
    
    public static final AuthorizationToken generateAdminToken(Instant created, Duration validDuration) {
        return generateAdminToken(created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public static final AuthorizationToken generateAdminToken(Instant created, Instant expiration) {
        return new AuthorizationToken(-2, generateRandomToken(TokenLevel.ADMIN), TokenLevel.ADMIN, created, expiration);
    }
    
    //
    
    public static final AuthorizationToken generateRootToken(Instant created, Duration validDuration) {
        return generateRootToken(created, validDuration == null ? null : created.plus(validDuration));
    }
    
    public static final AuthorizationToken generateRootToken(Instant created, Instant expiration) {
        return new AuthorizationToken(-2, generateRandomToken(TokenLevel.ROOT), TokenLevel.ROOT, created, expiration);
    }
    
    //
    // //
    //
    
    public static final String generateRandomToken(TokenLevel level) {
        return RandomUtil.randomUrlBase64String(level.getLength());
    }
    
    public static final String generateRandomToken(int length) {
        return RandomUtil.randomUrlBase64String(length);
    }
    
    //
    // //
    // // //
    
    public enum TokenLevel {
        /**
         * Unknown
         */
        UNKNOWN(-1, 0, false, 0),
        /**
         * Has unlimited uses
         * <br>
         * Can create {@link #SINGLE_USE}, {@link #UNLIMITED_USE}, {@link #GRANTER}, {@link #SUPER_GRANTER}, {@link #ADMIN} and {@link #ROOT} Tokens
         * <br>
         * Can revoke {@link #SINGLE_USE}, {@link #UNLIMITED_USE}, {@link #GRANTER}, {@link #SUPER_GRANTER}, {@link #ADMIN} Tokens
         */
        ROOT(Integer.MAX_VALUE, 1024, true, -1),
        /**
         * Has unlimited uses
         * <br>
         * Can create {@link #SINGLE_USE}, {@link #UNLIMITED_USE}, {@link #GRANTER}, {@link #SUPER_GRANTER} Tokens
         * <br>
         * Can revoke {@link #SINGLE_USE}, {@link #UNLIMITED_USE}, {@link #GRANTER}, {@link #SUPER_GRANTER} Tokens
         */
        ADMIN(Integer.MAX_VALUE - 1, 512, true, -1),
        /**
         * Has unlimited uses
         * <br>
         * Can create {@link #SINGLE_USE}, {@link #UNLIMITED_USE} Tokens
         */
        SUPER_GRANTER(3, 256, true, -1),
        /**
         * Has unlimited uses
         * <br>
         * Can create {@link #SINGLE_USE} Tokens
         */
        GRANTER(2, 128, true, -1),
        /**
         * Has unlimited uses
         */
        UNLIMITED_USE(1, 64, false, -1),
        /**
         * Has only one use
         */
        SINGLE_USE(0, 32, false, 1);
        
        private final int level;
        private final int length;
        private final boolean grants;
        private final int uses;
        
        TokenLevel(int level, int length, boolean grants, int uses) {
            this.level = level;
            this.length = length;
            this.grants = grants;
            this.uses = uses;
        }
        
        public int getLevel() {
            return level;
        }
        
        public int getLength() {
            return length;
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
        
        public boolean isThisHigher(TokenLevel level) {
            return level == null || this.level > level.level;
        }
        
        public boolean isThisHigherOrEqual(TokenLevel level) {
            return level == null || this.level >= level.level;
        }
        
        public boolean isEqual(TokenLevel level) {
            return level != null && this.level == level.level;
        }
        
        public boolean isThisLower(TokenLevel level) {
            return level != null && this.level < level.level;
        }
        
        public boolean isThisLowerOrEqual(TokenLevel level) {
            return level != null && this.level <= level.level;
        }
        
        public static final TokenLevel ofLevel(int level) {
            for (TokenLevel level_ : values()) {
                if (level_.level == level) {
                    return level_;
                }
            }
            return UNKNOWN;
        }
    }
    
}
