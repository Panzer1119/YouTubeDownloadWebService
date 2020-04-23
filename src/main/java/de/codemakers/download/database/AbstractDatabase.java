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

import de.codemakers.download.database.entities.impl.AuthorizationToken;
import de.codemakers.download.databaseOLD.entities.QueuedVideoState;

import java.time.Instant;
import java.util.List;

public abstract class AbstractDatabase<T extends AbstractDatabase, C extends AbstractConnector, V, P, CH, U, MF, EF, R, Q> {
    
    protected C connector;
    
    public AbstractDatabase(C connector) {
        this.connector = connector;
    }
    
    public T setConnector(C connector) {
        this.connector = connector;
        return (T) this;
    }
    
    public abstract boolean isConnected();
    
    public boolean start() {
        return start(null, null);
    }
    
    public abstract boolean start(String username, byte[] password);
    
    public abstract boolean stop();
    
    // SQL Gets
    
    public abstract int getLastInsertId();
    
    public abstract List<AuthorizationToken> getAllAuthorizationTokens();
    
    public abstract AuthorizationToken getAuthorizationTokenByToken(String token);
    
    public abstract boolean hasAuthorizationToken(String token);
    
    public abstract boolean hasChannel(String channelId);
    
    public abstract boolean hasUploader(String uploaderId);
    
    public abstract boolean hasRequester(int requesterId);
    
    public abstract boolean hasQueuedVideo(int queuedVideoId);
    
    public abstract boolean hasVideo(String videoId);
    
    public abstract boolean hasPlaylist(String playlistId);
    
    public abstract V getVideoByVideoId(String videoId);
    
    public abstract List<V> getAllVideos();
    
    public abstract List<V> getVideosByPlaylistId(String playlistId);
    
    public abstract List<V> getVideosByChannelId(String channelId);
    
    public abstract List<V> getVideosByUploaderId(String uploaderId);
    
    public abstract List<String> getAllVideoIds();
    
    public abstract List<String> getVideoIdsByPlaylistId(String playlistId);
    
    public abstract List<String> getVideoIdsByChannelId(String channelId);
    
    public abstract List<String> getVideoIdsByUploaderId(String uploaderId);
    
    public abstract int getVideoCountByPlaylistId(String playlistId);
    
    public abstract int getVideoCountByChannelId(String channelId);
    
    public abstract int getVideoCountByUploaderId(String uploaderId);
    
    public abstract boolean playlistContainsVideo(String playlistId, String videoId);
    
    public abstract boolean channelHasVideo(String playlistId, String channelId);
    
    public abstract boolean uploaderUploadedVideo(String playlistId, String uploaderId);
    
    public abstract P getPlaylistByPlaylistId(String playlistId);
    
    public abstract List<P> getAllPlaylists();
    
    public abstract List<P> getPlaylistsByVideoId(String videoId);
    
    public abstract List<P> getPlaylistsByUploaderId(String uploaderId);
    
    public abstract List<String> getAllPlaylistIds();
    
    public abstract List<String> getPlaylistIdsByVideoId(String videoId);
    
    public abstract List<String> getPlaylistIdsByUploaderId(String uploaderId);
    
    public abstract int getPlaylistCountByUploaderId(String uploaderId);
    
    public abstract int getIndexInPlaylist(String playlistId, String videoId);
    
    public abstract boolean uploaderCreatedPlaylist(String uploaderId, String playlistId);
    
    public abstract List<CH> getAllChannels();
    
    public abstract List<String> getAllChannelIds();
    
    public abstract CH getChannelByChannelId(String channelId);
    
    public abstract List<U> getAllUploaders();
    
    public abstract List<String> getAllUploaderIds();
    
    public abstract U getUploaderByUploaderId(String uploaderId);
    
    public abstract MF getMediaFileByVideoIdAndFile(String videoId, String file);
    
    public abstract List<MF> getMediaFilesByVideoId(String videoId);
    
    public abstract EF getExtraFileByVideoIdAndFile(String videoId, String file);
    
    public abstract List<EF> getExtraFilesByVideoId(String videoId);
    
    public abstract List<R> getAllRequesters();
    
    public abstract List<Integer> getAllRequesterIds();
    
    public abstract R getRequesterByRequesterId(int requesterId);
    
    public abstract R getRequesterByRequesterId(String tag);
    
    public abstract Q getNextQueuedVideo();
    
    public abstract List<Q> getNextQueuedVideos();
    
    // SQL Adds
    
    public abstract CH createChannel(String channelId, String name);
    
    public abstract U createUploader(String uploaderId, String name);
    
    public abstract R createRequester(String tag, String name);
    
