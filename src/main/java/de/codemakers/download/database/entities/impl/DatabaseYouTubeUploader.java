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

public class DatabaseYouTubeUploader extends BasicYouTubeUploader<DatabaseYouTubeUploader, DatabaseYouTubeVideo, DatabaseYouTubePlaylist> implements DatabaseEntity<DatabaseYouTubeUploader, YouTubeDatabase<?>> {
    
    private transient YouTubeDatabase<?> database = null;
    
    public DatabaseYouTubeUploader(String uploaderId) {
        super(uploaderId);
    }
    
    public DatabaseYouTubeUploader(String uploaderId, String name) {
        super(uploaderId, name);
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
