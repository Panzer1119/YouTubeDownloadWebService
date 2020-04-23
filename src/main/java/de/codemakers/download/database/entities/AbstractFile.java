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

package de.codemakers.download.database.entities;

import de.codemakers.download.database.YouTubeDatabase;

import java.time.Instant;

public abstract class AbstractFile<T extends AbstractFile> implements DatabaseEntity<T, YouTubeDatabase<?>> {
    
    private transient YouTubeDatabase<?> database = null;
    protected String videoId;
    protected String file;
    protected String fileType;
    protected Instant created;
    
    public AbstractFile() {
        this(null, null, null);
    }
    
    public AbstractFile(String videoId, String file, String fileType) {
        this(videoId, file, fileType, Instant.now());
    }
    
    public AbstractFile(String videoId, String file, String fileType, Instant created) {
        this.videoId = videoId;
        this.file = file;
        this.fileType = fileType;
        this.created = created;
    }
    
    public String getVideoId() {
        return videoId;
    }
    
    public T setVideoId(String videoId) {
        this.videoId = videoId;
        return (T) this;
    }
    
    public String getFile() {
        return file;
    }
    
    public T setFile(String file) {
        this.file = file;
        return (T) this;
    }
    
    public String getFileType() {
        return fileType;
    }
    
    public T setFileType(String fileType) {
        this.fileType = fileType;
        return (T) this;
    }
    
    public Instant getCreated() {
        return created;
    }
    
    public T setCreated(Instant created) {
        this.created = created;
        return (T) this;
    }
    
    @Override
    public T setDatabase(YouTubeDatabase<?> database) {
        this.database = database;
        return (T) this;
    }
    
    @Override
    public YouTubeDatabase<?> getDatabase() {
        return database;
    }
    
    @Override
    public String toString() {
        return "AbstractFile{" + "database=" + database + ", videoId='" + videoId + '\'' + ", file='" + file + '\'' + ", fileType='" + fileType + '\'' + ", created=" + created + '}';
    }
    
}
