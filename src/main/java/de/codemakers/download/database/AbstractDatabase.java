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

import com.google.gson.JsonObject;
import de.codemakers.download.database.entities.*;
import de.codemakers.download.database.entities.impl.ExtraFile;
import de.codemakers.download.database.entities.impl.MediaFile;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDatabase<T extends AbstractDatabase, M extends AbstractFile, E extends AbstractFile, V extends AbstractVideo, P extends AbstractPlaylist, Q extends AbstractQueuedVideo, CH extends AbstractChannel, U extends AbstractUploader, R extends AbstractRequester, C extends AbstractConnector> {
    
    protected C connector;
    
    public AbstractDatabase(C connector) {
        this.connector = connector;
    }
    
    public C getConnector() {
        return connector;
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
    
    // Gets
    
    public abstract int getLastInsertId();
    
    public abstract List<AuthorizationToken> getAllAuthorizationTokens();
    
    public abstract AuthorizationToken getAuthorizationTokenByToken(String token);
    
    public boolean hasAuthorizationToken(AuthorizationToken authorizationToken) {
        return authorizationToken != null && hasAuthorizationToken(authorizationToken.getToken());
    }
    
    public abstract boolean hasAuthorizationToken(String token);
    
    public abstract boolean hasChannel(String channelId);
    
    public abstract boolean hasUploader(String uploaderId);
    
    public abstract boolean hasRequester(int requesterId);
    
    public abstract boolean hasQueuedVideo(int queuedVideoId);
    
    public abstract boolean hasVideo(String videoId);
    
    public abstract V getVideoByVideoId(String videoId);
    
    public abstract List<V> getAllVideos();
    
    public abstract List<V> getVideosByPlaylistId(String playlistId);
    
    public abstract List<String> getAllVideoIds();
    
    public abstract List<String> getVideoIdsByPlaylistId(String playlistId);
    
    public abstract P getPlaylistByPlaylistId(String playlistId);
    
    public abstract List<P> getAllPlaylists();
    
    public abstract List<P> getPlaylistsByVideoId(String videoId);
    
    public abstract List<String> getAllPlaylistIds();
    
    public abstract List<String> getPlaylistIdsByVideoId(String videoId);
    
    public abstract int getIndexInPlaylist(String playlistId, String videoId);
    
    public abstract boolean playlistContainsVideo(String playlistId, String videoId);
    
    public abstract M getMediaFileByVideoIdAndFile(String videoId, String file);
    
    public abstract List<M> getMediaFilesByVideoId(String videoId);
    
    public abstract E getExtraFileByVideoIdAndFile(String videoId, String file);
    
    public abstract List<E> getExtraFilesByVideoId(String videoId);
    
    public abstract List<CH> getAllChannels();
    
    public abstract List<String> getAllChannelIds();
    
    public abstract CH getChannelByChannelId(String channelId);
    
    public abstract List<V> getVideosByChannelId(String channelId);
    
    public abstract List<String> getVideoIdsByChannelId(String channelId);
    
    public abstract boolean hasVideoOnChannel(String channelId, String videoId);
    
    public abstract List<U> getAllUploaders();
    
    public abstract List<String> getAllUploaderIds();
    
    public abstract U getUploaderByUploaderId(String uploaderId);
    
    public abstract List<V> getVideosByUploaderId(String uploaderId);
    
    public abstract List<String> getVideoIdsByUploaderId(String uploaderId);
    
    public abstract boolean hasVideoUploaded(String uploaderId, String videoId);
    
    public abstract List<P> getPlaylistsByUploaderId(String uploaderId);
    
    public abstract List<String> getPlaylistIdsByUploaderId(String uploaderId);
    
    public abstract Q getQueuedVideoById(int id);
    
    public abstract List<Q> getAllQueuedVideos();
    
    public abstract List<Q> getQueuedVideosByVideoId(String videoId);
    
    public abstract List<Q> getQueuedVideosByRequesterId(int requesterId);
    
    public abstract List<String> getQueuedVideoIdsByRequesterId(int requesterId);
    
    public abstract Q getNextQueuedVideo();
    
    public abstract List<Q> getNextQueuedVideos();
    
    public abstract List<R> getAllRequesters();
    
    public abstract List<Integer> getAllRequesterIds();
    
    public abstract R getRequesterByRequesterId(int requesterId);
    
    public abstract R getRequesterByTag(String tag);
    
    // Adds
    
    public abstract CH createChannel(String channelId, String name);
    
    public abstract U createUploader(String uploaderId, String name);
    
    public abstract R createRequester(String tag, String name);
    
    public abstract V createVideo(String videoId, String channelId, String uploaderId, String title, String altTitle, long duration, long uploadDate);
    
    public Q createQueuedVideo(String videoId, int priority, int requesterId, String fileType) {
        return createQueuedVideo(videoId, priority, requesterId, fileType, null, null, null);
    }
    
    public Q createQueuedVideo(String videoId, int priority, int requesterId, String fileType, String arguments, String configFile, String outputDirectory) {
        return createQueuedVideo(videoId, priority, Instant.now(), requesterId, fileType, arguments, configFile, outputDirectory, QueuedVideoState.QUEUED);
    }
    
    public abstract Q createQueuedVideo(String videoId, int priority, Instant requested, int requesterId, String fileType, String arguments, String configFile, String outputDirectory, QueuedVideoState state);
    
    public abstract boolean addAuthorizationToken(AuthorizationToken authorizationToken);
    
    public abstract boolean addPlaylist(P playlist);
    
    public boolean addVideoToPlaylist(P playlist, V video) {
        return addVideoToPlaylist(playlist, video, -1);
    }
    
    public boolean addVideoToPlaylist(String playlistId, String videoId) {
        return addVideoToPlaylist(playlistId, videoId, -1);
    }
    
    public boolean addVideoToPlaylist(P playlist, V video, int index) {
        if (playlist == null || video == null) {
            return false;
        }
        return addVideoToPlaylist(playlist.getPlaylistId(), video.getVideoId(), index);
    }
    
    public abstract boolean addVideoToPlaylist(String playlistId, String videoId, int index);
    
    public boolean addVideoToPlaylists(V video, List<P> playlists) {
        if (video == null || playlists == null) {
            return false;
        }
        if (playlists.isEmpty()) {
            return true;
        }
        return addVideoToPlaylists(video.getVideoId(), playlists.stream().map(P::getPlaylistId).collect(Collectors.toList()));
    }
    
    public boolean addVideoToPlaylists(String videoId, List<String> playlistIds) {
        if (videoId == null || playlistIds == null) {
            return false;
        }
        if (playlistIds.isEmpty()) {
            return true;
        }
        boolean bad = false;
        for (String playlistId : playlistIds) {
            if (playlistId == null || playlistId.isEmpty()) {
                continue;
            }
            if (!addVideoToPlaylist(playlistId, videoId)) {
                bad = true;
            }
        }
        return !bad;
    }
    
    public boolean addPlaylistToVideos(P playlist, List<V> videos) {
        if (playlist == null || videos == null) {
            return false;
        }
        if (videos.isEmpty()) {
            return true;
        }
        return addVideoToPlaylists(playlist.getPlaylistId(), videos.stream().map(V::getVideoId).collect(Collectors.toList()));
    }
    
    public boolean addPlaylistToVideos(String playlistId, List<String> videoIds) {
        if (playlistId == null || videoIds == null) {
            return false;
        }
        if (videoIds.isEmpty()) {
            return true;
        }
        boolean bad = false;
        for (String videoId : videoIds) {
            if (videoId == null || videoId.isEmpty()) {
                continue;
            }
            if (!addVideoToPlaylist(playlistId, videoId)) {
                bad = true;
            }
        }
        return !bad;
    }
    
    public abstract boolean addQueuedVideo(Q queuedVideo);
    
    public abstract boolean addVideo(V video);
    
    public abstract boolean addChannel(CH channel);
    
    public abstract boolean addUploader(U uploader);
    
    public abstract boolean addRequester(R requester);
    
    // Sets
    
    public V updateVideoInfoByJsonObject(JsonObject jsonObject) {
        return updateVideoInfoByJsonObject(jsonObject, false);
    }
    
    public abstract V updateVideoInfoByJsonObject(JsonObject jsonObject, boolean overwrite);
    
    public abstract boolean setAuthorizationTokenByToken(AuthorizationToken authorizationToken, String oldToken);
    
    public abstract boolean setAuthorizationTokenTimesUsedByToken(String token, int timesUsed);
    
    public abstract boolean setPlaylistVideo(P playlist, V video, int index);
    
    public abstract boolean setVideoByVideoId(V video, String videoId);
    
    //public abstract boolean setVideosByPlaylistId(List<V> videos, String playlistId);
    
    //public abstract boolean setVideoIdsByPlaylistId(List<String> videoIds, String playlistId);
    
    public abstract boolean setPlaylistByPlaylistId(P playlist, String playlistId);
    
    //public abstract boolean setPlaylistsByVideoId(List<P> playlists, String videoId);
    
    //public abstract boolean setPlaylistIdsByVideoId(List<String> playlistIds, String videoId);
    
    //public abstract boolean playlistContainsVideo(String playlistId, String videoId);
    
    public abstract boolean setMediaFileByVideoIdAndFile(MediaFile mediaFile, String videoId, String file);
    
    public abstract boolean setMediaFilesByVideoId(List<M> mediaFiles, String videoId);
    
    public abstract boolean setExtraFileByVideoIdAndFile(ExtraFile extraFile, String videoId, String file);
    
    public abstract boolean setExtraFilesByVideoId(List<E> extraFiles, String videoId);
    
    public abstract boolean setChannelByChannelId(CH channel, String channelId);
    
    public abstract boolean setUploaderByUploaderId(U uploader, String uploaderId);
    
    public abstract boolean setQueuedVideoById(Q queuedVideo, int id);
    
    public abstract boolean setRequesterByRequesterId(R requester, int requesterId);
    
    public abstract boolean setRequesterByRequesterId(R requester, String tag);
    
    // Removes
    
    public abstract boolean removeAllAuthorizationTokenByTokens();
    
    public abstract boolean removeAuthorizationTokenByToken(String token);
    
    public abstract boolean removeVideoFromPlaylist(P playlist, V video);
    
    public abstract boolean removeQueuedVideoById(int id);
    
    public abstract boolean removeAllQueuedVideos();
    
    public abstract boolean removeQueuedVideosByVideoId(String videoId);
    
    public abstract boolean removeQueuedVideosByRequesterId(int requesterId);
    
    //
    
    public boolean isValidAuthorizationToken(AuthorizationToken authorizationToken) {
        return authorizationToken != null && isValidAuthorizationToken(authorizationToken.getToken());
    }
    
    public boolean isValidAuthorizationToken(String token) {
        if (token == null || token.isEmpty() || !isConnected()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getAuthorizationTokenByToken(token);
        if (authorizationToken == null || !authorizationToken.isValidNow()) {
            return false;
        }
        return authorizationToken.getLevel().hasUnlimitedUses() || authorizationToken.getUsed() < authorizationToken.getLevel().getUses();
    }
    
    public boolean useAuthorizationToken(AuthorizationToken authorizationToken) {
        return authorizationToken != null && useAuthorizationToken(authorizationToken.getToken());
    }
    
    public boolean useAuthorizationToken(String token) {
        if (token == null || token.isEmpty() || !isConnected()) {
            return false;
        }
        final AuthorizationToken authorizationToken = getAuthorizationTokenByToken(token);
        if (authorizationToken == null || !authorizationToken.isValidNow()) {
            return false;
        }
        return setAuthorizationTokenTimesUsedByToken(token, authorizationToken.getUsed() + 1);
    }
    
    @Override
    public String toString() {
        return "AbstractDatabase{" + "connector=" + connector + '}';
    }
    
}
