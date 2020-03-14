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

package de.codemakers.download.database.entities.impl;

import de.codemakers.download.database.YouTubeDatabase;
import de.codemakers.download.database.entities.AbstractFile;

public class MediaFile extends AbstractFile<MediaFile, YouTubeDatabase> {
    
    protected String format = null;
    protected String vcodec = null;
    protected String acodec = null;
    protected int width = -1;
    protected int height = -1;
    protected int fps = -1;
    protected int asr = -1;
    
    public MediaFile() {
        super();
    }
    
    public MediaFile(String videoId, String file, String fileType, String format, String vcodec, String acodec, int width, int height, int fps, int asr) {
        super(videoId, file, fileType);
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
    
    public MediaFile setFormat(String format) {
        this.format = format;
        return this;
    }
    
    public String getVcodec() {
        return vcodec;
    }
    
    public MediaFile setVcodec(String vcodec) {
        this.vcodec = vcodec;
        return this;
    }
    
    public String getAcodec() {
        return acodec;
    }
    
    public MediaFile setAcodec(String acodec) {
        this.acodec = acodec;
        return this;
    }
    
    public int getWidth() {
        return width;
    }
    
    public MediaFile setWidth(int width) {
        this.width = width;
        return this;
    }
    
    public int getHeight() {
        return height;
    }
    
    public MediaFile setHeight(int height) {
        this.height = height;
        return this;
    }
    
    public int getFps() {
        return fps;
    }
    
    public MediaFile setFps(int fps) {
        this.fps = fps;
        return this;
    }
    
    public int getAsr() {
        return asr;
    }
    
    public MediaFile setAsr(int asr) {
        this.asr = asr;
        return this;
    }
    
    @Override
    protected MediaFile getFromDatabase() {
        return (MediaFile) useDatabaseOrNull((database) -> database.getMediaFileByVideoIdAndFile(getVideoId(), getFile()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setMediaFileByVideoIdAndFile(this, getVideoId(), getFile()));
    }
    
    @Override
    public void set(MediaFile mediaFile) {
        if (mediaFile == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setVideoId(mediaFile.getVideoId());
        setFile(mediaFile.getFile());
        setFileType(mediaFile.getFileType());
        setFormat(mediaFile.getFormat());
        setVcodec(mediaFile.getVcodec());
        setAcodec(mediaFile.getAcodec());
        setWidth(mediaFile.getWidth());
        setHeight(mediaFile.getHeight());
        setFps(mediaFile.getFps());
        setAsr(mediaFile.getAsr());
    }
    
    @Override
    public String toString() {
        return "MediaFile{" + "format='" + format + '\'' + ", vcodec='" + vcodec + '\'' + ", acodec='" + acodec + '\'' + ", width=" + width + ", height=" + height + ", fps=" + fps + ", asr=" + asr + ", videoId='" + videoId + '\'' + ", file='" + file + '\'' + ", fileType='" + fileType + '\'' + '}';
    }
    
}
