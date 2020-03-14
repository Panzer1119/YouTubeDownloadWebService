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
import de.codemakers.download.database.entities.AbstractQueuedVideo;
import de.codemakers.download.database.entities.QueuedVideoState;

import java.time.Instant;

public class QueuedYouTubeVideo extends AbstractQueuedVideo<QueuedYouTubeVideo, YouTubeDatabase, YouTubeVideo> {
    
    public QueuedYouTubeVideo() {
        super();
    }
    
    public QueuedYouTubeVideo(String videoId, int priority, int requesterId, String fileType) {
        this(videoId, priority, requesterId, fileType, null, null, null);
    }
    
    public QueuedYouTubeVideo(String videoId, int priority, int requesterId, String fileType, String arguments, String configFile, String outputDirectory) {
        this(videoId, priority, Instant.now(), requesterId, fileType, arguments, configFile, outputDirectory, QueuedVideoState.QUEUED);
    }
    
    public QueuedYouTubeVideo(String videoId, int priority, Instant requested, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state) {
        super(videoId, priority, requested, requesterId, fileType, arguments, configFile, outputDirectory, state);
    }
    
    public QueuedYouTubeVideo(int id, String videoId, int priority, Instant requested, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state) {
        super(id, videoId, priority, requested, requesterId, fileType, arguments, configFile, outputDirectory, state);
    }
    
    @Override
    public void set(QueuedYouTubeVideo queuedYouTubeVideo) {
        if (queuedYouTubeVideo == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setId(queuedYouTubeVideo.getId());
        setVideoId(queuedYouTubeVideo.getVideoId());
        setPriority(queuedYouTubeVideo.getPriority());
        setRequested(queuedYouTubeVideo.getRequested());
        setRequesterId(queuedYouTubeVideo.getRequesterId());
        setFileType(queuedYouTubeVideo.getFileType());
        setArguments(queuedYouTubeVideo.getArguments());
        setConfigFile(queuedYouTubeVideo.getConfigFile());
        setOutputDirectory(queuedYouTubeVideo.getOutputDirectory());
        setState(queuedYouTubeVideo.getState());
    }
    
    @Override
    public String toString() {
        return "QueuedYouTubeVideo{" + "id=" + id + ", videoId='" + videoId + '\'' + ", priority=" + priority + ", requested=" + requested + ", requesterId=" + requesterId + ", fileType='" + fileType + '\'' + ", arguments='" + arguments + '\'' + ", configFile='" + configFile + '\'' + ", outputDirectory='" + outputDirectory + '\'' + ", state=" + state + ", configFileResolved='" + configFileResolved + '\'' + ", outputDirectoryResolved='" + outputDirectoryResolved + '\'' + '}';
    }
    
}
