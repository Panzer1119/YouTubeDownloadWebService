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

package de.codemakers.download.database.entities;

import de.codemakers.download.database.AbstractDatabase;

import java.util.List;

public abstract class AbstractRequester<T extends AbstractRequester, D extends AbstractDatabase, Q extends AbstractQueuedVideo> extends AbstractEntity<T, D> {
    
    protected int requesterId;
    protected String tag;
    protected String name;
    
    public AbstractRequester() {
        this(null, null);
    }
    
    public AbstractRequester(String tag, String name) {
        this(-2, tag, name);
    }
    
    public AbstractRequester(int requesterId, String tag, String name) {
        this.requesterId = requesterId;
        this.tag = tag;
        this.name = name;
    }
    
    public int getRequesterId() {
        return requesterId;
    }
    
    public T setRequesterId(int requesterId) {
        this.requesterId = requesterId;
        return (T) this;
    }
    
    public String getTag() {
        return tag;
    }
    
    public T setTag(String tag) {
        this.tag = tag;
        return (T) this;
    }
    
    public String getName() {
        return name;
    }
    
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }
    
    @Override
    protected T getFromDatabase() {
        if (requesterId == -2) {
            return null;
        }
        return (T) useDatabaseOrNull((database) -> database.getRequesterByRequesterId(getRequesterId()));
    }
    
    @Override
    public boolean save() {
        if (requesterId == -2 || !useDatabaseOrFalse((database) -> database.hasRequester(getRequesterId()))) {
            return useDatabaseOrFalse((database) -> database.addRequester(this));
        }
        return useDatabaseOrFalse((database) -> database.setRequesterByRequesterId(this, getRequesterId()));
    }
    
    public List<Q> getQueuedVideos() {
        return useDatabaseOrNull((database) -> database.getQueuedVideosByRequesterId(getRequesterId()));
    }
    
    public List<String> getQueuedVideoIds() {
        return useDatabaseOrNull((database) -> database.getQueuedVideoIdsByRequesterId(getRequesterId()));
    }
    
    @Override
    public String toString() {
        return "AbstractRequester{" + "requesterId=" + requesterId + ", tag='" + tag + '\'' + ", name='" + name + '\'' + '}';
    }
    
}
