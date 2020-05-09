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

import de.codemakers.download.database.YouTubeDatabase;
import de.codemakers.download.database.entities.DatabaseEntity;

import java.time.Instant;
import java.util.List;

public class DatabaseRequester implements DatabaseEntity<DatabaseRequester, YouTubeDatabase<?>> {
    
    private transient YouTubeDatabase<?> database = null;
    protected int requesterId;
    protected String tag;
    protected String name;
    protected Instant created = Instant.now();
    
    public DatabaseRequester() {
        this(null, null);
    }
    
    public DatabaseRequester(String tag, String name) {
        this(-1, tag, name);
    }
    
    public DatabaseRequester(int requesterId, String tag, String name) {
        this.requesterId = requesterId;
        this.tag = tag;
        this.name = name;
    }
    
    public DatabaseRequester(int requesterId, String tag, String name, Instant created) {
        this(requesterId, tag, name);
        this.created = created;
    }
    
    public int getRequesterId() {
        return requesterId;
    }
    
    public DatabaseRequester setRequesterId(int requesterId) {
        this.requesterId = requesterId;
        return this;
    }
    
    public String getTag() {
        return tag;
    }
    
    public DatabaseRequester setTag(String tag) {
        this.tag = tag;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public DatabaseRequester setName(String name) {
        this.name = name;
        return this;
    }
    
    public Instant getCreated() {
        return created;
    }
    
    public DatabaseRequester setCreated(Instant created) {
        this.created = created;
        return this;
    }
    
    public List<String> getQueuedVideoIds() {
        return useDatabaseOrNull((database) -> database.getQueuedVideoIdsByRequesterId(getRequesterId()));
    }
    
    public List<DatabaseQueuedYouTubeVideo> getQueuedVideos() {
        return useDatabaseOrNull((database) -> database.getQueuedVideosByRequesterId(getRequesterId()));
    }
    
    @Override
    public DatabaseRequester setDatabase(YouTubeDatabase<?> database) {
        this.database = database;
        return this;
    }
    
    @Override
    public YouTubeDatabase<?> getDatabase() {
        return database;
    }
    
    @Override
    public DatabaseRequester loadFromDatabase() {
        final int requesterId = getRequesterId();
        if (requesterId == -2) {
            return null;
        }
        return useDatabaseOrNull((database) -> database.getRequesterByRequesterId(requesterId));
    }
    
    @Override
    public boolean save() {
        final int requesterId = getRequesterId();
        if (requesterId == -2 || !useDatabaseOrFalse((database) -> database.hasRequester(requesterId))) {
            return useDatabaseOrFalse((database) -> database.addRequester(this));
        }
        return useDatabaseOrFalse((database) -> database.setRequesterByRequesterId(this, requesterId));
    }
    
    @Override
    public void set(DatabaseRequester databaseRequester) {
        if (databaseRequester == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setRequesterId(databaseRequester.getRequesterId());
        setTag(databaseRequester.getTag());
        setName(databaseRequester.getName());
        setCreated(databaseRequester.getCreated());
    }
    
    @Override
    public String toString() {
        return "DatabaseRequester{" + "database=" + database + ", requesterId=" + requesterId + ", tag='" + tag + '\'' + ", name='" + name + '\'' + ", created=" + created + '}';
    }
    
}
