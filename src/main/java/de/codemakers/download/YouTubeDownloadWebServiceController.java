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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.codemakers.base.Standard;
import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.logger.Logger;
import de.codemakers.download.database.entities.impl.*;
import de.codemakers.download.entities.AbstractToken;
import de.codemakers.download.remote.WebService;
import de.codemakers.io.file.AdvancedFile;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@RestController
public class YouTubeDownloadWebServiceController {
    
    @Deprecated
    @RequestMapping(value = "/request/{video_id}", method = RequestMethod.POST)
    public ResponseEntity<String> request(@PathVariable(value = "video_id") String videoId, @RequestParam(value = "priority", defaultValue = "-1") int priority, @RequestParam(value = "requesterId", defaultValue = "-1") int requesterId, @RequestParam(value = "fileType", defaultValue = "B") String fileType, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            return createResponseEntityUnauthorized();
        }
        //TODO Hmmm restrict priority to level of permission?
        final List<DatabaseQueuedYouTubeVideo> databaseQueuedYouTubeVideos = YouTubeDownloadWebService.useDatabaseOrNull((database) -> database.getQueuedVideosByVideoId(videoId));
        if (databaseQueuedYouTubeVideos != null && !databaseQueuedYouTubeVideos.isEmpty()) {
            //TODO Check and only error, if the same fileType is already queued?
            //return String.format("Already queued"); //TODO How to return the right HttpStatus? //TODO IMPORTANT Should we even stop this?
            return null;
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
        //return String.format("Video queued for Download...?");
        return createResponseEntity("Video queued for Download...?", HttpStatus.OK);
    }
    
