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

import de.codemakers.download.database.entities.AbstractFile;

import java.time.Instant;

public class DatabaseMediaFile extends AbstractFile<DatabaseMediaFile> {
    
    protected String format = null;
    protected String vcodec = null;
    protected String acodec = null;
    protected int width = -1;
    protected int height = -1;
    protected int fps = -1;
    protected int asr = -1;
    
    public DatabaseMediaFile() {
        super();
    }
    
    public DatabaseMediaFile(String videoId, String file, String fileType, String format, String vcodec, String acodec, int width, int height, int fps, int asr) {
        super(videoId, file, fileType);
        this.format = format;
        this.vcodec = vcodec;
        this.acodec = acodec;
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.asr = asr;
    }
    
    public DatabaseMediaFile(String videoId, String file, String fileType, Instant created, String format, String vcodec, String acodec, int width, int height, int fps, int asr) {
        super(videoId, file, fileType, created);
        this.format = format;
        this.vcodec = vcodec;
        this.acodec = acodec;
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.asr = asr;
    }
    
    public String getFormat() {
        return format;
    }
    
    public DatabaseMediaFile setFormat(String format) {
        this.format = format;
        return this;
    }
    
    public String getVcodec() {
        return vcodec;
    }
    
    public DatabaseMediaFile setVcodec(String vcodec) {
        this.vcodec = vcodec;
        return this;
    }
    
    public String getAcodec() {
        return acodec;
    }
    
    public DatabaseMediaFile setAcodec(String acodec) {
        this.acodec = acodec;
        return this;
    }
    
    public int getWidth() {
        return width;
    }
    
    public DatabaseMediaFile setWidth(int width) {
        this.width = width;
        return this;
    }
    
    public int getHeight() {
        return height;
    }
    
    public DatabaseMediaFile setHeight(int height) {
        this.height = height;
        return this;
    }
    
    public int getFps() {
        return fps;
    }
    
    public DatabaseMediaFile setFps(int fps) {
        this.fps = fps;
        return this;
    }
    
    public int getAsr() {
        return asr;
    }
    
    public DatabaseMediaFile setAsr(int asr) {
        this.asr = asr;
        return this;
    }
    
    @Override
    public DatabaseMediaFile loadFromDatabase() {
        return useDatabaseOrNull((database) -> database.getMediaFileByVideoIdAndFile(getVideoId(), getFile()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setMediaFileByVideoIdAndFile(this, getVideoId(), getFile()));
    }
    
    @Override
    public void set(DatabaseMediaFile databaseMediaFile) {
        if (databaseMediaFile == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setVideoId(databaseMediaFile.getVideoId());
        setFile(databaseMediaFile.getFile());
        setFileType(databaseMediaFile.getFileType());
        setCreated(databaseMediaFile.getCreated());
        setFormat(databaseMediaFile.getFormat());
        setVcodec(databaseMediaFile.getVcodec());
        setAcodec(databaseMediaFile.getAcodec());
        setWidth(databaseMediaFile.getWidth());
        setHeight(databaseMediaFile.getHeight());
        setFps(databaseMediaFile.getFps());
        setAsr(databaseMediaFile.getAsr());
    }
    
    @Override
    public String toString() {
        return "DatabaseMediaFile{" + "format='" + format + '\'' + ", vcodec='" + vcodec + '\'' + ", acodec='" + acodec + '\'' + ", width=" + width + ", height=" + height + ", fps=" + fps + ", asr=" + asr + ", videoId='" + videoId + '\'' + ", file='" + file + '\'' + ", fileType='" + fileType + '\'' + ", created=" + created + '}';
    }
    
}
