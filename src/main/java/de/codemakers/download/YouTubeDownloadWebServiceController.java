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

package de.codemakers.download;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.codemakers.base.Standard;
import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.logger.Logger;
import de.codemakers.download.database.entities.impl.*;
import de.codemakers.download.entities.AbstractToken;
import de.codemakers.io.file.AdvancedFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class YouTubeDownloadWebServiceController {
    
    @Deprecated
    @RequestMapping(value = "/requesters/byTag/{tag}", method = RequestMethod.GET)
    public String getRequesterByTag(ServerHttpResponse serverHttpResponse, @PathVariable(value = "tag") String tag, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return String.format("Unauthorized authToken"); //TODO How to return the right HttpStatus?
        }
        //useToken(authToken);
        //TODO Return jsonObject form of the Requester object
        return String.format("{%n\"tag\":\"%s\",%n\"timestamp\":\"%s\"%n%n}", tag, ZonedDateTime.now().toString());
    }
    
    @Deprecated
    @RequestMapping(value = "/requesters/{requester_id}", method = RequestMethod.GET)
    public String getRequester(@PathVariable(value = "requester_id") int requesterId, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            return String.format("Unauthorized authToken"); //TODO How to return the right HttpStatus?
        }
        //useToken(authToken);
        //TODO Return jsonObject form of the Requester object
        return String.format("{%n\"requesterId\":%d,%n\"timestamp\":\"%s\"%n%n}", requesterId, ZonedDateTime.now().toString());
    }
    
    @Deprecated
    @RequestMapping(value = "/request/{video_id}", method = RequestMethod.POST)
    public String request(@PathVariable(value = "video_id") String videoId, @RequestParam(value = "priority", defaultValue = "-1") int priority, @RequestParam(value = "requesterId", defaultValue = "-1") int requesterId, @RequestParam(value = "fileType", defaultValue = "B") String fileType, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            return String.format("Unauthorized authToken"); //TODO How to return the right HttpStatus?
        }
        //TODO Hmmm restrict priority to level of permission?
        final List<DatabaseQueuedYouTubeVideo> databaseQueuedYouTubeVideos = YouTubeDownloadWebService.useDatabaseOrNull((database) -> database.getQueuedVideosByVideoId(videoId));
        if (databaseQueuedYouTubeVideos != null && !databaseQueuedYouTubeVideos.isEmpty()) {
            //TODO Check and only error, if the same fileType is already queued?
            return String.format("Already queued"); //TODO How to return the right HttpStatus? //TODO IMPORTANT Should we even stop this?
        }
        //TODO IMPORTANT What is with currently live Videos?
        Standard.async(() -> {
            //TODO IMPORTANT Check already downloaded MediaFiles!!! And then do not request a same download again
            //throw new NotYetImplementedRuntimeException("DatabaseYouTubeVideo::updateVideoInstanceInfo does not exist any more");
            //final DatabaseYouTubeVideo youTubeVideo = YouTubeDownloadWebService.useDatabaseOrNull((database) -> database.updateVideoInstanceInfo(videoId));
            //Logger.logDebug("youTubeVideo=" + youTubeVideo);
            final int requesterId_ = YouTubeDownloadWebService.useDatabaseOrFalse((database) -> database.hasRequester(requesterId)) ? requesterId : -1; //TODO How to create a missing/new Requester without the tag (or even the name)?
            final DatabaseQueuedYouTubeVideo queuedYouTubeVideo = YouTubeDownloadWebService.useDatabaseOrNull((database) -> database.createQueuedVideo(videoId, priority, requesterId_, fileType));
            Logger.logDebug("queuedYouTubeVideo=" + queuedYouTubeVideo);
        });
        return String.format("Video queued for Download...?");
    }
    
    @Deprecated
    @RequestMapping(value = "/download/{video_id}", method = RequestMethod.GET)
    public Mono<Void> download(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = "fileType", defaultValue = "B") String fileType, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            //TODO Does this work?
            return null;
        }
        //FIXME Check if MediaFile for Video and File Type exist...
        final AdvancedFile advancedFile = new AdvancedFile("data/test.txt"); //TODO TEST only
        final String filename = "test.txt"; //TODO TEST only
        if (advancedFile == null || !advancedFile.exists()) {
            serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
            //TODO Does this work?
            return null;
        }
        final ZeroCopyHttpOutputMessage zeroCopyHttpOutputMessage = (ZeroCopyHttpOutputMessage) serverHttpResponse;
        serverHttpResponse.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, String.format("filename=\"%s\"", filename)); //FIXME Are these "" allowed there?
        serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        final File file = advancedFile.toFile();
        if (file == null) {
            serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            //TODO Does this work?
            return null;
        }
        //useToken(authToken);
        return zeroCopyHttpOutputMessage.writeWith(file, 0, file.length());
    }
    
    @Deprecated
    @RequestMapping(value = "/download/videoIds/byPlaylistId/{playlist_id}", method = RequestMethod.GET)
    public List<String> downloadVideoIdsByPlaylistId(@PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            return null; //TODO How to return the right HttpStatus?
        }
        //useToken(authToken);
        //return YouTubeDL.downloadVideoIdsFromURL(String.format("https://www.youtube.com/playlist?list=%s", playlistId));
        throw new NotYetImplementedRuntimeException();
    }
    
    @Deprecated
    @RequestMapping(value = "/generate/authorizationToken", method = RequestMethod.GET)
    public String generateAuthorizationToken(@RequestParam(value = "unlimited", defaultValue = "0") boolean unlimited, @RequestParam(value = "granting", defaultValue = "0") boolean granting, @RequestParam(value = "duration", defaultValue = "100000") long durationMillis, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            return String.format("Unauthorized authToken"); //TODO How to return the right HttpStatus?
        }
        final AuthorizationToken authorizationTokenMaster = getAuthorizationToken(authToken);
        if (authorizationTokenMaster == null) {
            return String.format("Unauthorized"); //TODO How to return the right HttpStatus?
        }
        if (!authorizationTokenMaster.getLevel().isGranting() || (unlimited && !authorizationTokenMaster.getLevel().isThisHigherOrEqual(AuthorizationToken.TokenLevel.SUPER_GRANTER)) || (granting && !authorizationTokenMaster.getLevel().isThisHigherOrEqual(AuthorizationToken.TokenLevel.ADMIN))) {
            return String.format("Forbidden");//TODO How to return the right HttpStatus?
        }
        final Instant timestamp = Instant.now();
        final Duration duration = Duration.ofMillis(durationMillis);
        final AuthorizationToken authorizationTokenSlave;
        if (granting) {
            authorizationTokenSlave = AuthorizationToken.generateGranterToken(timestamp, duration);
        } else {
            if (unlimited) {
                authorizationTokenSlave = AuthorizationToken.generateUnlimitedUseToken(timestamp, duration);
            } else {
                authorizationTokenSlave = AuthorizationToken.generateSingleUseToken(timestamp, duration);
            }
        }
        if (!YouTubeDownloadWebService.useDatabaseOrFalse((database) -> database.addAuthorizationToken(authorizationTokenSlave))) {
            return String.format("Internal Error"); //TODO How to return the right HttpStatus?
        }
        //useToken(authToken);
        return authorizationTokenSlave.toJson();
    }
    
    // NEW ENDPOINTS
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping(value = "/videos/byVideoId/{video_id}", method = RequestMethod.GET)
    public String getVideoByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeVideo video = database.getVideoByVideoId(videoId);
                if (video == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Video Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                return video.toJsonObject().toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byPlaylistId/{playlist_id}", method = RequestMethod.GET)
    public String getVideosByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubePlaylist playlist = database.getPlaylistByPlaylistId(playlistId);
                if (playlist == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Playlist Not Found";
                }
                final List<DatabaseYouTubeVideo> videos = playlist.getVideos();
                if (videos.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videos.stream().map(DatabaseYouTubeVideo::toJsonObject).forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byPlaylistId/{playlist_id}/getIds", method = RequestMethod.GET)
    public String getVideoIdsByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubePlaylist playlist = database.getPlaylistByPlaylistId(playlistId);
                if (playlist == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Playlist Not Found";
                }
                final List<String> videoIds = playlist.getVideoIds();
                if (videoIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videoIds.forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byChannelId/{channel_id}", method = RequestMethod.GET)
    public String getVideosByChannelId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeChannel channel = database.getChannelByChannelId(channelId);
                if (channel == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Channel Not Found";
                }
                final List<DatabaseYouTubeVideo> videos = channel.getVideos();
                if (videos.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videos.stream().map(DatabaseYouTubeVideo::toJsonObject).forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byChannelId/{channel_id}/getIds", method = RequestMethod.GET)
    public String getVideoIdsByChannelId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeChannel channel = database.getChannelByChannelId(channelId);
                if (channel == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Channel Not Found";
                }
                final List<String> videoIds = channel.getVideoIds();
                if (videoIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videoIds.forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byUploaderId/{uploader_id}", method = RequestMethod.GET)
    public String getVideosByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploader == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Uploader Not Found";
                }
                final List<DatabaseYouTubeVideo> videos = uploader.getUploadedVideos();
                if (videos.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videos.stream().map(DatabaseYouTubeVideo::toJsonObject).forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byUploaderId/{uploader_id}/getIds", method = RequestMethod.GET)
    public String getVideoIdsByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploader == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Uploader Not Found";
                }
                final List<String> videoIds = uploader.getUploadedVideoIds();
                if (videoIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videoIds.forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byChannelId/{channel_id}/getCount", method = RequestMethod.GET)
    public String getVideoCountByChannelId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getVideoCountByChannelId(channelId);
                if (count == -1) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Channel Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("count", count);
                return jsonObject.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byUploaderId/{uploader_id}/getCount", method = RequestMethod.GET)
    public String getVideoCountByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getVideoCountByUploaderId(uploaderId);
                if (count == -1) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Uploader Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("count", count);
                return jsonObject.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byPlaylistId/{playlist_id}", method = RequestMethod.GET)
    public String getPlaylistByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubePlaylist playlist = database.getPlaylistByPlaylistId(playlistId);
                if (playlist == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Playlist Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                return playlist.toJsonObject().toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byVideoId/{video_id}", method = RequestMethod.GET)
    public String getPlaylistsByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeVideo video = database.getVideoByVideoId(videoId);
                if (video == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Video Not Found";
                }
                final List<DatabaseYouTubePlaylist> playlists = video.getPlaylists();
                if (playlists.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlists.stream().map(DatabaseYouTubePlaylist::toJsonObject).forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byVideoId/{video_id}/getIds", method = RequestMethod.GET)
    public String getPlaylistIdsByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeVideo video = database.getVideoByVideoId(videoId);
                if (video == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Video Not Found";
                }
                final List<String> playlistIds = video.getPlaylistIds();
                if (playlistIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlistIds.forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byUploaderId/{uploader_id}", method = RequestMethod.GET)
    public String getPlaylistsByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploaderId == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Uploader Not Found";
                }
                final List<DatabaseYouTubePlaylist> playlists = uploader.getCreatedPlaylists();
                if (playlists.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlists.stream().map(DatabaseYouTubePlaylist::toJsonObject).forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byUploaderId/{uploader_id}/getIds", method = RequestMethod.GET)
    public String getPlaylistIdsByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploaderId == null) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Uploader Not Found";
                }
                final List<String> playlistIds = uploader.getCreatedPlaylistIds();
                if (playlistIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlistIds.forEach(jsonArray::add);
                return jsonArray.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byPlaylistId/{playlist_id}/getIndex/{video_id}", method = RequestMethod.GET)
    public String getVideoCountByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int index = database.getIndexInPlaylist(playlistId, videoId);
                if (index == -2) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Playlist Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("index", index);
                return jsonObject.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byPlaylistId/{playlist_id}/getCount", method = RequestMethod.GET)
    public String getVideoCountByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getVideoCountByPlaylistId(playlistId);
                if (count == -1) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Playlist Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("count", count);
                return jsonObject.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byVideoId/{video_id}/getCount", method = RequestMethod.GET)
    public String getPlaylistCountByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getPlaylistCountByVideoId(videoId);
                if (count == -1) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Video Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("count", count);
                return jsonObject.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byUploaderId/{uploader_id}/getCount", method = RequestMethod.GET)
    public String getPlaylistCountByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getPlaylistCountByUploaderId(uploaderId);
                if (count == -1) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Uploader Not Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("count", count);
                return jsonObject.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byPlaylistId/{playlist_id}/containsVideo/{video_id}", method = RequestMethod.GET)
    public String getPlaylistCountByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final boolean contains = database.playlistContainsVideo(playlistId, videoId);
                /* //FIXME Check if Playlist/Video even exists?
                if (count == -1) {
                    serverHttpResponse.setStatusCode(HttpStatus.NOT_FOUND);
                    return "Uploader Not Found";
                }
                */
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("contains", contains);
                return jsonObject.toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @RequestMapping(value = "/requesters/byTag/{tag}", method = RequestMethod.POST)
    public String addRequesterByTag(ServerHttpResponse serverHttpResponse, @PathVariable(value = "tag") String tag, @RequestParam(value = DatabaseRequester.KEY_NAME, defaultValue = "") String name, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            return "Unauthorized";
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                if (database.hasRequester(tag)) {
                    serverHttpResponse.setStatusCode(HttpStatus.OK);
                    return database.getRequesterByTag(tag).toJsonObject().toString();
                }
                final DatabaseRequester databaseRequester = new DatabaseRequester(-2, tag, name.isEmpty() ? tag : name);
                database.addRequester(databaseRequester);
                serverHttpResponse.setStatusCode(HttpStatus.CREATED);
                return databaseRequester.toJsonObject().toString();
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    private static final boolean isTokenValid(String token) {
        return YouTubeDownloadWebService.useDatabaseOrFalse((database) -> database.isTokenValid(token));
    }
    
    private static final AuthorizationToken getAuthorizationToken(String token) {
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> database.getAuthorizationTokenByToken(token));
    }
    
    private static final boolean useTokenOnce(String token) {
        return YouTubeDownloadWebService.useDatabaseOrFalse((database) -> database.useTokenOnce(token));
    }
    
}
