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
import de.codemakers.download.entities.impl.BasicYouTubeVideo;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DatabaseYouTubeVideo extends BasicYouTubeVideo<DatabaseYouTubeVideo, DatabaseYouTubePlaylist> implements DatabaseEntity<DatabaseYouTubeVideo, YouTubeDatabase<?>> {
    
    public static final DateTimeFormatter DATE_TIME_FORMATTER_UPLOAD_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    private transient YouTubeDatabase<?> database = null;
    
    public DatabaseYouTubeVideo(String videoId) {
        super(videoId);
    }
    
    public DatabaseYouTubeVideo(YouTubeSource source) {
        super(source);
    }
    
    public DatabaseYouTubeVideo(String videoId, String uploaderId, String title, long durationMillis) {
        super(videoId, uploaderId, title, durationMillis);
    }
    
    public DatabaseYouTubeVideo(YouTubeSource source, String uploaderId, String title, long durationMillis) {
        super(source, uploaderId, title, durationMillis);
    }
    
    public DatabaseYouTubeVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long durationMillis) {
        super(videoId, channelId, uploaderId, title, altTitle, durationMillis);
    }
    
    public DatabaseYouTubeVideo(YouTubeSource source, String channelId, String uploaderId, String title, String altTitle, long durationMillis) {
        super(source, channelId, uploaderId, title, altTitle, durationMillis);
    }
    
    public Long getUploadDateAsLong() {
        if (timestamp == null) {
            return -1L;
        }
        return Long.parseLong(getUploadDateAsString());
    }
    
    public String getUploadDateAsString() {
        if (timestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault()).toLocalDate().format(DATE_TIME_FORMATTER_UPLOAD_DATE);
    }
    
    @Override
    public List<String> getPlaylistIds() {
        return useDatabaseOrNull((database) -> database.getPlaylistIdsByVideoId(getVideoId()));
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getPlaylists() {
        return useDatabaseOrNull((database) -> database.getPlaylistsByVideoId(getVideoId()));
    }
    
    @Override
    public boolean isInPlaylist(String playlistId) {
        return useDatabaseOrFalse((database) -> database.playlistContainsVideo(playlistId, getVideoId()));
    }
    
    @Override
    public boolean isInPlaylist(BasicYouTubePlaylist playlist) {
        if (playlist == null) {
            return false;
        }
        return useDatabaseOrFalse((database) -> database.playlistContainsVideo(playlist.getPlaylistId(), getVideoId()));
    }
    
    @Override
    public int getIndexInPlaylist(String playlistId) {
        return useDatabase((database) -> database.getIndexInPlaylist(playlistId, getVideoId()), -1);
    }
    
    @Override
    public int getIndexInPlaylist(BasicYouTubePlaylist playlist) {
        if (playlist == null) {
            return -1;
        }
        return useDatabase((database) -> database.getIndexInPlaylist(playlist.getPlaylistId(), getVideoId()), -1);
    }
    
    @Override
    public DatabaseYouTubeVideo setDatabase(YouTubeDatabase<?> database) {
        this.database = database;
        return this;
    }
    
    @Override
    public YouTubeDatabase<?> getDatabase() {
        return database;
    }
    
    @Override
    public DatabaseYouTubeVideo loadFromDatabase() {
        return useDatabaseOrNull((database) -> database.getVideoByVideoId(getVideoId()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setVideoByVideoId(this, getVideoId()));
    }
    
    @Override
    public void set(DatabaseYouTubeVideo databaseYouTubeVideo) {
        if (databaseYouTubeVideo == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setVideoId(databaseYouTubeVideo.getVideoId());
        setChannelId(databaseYouTubeVideo.getChannelId());
        setUploaderId(databaseYouTubeVideo.getUploaderId());
        setTitle(databaseYouTubeVideo.getTitle());
        setAltTitle(databaseYouTubeVideo.getAltTitle());
        setDurationMillis(databaseYouTubeVideo.getDurationMillis());
        setTimestamp(databaseYouTubeVideo.getTimestamp());
    }
    
    @Override
    public String toString() {
        return "DatabaseYouTubeVideo{" + "database=" + database + ", channelId='" + channelId + '\'' + ", altTitle='" + altTitle + '\'' + ", uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", durationMillis=" + durationMillis + ", source=" + source + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
    public static LocalDate uploadDateToLocalDate(String uploadDate) {
        if (uploadDate == null) {
            return null;
        }
        return LocalDate.parse(uploadDate, DATE_TIME_FORMATTER_UPLOAD_DATE);
    }
    
}