    @Deprecated
    @RequestMapping(value = "/download/{video_id}", method = RequestMethod.GET)
    public Mono<Void> download(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = "fileType", defaultValue = "B") String fileType, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            
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
    public ResponseEntity<List<String>> downloadVideoIdsByPlaylistId(@PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            return createResponseEntityUnauthorized();
        }
        //useToken(authToken);
        //return YouTubeDL.downloadVideoIdsFromURL(String.format("https://www.youtube.com/playlist?list=%s", playlistId));
        throw new NotYetImplementedRuntimeException();
    }
    
    @Deprecated
    @RequestMapping(value = "/generate/authorizationToken", method = RequestMethod.GET)
    public ResponseEntity<String> generateAuthorizationToken(@RequestParam(value = "unlimited", defaultValue = "0") boolean unlimited, @RequestParam(value = "granting", defaultValue = "0") boolean granting, @RequestParam(value = "duration", defaultValue = "100000") long durationMillis, @RequestParam(value = "authToken") String authToken) {
        //if (!isValidToken(authToken)) {
        if (!useTokenOnce(authToken)) {
            return createResponseEntityUnauthorized();
        }
        final AuthorizationToken authorizationTokenMaster = getAuthorizationToken(authToken);
        if (authorizationTokenMaster == null) {
            return createResponseEntityUnauthorized();
        }
        if (!authorizationTokenMaster.getLevel().isGranting() || (unlimited && !authorizationTokenMaster.getLevel().isThisHigherOrEqual(AuthorizationToken.TokenLevel.SUPER_GRANTER)) || (granting && !authorizationTokenMaster.getLevel().isThisHigherOrEqual(AuthorizationToken.TokenLevel.ADMIN))) {
            return createResponseEntityForbidden();
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
            return createResponseEntityInternalServerError();
        }
        //useToken(authToken);
        //return authorizationTokenSlave.toJson();
        return createResponseEntityOk(authorizationTokenSlave.toJsonObject());
    }
    
    // NEW ENDPOINTS
    
    
    // // GETs
    
    @RequestMapping(value = "/videos/all/", method = RequestMethod.GET)
    public ResponseEntity<String> getAllVideos(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<DatabaseYouTubeVideo> videos = database.getAllVideos();
                if (videos == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videos.stream().map(DatabaseYouTubeVideo::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/all/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getAllVideoIds(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> videoIds = database.getAllVideoIds();
                if (videoIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videoIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/all/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoCount(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> videoIds = database.getAllVideoIds(); //TODO Create a custom SQL Query for that
                if (videoIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, videoIds.size());
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byVideoId/{video_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeVideo video = database.getVideoByVideoId(videoId);
                if (video == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                return createResponseEntityOk(video.toJsonObject());
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byPlaylistId/{playlist_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getVideosByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubePlaylist playlist = database.getPlaylistByPlaylistId(playlistId);
                if (playlist == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<DatabaseYouTubeVideo> videos = playlist.getVideos();
                if (videos.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videos.stream().map(DatabaseYouTubeVideo::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byPlaylistId/{playlist_id}/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoIdsByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubePlaylist playlist = database.getPlaylistByPlaylistId(playlistId);
                if (playlist == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<String> videoIds = playlist.getVideoIds();
                if (videoIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videoIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byChannelId/{channel_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getVideosByChannelId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeChannel channel = database.getChannelByChannelId(channelId);
                if (channel == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<DatabaseYouTubeVideo> videos = channel.getVideos();
                if (videos.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videos.stream().map(DatabaseYouTubeVideo::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byChannelId/{channel_id}/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoIdsByChannelId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeChannel channel = database.getChannelByChannelId(channelId);
                if (channel == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<String> videoIds = channel.getVideoIds();
                if (videoIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videoIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byUploaderId/{uploader_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getVideosByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploader == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<DatabaseYouTubeVideo> videos = uploader.getUploadedVideos();
                if (videos.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videos.stream().map(DatabaseYouTubeVideo::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byUploaderId/{uploader_id}/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoIdsByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploader == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<String> videoIds = uploader.getUploadedVideoIds();
                if (videoIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                videoIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byChannelId/{channel_id}/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoCountByChannelId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getVideoCountByChannelId(channelId);
                if (count == -1) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, count);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byUploaderId/{uploader_id}/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoCountByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getVideoCountByUploaderId(uploaderId);
                if (count == -1) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, count);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/all/", method = RequestMethod.GET)
    public ResponseEntity<String> getAllPlaylists(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<DatabaseYouTubePlaylist> playlists = database.getAllPlaylists();
                if (playlists == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlists.stream().map(DatabaseYouTubePlaylist::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/all/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getAllPlaylistIds(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> playlistIds = database.getAllPlaylistIds();
                if (playlistIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlistIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/all/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistCount(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> playlistIds = database.getAllPlaylistIds(); //TODO Create a custom SQL Query for that
                if (playlistIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, playlistIds.size());
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byPlaylistId/{playlist_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubePlaylist playlist = database.getPlaylistByPlaylistId(playlistId);
                if (playlist == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                return createResponseEntityOk(playlist.toJsonObject());
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byVideoId/{video_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistsByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeVideo video = database.getVideoByVideoId(videoId);
                if (video == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<DatabaseYouTubePlaylist> playlists = video.getPlaylists();
                if (playlists.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlists.stream().map(DatabaseYouTubePlaylist::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byVideoId/{video_id}/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistIdsByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeVideo video = database.getVideoByVideoId(videoId);
                if (video == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<String> playlistIds = video.getPlaylistIds();
                if (playlistIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlistIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byUploaderId/{uploader_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistsByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploaderId == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<DatabaseYouTubePlaylist> playlists = uploader.getCreatedPlaylists();
                if (playlists.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlists.stream().map(DatabaseYouTubePlaylist::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byUploaderId/{uploader_id}/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistIdsByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploaderId == null) {
                    
                    return createResponseEntityNotFound();
                }
                final List<String> playlistIds = uploader.getCreatedPlaylistIds();
                if (playlistIds.isEmpty()) {
                    //serverHttpResponse.setStatusCode(HttpStatus.NO_CONTENT);
                    //return "Playlist Found, But No Videos Found";
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                playlistIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byPlaylistId/{playlist_id}/getIndex/{video_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoIndexInPlaylist(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int index = database.getIndexInPlaylist(playlistId, videoId);
                if (index == -2) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, index);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/videos/byPlaylistId/{playlist_id}/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getVideoCountByPlaylistId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getVideoCountByPlaylistId(playlistId);
                if (count == -1) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, count);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byVideoId/{video_id}/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistCountByVideoId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getPlaylistCountByVideoId(videoId);
                if (count == -1) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, count);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byUploaderId/{uploader_id}/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getPlaylistCountByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final int count = database.getPlaylistCountByUploaderId(uploaderId);
                if (count == -1) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, count);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/playlists/byPlaylistId/{playlist_id}/containsVideo/{video_id}", method = RequestMethod.GET)
    public ResponseEntity<String> playlistContainsVideo(ServerHttpResponse serverHttpResponse, @PathVariable(value = "playlist_id") String playlistId, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final boolean contains = database.playlistContainsVideo(playlistId, videoId);
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, contains);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/channels/all/", method = RequestMethod.GET)
    public ResponseEntity<String> getAllChannels(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<DatabaseYouTubeChannel> channels = database.getAllChannels();
                if (channels == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                channels.stream().map(DatabaseYouTubeChannel::toJsonObject).forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/channels/all/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getAllChannelIds(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> channelIds = database.getAllChannelIds();
                if (channelIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                channelIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/channels/all/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getChannelCount(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> channelIds = database.getAllChannelIds(); //TODO Create a custom SQL Query for that
                if (channelIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, channelIds.size());
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/channels/byChannelId/{channel_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getChannelByChannelId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeChannel channel = database.getChannelByChannelId(channelId);
                if (channel == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                return createResponseEntityOk(channel.toJsonObject());
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/channels/byChannelId/{channel_id}/hasVideo/{video_id}", method = RequestMethod.GET)
    public ResponseEntity<String> channelHasVideo(ServerHttpResponse serverHttpResponse, @PathVariable(value = "channel_id") String channelId, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final boolean contains = database.channelHasVideo(channelId, videoId);
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, contains);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/uploaders/byUploaderId/{uploader_id}", method = RequestMethod.GET)
    public ResponseEntity<String> getUploaderByUploaderId(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final DatabaseYouTubeUploader uploader = database.getUploaderByUploaderId(uploaderId);
                if (uploader == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                return createResponseEntityOk(uploader.toJsonObject());
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/uploaders/all", method = RequestMethod.GET)
    public ResponseEntity<String> getAllUploaders(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            //
            ////return "Unauthorized";
            //return null;
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<DatabaseYouTubeUploader> uploaders = database.getAllUploaders();
                if (uploaders == null) {
                    //
                    ////return createResponseEntityNotFound();
                    //return null;
                    return createResponseEntityNotFound();
                }
                final JsonArray jsonArray = new JsonArray();
                uploaders.stream().map(DatabaseYouTubeUploader::toJsonObject).forEach(jsonArray::add);
                //serverHttpResponse.setStatusCode(HttpStatus.OK);
                ////return createResponseEntityOk(jsonArray);
                //return jsonArray;
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/uploaders/all/getIds", method = RequestMethod.GET)
    public ResponseEntity<String> getAllUploaderIds(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> uploaderIds = database.getAllUploaderIds();
                if (uploaderIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonArray jsonArray = new JsonArray();
                uploaderIds.forEach(jsonArray::add);
                return createResponseEntityOk(jsonArray);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/uploaders/all/getCount", method = RequestMethod.GET)
    public ResponseEntity<String> getUploaderCount(ServerHttpResponse serverHttpResponse, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final List<String> uploaderIds = database.getAllUploaderIds(); //TODO Create a custom SQL Query for that
                if (uploaderIds == null) {
                    
                    return createResponseEntityNotFound();
                }
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, uploaderIds.size());
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/uploaders/byUploaderId/{uploader_id}/uploadedVideo/{video_id}", method = RequestMethod.GET)
    public ResponseEntity<String> uploaderUploadedVideo(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @PathVariable(value = "video_id") String videoId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final boolean contains = database.uploaderUploadedVideo(uploaderId, videoId);
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, contains);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    @RequestMapping(value = "/uploaders/byUploaderId/{uploader_id}/createdPlaylist/{playlist_id}", method = RequestMethod.GET)
    public ResponseEntity<String> uploaderCreatedPlaylist(ServerHttpResponse serverHttpResponse, @PathVariable(value = "uploader_id") String uploaderId, @PathVariable(value = "playlist_id") String playlistId, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                final boolean contains = database.uploaderCreatedPlaylist(uploaderId, playlistId);
                serverHttpResponse.setStatusCode(HttpStatus.OK);
                final JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(WebService.KEY_RESULT, contains);
                return createResponseEntityOk(jsonObject);
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    // // POSTs
    
    @RequestMapping(value = "/requesters/byTag/{tag}", method = RequestMethod.POST)
    public ResponseEntity<String> addRequesterByTag(ServerHttpResponse serverHttpResponse, @PathVariable(value = "tag") String tag, @RequestParam(value = DatabaseRequester.KEY_NAME, defaultValue = "") String name, @RequestParam(value = AbstractToken.KEY_TOKEN) String token) {
        if (!useTokenOnce(token)) {
            
            return createResponseEntityUnauthorized();
        }
        return YouTubeDownloadWebService.useDatabaseOrNull((database) -> {
            try {
                if (database.hasRequester(tag)) {
                    serverHttpResponse.setStatusCode(HttpStatus.OK);
                    return createResponseEntityOk(database.getRequesterByTag(tag).toJsonObject());
                }
                final DatabaseRequester databaseRequester = new DatabaseRequester(-2, tag, name.isEmpty() ? tag : name);
                database.addRequester(databaseRequester);
                serverHttpResponse.setStatusCode(HttpStatus.CREATED);
                return createResponseEntityOk(databaseRequester.toJsonObject());
            } catch (Exception ex) {
                serverHttpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                throw ex;
            }
        });
    }
    
    // //
    
    private static final <R> ResponseEntity<R> createResponseEntityUnauthorized() {
        return createResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntityForbidden() {
        return createResponseEntity(HttpStatus.FORBIDDEN);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntityInternalServerError() {
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntityNotFound() {
        return createResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntityNoContent() {
        return createResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntityOk() {
        return createResponseEntity(HttpStatus.OK);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntityCreated() {
        return createResponseEntity(HttpStatus.CREATED);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntity(HttpStatus httpStatus) {
        return new ResponseEntity<>(httpStatus);
    }
    
    private static final <R> ResponseEntity<R> createResponseEntity(R content, HttpStatus httpStatus) {
        return new ResponseEntity<>(content, httpStatus);
    }
    
    private static final ResponseEntity<String> createResponseEntityOk(JsonElement jsonElement) {
        return createResponseEntity(jsonElement, HttpStatus.OK);
    }
    
    private static final ResponseEntity<String> createResponseEntity(JsonElement jsonElement, HttpStatus httpStatus) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(Objects.toString(jsonElement), httpHeaders, httpStatus);
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
