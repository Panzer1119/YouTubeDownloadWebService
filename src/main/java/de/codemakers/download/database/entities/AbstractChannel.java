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

public abstract class AbstractChannel<T extends AbstractChannel, D extends AbstractDatabase, V extends AbstractVideo> extends AbstractEntity<T, D> {
    
    protected String channelId;
    protected String name;
    
    public AbstractChannel() {
        this(null, null);
    }
    
    public AbstractChannel(String channelId, String name) {
        this.channelId = channelId;
        this.name = name;
    }
    
    public String getChannelId() {
        return channelId;
    }
    
    public T setChannelId(String channelId) {
        this.channelId = channelId;
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
        return (T) useDatabaseOrNull((database) -> database.getChannelByChannelId(getChannelId()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setChannelByChannelId(this, getChannelId()));
    }
    
    public List<V> getVideos() {
        return useDatabaseOrNull((database) -> database.getVideosByChannelId(getChannelId()));
    }
    
    public List<String> getVideoIds() {
        return useDatabaseOrNull((database) -> database.getVideoIdsByChannelId(getChannelId()));
    }
    
    public boolean hasVideoOnChannel(V video) {
        if (video == null) {
            return false;
        }
        return hasVideoOnChannel(video.getVideoId());
    }
    
    public boolean hasVideoOnChannel(final String videoId) {
        return useDatabaseOrFalse((database) -> database.hasVideoOnChannel(getChannelId(), videoId));
    }
    
    @Override
    public String toString() {
        return "AbstractChannel{" + "channelId='" + channelId + '\'' + ", name='" + name + '\'' + '}';
    }
    
}
