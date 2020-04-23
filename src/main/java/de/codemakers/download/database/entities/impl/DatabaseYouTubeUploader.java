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
import de.codemakers.download.entities.impl.BasicYouTubeUploader;

import java.util.List;

public class DatabaseYouTubeUploader extends BasicYouTubeUploader<DatabaseYouTubeUploader, DatabaseYouTubeVideo, DatabaseYouTubePlaylist> implements DatabaseEntity<DatabaseYouTubeUploader, YouTubeDatabase<?>> {
    
    private transient YouTubeDatabase<?> database = null;
    
    public DatabaseYouTubeUploader(String uploaderId) {
        super(uploaderId);
    }
    
    public DatabaseYouTubeUploader(String uploaderId, String name) {
        super(uploaderId, name);
    }
    
    @Override
    public int getVideoCount() {
        return useDatabase((database) -> database.getVideoCountByUploaderId(getUploaderId()), -1);
    }
    
    @Override
    public List<String> getUploadedVideoIds() {
        return useDatabaseOrNull((database) -> database.getVideoIdsByUploaderId(getUploaderId()));
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getUploadedVideos() {
        return useDatabaseOrNull((database) -> database.getVideosByUploaderId(getUploaderId()));
    }
    
    @Override
    public boolean hasVideoUploaded(String videoId) {
        return useDatabaseOrFalse((database) -> database.uploaderUploadedVideo(getUploaderId(), videoId));
    }
    
    @Override
    public boolean hasVideoUploaded(DatabaseYouTubeVideo video) {
        if (video == null) {
            return false;
        }
        return useDatabaseOrFalse((database) -> database.uploaderUploadedVideo(getUploaderId(), video.getVideoId()));
    }
    
    @Override
    public int getPlaylistCount() {
        return useDatabase((database) -> database.getPlaylistCountByUploaderId(getUploaderId()), -1);
    }
    
    @Override
    public List<String> getCreatedPlaylistIds() {
        return useDatabaseOrNull((database) -> database.getPlaylistIdsByUploaderId(getUploaderId()));
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getCreatedPlaylists() {
        return useDatabaseOrNull((database) -> database.getPlaylistsByUploaderId(getUploaderId()));
    }
    
    @Override
    public boolean hasPlaylistCreated(String playlistId) {
        return useDatabaseOrFalse((database) -> database.uploaderCreatedPlaylist(getUploaderId(), playlistId));
    }
    
    @Override
    public boolean hasPlaylistCreated(DatabaseYouTubePlaylist playlist) {
        if (playlist == null) {
            return false;
        }
        return useDatabaseOrFalse((database) -> database.uploaderCreatedPlaylist(getUploaderId(), playlist.getPlaylistId()));
    }
    
    @Override
    public DatabaseYouTubeUploader setDatabase(YouTubeDatabase<?> database) {
        this.database = database;
        return this;
    }
    
    @Override
    public YouTubeDatabase<?> getDatabase() {
        return database;
    }
    
    @Override
    public DatabaseYouTubeUploader loadFromDatabase() {
        return useDatabaseOrNull((database) -> database.getUploaderByUploaderId(getUploaderId()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setUploaderByUploaderId(this, getUploaderId()));
    }
    
    @Override
    public void set(DatabaseYouTubeUploader databaseYouTubeUploader) {
        if (databaseYouTubeUploader == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setUploaderId(databaseYouTubeUploader.getUploaderId());
        setName(databaseYouTubeUploader.getName());
    }
    
    @Override
    public String toString() {
        return "DatabaseYouTubeUploader{" + "database=" + database + ", name='" + name + '\'' + ", source=" + source + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
}
