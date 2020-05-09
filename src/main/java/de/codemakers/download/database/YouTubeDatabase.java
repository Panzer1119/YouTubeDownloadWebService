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
import de.codemakers.base.util.TimeUtil;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private transient PreparedStatement preparedStatement_getPlaylistCountByUploaderId = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_getAllPlaylistVideos = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideosByPlaylistId = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId = null;
    private transient PreparedStatement preparedStatement_getVideoCountByPlaylistId = null;
    private transient PreparedStatement preparedStatement_getPlaylistCountByVideoId = null;
    // Table: Requester
    private transient PreparedStatement preparedStatement_getAllRequesters = null;
    private transient PreparedStatement preparedStatement_getRequesterByRequesterId = null;
    private transient PreparedStatement preparedStatement_getRequesterByTag = null;
    // Table: Token
    private transient PreparedStatement preparedStatement_getAllTokens = null;
    private transient PreparedStatement preparedStatement_getTokenByTokenId = null;
    private transient PreparedStatement preparedStatement_getTokenByToken = null;
    // Table: Uploader
    private transient PreparedStatement preparedStatement_getAllUploaders = null;
    private transient PreparedStatement preparedStatement_getUploaderByUploaderId = null;
    // Table: Video
    private transient PreparedStatement preparedStatement_getAllVideos = null;
    private transient PreparedStatement preparedStatement_getVideoByVideoId = null;
    private transient PreparedStatement preparedStatement_getVideosByChannelId = null;
    private transient PreparedStatement preparedStatement_getVideosByUploaderId = null;
    private transient PreparedStatement preparedStatement_getVideoCountByChannelId = null;
    private transient PreparedStatement preparedStatement_getVideoCountByUploaderId = null;
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
    // Table: Log
    private transient PreparedStatement preparedStatement_addLog = null;
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
    private transient PreparedStatement preparedStatement_setTokenByTokenId = null;
    private transient PreparedStatement preparedStatement_setTokenByToken = null;
    private transient PreparedStatement preparedStatement_setTokenTimesUsedByTokenId = null;
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
    private transient PreparedStatement preparedStatement_removeTokenByTokenId = null;
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
        preparedStatement_getAllChannels = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNEL_SELECT_ALL);
        preparedStatement_getChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNEL_SELECT_BY_CHANNEL_ID);
        // Table: File Extra
        preparedStatement_getAllFilesExtra = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_SELECT_ALL);
        preparedStatement_getFilesExtraByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getFilesExtraByFileType = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_SELECT_ALL_BY_FILE_TYPE);
        preparedStatement_getFileExtraByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_SELECT_BY_VIDEO_ID_AND_FILE);
        // Table: File Logs
        preparedStatement_getAllFileLogs = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_SELECT_ALL);
        preparedStatement_getFilesLogsByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getFilesLogsByLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_SELECT_ALL_BY_LOG_ID);
        preparedStatement_getFileLogByVideoIdAndFileAndLogIdAndIsMediaFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_SELECT_BY_VIDEO_ID_AND_FILE_AND_LOG_ID_AND_IS_MEDIA_FILE);
        // Table: File Media
        preparedStatement_getAllFilesMedia = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_SELECT_ALL);
        preparedStatement_getFilesMediaByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getFilesMediaByFileType = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_SELECT_ALL_BY_FILE_TYPE);
        preparedStatement_getFileMediaByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_SELECT_BY_VIDEO_ID_AND_FILE);
        // Table: Log
        preparedStatement_getAllLogs = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_LOG_SELECT_ALL);
        preparedStatement_getLogByLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_LOG_SELECT_BY_LOG_ID);
        // Table: Playlist
        preparedStatement_getAllPlaylists = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_SELECT_ALL);
        preparedStatement_getPlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_SELECT_BY_PLAYLIST_ID);
        preparedStatement_getPlaylistsByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_SELECT_ALL_UPLOADER_ID);
        preparedStatement_getPlaylistCountByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_COUNT_BY_UPLOADER_ID);
        // Table: Playlist Videos
        preparedStatement_getAllPlaylistVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL);
        preparedStatement_getPlaylistVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getPlaylistVideosByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_PLAYLIST_ID);
        preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_BY_PLAYLIST_ID_AND_VIDEO_ID);
        preparedStatement_getVideoCountByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_COUNT_BY_PLAYLIST_ID);
        preparedStatement_getPlaylistCountByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_COUNT_BY_VIDEO_ID);
        // Table: Requester
        preparedStatement_getAllRequesters = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_SELECT_ALL);
        preparedStatement_getRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_SELECT_BY_REQUESTER_ID);
        preparedStatement_getRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_SELECT_BY_TAG);
        // Table: Token
        preparedStatement_getAllTokens = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_SELECT_ALL);
        preparedStatement_getTokenByTokenId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_SELECT_BY_TOKEN_ID);
        preparedStatement_getTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_SELECT_BY_TOKEN);
        // Table: Uploader
        preparedStatement_getAllUploaders = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADER_SELECT_ALL);
        preparedStatement_getUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADER_SELECT_BY_UPLOADER_ID);
        // Table: Video
        preparedStatement_getAllVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_SELECT_ALL);
        preparedStatement_getVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_SELECT_BY_VIDEO_ID);
        preparedStatement_getVideosByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_SELECT_ALL_BY_CHANNEL_ID);
        preparedStatement_getVideosByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_SELECT_ALL_BY_UPLOADER_ID);
        preparedStatement_getVideoCountByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_COUNT_BY_CHANNEL_ID);
        preparedStatement_getVideoCountByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_COUNT_BY_UPLOADER_ID);
        // Table: Video Logs
        preparedStatement_getAllVideoLogs = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_SELECT_ALL);
        preparedStatement_getVideoLogsByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getVideoLogsByLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_SELECT_ALL_BY_LOG_ID);
        preparedStatement_getVideoLogByVideoIdAndLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_SELECT_BY_VIDEO_ID_AND_LOG_ID);
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
        preparedStatement_addChannel = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNEL_INSERT);
        // Table: File Extra
        preparedStatement_addFileExtra = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_INSERT);
        // Table: File Logs
        preparedStatement_addFileLog = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_INSERT);
        // Table: File Media
        preparedStatement_addFileMedia = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_INSERT);
        // Table: Log
        preparedStatement_addLog = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_LOG_INSERT);
        // Table: Playlist
        preparedStatement_addPlaylist = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_INSERT);
        // Table: Playlist Videos
        preparedStatement_addPlaylistVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_INSERT);
        // Table: Requester
        preparedStatement_addRequester = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_INSERT);
        // Table: Token
        preparedStatement_addToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_INSERT);
        // Table: Uploader
        preparedStatement_addUploader = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADER_INSERT);
        // Table: Video
        preparedStatement_addVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_INSERT);
        // Table: Video Log
        preparedStatement_addVideoLog = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_INSERT);
        // Table: Video Queue
        preparedStatement_addQueuedVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_INSERT);
        //
        // // Updates / Sets
        // Table: Channel
        preparedStatement_setChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNEL_UPDATE_BY_CHANNEL_ID);
        // Table: File Extra
        preparedStatement_setFileExtraByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_UPDATE_BY_VIDEO_ID_AND_FILE);
        // Table: File Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
        // Table: File Media
        preparedStatement_setFileMediaByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_UPDATE_BY_VIDEO_ID_AND_FILE);
        // Table: Log
        preparedStatement_setLogByLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_LOG_UPDATE_BY_LOG_ID);
        // Table: Playlist
        preparedStatement_setPlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_UPDATE_BY_PLAYLIST_ID);
        // Table: Playlist Videos
        preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_UPDATE_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requester
        preparedStatement_setRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_UPDATE_BY_REQUESTER_ID);
        preparedStatement_setRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_UPDATE_BY_TAG);
        // Table: Token
        preparedStatement_setTokenByTokenId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_UPDATE_BY_TOKEN_ID);
        preparedStatement_setTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_UPDATE_BY_TOKEN);
        preparedStatement_setTokenTimesUsedByTokenId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_UPDATE_USED_BY_TOKEN_ID);
        preparedStatement_setTokenTimesUsedByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_UPDATE_USED_BY_TOKEN);
        // Table: Uploader
        preparedStatement_setUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADER_UPDATE_BY_UPLOADER_ID);
        // Table: Video
        preparedStatement_setVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_UPDATE_BY_VIDEO_ID);
        // Table: Video Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
        // Table: Video Queue
        preparedStatement_setQueuedVideoById = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_UPDATE_BY_ID);
        //
        // // Deletes / Removes
        // Table: Channel
        preparedStatement_removeAllChannels = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNEL_DELETE_ALL);
        preparedStatement_removeChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNEL_DELETE_BY_CHANNEL_ID);
        // Table: File Extra
        preparedStatement_removeAllFilesExtra = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_DELETE_ALL);
        preparedStatement_removeFilesExtraByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeFileExtraByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_EXTRA_DELETE_BY_VIDEO_ID_AND_FILE);
        // Table: File Logs
        preparedStatement_removeAllFilesLogs = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_DELETE_ALL);
        preparedStatement_removeFilesLogsByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeFilesLogsByFileAndIsMediaFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_DELETE_ALL_BY_FILE_AND_IS_MEDIA_FILE);
        preparedStatement_removeFilesLogsByLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_DELETE_ALL_BY_LOG_ID);
        preparedStatement_removeFileLogsByVideoIdAndFileAndLogIdAndIsMediaFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_LOGS_DELETE_BY_VIDEO_ID_AND_FILE_AND_LOG_ID_AND_IS_MEDIA_FILE);
        // Table: File Media
        preparedStatement_removeAllFilesMedia = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_DELETE_ALL);
        preparedStatement_removeFilesMediaByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeFileMediaByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_FILE_MEDIA_DELETE_BY_VIDEO_ID_AND_FILE);
        // Table: Log
        preparedStatement_removeAllLogs = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_LOG_DELETE_ALL);
        preparedStatement_removeLogByLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_LOG_DELETE_BY_LOG_ID);
        // Table: Playlist
        preparedStatement_removeAllPlaylists = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_DELETE_ALL);
        preparedStatement_removePlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_DELETE_BY_PLAYLIST_ID);
        preparedStatement_removePlaylistsByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_DELETE_ALL_BY_UPLOADER_ID);
        // Table: Playlist Videos
        preparedStatement_removeAllPlaylistVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL);
        preparedStatement_removePlaylistVideosByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_PLAYLIST_ID);
        preparedStatement_removePlaylistVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requester
        preparedStatement_removeAllRequesters = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_DELETE_ALL);
        preparedStatement_removeRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_DELETE_BY_REQUESTER_ID);
        preparedStatement_removeRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTER_DELETE_BY_TAG);
        // Table: Token
        preparedStatement_removeAllTokens = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_DELETE_ALL);
        preparedStatement_removeTokenByTokenId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_DELETE_BY_TOKEN_ID);
        preparedStatement_removeTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_TOKEN_DELETE_BY_TOKEN);
        // Table: Uploader
        preparedStatement_removeAllUploaders = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADER_DELETE_ALL);
        preparedStatement_removeUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADER_DELETE_BY_CHANNEL_ID);
        // Table: Video
        preparedStatement_removeAllVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_DELETE_ALL);
        preparedStatement_removeVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_DELETE_BY_VIDEO_ID);
        preparedStatement_removeVideosByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_DELETE_ALL_BY_CHANNEL_ID);
        preparedStatement_removeVideosByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_DELETE_ALL_BY_UPLOADER_ID);
        // Table: Video Logs
        preparedStatement_removeAllVideosLogs = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_DELETE_ALL);
        preparedStatement_removeVideosLogsByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeVideosLogsByLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_DELETE_ALL_BY_LOG_ID);
        preparedStatement_removeVideoLogByVideoIdAndLogId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_LOGS_DELETE_BY_VIDEO_ID_AND_LOG_ID);
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
        IOUtil.closeQuietly(preparedStatement_getAllFilesExtra);
        IOUtil.closeQuietly(preparedStatement_getFilesExtraByVideoId);
        IOUtil.closeQuietly(preparedStatement_getFilesExtraByFileType);
        IOUtil.closeQuietly(preparedStatement_getFileExtraByVideoIdAndFile);
        // Table: File Logs
        IOUtil.closeQuietly(preparedStatement_getAllFileLogs);
        IOUtil.closeQuietly(preparedStatement_getFilesLogsByVideoId);
        IOUtil.closeQuietly(preparedStatement_getFilesLogsByLogId);
        IOUtil.closeQuietly(preparedStatement_getFileLogByVideoIdAndFileAndLogIdAndIsMediaFile);
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_getAllFilesMedia);
        IOUtil.closeQuietly(preparedStatement_getFilesMediaByVideoId);
        IOUtil.closeQuietly(preparedStatement_getFilesMediaByFileType);
        IOUtil.closeQuietly(preparedStatement_getFileMediaByVideoIdAndFile);
        // Table: Log
        IOUtil.closeQuietly(preparedStatement_getAllLogs);
        IOUtil.closeQuietly(preparedStatement_getLogByLogId);
        // Table: Playlist
        IOUtil.closeQuietly(preparedStatement_getAllPlaylists);
        IOUtil.closeQuietly(preparedStatement_getPlaylistByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistsByUploaderId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistCountByUploaderId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_getAllPlaylistVideos);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideosByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId);
        IOUtil.closeQuietly(preparedStatement_getVideoCountByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistCountByVideoId);
        // Table: Requester
        IOUtil.closeQuietly(preparedStatement_getAllRequesters);
        IOUtil.closeQuietly(preparedStatement_getRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_getRequesterByTag);
        // Table: Token
        IOUtil.closeQuietly(preparedStatement_getAllTokens);
        IOUtil.closeQuietly(preparedStatement_getTokenByTokenId);
        IOUtil.closeQuietly(preparedStatement_getTokenByToken);
        // Table: Uploader
        IOUtil.closeQuietly(preparedStatement_getAllUploaders);
        IOUtil.closeQuietly(preparedStatement_getUploaderByUploaderId);
        // Table: Video
        IOUtil.closeQuietly(preparedStatement_getAllVideos);
        IOUtil.closeQuietly(preparedStatement_getVideoByVideoId);
        IOUtil.closeQuietly(preparedStatement_getVideosByChannelId);
        IOUtil.closeQuietly(preparedStatement_getVideosByUploaderId);
        IOUtil.closeQuietly(preparedStatement_getVideoCountByChannelId);
        IOUtil.closeQuietly(preparedStatement_getVideoCountByUploaderId);
        // Table: Video Logs
        IOUtil.closeQuietly(preparedStatement_getAllVideoLogs);
        IOUtil.closeQuietly(preparedStatement_getVideoLogsByVideoId);
        IOUtil.closeQuietly(preparedStatement_getVideoLogsByLogId);
        IOUtil.closeQuietly(preparedStatement_getVideoLogByVideoIdAndLogId);
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
        IOUtil.closeQuietly(preparedStatement_addFileExtra);
        // Table: File Logs
        IOUtil.closeQuietly(preparedStatement_addFileLog);
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_addFileMedia);
        // Table: Log
        IOUtil.closeQuietly(preparedStatement_addLog);
        // Table: Playlist
        IOUtil.closeQuietly(preparedStatement_addPlaylist);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_addPlaylistVideo);
        // Table: Requester
        IOUtil.closeQuietly(preparedStatement_addRequester);
        // Table: Token
        IOUtil.closeQuietly(preparedStatement_addToken);
        // Table: Uploader
        IOUtil.closeQuietly(preparedStatement_addUploader);
        // Table: Video
        IOUtil.closeQuietly(preparedStatement_addVideo);
        // Table: Video Logs
        IOUtil.closeQuietly(preparedStatement_addVideoLog);
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_addQueuedVideo);
        //
        // // Updates / Sets
        // Table: Channel
        IOUtil.closeQuietly(preparedStatement_setChannelByChannelId);
        // Table: File Extra
        IOUtil.closeQuietly(preparedStatement_setFileExtraByVideoIdAndFile);
        // Table: File Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_setFileMediaByVideoIdAndFile);
        // Table: Log
        IOUtil.closeQuietly(preparedStatement_setLogByLogId);
        // Table: Playlist
        IOUtil.closeQuietly(preparedStatement_setPlaylistByPlaylistId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId);
        // Table: Requester
        IOUtil.closeQuietly(preparedStatement_setRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_setRequesterByTag);
        // Table: Token
        IOUtil.closeQuietly(preparedStatement_setTokenByTokenId);
        IOUtil.closeQuietly(preparedStatement_setTokenByToken);
        IOUtil.closeQuietly(preparedStatement_setTokenTimesUsedByTokenId);
        IOUtil.closeQuietly(preparedStatement_setTokenTimesUsedByToken);
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
        IOUtil.closeQuietly(preparedStatement_removeAllFilesExtra);
        IOUtil.closeQuietly(preparedStatement_removeFilesExtraByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeFileExtraByVideoIdAndFile);
        // Table: File Logs
        IOUtil.closeQuietly(preparedStatement_removeAllFilesLogs);
        IOUtil.closeQuietly(preparedStatement_removeFilesLogsByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeFilesLogsByFileAndIsMediaFile);
        IOUtil.closeQuietly(preparedStatement_removeFilesLogsByLogId);
        IOUtil.closeQuietly(preparedStatement_removeFileLogsByVideoIdAndFileAndLogIdAndIsMediaFile);
        // Table: File Media
        IOUtil.closeQuietly(preparedStatement_removeAllFilesMedia);
        IOUtil.closeQuietly(preparedStatement_removeFilesMediaByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeFileMediaByVideoIdAndFile);
        // Table: Log
        IOUtil.closeQuietly(preparedStatement_removeAllLogs);
        IOUtil.closeQuietly(preparedStatement_removeLogByLogId);
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
        IOUtil.closeQuietly(preparedStatement_removeAllTokens);
        IOUtil.closeQuietly(preparedStatement_removeTokenByTokenId);
        IOUtil.closeQuietly(preparedStatement_removeTokenByToken);
        // Table: Uploader
        IOUtil.closeQuietly(preparedStatement_removeAllUploaders);
        IOUtil.closeQuietly(preparedStatement_removeUploaderByUploaderId);
        // Table: Video
        IOUtil.closeQuietly(preparedStatement_removeAllVideos);
        IOUtil.closeQuietly(preparedStatement_removeVideoByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeVideosByChannelId);
        IOUtil.closeQuietly(preparedStatement_removeVideosByUploaderId);
        // Table: Video Logs
        IOUtil.closeQuietly(preparedStatement_removeAllVideosLogs);
        IOUtil.closeQuietly(preparedStatement_removeVideosLogsByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeVideosLogsByLogId);
        IOUtil.closeQuietly(preparedStatement_removeVideoLogByVideoIdAndLogId);
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_removeAllQueuedVideos);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideoById);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideosByRequesterId);
        //
        // //
    }
    
    @Override
    public int getLastInsertId() { //TODO Test this
        if (!isConnected()) {
            return -1;
        }
        synchronized (preparedStatement_getLastInsertId) {
            return useResultSetAndClose(preparedStatement_getLastInsertId::executeQuery, (resultSet) -> resultSet.getInt(1));
        }
    }
    
    @Override
    public List<AuthorizationToken> getAllAuthorizationTokens() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllTokens) {
            return useResultSetAndClose(preparedStatement_getAllTokens::executeQuery, YouTubeDatabase::resultSetToAuthorizationTokens);
        }
    }
    
    @Override
    public AuthorizationToken getAuthorizationTokenByToken(String token) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getTokenByToken) {
            if (!setPreparedStatement(preparedStatement_getTokenByToken, token)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getTokenByToken::executeQuery, YouTubeDatabase::resultSetToAuthorizationToken);
        }
    }
    
    @Override
    public boolean hasAuthorizationToken(String token) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getTokenByToken) {
            if (!setPreparedStatement(preparedStatement_getTokenByToken, token)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getTokenByToken::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public boolean hasChannel(String channelId) {
        if (!isConnected() || channelId == null || channelId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getChannelByChannelId) {
            if (!setPreparedStatement(preparedStatement_getChannelByChannelId, channelId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getChannelByChannelId::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public boolean hasUploader(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getUploaderByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getUploaderByUploaderId, uploaderId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getUploaderByUploaderId::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public boolean hasRequester(int requesterId) {
        if (!isConnected() || requesterId < -1) {
            return false;
        }
        synchronized (preparedStatement_getRequesterByRequesterId) {
            if (!setPreparedStatement(preparedStatement_getRequesterByRequesterId, requesterId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getRequesterByRequesterId::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    @Override
    public boolean hasRequester(String tag) {
        if (!isConnected() || tag == null || tag.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getRequesterByTag) {
            if (!setPreparedStatement(preparedStatement_getRequesterByTag, tag)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getRequesterByTag::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public boolean hasQueuedVideo(int queuedVideoId) {
        if (!isConnected() || queuedVideoId < 0) {
            return false;
        }
        synchronized (preparedStatement_getQueuedVideoById) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideoById, queuedVideoId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideoById::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public boolean hasVideo(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getVideoByVideoId) {
            if (!setPreparedStatement(preparedStatement_getVideoByVideoId, videoId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getVideoByVideoId::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public boolean hasPlaylist(String playlistId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getPlaylistByPlaylistId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistByPlaylistId, playlistId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistByPlaylistId::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public DatabaseYouTubeVideo getVideoByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideoByVideoId) {
            if (!setPreparedStatement(preparedStatement_getVideoByVideoId, videoId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getVideoByVideoId::executeQuery, this::resultSetToDatabaseYouTubeVideo);
        }
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getAllVideos() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllVideos) {
            return useResultSetAndClose(preparedStatement_getAllVideos::executeQuery, this::resultSetToDatabaseYouTubeVideos);
        }
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideosByPlaylistId(String playlistId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty()) {
            return null;
        }
        final List<String> videoIds = getVideoIdsByPlaylistId(playlistId);
        if (videoIds == null || videoIds.isEmpty()) {
            return null;
        }
        return videoIds.stream().map(this::getVideoByVideoId).filter(Objects::nonNull).collect(Collectors.toList());
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideosByChannelId(String channelId) {
        if (!isConnected() || channelId == null || channelId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideosByChannelId) {
            if (!setPreparedStatement(preparedStatement_getVideosByChannelId, channelId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getVideosByChannelId::executeQuery, this::resultSetToDatabaseYouTubeVideos);
        }
    }
    
    @Override
    public List<DatabaseYouTubeVideo> getVideosByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideosByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getVideosByUploaderId, uploaderId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getVideosByUploaderId::executeQuery, this::resultSetToDatabaseYouTubeVideos);
        }
    }
    
    @Override
    public List<String> getAllVideoIds() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllVideos) {
            return useResultSetAndClose(preparedStatement_getAllVideos::executeQuery, YouTubeDatabase::resultSetVideoIdsFromVideos);
        }
    }
    
    @Override
    public List<String> getVideoIdsByPlaylistId(String playlistId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getPlaylistVideosByPlaylistId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistVideosByPlaylistId, playlistId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistVideosByPlaylistId::executeQuery, YouTubeDatabase::resultSetVideoIdsFromPlaylistVideos);
        }
    }
    
    @Override
    public List<String> getVideoIdsByChannelId(String channelId) {
        if (!isConnected() || channelId == null || channelId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideosByChannelId) {
            if (!setPreparedStatement(preparedStatement_getVideosByChannelId, channelId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getVideosByChannelId::executeQuery, YouTubeDatabase::resultSetVideoIdsFromVideos);
        }
    }
    
    @Override
    public List<String> getVideoIdsByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideosByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getVideosByUploaderId, uploaderId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getVideosByUploaderId::executeQuery, YouTubeDatabase::resultSetVideoIdsFromVideos);
        }
    }
    
    @Override
    public int getVideoCountByChannelId(String channelId) {
        if (!isConnected() || channelId == null || channelId.isEmpty()) {
            return -1;
        }
        synchronized (preparedStatement_getVideoCountByChannelId) {
            if (!setPreparedStatement(preparedStatement_getVideoCountByChannelId, channelId)) {
                return -1;
            }
            return useResultSetAndClose(preparedStatement_getVideoCountByChannelId::executeQuery, YouTubeDatabase::resultSetToVideoCount);
        }
    }
    
    @Override
    public int getVideoCountByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return -1;
        }
        synchronized (preparedStatement_getVideoCountByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getVideoCountByUploaderId, uploaderId)) {
                return -1;
            }
            return useResultSetAndClose(preparedStatement_getVideoCountByUploaderId::executeQuery, YouTubeDatabase::resultSetToVideoCount);
        }
    }
    
    @Override
    public boolean playlistContainsVideo(String playlistId, String videoId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId, playlistId, videoId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId::executeQuery, YouTubeDatabase::resultSetHasNext, false);
        }
    }
    
    @Override
    public boolean channelHasVideo(String channelId, String videoId) {
        if (!isConnected() || channelId == null || channelId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        final DatabaseYouTubeVideo databaseYouTubeVideo = getVideoByVideoId(videoId);
        if (databaseYouTubeVideo == null) {
            return false;
        }
        return Objects.equals(channelId, databaseYouTubeVideo.getChannelId());
    }
    
    @Override
    public boolean uploaderUploadedVideo(String uploaderId, String videoId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        final DatabaseYouTubeVideo databaseYouTubeVideo = getVideoByVideoId(videoId);
        if (databaseYouTubeVideo == null) {
            return false;
        }
        return Objects.equals(uploaderId, databaseYouTubeVideo.getUploaderId());
    }
    
    @Override
    public DatabaseYouTubePlaylist getPlaylistByPlaylistId(String playlistId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getPlaylistByPlaylistId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistByPlaylistId, playlistId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistByPlaylistId::executeQuery, this::resultSetToDatabaseYouTubePlaylist);
        }
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getAllPlaylists() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllPlaylists) {
            return useResultSetAndClose(preparedStatement_getAllPlaylists::executeQuery, this::resultSetToDatabaseYouTubePlaylists);
        }
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getPlaylistsByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        final List<String> playlistIds = getPlaylistIdsByVideoId(videoId);
        if (playlistIds == null || playlistIds.isEmpty()) {
            return null;
        }
        return playlistIds.stream().map(this::getPlaylistByPlaylistId).filter(Objects::nonNull).collect(Collectors.toList());
    }
    
    @Override
    public List<DatabaseYouTubePlaylist> getPlaylistsByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getPlaylistsByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistsByUploaderId, uploaderId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getPlaylistsByUploaderId::executeQuery, this::resultSetToDatabaseYouTubePlaylists);
        }
    }
    
    @Override
    public List<String> getAllPlaylistIds() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllPlaylists) {
            return useResultSetAndClose(preparedStatement_getAllPlaylists::executeQuery, YouTubeDatabase::resultSetPlaylistIdsFromPlaylists);
        }
    }
    
    @Override
    public List<String> getPlaylistIdsByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getPlaylistVideosByVideoId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistVideosByVideoId, videoId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistVideosByVideoId::executeQuery, YouTubeDatabase::resultSetPlaylistIdsFromPlaylistVideos);
        }
    }
    
    @Override
    public List<String> getPlaylistIdsByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getPlaylistsByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistsByUploaderId, uploaderId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getPlaylistsByUploaderId::executeQuery, YouTubeDatabase::resultSetPlaylistIdsFromPlaylists);
        }
    }
    
    @Override
    public int getPlaylistCountByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return -1;
        }
        synchronized (preparedStatement_getPlaylistCountByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistCountByUploaderId, uploaderId)) {
                return -1;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistCountByUploaderId::executeQuery, YouTubeDatabase::resultSetToPlaylistCount);
        }
    }
    
    @Override
    public int getIndexInPlaylist(String playlistId, String videoId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return -1;
        }
        synchronized (preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId, playlistId, videoId)) {
                return -1;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId::executeQuery, YouTubeDatabase::resultSetToPlaylistIndex);
        }
    }
    
    @Override
    public int getVideoCountByPlaylistId(String playlistId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty()) {
            return -1;
        }
        synchronized (preparedStatement_getVideoCountByPlaylistId) {
            if (!setPreparedStatement(preparedStatement_getVideoCountByPlaylistId, playlistId)) {
                return -1;
            }
            return useResultSetAndClose(preparedStatement_getVideoCountByPlaylistId::executeQuery, YouTubeDatabase::resultSetToVideoCount);
        }
    }
    
    @Override
    public int getPlaylistCountByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return -1;
        }
        synchronized (preparedStatement_getPlaylistCountByVideoId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistCountByVideoId, videoId)) {
                return -1;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistCountByVideoId::executeQuery, YouTubeDatabase::resultSetToPlaylistCount);
        }
    }
    
    @Override
    public boolean uploaderCreatedPlaylist(String uploaderId, String playlistId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty() || playlistId == null || playlistId.isEmpty()) {
            return false;
        }
        final DatabaseYouTubePlaylist databaseYouTubePlaylist = getPlaylistByPlaylistId(playlistId);
        if (databaseYouTubePlaylist == null) {
            return false;
        }
        return Objects.equals(uploaderId, databaseYouTubePlaylist.getUploaderId());
    }
    
    @Override
    public List<DatabaseYouTubeChannel> getAllChannels() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllChannels) {
            return useResultSetAndClose(preparedStatement_getAllChannels::executeQuery, this::resultSetToDatabaseYouTubeChannels);
        }
    }
    
    @Override
    public List<String> getAllChannelIds() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllChannels) {
            return useResultSetAndClose(preparedStatement_getAllChannels::executeQuery, YouTubeDatabase::resultSetChannelIdsFromChannels);
        }
    }
    
    @Override
    public DatabaseYouTubeChannel getChannelByChannelId(String channelId) {
        if (!isConnected() || channelId == null || channelId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getChannelByChannelId) {
            if (!setPreparedStatement(preparedStatement_getChannelByChannelId, channelId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getChannelByChannelId::executeQuery, this::resultSetToDatabaseYouTubeChannel);
        }
    }
    
    @Override
    public List<DatabaseYouTubeUploader> getAllUploaders() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllUploaders) {
            return useResultSetAndClose(preparedStatement_getAllUploaders::executeQuery, this::resultSetToDatabaseYouTubeUploaders);
        }
    }
    
    @Override
    public List<String> getAllUploaderIds() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllChannels) {
            return useResultSetAndClose(preparedStatement_getAllChannels::executeQuery, YouTubeDatabase::resultSetUploaderIdsFromUploaders);
        }
    }
    
    @Override
    public DatabaseYouTubeUploader getUploaderByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getUploaderByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getUploaderByUploaderId, uploaderId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getUploaderByUploaderId::executeQuery, this::resultSetToDatabaseYouTubeUploader);
        }
    }
    
    @Override
    public DatabaseMediaFile getMediaFileByVideoIdAndFile(String videoId, String file) {
        if (!isConnected() || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getFileMediaByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_getFileMediaByVideoIdAndFile, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getFileMediaByVideoIdAndFile::executeQuery, this::resultSetToDatabaseMediaFile);
        }
    }
    
    @Override
    public List<DatabaseMediaFile> getMediaFilesByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getFilesMediaByVideoId) {
            if (!setPreparedStatement(preparedStatement_getFilesMediaByVideoId, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getFilesMediaByVideoId::executeQuery, this::resultSetToDatabaseMediaFiles);
        }
    }
    
    @Override
    public DatabaseExtraFile getExtraFileByVideoIdAndFile(String videoId, String file) {
        if (!isConnected() || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getFileExtraByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_getFileExtraByVideoIdAndFile, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getFileExtraByVideoIdAndFile::executeQuery, this::resultSetToDatabaseExtraFile);
        }
    }
    
    @Override
    public List<DatabaseExtraFile> getExtraFilesByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getFilesExtraByVideoId) {
            if (!setPreparedStatement(preparedStatement_getFilesExtraByVideoId, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getFilesExtraByVideoId::executeQuery, this::resultSetToDatabaseExtraFiles);
        }
    }
    
    @Override
    public List<DatabaseRequester> getAllRequesters() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllRequesters) {
            return useResultSetAndClose(preparedStatement_getAllRequesters::executeQuery, this::resultSetToDatabaseRequesters);
        }
    }
    
    @Override
    public List<Integer> getAllRequesterIds() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllRequesters) {
            return useResultSetAndClose(preparedStatement_getAllRequesters::executeQuery, YouTubeDatabase::resultSetRequesterIdsFromRequesters);
        }
    }
    
    @Override
    public DatabaseRequester getRequesterByRequesterId(int requesterId) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getRequesterByRequesterId) {
            if (!setPreparedStatement(preparedStatement_getRequesterByRequesterId, requesterId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getRequesterByRequesterId::executeQuery, this::resultSetToDatabaseRequester);
        }
    }
    
    @Override
    public DatabaseRequester getRequesterByTag(String tag) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getRequesterByTag) {
            if (!setPreparedStatement(preparedStatement_getRequesterByTag, tag)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getRequesterByTag::executeQuery, this::resultSetToDatabaseRequester);
        }
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo getQueuedVideoById(int id) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideoById) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideoById, id)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideoById::executeQuery, this::resultSetToDatabaseQueuedYouTubeVideo);
        }
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getAllQueuedVideos() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllQueuedVideos) {
            return useResultSetAndClose(preparedStatement_getAllQueuedVideos::executeQuery, this::resultSetToDatabaseQueuedYouTubeVideos);
        }
    }
    
    @Override
    public List<String> getQueuedVideoIdsByVideoId(String videoId) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideosByVideoId) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideosByVideoId, videoId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideosByVideoId::executeQuery, YouTubeDatabase::resultSetVideoIdsFromQueuedVideos);
        }
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getQueuedVideosByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideosByVideoId) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideosByVideoId, videoId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideosByVideoId::executeQuery, this::resultSetToDatabaseQueuedYouTubeVideos);
        }
    }
    
    @Override
    public List<String> getQueuedVideoIdsByRequesterId(int requesterId) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideosByRequesterId) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideosByRequesterId, requesterId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideosByRequesterId::executeQuery, YouTubeDatabase::resultSetVideoIdsFromQueuedVideos);
        }
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getQueuedVideosByRequesterId(int requesterId) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideosByRequesterId) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideosByRequesterId, requesterId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideosByRequesterId::executeQuery, this::resultSetToDatabaseQueuedYouTubeVideos);
        }
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo getNextQueuedVideo() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getNextQueuedVideo) {
            return useResultSetAndClose(preparedStatement_getNextQueuedVideo::executeQuery, this::resultSetToDatabaseQueuedYouTubeVideo);
        }
    }
    
    @Override
    public List<DatabaseQueuedYouTubeVideo> getNextQueuedVideos() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getNextQueuedVideos) {
            return useResultSetAndClose(preparedStatement_getNextQueuedVideos::executeQuery, this::resultSetToDatabaseQueuedYouTubeVideos);
        }
    }
    
    @Override
    public DatabaseYouTubeChannel createChannel(String channelId, String name) {
        final DatabaseYouTubeChannel databaseYouTubeChannel = new DatabaseYouTubeChannel(channelId, name);
        databaseYouTubeChannel.setDatabase(this);
        if (!addChannel(databaseYouTubeChannel)) {
            return null;
        }
        return databaseYouTubeChannel;
    }
    
    @Override
    public DatabaseYouTubeUploader createUploader(String uploaderId, String name) {
        final DatabaseYouTubeUploader databaseYouTubeUploader = new DatabaseYouTubeUploader(uploaderId, name);
        databaseYouTubeUploader.setDatabase(this);
        if (!addUploader(databaseYouTubeUploader)) {
            return null;
        }
        return databaseYouTubeUploader;
    }
    
    @Override
    public DatabaseRequester createRequester(String tag, String name) {
        final DatabaseRequester databaseRequester = new DatabaseRequester(tag, name);
        databaseRequester.setDatabase(this);
        if (!addRequester(databaseRequester)) {
            return null;
        }
        return databaseRequester;
    }
    
    @Override
    public DatabaseYouTubeVideo createVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long duration, Instant uploadTimestamp) {
        final DatabaseYouTubeVideo databaseYouTubeVideo = new DatabaseYouTubeVideo(videoId, channelId, uploaderId, title, altTitle, duration, uploadTimestamp);
        databaseYouTubeVideo.setDatabase(this);
        if (!addVideo(databaseYouTubeVideo)) {
            return null;
        }
        return databaseYouTubeVideo;
    }
    
    @Override
    public DatabaseQueuedYouTubeVideo createQueuedVideo(String videoId, int priority, Instant requestedTimestamp, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state) {
        final DatabaseQueuedYouTubeVideo databaseQueuedYouTubeVideo = new DatabaseQueuedYouTubeVideo(videoId, priority, requestedTimestamp, requesterId, fileType, arguments, configFile, outputDirectory, state);
        databaseQueuedYouTubeVideo.setDatabase(this);
        if (!addQueuedVideo(databaseQueuedYouTubeVideo)) {
            return null;
        }
        return databaseQueuedYouTubeVideo;
    }
    
    @Override
    public boolean addVideoToPlaylist(DatabaseYouTubePlaylist playlist, DatabaseYouTubeVideo video, int index) {
        if (playlist == null || video == null) {
            return false;
        }
        return addVideoToPlaylist(playlist.getPlaylistId(), video.getVideoId(), index);
    }
    
    @Override
    public boolean addVideoToPlaylist(String playlistId, String videoId, int index) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        //TODO Only add, if it's not already in?
        synchronized (preparedStatement_addPlaylistVideo) {
            if (!setPreparedStatement(preparedStatement_addPlaylistVideo, playlistId, videoId, index)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_addPlaylistVideo.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean addVideoToPlaylists(DatabaseYouTubeVideo video, List<DatabaseYouTubePlaylist> playlists) {
        if (video == null || playlists == null) {
            return false;
        }
        return addVideoToPlaylists(video.getVideoId(), playlists.stream().filter(Objects::nonNull).map(DatabaseYouTubePlaylist::getPlaylistId).collect(Collectors.toList()));
    }
    
    @Override
    public boolean addVideoToPlaylists(String videoId, List<String> playlistIds) {
        if (videoId == null || playlistIds == null) {
            return false;
        }
        boolean good = true;
        for (String playlistId : playlistIds) {
            if (!addVideoToPlaylist(playlistId, videoId)) {
                good = false;
            }
        }
        return good;
    }
    
    @Override
    public boolean addVideosToPlaylist(DatabaseYouTubePlaylist playlist, List<DatabaseYouTubeVideo> videos) {
        if (playlist == null || videos == null) {
            return false;
        }
        return addVideosToPlaylist(playlist.getPlaylistId(), videos.stream().filter(Objects::nonNull).map(DatabaseYouTubeVideo::getVideoId).collect(Collectors.toList()));
    }
    
    @Override
    public boolean addVideosToPlaylist(String playlistId, List<String> videoIds) {
        if (playlistId == null || videoIds == null) {
            return false;
        }
        boolean good = true;
        for (String videoId : videoIds) {
            if (!addVideoToPlaylist(playlistId, videoId)) {
                good = false;
            }
        }
        return good;
    }
    
    @Override
    public boolean addPlaylist(DatabaseYouTubePlaylist playlist) {
        if (!isConnected() || playlist == null) {
            return false;
        }
        synchronized (preparedStatement_addPlaylist) {
            if (!setPreparedStatement(preparedStatement_addPlaylist, playlist.getPlaylistId(), playlist.getTitle(), playlist.getPlaylist(), playlist.getUploaderId())) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_addPlaylist.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean addAuthorizationToken(AuthorizationToken authorizationToken) {
        if (!isConnected() || authorizationToken == null) {
            return false;
        }
        synchronized (preparedStatement_addToken) {
            if (!setPreparedStatement(preparedStatement_addToken, authorizationToken.getToken(), authorizationToken.getLevel().name(), authorizationToken.getCreated().toEpochMilli(), authorizationToken.getExpiration() == null ? 0 : authorizationToken.getExpiration().toEpochMilli())) {
                return false;
            }
            final boolean success = Standard.silentError(() -> preparedStatement_addToken.executeUpdate()) > 0;
            final int id = getLastInsertId();
            authorizationToken.setId(id);
            return success;
        }
    }
    
    @Override
    public boolean addQueuedVideo(DatabaseQueuedYouTubeVideo queuedVideo) {
        if (!isConnected() || queuedVideo == null) {
            return false;
        }
        synchronized (preparedStatement_addQueuedVideo) {
            if (!setPreparedStatement(preparedStatement_addQueuedVideo, queuedVideo.getVideoId(), queuedVideo.getPriority(), queuedVideo.getRequestedAsTimestamp(), queuedVideo.getRequesterId(), queuedVideo.getFileType(), queuedVideo.getArguments(), queuedVideo.getConfigFile(), queuedVideo.getOutputDirectory(), queuedVideo.getState().name())) {
                return false;
            }
            final boolean success = Standard.silentError(() -> preparedStatement_addQueuedVideo.executeUpdate()) > 0;
            final int id = getLastInsertId();
            queuedVideo.setId(id);
            return success;
        }
    }
    
    @Override
    public boolean addVideo(DatabaseYouTubeVideo video) {
        if (!isConnected() || video == null) {
            return false;
        }
        synchronized (preparedStatement_addVideo) {
            if (!setPreparedStatement(preparedStatement_addVideo, video.getVideoId(), video.getChannelId(), video.getUploaderId(), video.getTitle(), video.getAltTitle(), video.getDurationMillis(), video.getUploadDateAsLong())) { //FIXME //TODO Switch to the TIMESTAMP SQL Column type
                return false;
            }
            return Standard.silentError(() -> preparedStatement_addVideo.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean addChannel(DatabaseYouTubeChannel channel) {
        if (!isConnected() || channel == null) {
            return false;
        }
        synchronized (preparedStatement_addChannel) {
            if (!setPreparedStatement(preparedStatement_addChannel, channel.getChannelId(), channel.getName())) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_addChannel.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean addUploader(DatabaseYouTubeUploader uploader) {
        if (!isConnected() || uploader == null) {
            return false;
        }
        synchronized (preparedStatement_addUploader) {
            if (!setPreparedStatement(preparedStatement_addUploader, uploader.getUploaderId(), uploader.getName())) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_addUploader.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean addRequester(DatabaseRequester requester) {
        if (!isConnected() || requester == null) {
            return false;
        }
        synchronized (preparedStatement_addRequester) {
            if (!setPreparedStatement(preparedStatement_addRequester, requester.getTag(), requester.getName(), requester.getCreated())) {
                return false;
            }
            final boolean success = Standard.silentError(() -> preparedStatement_addRequester.executeUpdate()) > 0;
            final int id = getLastInsertId();
            requester.setRequesterId(id);
            return success;
        }
    }
    
    @Override
    public boolean setAuthorizationTokenByToken(AuthorizationToken authorizationToken, String token) {
        if (!isConnected() || authorizationToken == null || token == null || token.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setTokenByToken) {
            if (!setPreparedStatement(preparedStatement_setTokenByToken, authorizationToken.getToken(), authorizationToken.getLevel().name(), authorizationToken.getCreated().toEpochMilli(), authorizationToken.getExpiration() == null ? 0 : authorizationToken.getExpiration().toEpochMilli(), authorizationToken.getTimesUsed(), token)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setTokenByToken.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setAuthorizationTokenTimesUsedByToken(String token, int timesUsed) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setTokenTimesUsedByToken) {
            if (!setPreparedStatement(preparedStatement_setTokenTimesUsedByToken, timesUsed, token)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setTokenTimesUsedByToken.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setPlaylistVideoIndex(DatabaseYouTubePlaylist playlist, DatabaseYouTubeVideo video, int index) {
        if (!isConnected() || playlist == null || video == null || !playlist.containsVideo(video)) {
            return false;
        }
        synchronized (preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId) {
            if (!setPreparedStatement(preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId, playlist.getPlaylistId(), video.getVideoId(), index, playlist.getPlaylistId(), video.getVideoId())) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setVideoByVideoId(DatabaseYouTubeVideo video, String videoId) {
        if (!isConnected() || video == null || videoId == null || videoId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setVideoByVideoId) {
            if (!setPreparedStatement(preparedStatement_setVideoByVideoId, video.getVideoId(), video.getChannelId(), video.getUploaderId(), video.getTitle(), video.getAltTitle(), video.getDurationMillis(), video.getUploadDateAsString(), videoId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setVideoByVideoId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setPlaylistByPlaylistId(DatabaseYouTubePlaylist playlist, String playlistId) {
        if (!isConnected() || playlist == null || playlistId == null || playlistId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setPlaylistByPlaylistId) {
            if (!setPreparedStatement(preparedStatement_setPlaylistByPlaylistId, playlist.getPlaylistId(), playlist.getTitle(), playlist.getPlaylist(), playlist.getUploaderId(), playlistId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setPlaylistByPlaylistId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setMediaFileByVideoIdAndFile(DatabaseMediaFile mediaFile, String videoId, String file) {
        if (!isConnected() || mediaFile == null || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setFileMediaByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_setFileMediaByVideoIdAndFile, mediaFile.getVideoId(), mediaFile.getFile(), mediaFile.getFileType(), mediaFile.getFormat(), mediaFile.getVcodec(), mediaFile.getAcodec(), mediaFile.getWidth(), mediaFile.getHeight(), mediaFile.getFps(), mediaFile.getAsr(), videoId, file)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setFileMediaByVideoIdAndFile.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setMediaFilesByVideoId(List<DatabaseMediaFile> mediaFiles, String videoId) {
        if (!isConnected() || mediaFiles == null || videoId == null || videoId.isEmpty()) {
            return false;
        }
        throw new NotYetImplementedRuntimeException("YouTubeDatabase::setMediaFilesByVideoId");
    }
    
    @Override
    public boolean setExtraFileByVideoIdAndFile(DatabaseExtraFile extraFile, String videoId, String file) {
        if (!isConnected() || extraFile == null || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setFileExtraByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_setFileExtraByVideoIdAndFile, extraFile.getVideoId(), extraFile.getFile(), extraFile.getFileType(), videoId, file)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setFileExtraByVideoIdAndFile.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setExtraFilesByVideoId(List<DatabaseExtraFile> extraFiles, String videoId) {
        if (!isConnected() || extraFiles == null || videoId == null || videoId.isEmpty()) {
            return false;
        }
        throw new NotYetImplementedRuntimeException("YouTubeDatabase::setExtraFilesByVideoId");
    }
    
    @Override
    public boolean setChannelByChannelId(DatabaseYouTubeChannel channel, String channelId) {
        if (!isConnected() || channel == null || channelId == null || channelId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setChannelByChannelId) {
            if (!setPreparedStatement(preparedStatement_setChannelByChannelId, channel.getChannelId(), channel.getName(), channelId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setChannelByChannelId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setUploaderByUploaderId(DatabaseYouTubeUploader uploader, String uploaderId) {
        if (!isConnected() || uploader == null || uploaderId == null || uploaderId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setUploaderByUploaderId) {
            if (!setPreparedStatement(preparedStatement_setUploaderByUploaderId, uploader.getUploaderId(), uploader.getName(), uploaderId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setUploaderByUploaderId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setQueuedVideoById(DatabaseQueuedYouTubeVideo queuedVideo, int id) {
        if (!isConnected() || queuedVideo == null) {
            return false;
        }
        synchronized (preparedStatement_setQueuedVideoById) {
            if (!setPreparedStatement(preparedStatement_setQueuedVideoById, queuedVideo.getId(), queuedVideo.getVideoId(), queuedVideo.getPriority(), queuedVideo.getRequestedAsTimestamp(), queuedVideo.getArguments(), queuedVideo.getConfigFile(), queuedVideo.getOutputDirectory(), queuedVideo.getState().name(), id)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setQueuedVideoById.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setRequesterByRequesterId(DatabaseRequester requester, int requesterId) {
        if (!isConnected() || requester == null) {
            return false;
        }
        synchronized (preparedStatement_setRequesterByRequesterId) {
            if (!setPreparedStatement(preparedStatement_setRequesterByRequesterId, requester.getRequesterId(), requester.getTag(), requester.getName(), requesterId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setRequesterByRequesterId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setRequesterByRequesterTag(DatabaseRequester requester, String tag) {
        if (!isConnected() || requester == null || tag == null || tag.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setRequesterByTag) {
            if (!setPreparedStatement(preparedStatement_setRequesterByTag, requester.getTag(), requester.getName(), tag)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setRequesterByTag.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeAllAuthorizationTokens() {
        if (!isConnected()) {
            return false;
        }
        synchronized (preparedStatement_removeAllTokens) {
            return Standard.silentError(() -> preparedStatement_removeAllTokens.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeAuthorizationTokenByToken(String token) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_removeTokenByToken) {
            if (!setPreparedStatement(preparedStatement_removeTokenByToken, token)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_removeTokenByToken.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeVideoFromPlaylist(DatabaseYouTubePlaylist playlist, DatabaseYouTubeVideo video) {
        if (!isConnected() || playlist == null || video == null) {
            return false;
        }
        synchronized (preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId) {
            if (!setPreparedStatement(preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId, playlist.getPlaylistId(), video.getVideoId())) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeVideoIdFromPlaylistId(String playlistId, String videoId) {
        if (!isConnected() || playlistId == null || videoId == null) {
            return false;
        }
        synchronized (preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId) {
            if (!setPreparedStatement(preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId, playlistId, videoId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeAllQueuedVideos() {
        if (!isConnected()) {
            return false;
        }
        synchronized (preparedStatement_removeAllQueuedVideos) {
            return Standard.silentError(() -> preparedStatement_removeAllQueuedVideos.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeQueuedVideoById(int id) {
        if (!isConnected() || id < 0) {
            return false;
        }
        synchronized (preparedStatement_removeQueuedVideoById) {
            if (!setPreparedStatement(preparedStatement_removeQueuedVideoById, id)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_removeQueuedVideoById.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeQueuedVideosByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_removeQueuedVideosByVideoId) {
            if (!setPreparedStatement(preparedStatement_removeQueuedVideosByVideoId, videoId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_removeQueuedVideosByVideoId.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeQueuedVideosByRequesterId(int requesterId) {
        if (!isConnected() || requesterId < 0) {
            return false;
        }
        synchronized (preparedStatement_removeQueuedVideosByRequesterId) {
            if (!setPreparedStatement(preparedStatement_removeQueuedVideosByRequesterId, requesterId)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_removeQueuedVideosByRequesterId.executeUpdate()) > 0;
        }
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
        return useResultSetAndClose(toughSupplier, toughFunction, null);
    }
    
    private static <R> R useResultSetAndClose(ToughSupplier<ResultSet> toughSupplier, ToughFunction<ResultSet, R> toughFunction, R defaultValue) {
        if (toughSupplier == null || toughFunction == null) {
            return defaultValue;
        }
        final ResultSet resultSet = toughSupplier.getWithoutException();
        if (resultSet == null || !Standard.silentError(resultSet::next)) {
            return defaultValue;
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
    
    private static int resultSetToVideoCount(ResultSet resultSet) {
        return resultSetToR(resultSet, (resultSet_) -> resultSet_.getInt(1));
    }
    
    private static int resultSetToPlaylistCount(ResultSet resultSet) {
        return resultSetToR(resultSet, (resultSet_) -> resultSet_.getInt(1));
    }
    
    private static boolean resultSetHasNext(ResultSet resultSet) {
        return resultSet != null/* && Standard.silentError(resultSet::next)*/;
    }
    
    private static AuthorizationToken resultSetToAuthorizationToken(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        final AuthorizationToken authorizationToken = Standard.silentError(() -> new AuthorizationToken(resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_TOKEN_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN), AuthorizationToken.TokenLevel.valueOf(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_TOKEN_COLUMN_LEVEL)), Instant.ofEpochMilli(resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_TOKEN_COLUMN_CREATED)), resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_TOKEN_COLUMN_EXPIRATION) == 0 ? null : Instant.ofEpochMilli(resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_TOKEN_COLUMN_EXPIRATION))));
        if (authorizationToken != null) {
            Standard.silentError(() -> authorizationToken.setTimesUsed(resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_TOKEN_COLUMN_TIMES_USED)));
        }
        return authorizationToken;
    }
    
    private static List<AuthorizationToken> resultSetToAuthorizationTokens(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<AuthorizationToken> authorizationTokens = new ArrayList<>();
        do {
            final AuthorizationToken authorizationToken = resultSetToAuthorizationToken(resultSet);
            if (authorizationToken != null) {
                authorizationTokens.add(authorizationToken);
            }
        } while (Standard.silentError(resultSet::next));
        return authorizationTokens;
    }
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER_UPLOAD_DATE_OLD = DateTimeFormatter.ofPattern("yyyyMMdd"); //FIXME REMOVE this
    
    private DatabaseYouTubeVideo resultSetToDatabaseYouTubeVideo(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        final String uploadDate_OLD = Standard.silentError(() -> resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOAD_DATE)); //FIXME Convert Database entries to TIMESTAMP?
        final Instant uploadDate = uploadDate_OLD == null ? null : LocalDate.parse(uploadDate_OLD, DATE_TIME_FORMATTER_UPLOAD_DATE_OLD).atStartOfDay().toInstant(TimeUtil.ZONE_OFFSET_UTC);
        //final Instant uploadDate = resultSet.getTimestamp(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOAD_DATE).toInstant();
        return setDatabase(Standard.silentError(() -> new DatabaseYouTubeVideo(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_CHANNEL_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOADER_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_TITLE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_ALT_TITLE), resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_COLUMN_DURATION), uploadDate)));
    }
    
    private List<DatabaseYouTubeVideo> resultSetToDatabaseYouTubeVideos(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseYouTubeVideo> databaseYouTubeVideos = new ArrayList<>();
        do {
            final DatabaseYouTubeVideo databaseYouTubeVideo = resultSetToDatabaseYouTubeVideo(resultSet);
            if (databaseYouTubeVideo != null) {
                databaseYouTubeVideos.add(databaseYouTubeVideo);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseYouTubeVideos;
    }
    
    private DatabaseYouTubePlaylist resultSetToDatabaseYouTubePlaylist(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new DatabaseYouTubePlaylist(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_COLUMN_UPLOADER_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_COLUMN_TITLE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_COLUMN_PLAYLIST)))); //TODO Timestamp?
    }
    
    private List<DatabaseYouTubePlaylist> resultSetToDatabaseYouTubePlaylists(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseYouTubePlaylist> databaseYouTubePlaylists = new ArrayList<>();
        do {
            final DatabaseYouTubePlaylist databaseYouTubePlaylist = resultSetToDatabaseYouTubePlaylist(resultSet);
            if (databaseYouTubePlaylist != null) {
                databaseYouTubePlaylists.add(databaseYouTubePlaylist);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseYouTubePlaylists;
    }
    
    private DatabaseQueuedYouTubeVideo resultSetToDatabaseQueuedYouTubeVideo(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new DatabaseQueuedYouTubeVideo(resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY), resultSet.getTimestamp(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED).toInstant(), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_FILE_TYPE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ARGUMENTS), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_CONFIG_FILE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_OUTPUT_DIRECTORY), QueuedVideoState.ofState(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE)))));
    }
    
    private List<DatabaseQueuedYouTubeVideo> resultSetToDatabaseQueuedYouTubeVideos(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseQueuedYouTubeVideo> databaseQueuedYouTubeVideos = new ArrayList<>();
        do {
            final DatabaseQueuedYouTubeVideo databaseQueuedYouTubeVideo = resultSetToDatabaseQueuedYouTubeVideo(resultSet);
            if (databaseQueuedYouTubeVideo != null) {
                databaseQueuedYouTubeVideos.add(databaseQueuedYouTubeVideo);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseQueuedYouTubeVideos;
    }
    
    private DatabaseMediaFile resultSetToDatabaseMediaFile(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new DatabaseMediaFile(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE_TYPE), resultSet.getTimestamp(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_CREATED).toInstant(), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FORMAT), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VCODEC), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ACODEC), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_WIDTH), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_HEIGHT), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FPS), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ASR))));
    }
    
    private List<DatabaseMediaFile> resultSetToDatabaseMediaFiles(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseMediaFile> databaseMediaFiles = new ArrayList<>();
        do {
            final DatabaseMediaFile databaseMediaFile = resultSetToDatabaseMediaFile(resultSet);
            if (databaseMediaFile != null) {
                databaseMediaFiles.add(databaseMediaFile);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseMediaFiles;
    }
    
    private DatabaseExtraFile resultSetToDatabaseExtraFile(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new DatabaseExtraFile(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE_TYPE), resultSet.getTimestamp(YouTubeDatabaseConstants.IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_CREATED).toInstant())));
    }
    
    private List<DatabaseExtraFile> resultSetToDatabaseExtraFiles(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseExtraFile> databaseExtraFiles = new ArrayList<>();
        do {
            final DatabaseExtraFile databaseExtraFile = resultSetToDatabaseExtraFile(resultSet);
            if (databaseExtraFile != null) {
                databaseExtraFiles.add(databaseExtraFile);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseExtraFiles;
    }
    
    private DatabaseYouTubeChannel resultSetToDatabaseYouTubeChannel(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new DatabaseYouTubeChannel(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_CHANNEL_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_CHANNEL_COLUMN_NAME)))); //TODO Timestamp?
    }
    
    private List<DatabaseYouTubeChannel> resultSetToDatabaseYouTubeChannels(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseYouTubeChannel> databaseYouTubeChannels = new ArrayList<>();
        do {
            final DatabaseYouTubeChannel databaseYouTubeChannel = resultSetToDatabaseYouTubeChannel(resultSet);
            if (databaseYouTubeChannel != null) {
                databaseYouTubeChannels.add(databaseYouTubeChannel);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseYouTubeChannels;
    }
    
    private DatabaseYouTubeUploader resultSetToDatabaseYouTubeUploader(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new DatabaseYouTubeUploader(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_UPLOADER_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_UPLOADER_COLUMN_NAME)))); //TODO Timestamp?
    }
    
    private List<DatabaseYouTubeUploader> resultSetToDatabaseYouTubeUploaders(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseYouTubeUploader> databaseYouTubeUploaders = new ArrayList<>();
        do {
            final DatabaseYouTubeUploader databaseYouTubeUploader = resultSetToDatabaseYouTubeUploader(resultSet);
            if (databaseYouTubeUploader != null) {
                databaseYouTubeUploaders.add(databaseYouTubeUploader);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseYouTubeUploaders;
    }
    
    private DatabaseRequester resultSetToDatabaseRequester(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new DatabaseRequester(resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTER_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTER_COLUMN_NAME), resultSet.getTimestamp(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTER_COLUMN_CREATED).toInstant())));
    }
    
    private List<DatabaseRequester> resultSetToDatabaseRequesters(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<DatabaseRequester> databaseRequesters = new ArrayList<>();
        do {
            final DatabaseRequester databaseRequester = resultSetToDatabaseRequester(resultSet);
            if (databaseRequester != null) {
                databaseRequesters.add(databaseRequester);
            }
        } while (Standard.silentError(resultSet::next));
        return databaseRequesters;
    }
    
    protected static void createTables(YouTubeDatabase<?> database) {
        //TODO Use the "database_create_tables.sql" to create (if not existing) tables...
    }
    
}
