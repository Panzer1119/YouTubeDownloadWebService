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
import de.codemakers.download.entities.impl.BasicYouTubeChannel;

import java.util.List;

public class DatabaseYouTubeChannel extends BasicYouTubeChannel<DatabaseYouTubeChannel, DatabaseYouTubeVideo> implements DatabaseEntity<DatabaseYouTubeChannel, YouTubeDatabase<?>> {
    
    private transient YouTubeDatabase<?> database = null;
    
    public DatabaseYouTubeChannel(String channelId) {
        super(channelId);
    }
    
    public DatabaseYouTubeChannel(String channelId, String name) {
        super(channelId, name);
    }
    
    @Override
    public int getVideoCount() {
        return useDatabase((database) -> database.getVideoCountByChannelId(getChannelId()), -1);
    }
    
    @Override
    public List<String> getVideoIds() {
        return useDatabaseOrNull((database) -> database.getVideoIdsByChannelId(getChannelId()));
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideos() {
        return useDatabaseOrNull((database) -> database.getVideosByChannelId(getChannelId()));
    }
    
    @Override
    public boolean hasVideo(String videoId) {
        return useDatabaseOrFalse((database) -> database.channelHasVideo(getChannelId(), videoId));
    }
    
    @Override
    public boolean hasVideo(DatabaseYouTubeVideo video) {
        if (video == null) {
            return false;
        }
        return useDatabaseOrFalse((database) -> database.channelHasVideo(getChannelId(), video.getVideoId()));
    }
    
    @Override
    public DatabaseYouTubeChannel setDatabase(YouTubeDatabase<?> database) {
        this.database = database;
        return this;
    }
    
    @Override
    public YouTubeDatabase<?> getDatabase() {
        return database;
    }
    
    @Override
    public DatabaseYouTubeChannel loadFromDatabase() {
        return useDatabaseOrNull((database) -> database.getChannelByChannelId(getChannelId()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setChannelByChannelId(this, getChannelId()));
    }
    
    @Override
    public void set(DatabaseYouTubeChannel databaseYouTubeChannel) {
        if (databaseYouTubeChannel == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setChannelId(databaseYouTubeChannel.getChannelId());
        setName(databaseYouTubeChannel.getName());
        setTimestamp(databaseYouTubeChannel.getTimestamp());
    }
    
    @Override
    public String toString() {
        return "DatabaseYouTubeChannel{" + "database=" + database + ", name='" + name + '\'' + ", source=" + source + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
}
