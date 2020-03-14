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
import de.codemakers.download.database.entities.AbstractVideo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class YouTubeVideo extends AbstractVideo<YouTubeVideo, MediaFile, ExtraFile, YouTubeDatabase, YouTubePlaylist> {
    
    public static final DateTimeFormatter DATE_TIME_FORMATTER_UPLOAD_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    protected String channelId = null;
    protected String altTitle = null;
    
    public YouTubeVideo() {
        super();
    }
    
    public YouTubeVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long durationMillis, long uploadDate) {
        this(videoId, channelId, uploaderId, title, altTitle, durationMillis, uploadDate == 0 ? null : "" + uploadDate);
    }
    
    public YouTubeVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long durationMillis, String uploadDate) {
        this(videoId, channelId, uploaderId, title, altTitle, durationMillis, uploadDateToLocalDate(uploadDate));
    }
    
    public YouTubeVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long durationMillis, LocalDate uploadDate) {
        super(videoId, uploaderId, title, durationMillis, uploadDate);
        this.channelId = channelId;
        this.altTitle = altTitle;
    }
    
    public String getChannelId() {
        return channelId;
    }
    
    public YouTubeVideo setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }
    
    public String getAltTitle() {
        return altTitle;
    }
    
    public YouTubeVideo setAltTitle(String altTitle) {
        this.altTitle = altTitle;
        return this;
    }
    
    public Long getUploadDateAsLong() {
        if (uploadDate == null) {
            return 0L;
        }
        return Long.parseLong(getUploadDateAsString());
    }
    
    public String getUploadDateAsString() {
        if (uploadDate == null) {
            return null;
        }
        return uploadDate.format(DATE_TIME_FORMATTER_UPLOAD_DATE);
    }
    
    @Override
    public void set(YouTubeVideo youTubeVideo) {
        if (youTubeVideo == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setVideoId(youTubeVideo.getVideoId());
        setChannelId(youTubeVideo.getChannelId());
        setUploaderId(youTubeVideo.getUploaderId());
        setTitle(youTubeVideo.getTitle());
        setAltTitle(youTubeVideo.getAltTitle());
        setDurationMillis(youTubeVideo.getDurationMillis());
        setUploadDate(youTubeVideo.getUploadDate());
    }
    
    @Override
    public String toString() {
        return "YouTubeVideo{" + "channelId='" + channelId + '\'' + ", altTitle='" + altTitle + '\'' + ", videoId='" + videoId + '\'' + ", uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", durationMillis=" + durationMillis + ", uploadDate=" + uploadDate + '}';
    }
    
    public static LocalDate uploadDateToLocalDate(String uploadDate) {
        if (uploadDate == null) {
            return null;
        }
        return LocalDate.parse(uploadDate, DATE_TIME_FORMATTER_UPLOAD_DATE);
    }
    
}
