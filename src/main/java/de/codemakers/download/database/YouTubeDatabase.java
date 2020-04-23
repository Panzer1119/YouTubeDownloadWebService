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

package de.codemakers.download.database;

import de.codemakers.base.Standard;
import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.logger.Logger;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughSupplier;
import de.codemakers.download.database.entities.DatabaseEntity;
import de.codemakers.download.database.entities.impl.*;
import de.codemakers.io.IOUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class YouTubeDatabase<C extends AbstractConnector> extends AbstractDatabase<YouTubeDatabase, C, DatabaseYouTubeVideo, DatabaseYouTubePlaylist, DatabaseYouTubeChannel, DatabaseYouTubeUploader, DatabaseMediaFile, DatabaseExtraFile, DatabaseRequester, DatabaseQueuedYouTubeVideo> {
    
    // // Selects / Gets
    private transient PreparedStatement preparedStatement_getLastInsertId = null;
    // Table: Channel
    private transient PreparedStatement preparedStatement_getAllChannels = null;
    private transient PreparedStatement preparedStatement_getChannelByChannelId = null;
    // Table: File Extra
    private transient PreparedStatement preparedStatement_getAllFilesExtra = null;
    private transient PreparedStatement preparedStatement_getFilesExtraByVideoId = null;
    private transient PreparedStatement preparedStatement_getFilesExtraByFileType = null;
    private transient PreparedStatement preparedStatement_getFileExtraByVideoIdAndFile = null;
    // Table: File Logs
    private transient PreparedStatement preparedStatement_getAllFileLogs = null;
    private transient PreparedStatement preparedStatement_getFilesLogsByVideoId = null;
    private transient PreparedStatement preparedStatement_getFilesLogsByLogId = null;
    private transient PreparedStatement preparedStatement_getFileLogByVideoIdAndFileAndLogIdAndIsMediaFile = null;
    // Table: File Media
    private transient PreparedStatement preparedStatement_getAllFilesMedia = null;
    private transient PreparedStatement preparedStatement_getFilesMediaByVideoId = null;
    private transient PreparedStatement preparedStatement_getFilesMediaByFileType = null;
    private transient PreparedStatement preparedStatement_getFileMediaByVideoIdAndFile = null;
    // Table: Log
    private transient PreparedStatement preparedStatement_getAllLogs = null;
    private transient PreparedStatement preparedStatement_getLogByLogId = null;
    // Table: Playlist
    private transient PreparedStatement preparedStatement_getAllPlaylists = null;
    private transient PreparedStatement preparedStatement_getPlaylistByPlaylistId = null;
    private transient PreparedStatement preparedStatement_getPlaylistsByUploaderId = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_getAllPlaylistVideos = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideosByPlaylistId = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId = null;
    // Table: Requester
    private transient PreparedStatement preparedStatement_getAllRequesters = null;
    private transient PreparedStatement preparedStatement_getRequesterByRequesterId = null;
    private transient PreparedStatement preparedStatement_getRequesterByTag = null;
    // Table: Token
    private transient PreparedStatement preparedStatement_getAllTokens = null;
    private transient PreparedStatement preparedStatement_getTokenByToken = null;
    // Table: Uploader
    private transient PreparedStatement preparedStatement_getAllUploaders = null;
    private transient PreparedStatement preparedStatement_getUploaderByUploaderId = null;
    // Table: Video
    private transient PreparedStatement preparedStatement_getAllVideos = null;
    private transient PreparedStatement preparedStatement_getVideoByVideoId = null;
    private transient PreparedStatement preparedStatement_getVideosByChannelId = null;
    private transient PreparedStatement preparedStatement_getVideosByUploaderId = null;
    // Table: Video Logs
    private transient PreparedStatement preparedStatement_getAllVideoLogs = null;
    private transient PreparedStatement preparedStatement_getVideoLogsByVideoId = null;
    private transient PreparedStatement preparedStatement_getVideoLogsByLogId = null;
    private transient PreparedStatement preparedStatement_getVideoLogByVideoIdAndLogId = null;
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_getAllQueuedVideos = null;
    private transient PreparedStatement preparedStatement_getQueuedVideoById = null;
    private transient PreparedStatement preparedStatement_getQueuedVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_getQueuedVideosByRequesterId = null;
    private transient PreparedStatement preparedStatement_getNextQueuedVideos = null;
    private transient PreparedStatement preparedStatement_getNextQueuedVideo = null;
    //
    // // Inserts / Adds
    // Table: Channel
    private transient PreparedStatement preparedStatement_addChannel = null;
    // Table: File Extra
    private transient PreparedStatement preparedStatement_addFileExtra = null;
    // Table: File Logs
    private transient PreparedStatement preparedStatement_addFileLog = null;
    // Table: File Media
    private transient PreparedStatement preparedStatement_addFileMedia = null;
    // Table: Playlist
    private transient PreparedStatement preparedStatement_addPlaylist = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_addPlaylistVideo = null;
    // Table: Requester
    private transient PreparedStatement preparedStatement_addRequester = null;
    // Table: Token
    private transient PreparedStatement preparedStatement_addToken = null;
    // Table: Uploader
    private transient PreparedStatement preparedStatement_addUploader = null;
    // Table: Video
    private transient PreparedStatement preparedStatement_addVideo = null;
    // Table: Video Logs
    private transient PreparedStatement preparedStatement_addVideoLog = null;
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_addQueuedVideo = null;
    //
    // // Updates / Sets
    // Table: Channel
    private transient PreparedStatement preparedStatement_setChannelByChannelId = null;
    // Table: File Extra
    private transient PreparedStatement preparedStatement_setFileExtraByVideoIdAndFile = null;
    // Table: File Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
    // Table: File Media
    private transient PreparedStatement preparedStatement_setFileMediaByVideoIdAndFile = null;
    // Table: Log
    private transient PreparedStatement preparedStatement_setLogByLogId = null;
    // Table: Playlist
    private transient PreparedStatement preparedStatement_setPlaylistByPlaylistId = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId = null;
    // Table: Requester
    private transient PreparedStatement preparedStatement_setRequesterByRequesterId = null;
    private transient PreparedStatement preparedStatement_setRequesterByTag = null;
    // Table: Token
    private transient PreparedStatement preparedStatement_setTokenByToken = null;
    private transient PreparedStatement preparedStatement_setTokenTimesUsedByToken = null;
    // Table: Uploader
    private transient PreparedStatement preparedStatement_setUploaderByUploaderId = null;
    // Table: Video
    private transient PreparedStatement preparedStatement_setVideoByVideoId = null;
    // Table: Video Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_setQueuedVideoById = null;
    //
    // // Deletes / Removes
    // Table: Channel
    private transient PreparedStatement preparedStatement_removeAllChannels = null;
    private transient PreparedStatement preparedStatement_removeChannelByChannelId = null;
    // Table: File Extra
    private transient PreparedStatement preparedStatement_removeAllFilesExtra = null;
    private transient PreparedStatement preparedStatement_removeFilesExtraByVideoId = null;
    private transient PreparedStatement preparedStatement_removeFileExtraByVideoIdAndFile = null;
    // Table: File Logs
    private transient PreparedStatement preparedStatement_removeAllFilesLogs = null;
    private transient PreparedStatement preparedStatement_removeFilesLogsByVideoId = null;
    private transient PreparedStatement preparedStatement_removeFilesLogsByFileAndIsMediaFile = null;
    private transient PreparedStatement preparedStatement_removeFilesLogsByLogId = null;
    private transient PreparedStatement preparedStatement_removeFileLogsByVideoIdAndFileAndLogIdAndIsMediaFile = null;
    // Table: File Media
    private transient PreparedStatement preparedStatement_removeAllFilesMedia = null;
    private transient PreparedStatement preparedStatement_removeFilesMediaByVideoId = null;
    private transient PreparedStatement preparedStatement_removeFileMediaByVideoIdAndFile = null;
    // Table: Log
    private transient PreparedStatement preparedStatement_removeAllLogs = null;
    private transient PreparedStatement preparedStatement_removeLogByLogId = null;
    // Table: Playlist
    private transient PreparedStatement preparedStatement_removeAllPlaylists = null;
    private transient PreparedStatement preparedStatement_removePlaylistByPlaylistId = null;
    private transient PreparedStatement preparedStatement_removePlaylistsByUploaderId = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_removeAllPlaylistVideos = null;
    private transient PreparedStatement preparedStatement_removePlaylistVideosByPlaylistId = null;
    private transient PreparedStatement preparedStatement_removePlaylistVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId = null;
    // Table: Requester
    private transient PreparedStatement preparedStatement_removeAllRequesters = null;
    private transient PreparedStatement preparedStatement_removeRequesterByRequesterId = null;
    private transient PreparedStatement preparedStatement_removeRequesterByTag = null;
    // Table: Token
    private transient PreparedStatement preparedStatement_removeAllTokens = null;
    private transient PreparedStatement preparedStatement_removeTokenByToken = null;
    // Table: Uploader
    private transient PreparedStatement preparedStatement_removeAllUploaders = null;
    private transient PreparedStatement preparedStatement_removeUploaderByUploaderId = null;
    // Table: Video
    private transient PreparedStatement preparedStatement_removeAllVideos = null;
    private transient PreparedStatement preparedStatement_removeVideoByVideoId = null;
    private transient PreparedStatement preparedStatement_removeVideosByChannelId = null;
    private transient PreparedStatement preparedStatement_removeVideosByUploaderId = null;
    // Table: Video Logs
    private transient PreparedStatement preparedStatement_removeAllVideosLogs = null;
    private transient PreparedStatement preparedStatement_removeVideosLogsByVideoId = null;
    private transient PreparedStatement preparedStatement_removeVideosLogsByLogId = null;
    private transient PreparedStatement preparedStatement_removeVideoLogByVideoIdAndLogId = null;
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_removeAllQueuedVideos = null;
    private transient PreparedStatement preparedStatement_removeQueuedVideoById = null;
    private transient PreparedStatement preparedStatement_removeQueuedVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_removeQueuedVideosByRequesterId = null;
    //
    // //
    
    public YouTubeDatabase(C connector) {
        super(connector);
    }
    
    @Override
    public boolean isConnected() {
        if (connector == null) {
            return false;
        }
        return connector.isConnected();
    }
    
    @Override
    public boolean start(String username, byte[] password) {
        if (isConnected()) {
            return false;
        }
        if (!connector.createConnection(username, password)) {
            return false;
        }
        createStatements();
        return true;
    }
    
    @Override
    public boolean stop() {
        if (!isConnected()) {
            return false;
        }
        closeStatements();
        connector.closeWithoutException();
        return true;
    }
    
    private PreparedStatement createPreparedStatement(String sql) {
        return createPreparedStatement(connector, sql);
    }
    
    private void createStatements() {
        // // Selects / Gets
        preparedStatement_getLastInsertId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_SELECT_LAST_INSERT_ID);
        // Table: Channel
        preparedStatement_getAllChannels = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_SELECT_ALL);
        preparedStatement_getChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_SELECT_BY_CHANNEL_ID);
        // Table: File Extra
        preparedStatement_getAllExtraFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_ALL);
        preparedStatement_getExtraFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getExtraFilesByFileType = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_ALL_BY_FILE_TYPE);
        preparedStatement_getExtraFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_BY_VIDEO_ID_AND_FILE);
        // Table: File Logs
        //TODO
        
        // Table: File Media
        preparedStatement_getAllMediaFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_ALL);
        preparedStatement_getMediaFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getMediaFilesByFileType = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_ALL_BY_FILE_TYPE);
        preparedStatement_getMediaFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_BY_VIDEO_ID_AND_FILE);
        // Table: Log
        //TODO
        
        // Table: Playlist
        preparedStatement_getAllPlaylists = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_SELECT_ALL);
        preparedStatement_getPlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_SELECT_BY_PLAYLIST_ID);
        preparedStatement_getPlaylistsByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_SELECT_ALL_UPLOADER_ID);
        // Table: Playlist Videos
        preparedStatement_getAllPlaylistVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL);
        preparedStatement_getPlaylistVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getPlaylistVideosByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_PLAYLIST_ID);
        preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requester
        preparedStatement_getAllRequesters = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_SELECT_ALL);
        preparedStatement_getRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_SELECT_BY_REQUESTER_ID);
        preparedStatement_getRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_SELECT_BY_TAG);
        // Table: Token
        preparedStatement_getAllAuthorizationTokens = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_SELECT_ALL);
        preparedStatement_getAuthorizationTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_SELECT_BY_TOKEN);
        // Table: Uploader
        preparedStatement_getAllUploaders = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_SELECT_ALL);
        preparedStatement_getUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_SELECT_BY_UPLOADER_ID);
        // Table: Video
        preparedStatement_getAllVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_ALL);
        preparedStatement_getVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_BY_VIDEO_ID);
        preparedStatement_getVideosByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_ALL_BY_CHANNEL_ID);
        preparedStatement_getVideosByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_ALL_BY_UPLOADER_ID);
        // Table: Video Logs
        //TODO
        
        // Table: Video Queue
        preparedStatement_getAllQueuedVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL);
        preparedStatement_getQueuedVideoById = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_BY_ID);
        preparedStatement_getQueuedVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getQueuedVideosByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_BY_REQUESTER_ID);
        preparedStatement_getNextQueuedVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_NEXT);
        preparedStatement_getNextQueuedVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_NEXT);
        //
        // // Inserts / Adds
        // Table: Channel
        preparedStatement_addChannel = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_INSERT);
        // Table: File Extra
        preparedStatement_addExtraFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_INSERT);
        // Table: File Logs
        //TODO
        
        // Table: File Media
        preparedStatement_addMediaFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_INSERT);
        // Table: Log
        //TODO
        
        // Table: Playlist
        preparedStatement_addPlaylist = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_INSERT);
        // Table: Playlist Videos
        preparedStatement_addPlaylistVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_INSERT);
        // Table: Requester
        preparedStatement_addRequester = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_INSERT);
        // Table: Token
        preparedStatement_addAuthorizationToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_INSERT);
        // Table: Uploader
        preparedStatement_addUploader = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_INSERT);
        // Table: Video
        preparedStatement_addVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_INSERT);
        // Table: Video Log
        //TODO
        
        // Table: Video Queue
        preparedStatement_addQueuedVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_INSERT);
        //
        // // Updates / Sets
        // Table: Channel
        preparedStatement_setChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_UPDATE_BY_CHANNEL_ID);
        // Table: File Extra
        preparedStatement_setExtraFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_UPDATE_BY_VIDEO_ID_AND_FILE);
        // Table: File Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
        // Table: File Media
        preparedStatement_setMediaFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_UPDATE_BY_VIDEO_ID_AND_FILE);
        // Table: Log
        //TODO
        
        // Table: Playlist
        preparedStatement_setPlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_UPDATE_BY_PLAYLIST_ID);
        // Table: Playlist Videos
        preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_UPDATE_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requester
        preparedStatement_setRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_UPDATE_BY_REQUESTER_ID);
        preparedStatement_setRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_UPDATE_BY_TAG);
        // Table: Token
        preparedStatement_setAuthorizationTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_UPDATE_BY_TOKEN);
        preparedStatement_setAuthorizationTokenTimesUsedByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_UPDATE_USED_BY_TOKEN);
        // Table: Uploader
        preparedStatement_setUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_UPDATE_BY_UPLOADER_ID);
        // Table: Video
        preparedStatement_setVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_UPDATE_BY_VIDEO_ID);
        // Table: Video Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
        // Table: Video Queue
        preparedStatement_setQueuedVideoById = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_UPDATE_BY_ID);
        //
        // // Deletes / Removes
        // Table: Channel
        preparedStatement_removeAllChannels = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_DELETE_ALL);
        preparedStatement_removeChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_DELETE_BY_CHANNEL_ID);
        // Table: File Extra
        preparedStatement_removeAllExtraFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_DELETE_ALL);
        preparedStatement_removeExtraFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeExtraFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_DELETE_BY_VIDEO_ID_AND_FILE);
        // Table: File Logs
        //TODO
        
        // Table: File Media
        preparedStatement_removeAllMediaFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_DELETE_ALL);
        preparedStatement_removeMediaFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeMediaFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_DELETE_BY_VIDEO_ID_AND_FILE);
        // Table: Log
        //TODO
        
        // Table: Playlist
        preparedStatement_removeAllPlaylists = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_DELETE_ALL);
        preparedStatement_removePlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_DELETE_BY_PLAYLIST_ID);
        preparedStatement_removePlaylistsByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_DELETE_ALL_BY_UPLOADER_ID);
        // Table: Playlist Videos
        preparedStatement_removeAllPlaylistVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL);
        preparedStatement_removePlaylistVideosByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_PLAYLIST_ID);
        preparedStatement_removePlaylistVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requester
        preparedStatement_removeAllRequesters = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_DELETE_ALL);
        preparedStatement_removeRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_DELETE_BY_REQUESTER_ID);
        preparedStatement_removeRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_DELETE_BY_TAG);
        // Table: Token
        preparedStatement_removeAllAuthorizationTokens = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_DELETE_ALL);
        preparedStatement_removeAuthorizationTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_DELETE_BY_TOKEN);
        // Table: Uploader
        preparedStatement_removeAllUploaders = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_DELETE_ALL);
        preparedStatement_removeUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_DELETE_BY_CHANNEL_ID);
        // Table: Video
        preparedStatement_removeAllVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_ALL);
        preparedStatement_removeVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_BY_VIDEO_ID);
        preparedStatement_removeVideosByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_ALL_BY_CHANNEL_ID);
        preparedStatement_removeVideosByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_ALL_BY_UPLOADER_ID);
        // Table: Video Logs
        //TODO
        
        // Table: Video Queue
        preparedStatement_removeAllQueuedVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL);
        preparedStatement_removeQueuedVideoById = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_BY_ID);
        preparedStatement_removeQueuedVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeQueuedVideosByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL_BY_REQUESTER_ID);
        //
        // //
    }
    
    private void closeStatements() {
        // // Selects / Gets
        IOUtil.closeQuietly(preparedStatement_getLastInsertId);
        // Table: Channel
        IOUtil.closeQuietly(preparedStatement_getAllChannels);
        IOUtil.closeQuietly(preparedStatement_getChannelByChannelId);
        // Table: File Extra
        IOUtil.closeQuietly(preparedStatement_getAllExtraFiles);
        IOUtil.closeQuietly(preparedStatement_getExtraFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_getExtraFilesByFileType);
        IOUtil.closeQuietly(preparedStatement_getExtraFileByVideoIdAndFile);
        // Table: File Logs
        //TODO
        
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_getAllMediaFiles);
        IOUtil.closeQuietly(preparedStatement_getMediaFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_getMediaFilesByFileType);
        IOUtil.closeQuietly(preparedStatement_getMediaFileByVideoIdAndFile);
        // Table: Log
        //TODO
        
        // Table: Playlist
        IOUtil.closeQuietly(preparedStatement_getAllPlaylists);
        IOUtil.closeQuietly(preparedStatement_getPlaylistByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistsByUploaderId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_getAllPlaylistVideos);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideosByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId);
        // Table: Requester
        IOUtil.closeQuietly(preparedStatement_getAllRequesters);
        IOUtil.closeQuietly(preparedStatement_getRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_getRequesterByTag);
        // Table: Token
        IOUtil.closeQuietly(preparedStatement_getAllAuthorizationTokens);
        IOUtil.closeQuietly(preparedStatement_getAuthorizationTokenByToken);
        // Table: Uploader
        IOUtil.closeQuietly(preparedStatement_getAllUploaders);
        IOUtil.closeQuietly(preparedStatement_getUploaderByUploaderId);
        // Table: Video
        IOUtil.closeQuietly(preparedStatement_getAllVideos);
        IOUtil.closeQuietly(preparedStatement_getVideoByVideoId);
        IOUtil.closeQuietly(preparedStatement_getVideosByChannelId);
        IOUtil.closeQuietly(preparedStatement_getVideosByUploaderId);
        // Table: Video Logs
        //TODO
        
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_getAllQueuedVideos);
        IOUtil.closeQuietly(preparedStatement_getQueuedVideoById);
        IOUtil.closeQuietly(preparedStatement_getQueuedVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_getQueuedVideosByRequesterId);
        IOUtil.closeQuietly(preparedStatement_getNextQueuedVideos);
        IOUtil.closeQuietly(preparedStatement_getNextQueuedVideo);
        //
        // // Inserts / Adds
        // Table: Channel
        IOUtil.closeQuietly(preparedStatement_addChannel);
        // Table: File Extra
        IOUtil.closeQuietly(preparedStatement_addExtraFile);
        // Table: File Logs
        //TODO
        
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_addMediaFile);
        // Table: Log
        //TODO
        
        // Table: Playlist
        IOUtil.closeQuietly(preparedStatement_addPlaylist);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_addPlaylistVideo);
        // Table: Requester
        IOUtil.closeQuietly(preparedStatement_addRequester);
        // Table: Token
        IOUtil.closeQuietly(preparedStatement_addAuthorizationToken);
        // Table: Uploader
        IOUtil.closeQuietly(preparedStatement_addUploader);
        // Table: Video
        IOUtil.closeQuietly(preparedStatement_addVideo);
        // Table: Video Logs
        //TODO
        
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_addQueuedVideo);
        //
        // // Updates / Sets
        // Table: Channel
        IOUtil.closeQuietly(preparedStatement_setChannelByChannelId);
        // Table: File Extra
        IOUtil.closeQuietly(preparedStatement_setExtraFileByVideoIdAndFile);
        // Table: File Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_setMediaFileByVideoIdAndFile);
        // Table: Log
        //TODO
        
        // Table: Playlist
        IOUtil.closeQuietly(preparedStatement_setPlaylistByPlaylistId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId);
        // Table: Requester
        IOUtil.closeQuietly(preparedStatement_setRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_setRequesterByTag);
        // Table: Token
        IOUtil.closeQuietly(preparedStatement_setAuthorizationTokenByToken);
        IOUtil.closeQuietly(preparedStatement_setAuthorizationTokenTimesUsedByToken);
        // Table: Uploader
        IOUtil.closeQuietly(preparedStatement_setUploaderByUploaderId);
        // Table: Video
        IOUtil.closeQuietly(preparedStatement_setVideoByVideoId);
        // Table: Video Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_setQueuedVideoById);
        //
        // // Deletes / Removes
        // Table: Channel
        IOUtil.closeQuietly(preparedStatement_removeAllChannels);
        IOUtil.closeQuietly(preparedStatement_removeChannelByChannelId);
        // Table: File Extra
        IOUtil.closeQuietly(preparedStatement_removeAllExtraFiles);
        IOUtil.closeQuietly(preparedStatement_removeExtraFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeExtraFileByVideoIdAndFile);
        // Table: File Logs
        //TODO
        
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_removeAllMediaFiles);
        IOUtil.closeQuietly(preparedStatement_removeMediaFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeMediaFileByVideoIdAndFile);
        // Table: Log
        //TODO
        
        // Table: Playlist
        IOUtil.closeQuietly(preparedStatement_removeAllPlaylists);
        IOUtil.closeQuietly(preparedStatement_removePlaylistByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_removePlaylistsByUploaderId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_removeAllPlaylistVideos);
        IOUtil.closeQuietly(preparedStatement_removePlaylistVideosByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_removePlaylistVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId);
        // Table: Requester
        IOUtil.closeQuietly(preparedStatement_removeAllRequesters);
        IOUtil.closeQuietly(preparedStatement_removeRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_removeRequesterByTag);
        // Table: Token
        IOUtil.closeQuietly(preparedStatement_removeAllAuthorizationTokens);
        IOUtil.closeQuietly(preparedStatement_removeAuthorizationTokenByToken);
        // Table: Uploader
        IOUtil.closeQuietly(preparedStatement_removeAllUploaders);
        IOUtil.closeQuietly(preparedStatement_removeUploaderByUploaderId);
        // Table: Video
        IOUtil.closeQuietly(preparedStatement_removeAllVideos);
        IOUtil.closeQuietly(preparedStatement_removeVideoByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeVideosByChannelId);
        IOUtil.closeQuietly(preparedStatement_removeVideosByUploaderId);
        // Table: Video Logs
        //TODO
        
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_removeAllQueuedVideos);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideoById);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideosByRequesterId);
        //
        // //
    }
    
    @Override
    public int getLastInsertId() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<AuthorizationToken> getAllAuthorizationTokens() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public AuthorizationToken getAuthorizationTokenByToken(String token) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasAuthorizationToken(String token) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasChannel(String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasUploader(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasRequester(int requesterId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasQueuedVideo(int queuedVideoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasVideo(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasPlaylist(String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseYouTubeVideo getVideoByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getAllVideos() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideosByPlaylistId(String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideosByChannelId(String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideosByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getAllVideoIds() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getVideoIdsByPlaylistId(String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getVideoIdsByChannelId(String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getVideoIdsByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public int getVideoCountByPlaylistId(String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public int getVideoCountByChannelId(String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public int getVideoCountByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean playlistContainsVideo(String playlistId, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean channelHasVideo(String channelId, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean uploaderUploadedVideo(String uploaderId, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseYouTubePlaylist getPlaylistByPlaylistId(String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getAllPlaylists() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getPlaylistsByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getPlaylistsByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getAllPlaylistIds() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getPlaylistIdsByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getPlaylistIdsByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public int getPlaylistCountByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public int getIndexInPlaylist(String playlistId, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean uploaderCreatedPlaylist(String uploaderId, String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubeChannel> getAllChannels() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getAllChannelIds() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseYouTubeChannel getChannelByChannelId(String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseYouTubeUploader> getAllUploaders() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getAllUploaderIds() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseYouTubeUploader getUploaderByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseMediaFile getMediaFileByVideoIdAndFile(String videoId, String file) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseMediaFile> getMediaFilesByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseExtraFile getExtraFileByVideoIdAndFile(String videoId, String file) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseExtraFile> getExtraFilesByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseRequester> getAllRequesters() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<Integer> getAllRequesterIds() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseRequester getRequesterByRequesterId(int requesterId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseRequester getRequesterByRequesterId(String tag) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo getQueuedVideoById(int id) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getAllQueuedVideos() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getQueuedVideoIdsByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getQueuedVideosByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getQueuedVideoIdsByRequesterId(int requesterId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getQueuedVideosByRequesterId(int requesterId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo getNextQueuedVideo() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getNextQueuedVideos() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseYouTubeChannel createChannel(String channelId, String name) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseYouTubeUploader createUploader(String uploaderId, String name) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseRequester createRequester(String tag, String name) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseYouTubeVideo createVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long duration, Instant uploadTimestamp) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo createQueuedVideo(String videoId, int priority, Instant requestedTimestamp, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addVideoToPlaylist(DatabaseYouTubePlaylist playlist, DatabaseYouTubeVideo video, int index) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addVideoToPlaylist(String playlistId, String videoId, int index) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addVideoToPlaylists(DatabaseYouTubeVideo video, List<DatabaseYouTubePlaylist> playlists) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addVideoToPlaylists(String videoId, List<String> playlistIds) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addVideosToPlaylist(DatabaseYouTubePlaylist playlist, List<DatabaseYouTubeVideo> videos) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addVideosToPlaylist(String playlistId, List<String> videoIds) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addPlaylist(DatabaseYouTubePlaylist playlist) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addAuthorizationToken(AuthorizationToken authorizationToken) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addQueuedVideo(DatabaseQueuedYouTubeVideo queuedVideo) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addVideo(DatabaseYouTubeVideo video) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addChannel(DatabaseYouTubeChannel channel) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addUploader(DatabaseYouTubeUploader uploader) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean addRequester(DatabaseRequester requester) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setAuthorizationTokenByToken(AuthorizationToken authorizationToken, String token) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setAuthorizationTokenTimesUsedByToken(String token, int timesUsed) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setPlaylistVideoIndex(DatabaseYouTubePlaylist playlist, DatabaseYouTubeVideo video, int index) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setVideoByVideoId(DatabaseYouTubeVideo video, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setPlaylistByPlaylistId(DatabaseYouTubePlaylist playlist, String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setMediaFileByVideoIdAndFile(DatabaseMediaFile mediaFile, String videoId, String file) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setMediaFilesByVideoId(List<DatabaseMediaFile> mediaFiles, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setExtraFileByVideoIdAndFile(DatabaseExtraFile extraFile, String videoId, String file) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setExtraFilesByVideoId(List<DatabaseExtraFile> extraFiles, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setChannelByChannelId(DatabaseYouTubeChannel channel, String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setUploaderByUploaderId(DatabaseYouTubeUploader uploader, String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setQueuedVideoById(DatabaseQueuedYouTubeVideo queuedVideo, int id) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setRequesterByRequesterId(DatabaseRequester requester, int requesterId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean setRequesterByRequesterTag(DatabaseRequester requester, String tag) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeAllAuthorizationTokens() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeAuthorizationTokenByToken(String token) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeVideoFromPlaylist(DatabaseYouTubePlaylist playlist, DatabaseYouTubeVideo video) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeVideoIdFromPlaylistId(String playlistId, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeAllQueuedVideos() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeQueuedVideoById(int id) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeQueuedVideosByVideoId(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean removeQueuedVideosByRequesterId(int requesterId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public String toString() {
        return "YouTubeDatabase{" + "connector=" + connector + '}';
    }
    
    private static PreparedStatement createPreparedStatement(AbstractConnector connector, String sql) {
        try {
            return connector.prepareStatement(sql);
        } catch (Exception ex) {
            return null;
        }
    }
    
    private static boolean setPreparedStatement(PreparedStatement preparedStatement, Object... arguments) {
        try {
            int offset = 0;
            for (int i = 0; i < arguments.length - offset; i++) {
                final Object object = arguments[i + offset];
                final int index = i + 1 + offset;
                if (object == null) {
                    //offset++;
                    //preparedStatement.setNull(index, (Integer) arguments[i + offset + 1]);
                    preparedStatement.setNull(index, Types.NULL);
                } else if (object instanceof String) {
                    preparedStatement.setString(index, (String) object);
                } else if (object instanceof Integer) {
                    preparedStatement.setInt(index, (Integer) object);
                } else if (object instanceof Long) {
                    preparedStatement.setLong(index, (Long) object);
                } else if (object instanceof Boolean) {
                    preparedStatement.setBoolean(index, (Boolean) object);
                } else if (object instanceof Timestamp) {
                    preparedStatement.setTimestamp(index, (Timestamp) object);
                } else if (object instanceof Instant) {
                    preparedStatement.setTimestamp(index, Timestamp.from((Instant) object));
                } else {
                    throw new IllegalArgumentException(String.format("The Class \"%s\" is not yet supported by \"setPreparedStatement\"!", object.getClass().getName()));
                }
            }
            Logger.logDebug("setPreparedStatement:preparedStatement=" + preparedStatement); //TODO DEBUG Remove this!
            return true;
        } catch (Exception ex) {
            Logger.handleError(ex);
            return false;
        }
    }
    
    private <T extends DatabaseEntity<T, YouTubeDatabase<?>>> T setDatabase(T entity) {
        return setDatabase(entity, this);
    }
    
    private static <T extends DatabaseEntity<T, YouTubeDatabase<?>>> T setDatabase(T entity, YouTubeDatabase<?> database) {
        return entity == null || database == null ? entity : entity.setDatabase(database);
    }
    
    private static <R> R useResultSetAndClose(ToughSupplier<ResultSet> toughSupplier, ToughFunction<ResultSet, R> toughFunction) {
        if (toughSupplier == null || toughFunction == null) {
            return null;
        }
        final ResultSet resultSet = toughSupplier.getWithoutException();
        if (resultSet == null || !Standard.silentError(resultSet::next)) {
            return null;
        }
        final R r = toughFunction.applyWithoutException(resultSet);
        Standard.silentError(resultSet::close);
        return r;
    }
    
    private static <R> List<R> resultSetToRs(ResultSet resultSet, ToughFunction<ResultSet, R> toughFunction) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<R> rs = new ArrayList<>();
        do {
            final R r = resultSetToR(resultSet, toughFunction);
            if (r != null) {
                rs.add(r);
            }
        } while (Standard.silentError(resultSet::next));
        return rs;
    }
    
    private static <R> R resultSetToR(ResultSet resultSet, ToughFunction<ResultSet, R> toughFunction) {
        if (resultSet == null) {
            return null;
        }
        return toughFunction.applyWithoutException(resultSet);
    }
    
    private static List<String> resultSetPlaylistIdsFromPlaylists(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID));
    }
    
    private static List<String> resultSetVideoIdsFromVideos(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_ID));
    }
    
    private static List<String> resultSetChannelIdsFromChannels(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_CHANNEL_COLUMN_ID));
    }
    
    private static List<String> resultSetUploaderIdsFromUploaders(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_UPLOADER_COLUMN_ID));
    }
    
    private static List<Integer> resultSetRequesterIdsFromRequesters(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTER_COLUMN_ID));
    }
    
    private static List<String> resultSetVideoIdsFromQueuedVideos(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID));
    }
    
    private static List<String> resultSetPlaylistIdsFromPlaylistVideos(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID));
    }
    
    private static List<String> resultSetVideoIdsFromPlaylistVideos(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID));
    }
    
    private static int resultSetToPlaylistIndex(ResultSet resultSet) {
        return resultSetToR(resultSet, (resultSet_) -> resultSet_.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX));
    }
    
    protected static void createTables(YouTubeDatabase<?> database) {
        //TODO Use the "database_create_tables.sql" to create (if not existing) tables...
    }
    
}
