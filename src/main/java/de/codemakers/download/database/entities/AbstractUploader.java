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

public abstract class AbstractUploader<T extends AbstractUploader, D extends AbstractDatabase, P extends AbstractPlaylist, V extends AbstractVideo> extends AbstractEntity<T, D> {
    
    protected String uploaderId;
    protected String name;
    
    public AbstractUploader() {
        this(null, null);
    }
    
    public AbstractUploader(String uploaderId, String name) {
        this.uploaderId = uploaderId;
        this.name = name;
    }
    
    public String getUploaderId() {
        return uploaderId;
    }
    
    public T setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
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
        return (T) useDatabaseOrNull((database) -> database.getUploaderByUploaderId(getUploaderId()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setUploaderByUploaderId(this, getUploaderId()));
    }
    
    public List<V> getVideos() {
        return useDatabaseOrNull((database) -> database.getVideosByUploaderId(getUploaderId()));
    }
    
    public List<String> getVideoIds() {
        return useDatabaseOrNull((database) -> database.getVideoIdsByUploaderId(getUploaderId()));
    }
    
    public boolean hasVideoUploaded(V video) {
        if (video == null) {
            return false;
        }
        return hasVideoUploaded(video.getVideoId());
    }
    
    public boolean hasVideoUploaded(final String videoId) {
        return useDatabaseOrFalse((database) -> database.hasVideoUploaded(getUploaderId(), videoId));
    }
    
    public List<P> getPlaylists() {
        return useDatabaseOrNull((database) -> database.getPlaylistsByUploaderId(getUploaderId()));
    }
    
    public List<String> getPlaylistIds() {
        return useDatabaseOrNull((database) -> database.getPlaylistIdsByUploaderId(getUploaderId()));
    }
    
    @Override
    public String toString() {
        return "AbstractUploader{" + "uploaderId='" + uploaderId + '\'' + ", name='" + name + '\'' + '}';
    }
    
}