    public abstract V createVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long duration, Instant uploadTimestamp);
    
    public Q createQueuedVideo(String videoId, int priority, int requesterId, String fileType) {
        return createQueuedVideo(videoId, priority, requesterId, fileType, null, null, null);
    }
    
    public Q createQueuedVideo(String videoId, int priority, int requesterId, String fileType, String arguments, String configFile, String outputDirectory) {
        return createQueuedVideo(videoId, priority, Instant.now(), requesterId, fileType, arguments, configFile, outputDirectory, QueuedVideoState.QUEUED);
    }
    
    public abstract Q createQueuedVideo(String videoId, int priority, Instant requestedTimestamp, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state);
    
    public boolean addVideoToPlaylist(P playlist, V video) {
        return addVideoToPlaylist(playlist, video, -1);
    }
    
    public abstract boolean addVideoToPlaylist(P playlist, V video, int index);
    
    public boolean addVideoToPlaylist(String playlistId, String videoId) {
        return addVideoToPlaylist(playlistId, videoId, -1);
    }
    
    public abstract boolean addVideoToPlaylist(String playlistId, String videoId, int index);
    
    public abstract boolean addVideoToPlaylists(V video, List<P> playlists);
    
    public abstract boolean addVideoToPlaylists(String videoId, List<String> playlistIds);
    
    public abstract boolean addVideosToPlaylist(P playlist, List<V> videos);
    
    public abstract boolean addVideosToPlaylist(String playlistId, List<String> videoIds);
    
    public abstract boolean addPlaylist(P playlist);
    
    public abstract boolean addAuthorizationToken(AuthorizationToken authorizationToken);
    
    public abstract boolean addQueuedVideo(Q queuedVideo);
    
    public abstract boolean addVideo(V video);
    
    public abstract boolean addChannel(CH channel);
    
    public abstract boolean addUploader(U uploader);
    
    public abstract boolean addRequester(R requester);
    
    // SQL Sets
    
    public abstract boolean setAuthorizationTokenByToken(AuthorizationToken authorizationToken, String token);
    
    public abstract boolean setAuthorizationTokenTimesUsedByToken(String token, int timesUsed);
    
    public abstract boolean setPlaylistVideoIndex(P playlist, V video, int index);
    
    public abstract boolean setVideoByVideoId(V video, String videoId);
    
    public abstract boolean setPlaylistByPlaylistId(P playlist, String playlistId);
    
    public abstract boolean setMediaFileByVideoIdAndFile(MF mediaFile, String videoId, String file);
    
    public abstract boolean setMediaFilesByVideoId(List<MF> mediaFiles, String videoId);
    
    public abstract boolean setExtraFileByVideoIdAndFile(EF extraFile, String videoId, String file);
    
    public abstract boolean setExtraFilesByVideoId(List<EF> extraFiles, String videoId);
    
    public abstract boolean setChannelByChannelId(CH channel, String channelId);
    
    public abstract boolean setUploaderByUploaderId(U uploader, String uploaderId);
    
    public abstract boolean setQueuedVideoById(Q queuedVideo, int id);
    
    public abstract boolean setRequesterByRequesterId(R requester, int requesterId);
    
    public abstract boolean setRequesterByRequesterTag(R requester, String tag);
    
    // SQL Removes
    
    public abstract boolean removeAllAuthorizationTokens();
    
    public abstract boolean removeAuthorizationTokenByToken(String token);
    
    public abstract boolean removeVideoFromPlaylist(P playlist, V video);
    
    public abstract boolean removeVideoIdFromPlaylistId(String playlistId, String videoId);
    
    public abstract boolean removeAllQueuedVideos();
    
    public abstract boolean removeQueuedVideoById(int id);
    
    public abstract boolean removeQueuedVideosByVideoId(String videoId);
    
    public abstract boolean removeQueuedVideosByRequesterId(int requesterId);
    
    //
    
    public boolean isTokenValid(AuthorizationToken token) {
        return isTokenValid(token, Instant.now());
    }
    
    public boolean isTokenValid(AuthorizationToken token, Instant timestamp) {
        return token != null && isTokenValid(token.getToken(), timestamp);
    }
    
    public boolean isTokenValid(String token) {
        return isTokenValid(token, Instant.now());
    }
    
    public boolean isTokenValid(String token, Instant timestamp) {
        if (token == null || token.isEmpty() || !isConnected()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getAuthorizationTokenByToken(token);
        return authorizationToken != null && authorizationToken.isValid(timestamp);
    }
    
    public boolean useTokenOnce(AuthorizationToken token) {
        return useTokenOnce(token, Instant.now());
    }
    
    public boolean useTokenOnce(AuthorizationToken token, Instant timestamp) {
        return token != null && useTokenOnce(token.getToken(), timestamp);
    }
    
    public boolean useTokenOnce(String token) {
        return useTokenOnce(token, Instant.now());
    }
    
    public boolean useTokenOnce(String token, Instant timestamp) {
        if (token == null || token.isEmpty() || !isConnected()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getAuthorizationTokenByToken(token);
        if (authorizationToken == null || !authorizationToken.isValid(timestamp)) {
            return false;
        }
        return setAuthorizationTokenTimesUsedByToken(token, authorizationToken.getTimesUsed() + 1);
    }
    
    @Override
    public String toString() {
        return "AbstractDatabase{" + "connector=" + connector + '}';
    }
    
}
