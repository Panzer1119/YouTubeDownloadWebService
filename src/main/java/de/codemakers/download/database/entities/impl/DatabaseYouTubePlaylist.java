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
import de.codemakers.download.entities.impl.BasicYouTubePlaylist;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.util.List;

public class DatabaseYouTubePlaylist extends BasicYouTubePlaylist<DatabaseYouTubePlaylist, DatabaseYouTubeVideo> implements DatabaseEntity<DatabaseYouTubePlaylist, YouTubeDatabase<?>> {
    
    private transient YouTubeDatabase<?> database = null;
    
    public DatabaseYouTubePlaylist(String playlistId) {
        super(playlistId);
    }
    
    public DatabaseYouTubePlaylist(YouTubeSource source) {
        super(source);
    }
    
    public DatabaseYouTubePlaylist(String playlistId, String uploaderId, String title) {
        super(playlistId, uploaderId, title);
    }
    
    public DatabaseYouTubePlaylist(YouTubeSource source, String uploaderId, String title) {
        super(source, uploaderId, title);
    }
    
    public DatabaseYouTubePlaylist(String playlistId, String uploaderId, String title, String playlist) {
        super(playlistId, uploaderId, title, playlist);
    }
    
    public DatabaseYouTubePlaylist(YouTubeSource source, String uploaderId, String title, String playlist) {
        super(source, uploaderId, title, playlist);
    }
    
    @Override
    public int getVideoCount() {
        return useDatabaseOrNull((database) -> database.getVideoCountByPlaylistId(getPlaylistId()));
    }
    
    @Override
    public List<String> getVideoIds() {
        return useDatabaseOrNull((database) -> database.getVideoIdsByPlaylistId(getPlaylistId()));
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideos() {
        return useDatabaseOrNull((database) -> database.getVideosByPlaylistId(getPlaylistId()));
    }
    
    @Override
    public boolean containsVideo(String videoId) {
        return useDatabaseOrNull((database) -> database.playlistContainsVideo(getPlaylistId(), videoId));
    }
    
    @Override
    public boolean containsVideo(DatabaseYouTubeVideo video) {
        if (video == null) {
            return false;
        }
        return useDatabaseOrNull((database) -> database.playlistContainsVideo(getPlaylistId(), video.getVideoId()));
    }
    
    @Override
    public int getIndex(String videoId) {
        return useDatabase((database) -> database.getIndexInPlaylist(getPlaylistId(), videoId), -1);
    }
    
    @Override
    public int getIndex(DatabaseYouTubeVideo video) {
        if (video == null) {
            return -1;
        }
        return useDatabase((database) -> database.getIndexInPlaylist(getPlaylistId(), video.getVideoId()), -1);
    }
    
    @Override
    public DatabaseYouTubePlaylist setDatabase(YouTubeDatabase<?> database) {
        this.database = database;
        return this;
    }
    
    @Override
    public YouTubeDatabase<?> getDatabase() {
        return database;
    }
    
    @Override
    public DatabaseYouTubePlaylist loadFromDatabase() {
        return useDatabaseOrNull((database) -> database.getPlaylistByPlaylistId(getPlaylistId()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setPlaylistByPlaylistId(this, getPlaylistId()));
    }
    
    @Override
    public void set(DatabaseYouTubePlaylist databaseYouTubePlaylist) {
        if (databaseYouTubePlaylist == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setPlaylistId(databaseYouTubePlaylist.getPlaylistId());
        setTitle(databaseYouTubePlaylist.getTitle());
        setPlaylist(databaseYouTubePlaylist.getPlaylist());
        setUploaderId(databaseYouTubePlaylist.getUploaderId());
        setTimestamp(databaseYouTubePlaylist.getTimestamp());
    }
    
    @Override
    public String toString() {
        return "DatabaseYouTubePlaylist{" + "database=" + database + ", playlist='" + playlist + '\'' + ", uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", source=" + source + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
}
