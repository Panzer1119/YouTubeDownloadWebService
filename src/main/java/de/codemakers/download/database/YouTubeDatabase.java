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

import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.download.database.entities.impl.*;

import java.sql.PreparedStatement;
import java.time.Instant;
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
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean stop() {
        throw new NotYetImplementedRuntimeException();
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
    
}
