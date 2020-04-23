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

import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.util.interfaces.StringResolver;
import de.codemakers.download.database.YouTubeDatabase;
import de.codemakers.download.database.entities.DatabaseEntity;
import de.codemakers.io.file.AdvancedFile;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class DatabaseQueuedYouTubeVideo implements DatabaseEntity<DatabaseQueuedYouTubeVideo, YouTubeDatabase<?>> {
    
    private transient YouTubeDatabase<?> database = null;
    protected int id;
    protected String videoId;
    protected int priority;
    protected Instant requested;
    protected int requesterId;
    protected String fileType;
    protected String arguments;
    protected String configFile;
    protected String outputDirectory;
    protected QueuedVideoState state;
    //
    protected transient String configFileResolved = null;
    protected transient String outputDirectoryResolved = null;
    
    public DatabaseQueuedYouTubeVideo() {
        this(null, Integer.MIN_VALUE, Instant.now(), -1, "B", null, null, null, QueuedVideoState.UNKNOWN);
    }
    
    public DatabaseQueuedYouTubeVideo(String videoId, int priority, Instant requested, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state) {
        this(-1, videoId, priority, requested, requesterId, fileType, arguments, configFile, outputDirectory, state);
    }
    
    public DatabaseQueuedYouTubeVideo(int id, String videoId, int priority, Instant requested, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state) {
        this.id = id;
        this.videoId = videoId;
        this.priority = priority;
        this.requested = requested;
        this.requesterId = requesterId;
        this.fileType = fileType;
        this.arguments = arguments;
        this.configFile = configFile;
        this.outputDirectory = outputDirectory;
        this.state = state;
    }
    
    public int getId() {
        return id;
    }
    
    public DatabaseQueuedYouTubeVideo setId(int id) {
        this.id = id;
        return this;
    }
    
    public String getVideoId() {
        return videoId;
    }
    
    public DatabaseQueuedYouTubeVideo setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public DatabaseQueuedYouTubeVideo setPriority(int priority) {
        this.priority = priority;
        return this;
    }
    
    public Instant getRequested() {
        return requested;
    }
    
    public Timestamp getRequestedAsTimestamp() {
        final Instant requested = getRequested();
        if (requested == null) {
            return null;
        }
        return Timestamp.from(requested);
    }
    
    public DatabaseQueuedYouTubeVideo setRequested(Instant requested) {
        this.requested = requested;
        return this;
    }
    
    public int getRequesterId() {
        return requesterId;
    }
    
    public DatabaseQueuedYouTubeVideo setRequesterId(int requesterId) {
        this.requesterId = requesterId;
        return this;
    }
    
    public String getFileType() {
        return fileType;
    }
    
    public DatabaseQueuedYouTubeVideo setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }
    
    public String getArguments() {
        return arguments;
    }
    
    public String getArgumentsOrEmptyString() {
        final String arguments = getArguments();
        if (arguments == null) {
            return "";
        }
        return " " + arguments;
    }
    
    public DatabaseQueuedYouTubeVideo setArguments(String arguments) {
        this.arguments = arguments;
        return this;
    }
    
    public boolean resolveStrings(StringResolver stringResolver) {
        Objects.requireNonNull(stringResolver, "stringResolver");
        if (configFile == null) {
            configFileResolved = null;
        } else {
            configFileResolved = stringResolver.resolve(configFile);
        }
        if (outputDirectory == null) {
            outputDirectoryResolved = null;
        } else {
            outputDirectoryResolved = stringResolver.resolve(outputDirectory);
        }
        //FIXME Initiate the downloading into the temp folder if the paths could not be resolved
        return ((configFile == null) == (configFileResolved == null)) && ((outputDirectory == null) == (outputDirectoryResolved == null));
    }
    
    public AdvancedFile getResolvedConfigFile() {
        if (configFile == null) {
            //return YouTubeDL.getConfigFile(); //Default Config File
            throw new NotYetImplementedRuntimeException();
        }
        if (configFileResolved == null) {
            return null;
        }
        return new AdvancedFile(configFileResolved);
    }
    
    public String getConfigFile() {
        return configFile;
    }
    
    public DatabaseQueuedYouTubeVideo setConfigFile(String configFile) {
        this.configFile = configFile;
        return this;
    }
    
    public AdvancedFile getResolvedOutputDirectory() {
        if (outputDirectory == null) {
            //FIXME What todo if there has never been an output directory set? (auto detect it with the file type?)
            return null;
        }
        if (outputDirectoryResolved == null) {
            return null;
        }
        return new AdvancedFile(outputDirectoryResolved);
    }
    
    public String getOutputDirectory() {
        return outputDirectory;
    }
    
    public DatabaseQueuedYouTubeVideo setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
        return this;
    }
    
    public QueuedVideoState getState() {
        return state;
    }
    
    public DatabaseQueuedYouTubeVideo setState(QueuedVideoState state) {
        this.state = state == null ? QueuedVideoState.UNKNOWN : state;
        return this;
    }
    
    public DatabaseYouTubeVideo asVideo() {
        return useDatabaseOrNull((database) -> database.getVideoByVideoId(getVideoId()));
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo setDatabase(YouTubeDatabase<?> database) {
        this.database = database;
        return this;
    }
    
    @Override
    public YouTubeDatabase<?> getDatabase() {
        return database;
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo loadFromDatabase() {
        if (id == -1) {
            return null;
        }
        return useDatabaseOrNull((database) -> database.getQueuedVideoById(getId()));
    }
    
    @Override
    public boolean save() {
        if (id == -1 || !useDatabaseOrFalse((database) -> database.hasQueuedVideo(id))) {
            return useDatabaseOrFalse((database) -> database.addQueuedVideo(this));
        }
        return useDatabaseOrFalse((database) -> database.setQueuedVideoById(this, getId()));
    }
    
    @Override
    public void set(DatabaseQueuedYouTubeVideo databaseQueuedYouTubeVideo) {
        if (databaseQueuedYouTubeVideo == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setId(databaseQueuedYouTubeVideo.getId());
        setVideoId(databaseQueuedYouTubeVideo.getVideoId());
        setPriority(databaseQueuedYouTubeVideo.getPriority());
        setRequested(databaseQueuedYouTubeVideo.getRequested());
        setRequesterId(databaseQueuedYouTubeVideo.getRequesterId());
        setFileType(databaseQueuedYouTubeVideo.getFileType());
        setArguments(databaseQueuedYouTubeVideo.getArguments());
        setConfigFile(databaseQueuedYouTubeVideo.getConfigFile());
        setOutputDirectory(databaseQueuedYouTubeVideo.getOutputDirectory());
        setState(databaseQueuedYouTubeVideo.getState());
    }
    
    @Override
    public String toString() {
        return "DatabaseQueuedYouTubeVideo{" + "database=" + database + ", id=" + id + ", videoId='" + videoId + '\'' + ", priority=" + priority + ", requested=" + requested + ", requesterId=" + requesterId + ", fileType='" + fileType + '\'' + ", arguments='" + arguments + '\'' + ", configFile='" + configFile + '\'' + ", outputDirectory='" + outputDirectory + '\'' + ", state=" + state + ", configFileResolved='" + configFileResolved + '\'' + ", outputDirectoryResolved='" + outputDirectoryResolved + '\'' + '}';
    }
    
}
