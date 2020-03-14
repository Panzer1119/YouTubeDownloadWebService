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

package de.codemakers.download.database;

import de.codemakers.base.Standard;
import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.logger.Logger;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughSupplier;
import de.codemakers.download.YouTubeDL;
import de.codemakers.download.database.entities.AbstractEntity;
import de.codemakers.download.database.entities.AuthorizationToken;
import de.codemakers.download.database.entities.QueuedVideoState;
import de.codemakers.download.database.entities.impl.*;
import de.codemakers.download.entities.VideoInstanceInfo;
import de.codemakers.download.sources.YouTubeSource;
import de.codemakers.io.IOUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class YouTubeDatabase<C extends AbstractConnector> extends AbstractDatabase<YouTubeDatabase, MediaFile, ExtraFile, YouTubeVideo, YouTubePlaylist, QueuedYouTubeVideo, YouTubeChannel, YouTubeUploader, YouTubeRequester, C> {
    
    // // Selects / Gets
    private transient PreparedStatement preparedStatement_getLastInsertId = null;
    // Table: Authorization Tokens
    private transient PreparedStatement preparedStatement_getAllAuthorizationTokens = null;
    private transient PreparedStatement preparedStatement_getAuthorizationTokenByToken = null;
    // Table: Channels
    private transient PreparedStatement preparedStatement_getAllChannels = null;
    private transient PreparedStatement preparedStatement_getChannelByChannelId = null;
    // Table: Extra Files
    private transient PreparedStatement preparedStatement_getAllExtraFiles = null;
    private transient PreparedStatement preparedStatement_getExtraFilesByVideoId = null;
    private transient PreparedStatement preparedStatement_getExtraFilesByFileType = null;
    private transient PreparedStatement preparedStatement_getExtraFileByVideoIdAndFile = null;
    // Table: Media Files
    private transient PreparedStatement preparedStatement_getAllMediaFiles = null;
    private transient PreparedStatement preparedStatement_getMediaFilesByVideoId = null;
    private transient PreparedStatement preparedStatement_getMediaFilesByFileType = null;
    private transient PreparedStatement preparedStatement_getMediaFileByVideoIdAndFile = null;
    // Table: Playlists
    private transient PreparedStatement preparedStatement_getAllPlaylists = null;
    private transient PreparedStatement preparedStatement_getPlaylistByPlaylistId = null;
    private transient PreparedStatement preparedStatement_getPlaylistsByUploaderId = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_getAllPlaylistVideos = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideosByPlaylistId = null;
    private transient PreparedStatement preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId = null;
    // Table: Requesters
    private transient PreparedStatement preparedStatement_getAllRequesters = null;
    private transient PreparedStatement preparedStatement_getRequesterByRequesterId = null;
    private transient PreparedStatement preparedStatement_getRequesterByTag = null;
    // Table: Uploaders
    private transient PreparedStatement preparedStatement_getAllUploaders = null;
    private transient PreparedStatement preparedStatement_getUploaderByUploaderId = null;
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_getAllQueuedVideos = null;
    private transient PreparedStatement preparedStatement_getQueuedVideoById = null;
    private transient PreparedStatement preparedStatement_getQueuedVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_getQueuedVideosByRequesterId = null;
    private transient PreparedStatement preparedStatement_getNextQueuedVideos = null;
    private transient PreparedStatement preparedStatement_getNextQueuedVideo = null;
    // Table: Videos
    private transient PreparedStatement preparedStatement_getAllVideos = null;
    private transient PreparedStatement preparedStatement_getVideoByVideoId = null;
    private transient PreparedStatement preparedStatement_getVideosByChannelId = null;
    private transient PreparedStatement preparedStatement_getVideosByUploaderId = null;
    //
    // // Inserts / Adds
    // Table: Authorization Tokens
    private transient PreparedStatement preparedStatement_addAuthorizationToken = null;
    // Table: Channels
    private transient PreparedStatement preparedStatement_addChannel = null;
    // Table: Extra Files
    private transient PreparedStatement preparedStatement_addExtraFile = null;
    // Table: Media Files
    private transient PreparedStatement preparedStatement_addMediaFile = null;
    // Table: Playlists
    private transient PreparedStatement preparedStatement_addPlaylist = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_addPlaylistVideo = null;
    // Table: Requesters
    private transient PreparedStatement preparedStatement_addRequester = null;
    // Table: Uploaders
    private transient PreparedStatement preparedStatement_addUploader = null;
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_addQueuedVideo = null;
    // Table: Videos
    private transient PreparedStatement preparedStatement_addVideo = null;
    //
    // // Updates / Sets
    // Table: Authorization Tokens
    private transient PreparedStatement preparedStatement_setAuthorizationTokenByToken = null;
    private transient PreparedStatement preparedStatement_setAuthorizationTokenTimesUsedByToken = null;
    // Table: Channels
    private transient PreparedStatement preparedStatement_setChannelByChannelId = null;
    // Table: Extra Files
    private transient PreparedStatement preparedStatement_setExtraFileByVideoIdAndFile = null;
    // Table: Media Files
    private transient PreparedStatement preparedStatement_setMediaFileByVideoIdAndFile = null;
    // Table: Playlists
    private transient PreparedStatement preparedStatement_setPlaylistByPlaylistId = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId = null;
    // Table: Requesters
    private transient PreparedStatement preparedStatement_setRequesterByRequesterId = null;
    private transient PreparedStatement preparedStatement_setRequesterByTag = null;
    // Table: Uploaders
    private transient PreparedStatement preparedStatement_setUploaderByUploaderId = null;
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_setQueuedVideoById = null;
    // Table: Videos
    private transient PreparedStatement preparedStatement_setVideoByVideoId = null;
    //
    // // Deletes / Removes
    // Table: Authorization Tokens
    private transient PreparedStatement preparedStatement_removeAllAuthorizationTokens = null;
    private transient PreparedStatement preparedStatement_removeAuthorizationTokenByToken = null;
    // Table: Channels
    private transient PreparedStatement preparedStatement_removeAllChannels = null;
    private transient PreparedStatement preparedStatement_removeChannelByChannelId = null;
    // Table: Extra Files
    private transient PreparedStatement preparedStatement_removeAllExtraFiles = null;
    private transient PreparedStatement preparedStatement_removeExtraFilesByVideoId = null;
    private transient PreparedStatement preparedStatement_removeExtraFileByVideoIdAndFile = null;
    // Table: Media Files
    private transient PreparedStatement preparedStatement_removeAllMediaFiles = null;
    private transient PreparedStatement preparedStatement_removeMediaFilesByVideoId = null;
    private transient PreparedStatement preparedStatement_removeMediaFileByVideoIdAndFile = null;
    // Table: Playlists
    private transient PreparedStatement preparedStatement_removeAllPlaylists = null;
    private transient PreparedStatement preparedStatement_removePlaylistByPlaylistId = null;
    private transient PreparedStatement preparedStatement_removePlaylistsByUploaderId = null;
    // Table: Playlist Videos
    private transient PreparedStatement preparedStatement_removeAllPlaylistVideos = null;
    private transient PreparedStatement preparedStatement_removePlaylistVideosByPlaylistId = null;
    private transient PreparedStatement preparedStatement_removePlaylistVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId = null;
    // Table: Requesters
    private transient PreparedStatement preparedStatement_removeAllRequesters = null;
    private transient PreparedStatement preparedStatement_removeRequesterByRequesterId = null;
    private transient PreparedStatement preparedStatement_removeRequesterByTag = null;
    // Table: Uploaders
    private transient PreparedStatement preparedStatement_removeAllUploaders = null;
    private transient PreparedStatement preparedStatement_removeUploaderByUploaderId = null;
    // Table: Video Queue
    private transient PreparedStatement preparedStatement_removeAllQueuedVideos = null;
    private transient PreparedStatement preparedStatement_removeQueuedVideoById = null;
    private transient PreparedStatement preparedStatement_removeQueuedVideosByVideoId = null;
    private transient PreparedStatement preparedStatement_removeQueuedVideosByRequesterId = null;
    // Table: Videos
    private transient PreparedStatement preparedStatement_removeAllVideos = null;
    private transient PreparedStatement preparedStatement_removeVideoByVideoId = null;
    private transient PreparedStatement preparedStatement_removeVideosByChannelId = null;
    private transient PreparedStatement preparedStatement_removeVideosByUploaderId = null;
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
        // Table: Authorization Tokens
        preparedStatement_getAllAuthorizationTokens = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_SELECT_ALL);
        preparedStatement_getAuthorizationTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_SELECT_BY_TOKEN);
        // Table: Channels
        preparedStatement_getAllChannels = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_SELECT_ALL);
        preparedStatement_getChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_SELECT_BY_CHANNEL_ID);
        // Table: Extra Files
        preparedStatement_getAllExtraFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_ALL);
        preparedStatement_getExtraFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getExtraFilesByFileType = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_ALL_BY_FILE_TYPE);
        preparedStatement_getExtraFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_SELECT_BY_VIDEO_ID_AND_FILE);
        // Table: Media Files
        preparedStatement_getAllMediaFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_ALL);
        preparedStatement_getMediaFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getMediaFilesByFileType = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_ALL_BY_FILE_TYPE);
        preparedStatement_getMediaFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_SELECT_BY_VIDEO_ID_AND_FILE);
        // Table: Playlists
        preparedStatement_getAllPlaylists = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_SELECT_ALL);
        preparedStatement_getPlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_SELECT_BY_PLAYLIST_ID);
        preparedStatement_getPlaylistsByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_SELECT_ALL_UPLOADER_ID);
        // Table: Playlist Videos
        preparedStatement_getAllPlaylistVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL);
        preparedStatement_getPlaylistVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getPlaylistVideosByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_PLAYLIST_ID);
        preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requesters
        preparedStatement_getAllRequesters = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_SELECT_ALL);
        preparedStatement_getRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_SELECT_BY_REQUESTER_ID);
        preparedStatement_getRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_SELECT_BY_TAG);
        // Table: Uploaders
        preparedStatement_getAllUploaders = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_SELECT_ALL);
        preparedStatement_getUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_SELECT_BY_UPLOADER_ID);
        // Table: Video Queue
        preparedStatement_getAllQueuedVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL);
        preparedStatement_getQueuedVideoById = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_BY_ID);
        preparedStatement_getQueuedVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_BY_VIDEO_ID);
        preparedStatement_getQueuedVideosByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_BY_REQUESTER_ID);
        preparedStatement_getNextQueuedVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_NEXT);
        preparedStatement_getNextQueuedVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_SELECT_NEXT);
        // Table: Videos
        preparedStatement_getAllVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_ALL);
        preparedStatement_getVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_BY_VIDEO_ID);
        preparedStatement_getVideosByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_ALL_BY_CHANNEL_ID);
        preparedStatement_getVideosByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_SELECT_ALL_BY_UPLOADER_ID);
        //
        // // Inserts / Adds
        // Table: Authorization Tokens
        preparedStatement_addAuthorizationToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_INSERT);
        // Table: Channels
        preparedStatement_addChannel = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_INSERT);
        // Table: Extra Files
        preparedStatement_addExtraFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_INSERT);
        // Table: Media Files
        preparedStatement_addMediaFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_INSERT);
        // Table: Playlists
        preparedStatement_addPlaylist = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_INSERT);
        // Table: Playlist Videos
        preparedStatement_addPlaylistVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_INSERT);
        // Table: Requesters
        preparedStatement_addRequester = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_INSERT);
        // Table: Uploaders
        preparedStatement_addUploader = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_INSERT);
        // Table: Video Queue
        preparedStatement_addQueuedVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_INSERT);
        // Table: Videos
        preparedStatement_addVideo = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_INSERT);
        //
        // // Updates / Sets
        // Table: Authorization Tokens
        preparedStatement_setAuthorizationTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_UPDATE_BY_TOKEN);
        preparedStatement_setAuthorizationTokenTimesUsedByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_UPDATE_USED_BY_TOKEN);
        // Table: Channels
        preparedStatement_setChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_UPDATE_BY_CHANNEL_ID);
        // Table: Extra Files
        preparedStatement_setExtraFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_UPDATE_BY_VIDEO_ID_AND_FILE);
        // Table: Media Files
        preparedStatement_setMediaFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_UPDATE_BY_VIDEO_ID_AND_FILE);
        // Table: Playlists
        preparedStatement_setPlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_UPDATE_BY_PLAYLIST_ID);
        // Table: Playlist Videos
        preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_UPDATE_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requesters
        preparedStatement_setRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_UPDATE_BY_REQUESTER_ID);
        preparedStatement_setRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_UPDATE_BY_TAG);
        // Table: Uploaders
        preparedStatement_setUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_UPDATE_BY_UPLOADER_ID);
        // Table: Video Queue
        preparedStatement_setQueuedVideoById = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_UPDATE_BY_ID);
        // Table: Videos
        preparedStatement_setVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_UPDATE_BY_VIDEO_ID);
        //
        // // Deletes / Removes
        // Table: Authorization Tokens
        preparedStatement_removeAllAuthorizationTokens = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_DELETE_ALL);
        preparedStatement_removeAuthorizationTokenByToken = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_AUTHORIZATION_TOKENS_DELETE_BY_TOKEN);
        // Table: Channels
        preparedStatement_removeAllChannels = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_DELETE_ALL);
        preparedStatement_removeChannelByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_CHANNELS_DELETE_BY_CHANNEL_ID);
        // Table: Extra Files
        preparedStatement_removeAllExtraFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_DELETE_ALL);
        preparedStatement_removeExtraFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeExtraFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_EXTRA_FILES_DELETE_BY_VIDEO_ID_AND_FILE);
        // Table: Media Files
        preparedStatement_removeAllMediaFiles = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_DELETE_ALL);
        preparedStatement_removeMediaFilesByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeMediaFileByVideoIdAndFile = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_MEDIA_FILES_DELETE_BY_VIDEO_ID_AND_FILE);
        // Table: Playlists
        preparedStatement_removeAllPlaylists = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_DELETE_ALL);
        preparedStatement_removePlaylistByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_DELETE_BY_PLAYLIST_ID);
        preparedStatement_removePlaylistsByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLISTS_DELETE_ALL_BY_UPLOADER_ID);
        // Table: Playlist Videos
        preparedStatement_removeAllPlaylistVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL);
        preparedStatement_removePlaylistVideosByPlaylistId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_PLAYLIST_ID);
        preparedStatement_removePlaylistVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_BY_PLAYLIST_ID_AND_VIDEO_ID);
        // Table: Requesters
        preparedStatement_removeAllRequesters = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_DELETE_ALL);
        preparedStatement_removeRequesterByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_DELETE_BY_REQUESTER_ID);
        preparedStatement_removeRequesterByTag = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_REQUESTERS_DELETE_BY_TAG);
        // Table: Uploaders
        preparedStatement_removeAllUploaders = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_DELETE_ALL);
        preparedStatement_removeUploaderByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_UPLOADERS_DELETE_BY_CHANNEL_ID);
        // Table: Video Queue
        preparedStatement_removeAllQueuedVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL);
        preparedStatement_removeQueuedVideoById = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_BY_ID);
        preparedStatement_removeQueuedVideosByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL_BY_VIDEO_ID);
        preparedStatement_removeQueuedVideosByRequesterId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL_BY_REQUESTER_ID);
        // Table: Videos
        preparedStatement_removeAllVideos = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_ALL);
        preparedStatement_removeVideoByVideoId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_BY_VIDEO_ID);
        preparedStatement_removeVideosByChannelId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_ALL_BY_CHANNEL_ID);
        preparedStatement_removeVideosByUploaderId = createPreparedStatement(YouTubeDatabaseConstants.QUERY_TABLE_VIDEOS_DELETE_ALL_BY_UPLOADER_ID);
        //
        // //
    }
    
    private void closeStatements() {
        // // Selects / Gets
        IOUtil.closeQuietly(preparedStatement_getLastInsertId);
        // Table: Authorization Tokens
        IOUtil.closeQuietly(preparedStatement_getAllAuthorizationTokens);
        IOUtil.closeQuietly(preparedStatement_getAuthorizationTokenByToken);
        // Table: Channels
        IOUtil.closeQuietly(preparedStatement_getAllChannels);
        IOUtil.closeQuietly(preparedStatement_getChannelByChannelId);
        // Table: Extra Files
        IOUtil.closeQuietly(preparedStatement_getAllExtraFiles);
        IOUtil.closeQuietly(preparedStatement_getExtraFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_getExtraFilesByFileType);
        IOUtil.closeQuietly(preparedStatement_getExtraFileByVideoIdAndFile);
        // Table: Media Files
        IOUtil.closeQuietly(preparedStatement_getAllMediaFiles);
        IOUtil.closeQuietly(preparedStatement_getMediaFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_getMediaFilesByFileType);
        IOUtil.closeQuietly(preparedStatement_getMediaFileByVideoIdAndFile);
        // Table: Playlists
        IOUtil.closeQuietly(preparedStatement_getAllPlaylists);
        IOUtil.closeQuietly(preparedStatement_getPlaylistByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistsByUploaderId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_getAllPlaylistVideos);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideosByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId);
        // Table: Requesters
        IOUtil.closeQuietly(preparedStatement_getAllRequesters);
        IOUtil.closeQuietly(preparedStatement_getRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_getRequesterByTag);
        // Table: Uploaders
        IOUtil.closeQuietly(preparedStatement_getAllUploaders);
        IOUtil.closeQuietly(preparedStatement_getUploaderByUploaderId);
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_getAllQueuedVideos);
        IOUtil.closeQuietly(preparedStatement_getQueuedVideoById);
        IOUtil.closeQuietly(preparedStatement_getQueuedVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_getQueuedVideosByRequesterId);
        IOUtil.closeQuietly(preparedStatement_getNextQueuedVideos);
        IOUtil.closeQuietly(preparedStatement_getNextQueuedVideo);
        // Table: Videos
        IOUtil.closeQuietly(preparedStatement_getAllVideos);
        IOUtil.closeQuietly(preparedStatement_getVideoByVideoId);
        IOUtil.closeQuietly(preparedStatement_getVideosByChannelId);
        IOUtil.closeQuietly(preparedStatement_getVideosByUploaderId);
        //
        // // Inserts / Adds
        // Table: Authorization Tokens
        IOUtil.closeQuietly(preparedStatement_addAuthorizationToken);
        // Table: Channels
        IOUtil.closeQuietly(preparedStatement_addChannel);
        // Table: Extra Files
        IOUtil.closeQuietly(preparedStatement_addExtraFile);
        // Table: Media Files
        IOUtil.closeQuietly(preparedStatement_addMediaFile);
        // Table: Playlists
        IOUtil.closeQuietly(preparedStatement_addPlaylist);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_addPlaylistVideo);
        // Table: Requesters
        IOUtil.closeQuietly(preparedStatement_addRequester);
        // Table: Uploaders
        IOUtil.closeQuietly(preparedStatement_addUploader);
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_addQueuedVideo);
        // Table: Videos
        IOUtil.closeQuietly(preparedStatement_addVideo);
        //
        // // Updates / Sets
        // Table: Authorization Tokens
        IOUtil.closeQuietly(preparedStatement_setAuthorizationTokenByToken);
        IOUtil.closeQuietly(preparedStatement_setAuthorizationTokenTimesUsedByToken);
        // Table: Channels
        IOUtil.closeQuietly(preparedStatement_setChannelByChannelId);
        // Table: Extra Files
        IOUtil.closeQuietly(preparedStatement_setExtraFileByVideoIdAndFile);
        // Table: Media Files
        IOUtil.closeQuietly(preparedStatement_setMediaFileByVideoIdAndFile);
        // Table: Playlists
        IOUtil.closeQuietly(preparedStatement_setPlaylistByPlaylistId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_setPlaylistVideoByPlaylistIdAndVideoId);
        // Table: Requesters
        IOUtil.closeQuietly(preparedStatement_setRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_setRequesterByTag);
        // Table: Uploaders
        IOUtil.closeQuietly(preparedStatement_setUploaderByUploaderId);
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_setQueuedVideoById);
        // Table: Videos
        IOUtil.closeQuietly(preparedStatement_setVideoByVideoId);
        //
        // // Deletes / Removes
        // Table: Authorization Tokens
        IOUtil.closeQuietly(preparedStatement_removeAllAuthorizationTokens);
        IOUtil.closeQuietly(preparedStatement_removeAuthorizationTokenByToken);
        // Table: Channels
        IOUtil.closeQuietly(preparedStatement_removeAllChannels);
        IOUtil.closeQuietly(preparedStatement_removeChannelByChannelId);
        // Table: Extra Files
        IOUtil.closeQuietly(preparedStatement_removeAllExtraFiles);
        IOUtil.closeQuietly(preparedStatement_removeExtraFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeExtraFileByVideoIdAndFile);
        // Table: Media Files
        IOUtil.closeQuietly(preparedStatement_removeAllMediaFiles);
        IOUtil.closeQuietly(preparedStatement_removeMediaFilesByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeMediaFileByVideoIdAndFile);
        // Table: Playlists
        IOUtil.closeQuietly(preparedStatement_removeAllPlaylists);
        IOUtil.closeQuietly(preparedStatement_removePlaylistByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_removePlaylistsByUploaderId);
        // Table: Playlist Videos
        IOUtil.closeQuietly(preparedStatement_removeAllPlaylistVideos);
        IOUtil.closeQuietly(preparedStatement_removePlaylistVideosByPlaylistId);
        IOUtil.closeQuietly(preparedStatement_removePlaylistVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_removePlaylistVideoByPlaylistIdAndVideoId);
        // Table: Requesters
        IOUtil.closeQuietly(preparedStatement_removeAllRequesters);
        IOUtil.closeQuietly(preparedStatement_removeRequesterByRequesterId);
        IOUtil.closeQuietly(preparedStatement_removeRequesterByTag);
        // Table: Uploaders
        IOUtil.closeQuietly(preparedStatement_removeAllUploaders);
        IOUtil.closeQuietly(preparedStatement_removeUploaderByUploaderId);
        // Table: Video Queue
        IOUtil.closeQuietly(preparedStatement_removeAllQueuedVideos);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideoById);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideosByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeQueuedVideosByRequesterId);
        // Table: Videos
        IOUtil.closeQuietly(preparedStatement_removeAllVideos);
        IOUtil.closeQuietly(preparedStatement_removeVideoByVideoId);
        IOUtil.closeQuietly(preparedStatement_removeVideosByChannelId);
        IOUtil.closeQuietly(preparedStatement_removeVideosByUploaderId);
        //
        // //
    }
    
    @Override
    public int getLastInsertId() { //TODO Test this
        synchronized (preparedStatement_getLastInsertId) {
            return useResultSetAndClose(preparedStatement_getLastInsertId::executeQuery, (resultSet) -> resultSet.getInt(1));
        }
    }
    
    @Override
    public List<AuthorizationToken> getAllAuthorizationTokens() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllAuthorizationTokens) {
            return useResultSetAndClose(preparedStatement_getAllAuthorizationTokens::executeQuery, YouTubeDatabase::resultSetToAuthorizationTokens);
        }
    }
    
    @Override
    public AuthorizationToken getAuthorizationTokenByToken(String token) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getAuthorizationTokenByToken) {
            if (!setPreparedStatement(preparedStatement_getAuthorizationTokenByToken, token)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getAuthorizationTokenByToken::executeQuery, YouTubeDatabase::resultSetToAuthorizationToken);
        }
    }
    
    @Override
    public boolean hasAuthorizationToken(String token) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getAuthorizationTokenByToken) {
            if (!setPreparedStatement(preparedStatement_getAuthorizationTokenByToken, token)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getAuthorizationTokenByToken::executeQuery, ResultSet::next);
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
            return useResultSetAndClose(preparedStatement_getChannelByChannelId::executeQuery, ResultSet::next);
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
            return useResultSetAndClose(preparedStatement_getUploaderByUploaderId::executeQuery, ResultSet::next);
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
            return useResultSetAndClose(preparedStatement_getRequesterByRequesterId::executeQuery, ResultSet::next);
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
            return useResultSetAndClose(preparedStatement_getQueuedVideoById::executeQuery, ResultSet::next);
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
            return useResultSetAndClose(preparedStatement_getVideoByVideoId::executeQuery, ResultSet::next);
        }
    }
    
    @Override
    public YouTubeVideo getVideoByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideoByVideoId) {
            if (!setPreparedStatement(preparedStatement_getVideoByVideoId, videoId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getVideoByVideoId::executeQuery, this::resultSetToYouTubeVideo);
        }
    }
    
    @Override
    public List<YouTubeVideo> getAllVideos() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllVideos) {
            return useResultSetAndClose(preparedStatement_getAllVideos::executeQuery, this::resultSetToYouTubeVideos);
        }
    }
    
    @Override
    public List<YouTubeVideo> getVideosByPlaylistId(String playlistId) {
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
    public YouTubePlaylist getPlaylistByPlaylistId(String playlistId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getPlaylistByPlaylistId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistByPlaylistId, playlistId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistByPlaylistId::executeQuery, this::resultSetToYouTubePlaylist);
        }
    }
    
    @Override
    public List<YouTubePlaylist> getAllPlaylists() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllPlaylists) {
            return useResultSetAndClose(preparedStatement_getAllPlaylists::executeQuery, this::resultSetToYouTubePlaylists);
        }
    }
    
    @Override
    public List<YouTubePlaylist> getPlaylistsByVideoId(String videoId) {
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
    public boolean playlistContainsVideo(String playlistId, String videoId) {
        if (!isConnected() || playlistId == null || playlistId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId, playlistId, videoId)) {
                return false;
            }
            return useResultSetAndClose(preparedStatement_getPlaylistVideoByPlaylistIdAndVideoId::executeQuery, ResultSet::next);
        }
    }
    
    @Override
    public MediaFile getMediaFileByVideoIdAndFile(String videoId, String file) {
        if (!isConnected() || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getMediaFileByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_getMediaFileByVideoIdAndFile, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getMediaFileByVideoIdAndFile::executeQuery, this::resultSetToMediaFile);
        }
    }
    
    @Override
    public List<MediaFile> getMediaFilesByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getMediaFilesByVideoId) {
            if (!setPreparedStatement(preparedStatement_getMediaFilesByVideoId, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getMediaFilesByVideoId::executeQuery, this::resultSetToMediaFiles);
        }
    }
    
    @Override
    public ExtraFile getExtraFileByVideoIdAndFile(String videoId, String file) {
        if (!isConnected() || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getExtraFileByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_getExtraFileByVideoIdAndFile, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getExtraFileByVideoIdAndFile::executeQuery, this::resultSetToExtraFile);
        }
    }
    
    @Override
    public List<ExtraFile> getExtraFilesByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getExtraFilesByVideoId) {
            if (!setPreparedStatement(preparedStatement_getExtraFilesByVideoId, videoId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getExtraFilesByVideoId::executeQuery, this::resultSetToExtraFiles);
        }
    }
    
    @Override
    public List<YouTubeChannel> getAllChannels() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllChannels) {
            return useResultSetAndClose(preparedStatement_getAllChannels::executeQuery, this::resultSetToChannels);
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
    public YouTubeChannel getChannelByChannelId(String channelId) {
        if (!isConnected() || channelId == null || channelId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getChannelByChannelId) {
            if (!setPreparedStatement(preparedStatement_getChannelByChannelId, channelId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getChannelByChannelId::executeQuery, this::resultSetToChannel);
        }
    }
    
    @Override
    public List<YouTubeVideo> getVideosByChannelId(String channelId) {
        if (!isConnected() || channelId == null || channelId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideosByChannelId) {
            if (!setPreparedStatement(preparedStatement_getVideosByChannelId, channelId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getVideosByChannelId::executeQuery, this::resultSetToYouTubeVideos);
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
    public boolean hasVideoOnChannel(String channelId, String videoId) {
        if (!isConnected() || channelId == null || channelId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        final YouTubeVideo youTubeVideo = getVideoByVideoId(videoId);
        if (youTubeVideo == null) {
            return false;
        }
        return Objects.equals(channelId, youTubeVideo.getChannelId());
    }
    
    @Override
    public List<YouTubeUploader> getAllUploaders() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllUploaders) {
            return useResultSetAndClose(preparedStatement_getAllUploaders::executeQuery, this::resultSetToUploaders);
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
    public YouTubeUploader getUploaderByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getUploaderByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getUploaderByUploaderId, uploaderId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getUploaderByUploaderId::executeQuery, this::resultSetToUploader);
        }
    }
    
    @Override
    public List<YouTubeVideo> getVideosByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getVideosByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getVideosByUploaderId, uploaderId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getVideosByUploaderId::executeQuery, this::resultSetToYouTubeVideos);
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
    public boolean hasVideoUploaded(String uploaderId, String videoId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty() || videoId == null || videoId.isEmpty()) {
            return false;
        }
        final YouTubeVideo youTubeVideo = getVideoByVideoId(videoId);
        if (youTubeVideo == null) {
            return false;
        }
        return Objects.equals(uploaderId, youTubeVideo.getUploaderId());
    }
    
    @Override
    public List<YouTubePlaylist> getPlaylistsByUploaderId(String uploaderId) {
        if (!isConnected() || uploaderId == null || uploaderId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getPlaylistsByUploaderId) {
            if (!setPreparedStatement(preparedStatement_getPlaylistsByUploaderId, uploaderId)) {
                return null; //TODO Hmm Should this be an empty list?
            }
            return useResultSetAndClose(preparedStatement_getPlaylistsByUploaderId::executeQuery, this::resultSetToYouTubePlaylists);
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
    public QueuedYouTubeVideo getQueuedVideoById(int id) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideoById) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideoById, id)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideoById::executeQuery, this::resultSetToQueuedYouTubeVideo);
        }
    }
    
    @Override
    public List<QueuedYouTubeVideo> getAllQueuedVideos() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllQueuedVideos) {
            return useResultSetAndClose(preparedStatement_getAllQueuedVideos::executeQuery, this::resultSetToQueuedYouTubeVideos);
        }
    }
    
    @Override
    public List<QueuedYouTubeVideo> getQueuedVideosByVideoId(String videoId) {
        if (!isConnected() || videoId == null || videoId.isEmpty()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideosByVideoId) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideosByVideoId, videoId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideosByVideoId::executeQuery, this::resultSetToQueuedYouTubeVideos);
        }
    }
    
    @Override
    public List<QueuedYouTubeVideo> getQueuedVideosByRequesterId(int requesterId) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getQueuedVideosByRequesterId) {
            if (!setPreparedStatement(preparedStatement_getQueuedVideosByRequesterId, requesterId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getQueuedVideosByRequesterId::executeQuery, this::resultSetToQueuedYouTubeVideos);
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
    public QueuedYouTubeVideo getNextQueuedVideo() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getNextQueuedVideo) {
            return useResultSetAndClose(preparedStatement_getNextQueuedVideo::executeQuery, this::resultSetToQueuedYouTubeVideo);
        }
    }
    
    @Override
    public List<QueuedYouTubeVideo> getNextQueuedVideos() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getNextQueuedVideos) {
            return useResultSetAndClose(preparedStatement_getNextQueuedVideos::executeQuery, this::resultSetToQueuedYouTubeVideos);
        }
    }
    
    @Override
    public List<YouTubeRequester> getAllRequesters() {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getAllRequesters) {
            return useResultSetAndClose(preparedStatement_getAllRequesters::executeQuery, this::resultSetToRequesters);
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
    public YouTubeRequester getRequesterByRequesterId(int requesterId) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getRequesterByRequesterId) {
            if (!setPreparedStatement(preparedStatement_getRequesterByRequesterId, requesterId)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getRequesterByRequesterId::executeQuery, this::resultSetToRequester);
        }
    }
    
    @Override
    public YouTubeRequester getRequesterByTag(String tag) {
        if (!isConnected()) {
            return null;
        }
        synchronized (preparedStatement_getRequesterByTag) {
            if (!setPreparedStatement(preparedStatement_getRequesterByTag, tag)) {
                return null;
            }
            return useResultSetAndClose(preparedStatement_getRequesterByTag::executeQuery, this::resultSetToRequester);
        }
    }
    
    @Override
    public YouTubeChannel createChannel(String channelId, String name) {
        final YouTubeChannel channel = new YouTubeChannel(channelId, name);
        channel.setDatabase(this);
        if (!addChannel(channel)) {
            return null;
        }
        return channel;
    }
    
    @Override
    public YouTubeUploader createUploader(String uploaderId, String name) {
        final YouTubeUploader uploader = new YouTubeUploader(uploaderId, name);
        uploader.setDatabase(this);
        if (!addUploader(uploader)) {
            return null;
        }
        return uploader;
    }
    
    @Override
    public YouTubeRequester createRequester(String tag, String name) {
        final YouTubeRequester requester = new YouTubeRequester(tag, name);
        requester.setDatabase(this);
        if (!addRequester(requester)) {
            return null;
        }
        return requester;
    }
    
    @Override
    public YouTubeVideo createVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long duration, long uploadDate) {
        final YouTubeVideo video = new YouTubeVideo(videoId, channelId, uploaderId, title, altTitle, duration, uploadDate);
        video.setDatabase(this);
        if (!addVideo(video)) {
            return null;
        }
        return video;
    }
    
    @Override
    public QueuedYouTubeVideo createQueuedVideo(String videoId, int priority, Instant requested, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state) {
        final QueuedYouTubeVideo queuedYouTubeVideo = new QueuedYouTubeVideo(videoId, priority, requested, requesterId, fileType, arguments, configFile, outputDirectory, state);
        queuedYouTubeVideo.setDatabase(this);
        if (!addQueuedVideo(queuedYouTubeVideo)) {
            return null;
        }
        return queuedYouTubeVideo;
    }
    
    @Override
    public boolean addAuthorizationToken(AuthorizationToken authorizationToken) {
        if (!isConnected() || authorizationToken == null) {
            return false;
        }
        synchronized (preparedStatement_addAuthorizationToken) {
            if (!setPreparedStatement(preparedStatement_addAuthorizationToken, authorizationToken.getToken(), authorizationToken.getLevel().name(), authorizationToken.getCreated().toEpochMilli(), authorizationToken.getExpiration() == null ? 0 : authorizationToken.getExpiration().toEpochMilli())) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_addAuthorizationToken.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean addPlaylist(YouTubePlaylist playlist) {
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
    public boolean addQueuedVideo(QueuedYouTubeVideo queuedVideo) {
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
    public boolean addVideo(YouTubeVideo video) {
        if (!isConnected() || video == null) {
            return false;
        }
        synchronized (preparedStatement_addVideo) {
            if (!setPreparedStatement(preparedStatement_addVideo, video.getVideoId(), video.getChannelId(), video.getUploaderId(), video.getTitle(), video.getAltTitle(), video.getDurationMillis(), video.getUploadDateAsLong())) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_addVideo.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean addChannel(YouTubeChannel channel) {
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
    public boolean addUploader(YouTubeUploader uploader) {
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
    public boolean addRequester(YouTubeRequester requester) {
        if (!isConnected() || requester == null) {
            return false;
        }
        synchronized (preparedStatement_addRequester) {
            if (!setPreparedStatement(preparedStatement_addRequester, requester.getTag(), requester.getName())) {
                return false;
            }
            final boolean success = Standard.silentError(() -> preparedStatement_addRequester.executeUpdate()) > 0;
            final int id = getLastInsertId();
            requester.setRequesterId(id);
            return success;
        }
    }
    
    public YouTubeVideo updateVideoInstanceInfo(String videoId) {
        return updateVideoInstanceInfo(videoId, false);
    }
    
    public YouTubeVideo updateVideoInstanceInfo(String videoId, boolean overwrite) {
        return updateVideoInstanceInfo(YouTubeDL.downloadVideoInstanceInfo(YouTubeSource.ofId(videoId)), overwrite);
    }
    
    @Override
    public YouTubeVideo updateVideoInstanceInfo(VideoInstanceInfo videoInstanceInfo, boolean overwrite) {
        if (videoInstanceInfo == null) {
            return null;
        }
        final String videoId = videoInstanceInfo.getId();
        //
        final String channelId = videoInstanceInfo.getChannel_id();
        final String channel = VideoInstanceInfo.resolveStringFromYouTubeDLToString(videoInstanceInfo.getChannel());
        //
        final String uploaderId = videoInstanceInfo.getUploader_id();
        final String uploader = VideoInstanceInfo.resolveStringFromYouTubeDLToString(videoInstanceInfo.getUploader());
        //
        final String title = VideoInstanceInfo.resolveStringFromYouTubeDLToString(videoInstanceInfo.getTitle());
        final String altTitle = VideoInstanceInfo.resolveStringFromYouTubeDLToString(videoInstanceInfo.getAlt_title());
        //
        final long duration = videoInstanceInfo.getDurationAsMillis();
        final long uploadDateLong = videoInstanceInfo.getUploadDateAsLong();
        final LocalDate uploadDate = videoInstanceInfo.getUploadDate();
        final YouTubeVideo youTubeVideo = getVideoByVideoId(videoId);
        if (youTubeVideo != null) {
            if (overwrite) {
                youTubeVideo.setChannelId(channelId);
                youTubeVideo.setUploaderId(uploaderId);
                youTubeVideo.setTitle(title);
                youTubeVideo.setAltTitle(altTitle);
                youTubeVideo.setDurationMillis(duration);
                youTubeVideo.setUploadDate(uploadDate);
                youTubeVideo.save();
            }
            return youTubeVideo;
        }
        YouTubeChannel youTubeChannel = getChannelByChannelId(channelId);
        if (youTubeChannel == null && (youTubeChannel = createChannel(channelId, channel)) == null) {
            Logger.logWarning(String.format("Could not create %s \"%s\" for \"%s\"", YouTubeChannel.class.getSimpleName(), channelId, videoInstanceInfo));
            return null;
        }
        if (overwrite) {
            if (youTubeChannel.getName() == null) {
                youTubeChannel.setName(channel);
            }
            youTubeChannel.save();
        }
        YouTubeUploader youTubeUploader = getUploaderByUploaderId(uploaderId);
        if (youTubeUploader == null && (youTubeUploader = createUploader(uploaderId, uploader)) == null) {
            Logger.logWarning(String.format("Could not create %s \"%s\" for \"%s\"", YouTubeUploader.class.getSimpleName(), uploaderId, videoInstanceInfo));
            return null;
        }
        if (overwrite) {
            if (youTubeUploader.getName() == null) {
                youTubeUploader.setName(uploader);
            }
            youTubeUploader.save();
        }
        return createVideo(videoId, channelId, uploaderId, title, altTitle, duration, uploadDateLong);
    }
    
    @Override
    public boolean setAuthorizationTokenByToken(AuthorizationToken authorizationToken, String oldToken) {
        if (!isConnected() || authorizationToken == null || oldToken == null || oldToken.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setAuthorizationTokenByToken) {
            if (!setPreparedStatement(preparedStatement_setAuthorizationTokenByToken, authorizationToken.getToken(), authorizationToken.getLevel().name(), authorizationToken.getCreated().toEpochMilli(), authorizationToken.getExpiration() == null ? 0 : authorizationToken.getExpiration().toEpochMilli(), authorizationToken.getUsed(), oldToken)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setAuthorizationTokenByToken.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setAuthorizationTokenTimesUsedByToken(String token, int timesUsed) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setAuthorizationTokenTimesUsedByToken) {
            if (!setPreparedStatement(preparedStatement_setAuthorizationTokenTimesUsedByToken, timesUsed, token)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setAuthorizationTokenTimesUsedByToken.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setPlaylistVideo(YouTubePlaylist playlist, YouTubeVideo video, int index) {
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
    public boolean setVideoByVideoId(YouTubeVideo video, String videoId) {
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
    
    /*
    @Override
    public boolean setVideosByPlaylistId(List<YouTubeVideo> videos, String playlistId) {
        if (!isConnected() || videos == null || playlistId == null || playlistId.isEmpty()) {
            return false;
        }
        throw new NotYetImplementedRuntimeException("YouTubeDatabase::setVideosByPlaylistId");
    }
    */
    
    @Override
    public boolean setPlaylistByPlaylistId(YouTubePlaylist playlist, String playlistId) {
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
    
    /*
    @Override
    public boolean setPlaylistsByVideoId(List<YouTubePlaylist> playlists, String videoId) {
        if (!isConnected() || playlists == null || videoId == null || videoId.isEmpty()) {
            return false;
        }
        throw new NotYetImplementedRuntimeException("YouTubeDatabase::setPlaylistsByVideoId");
    }
    */
    
    @Override
    public boolean setMediaFileByVideoIdAndFile(MediaFile mediaFile, String videoId, String file) {
        if (!isConnected() || mediaFile == null || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setMediaFileByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_setMediaFileByVideoIdAndFile, mediaFile.getVideoId(), mediaFile.getFile(), mediaFile.getFileType(), mediaFile.getFormat(), mediaFile.getVcodec(), mediaFile.getAcodec(), mediaFile.getWidth(), mediaFile.getHeight(), mediaFile.getFps(), mediaFile.getAsr(), videoId, file)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setMediaFileByVideoIdAndFile.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setMediaFilesByVideoId(List<MediaFile> mediaFiles, String videoId) {
        if (!isConnected() || mediaFiles == null || videoId == null || videoId.isEmpty()) {
            return false;
        }
        throw new NotYetImplementedRuntimeException("YouTubeDatabase::setMediaFilesByVideoId");
    }
    
    @Override
    public boolean setExtraFileByVideoIdAndFile(ExtraFile extraFile, String videoId, String file) {
        if (!isConnected() || extraFile == null || videoId == null || videoId.isEmpty() || file == null || file.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_setExtraFileByVideoIdAndFile) {
            if (!setPreparedStatement(preparedStatement_setExtraFileByVideoIdAndFile, extraFile.getVideoId(), extraFile.getFile(), extraFile.getFileType(), videoId, file)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setExtraFileByVideoIdAndFile.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setExtraFilesByVideoId(List<ExtraFile> extraFiles, String videoId) {
        if (!isConnected() || extraFiles == null || videoId == null || videoId.isEmpty()) {
            return false;
        }
        throw new NotYetImplementedRuntimeException("YouTubeDatabase::setExtraFilesByVideoId");
    }
    
    @Override
    public boolean setChannelByChannelId(YouTubeChannel channel, String channelId) {
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
    public boolean setUploaderByUploaderId(YouTubeUploader uploader, String uploaderId) {
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
    public boolean setQueuedVideoById(QueuedYouTubeVideo queuedYouTubeVideo, int id) {
        if (!isConnected() || queuedYouTubeVideo == null) {
            return false;
        }
        synchronized (preparedStatement_setQueuedVideoById) {
            if (!setPreparedStatement(preparedStatement_setQueuedVideoById, queuedYouTubeVideo.getId(), queuedYouTubeVideo.getVideoId(), queuedYouTubeVideo.getPriority(), queuedYouTubeVideo.getRequestedAsTimestamp(), queuedYouTubeVideo.getArguments(), queuedYouTubeVideo.getConfigFile(), queuedYouTubeVideo.getOutputDirectory(), queuedYouTubeVideo.getState().name(), id)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_setQueuedVideoById.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean setRequesterByRequesterId(YouTubeRequester requester, int requesterId) {
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
    public boolean setRequesterByRequesterId(YouTubeRequester requester, String tag) {
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
    public boolean removeAllAuthorizationTokenByTokens() {
        if (!isConnected()) {
            return false;
        }
        synchronized (preparedStatement_removeAllAuthorizationTokens) {
            return Standard.silentError(() -> preparedStatement_removeAllAuthorizationTokens.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeAuthorizationTokenByToken(String token) {
        if (!isConnected() || token == null || token.isEmpty()) {
            return false;
        }
        synchronized (preparedStatement_removeAuthorizationTokenByToken) {
            if (!setPreparedStatement(preparedStatement_removeAuthorizationTokenByToken, token)) {
                return false;
            }
            return Standard.silentError(() -> preparedStatement_removeAuthorizationTokenByToken.executeUpdate()) > 0;
        }
    }
    
    @Override
    public boolean removeVideoFromPlaylist(YouTubePlaylist playlist, YouTubeVideo video) {
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
    public boolean removeAllQueuedVideos() {
        if (!isConnected()) {
            return false;
        }
        synchronized (preparedStatement_removeAllQueuedVideos) {
            return Standard.silentError(() -> preparedStatement_removeAllQueuedVideos.executeUpdate()) > 0;
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
        return "SQLDatabase{" + "connector=" + connector + '}';
    }
    
    protected static PreparedStatement createPreparedStatement(AbstractConnector abstractConnector, String sql) {
        try {
            return abstractConnector.prepareStatement(sql);
        } catch (Exception ex) {
            return null;
        }
    }
    
    protected static boolean setPreparedStatement(PreparedStatement preparedStatement, Object... arguments) {
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
    
    private <T extends AbstractEntity<T, YouTubeDatabase>> T setDatabase(T entity) {
        return setDatabase(entity, this);
    }
    
    private static <T extends AbstractEntity<T, YouTubeDatabase>> T setDatabase(T entity, YouTubeDatabase database) {
        return entity == null || database == null ? entity : entity.setDatabase(database);
    }
    
    protected static <R> R useResultSetAndClose(ToughSupplier<ResultSet> toughSupplier, ToughFunction<ResultSet, R> toughFunction) {
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
    
    private static List<String> resultSetPlaylistIdsFromPlaylists(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID));
    }
    
    private static List<String> resultSetVideoIdsFromVideos(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_ID));
    }
    
    private static List<String> resultSetChannelIdsFromChannels(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_CHANNELS_COLUMN_ID));
    }
    
    private static List<String> resultSetUploaderIdsFromUploaders(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID));
    }
    
    private static List<Integer> resultSetRequesterIdsFromRequesters(ResultSet resultSet) {
        return resultSetToRs(resultSet, (resultSet_) -> resultSet_.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTERS_COLUMN_ID));
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
    
    protected static int resultSetToPlaylistIndex(ResultSet resultSet) {
        return resultSetToR(resultSet, (resultSet_) -> resultSet_.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX));
    }
    
    protected static <R> List<R> resultSetToRs(ResultSet resultSet, ToughFunction<ResultSet, R> toughFunction) {
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
    
    protected static <R> R resultSetToR(ResultSet resultSet, ToughFunction<ResultSet, R> toughFunction) {
        if (resultSet == null) {
            return null;
        }
        return toughFunction.applyWithoutException(resultSet);
    }
    
    protected static AuthorizationToken resultSetToAuthorizationToken(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        final AuthorizationToken authorizationToken = Standard.silentError(() -> new AuthorizationToken(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN), AuthorizationToken.AuthorizationTokenLevel.valueOf(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_LEVEL)), Instant.ofEpochMilli(resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_CREATED)), resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION) == 0 ? null : Instant.ofEpochMilli(resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION))));
        if (authorizationToken != null) {
            Standard.silentError(() -> authorizationToken.setUsed(resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_USED)));
        }
        return authorizationToken;
    }
    
    protected static List<AuthorizationToken> resultSetToAuthorizationTokens(ResultSet resultSet) {
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
    
    protected YouTubeVideo resultSetToYouTubeVideo(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new YouTubeVideo(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_CHANNEL_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOADER_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_TITLE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_ALT_TITLE), resultSet.getLong(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_DURATION), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOAD_DATE))));
    }
    
    protected List<YouTubeVideo> resultSetToYouTubeVideos(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<YouTubeVideo> youTubeVideos = new ArrayList<>();
        do {
            final YouTubeVideo youTubeVideo = resultSetToYouTubeVideo(resultSet);
            if (youTubeVideo != null) {
                youTubeVideos.add(youTubeVideo);
            }
        } while (Standard.silentError(resultSet::next));
        return youTubeVideos;
    }
    
    protected YouTubePlaylist resultSetToYouTubePlaylist(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new YouTubePlaylist(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLISTS_COLUMN_TITLE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLISTS_COLUMN_PLAYLIST), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_PLAYLISTS_COLUMN_UPLOADER_ID))));
    }
    
    protected List<YouTubePlaylist> resultSetToYouTubePlaylists(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<YouTubePlaylist> youTubePlaylists = new ArrayList<>();
        do {
            final YouTubePlaylist youTubePlaylist = resultSetToYouTubePlaylist(resultSet);
            if (youTubePlaylist != null) {
                youTubePlaylists.add(youTubePlaylist);
            }
        } while (Standard.silentError(resultSet::next));
        return youTubePlaylists;
    }
    
    protected QueuedYouTubeVideo resultSetToQueuedYouTubeVideo(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new QueuedYouTubeVideo(resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY), resultSet.getTimestamp(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED).toInstant(), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_FILE_TYPE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ARGUMENTS), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_CONFIG_FILE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_OUTPUT_DIRECTORY), QueuedVideoState.ofState(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE)))));
    }
    
    protected List<QueuedYouTubeVideo> resultSetToQueuedYouTubeVideos(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<QueuedYouTubeVideo> queuedYouTubeVideos = new ArrayList<>();
        do {
            final QueuedYouTubeVideo queuedYouTubeVideo = resultSetToQueuedYouTubeVideo(resultSet);
            if (queuedYouTubeVideo != null) {
                queuedYouTubeVideos.add(queuedYouTubeVideo);
            }
        } while (Standard.silentError(resultSet::next));
        return queuedYouTubeVideos;
    }
    
    protected MediaFile resultSetToMediaFile(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new MediaFile(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE_TYPE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FORMAT), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VCODEC), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ACODEC), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_WIDTH), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_HEIGHT), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FPS), resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ASR))));
    }
    
    protected List<MediaFile> resultSetToMediaFiles(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<MediaFile> mediaFiles = new ArrayList<>();
        do {
            final MediaFile mediaFile = resultSetToMediaFile(resultSet);
            if (mediaFile != null) {
                mediaFiles.add(mediaFile);
            }
        } while (Standard.silentError(resultSet::next));
        return mediaFiles;
    }
    
    protected ExtraFile resultSetToExtraFile(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new ExtraFile(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE_TYPE))));
    }
    
    protected List<ExtraFile> resultSetToExtraFiles(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<ExtraFile> extraFiles = new ArrayList<>();
        do {
            final ExtraFile extraFile = resultSetToExtraFile(resultSet);
            if (extraFile != null) {
                extraFiles.add(extraFile);
            }
        } while (Standard.silentError(resultSet::next));
        return extraFiles;
    }
    
    protected YouTubeChannel resultSetToChannel(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new YouTubeChannel(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_CHANNELS_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_CHANNELS_COLUMN_NAME))));
    }
    
    protected List<YouTubeChannel> resultSetToChannels(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<YouTubeChannel> youTubeChannels = new ArrayList<>();
        do {
            final YouTubeChannel youTubeChannel = resultSetToChannel(resultSet);
            if (youTubeChannel != null) {
                youTubeChannels.add(youTubeChannel);
            }
        } while (Standard.silentError(resultSet::next));
        return youTubeChannels;
    }
    
    protected YouTubeUploader resultSetToUploader(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new YouTubeUploader(resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_UPLOADERS_COLUMN_NAME))));
    }
    
    protected List<YouTubeUploader> resultSetToUploaders(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<YouTubeUploader> youTubeUploaders = new ArrayList<>();
        do {
            final YouTubeUploader youTubeUploader = resultSetToUploader(resultSet);
            if (youTubeUploader != null) {
                youTubeUploaders.add(youTubeUploader);
            }
        } while (Standard.silentError(resultSet::next));
        return youTubeUploaders;
    }
    
    protected YouTubeRequester resultSetToRequester(ResultSet resultSet) {
        if (resultSet == null) {
            return null;
        }
        return setDatabase(Standard.silentError(() -> new YouTubeRequester(resultSet.getInt(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTERS_COLUMN_ID), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG), resultSet.getString(YouTubeDatabaseConstants.IDENTIFIER_TABLE_REQUESTERS_COLUMN_NAME))));
    }
    
    protected List<YouTubeRequester> resultSetToRequesters(ResultSet resultSet) {
        if (resultSet == null) {
            return null; //TODO Hmm Should this be an empty list?
        }
        final List<YouTubeRequester> youTubeRequesters = new ArrayList<>();
        do {
            final YouTubeRequester youTubeRequester = resultSetToRequester(resultSet);
            if (youTubeRequester != null) {
                youTubeRequesters.add(youTubeRequester);
            }
        } while (Standard.silentError(resultSet::next));
        return youTubeRequesters;
    }
    
    protected static void createTables(YouTubeDatabase youTubeDatabase) {
        //TODO Use the "database_create_tables.sql" to create (if not existing) tables...
    }
    
}
