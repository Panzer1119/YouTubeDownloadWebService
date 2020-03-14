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

import de.codemakers.download.database.entities.QueuedVideoState;

public class YouTubeDatabaseConstants {
    
    // // // Identifiers
    // // Tables
    // Database: YouTube
    public static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS = "authorizationTokens";
    public static final String IDENTIFIER_TABLE_CHANNELS = "channels";
    public static final String IDENTIFIER_TABLE_EXTRA_FILES = "extraFiles";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES = "mediaFiles";
    public static final String IDENTIFIER_TABLE_PLAYLISTS = "playlists";
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS = "playlistVideos";
    public static final String IDENTIFIER_TABLE_REQUESTERS = "requesters";
    public static final String IDENTIFIER_TABLE_UPLOADERS = "uploaders";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE = "videoQueue";
    public static final String IDENTIFIER_TABLE_VIDEOS = "videos";
    //
    // // Columns
    // Table: Authorization Tokens
    public static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN = "token";
    public static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_LEVEL = "level";
    public static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_CREATED = "created";
    public static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION = "expiration";
    public static final String IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_USED = "used";
    // Table: Channels
    public static final String IDENTIFIER_TABLE_CHANNELS_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_CHANNELS_COLUMN_NAME = "name";
    // Table: Extra Files
    public static final String IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID = "videoId";
    public static final String IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE = "file";
    public static final String IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE_TYPE = "fileType";
    // Table: Media Files
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID = "videoId";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE = "file";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE_TYPE = "fileType";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FORMAT = "format";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VCODEC = "vcodec";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ACODEC = "acodec";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_WIDTH = "width";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_HEIGHT = "height";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FPS = "fps";
    public static final String IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ASR = "asr";
    // Table: Playlists
    public static final String IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_PLAYLISTS_COLUMN_TITLE = "title";
    public static final String IDENTIFIER_TABLE_PLAYLISTS_COLUMN_PLAYLIST = "playlist";
    public static final String IDENTIFIER_TABLE_PLAYLISTS_COLUMN_UPLOADER_ID = "uploaderId";
    // Table: Playlist Videos
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID = "playlistId";
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID = "videoId";
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX = "playlistIndex";
    // Table: Requesters
    public static final String IDENTIFIER_TABLE_REQUESTERS_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG = "tag";
    public static final String IDENTIFIER_TABLE_REQUESTERS_COLUMN_NAME = "name";
    // Table: Uploaders
    public static final String IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_UPLOADERS_COLUMN_NAME = "name";
    // Table: Video Queue
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID = "videoId";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY = "priority";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED = "requested";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID = "requesterId";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_FILE_TYPE = "fileType";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ARGUMENTS = "arguments";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_CONFIG_FILE = "configFile";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_OUTPUT_DIRECTORY = "outputDirectory";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE = "state";
    // Table: Videos
    public static final String IDENTIFIER_TABLE_VIDEOS_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_VIDEOS_COLUMN_CHANNEL_ID = "channelId";
    public static final String IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOADER_ID = "uploaderId";
    public static final String IDENTIFIER_TABLE_VIDEOS_COLUMN_TITLE = "title";
    public static final String IDENTIFIER_TABLE_VIDEOS_COLUMN_ALT_TITLE = "altTitle";
    public static final String IDENTIFIER_TABLE_VIDEOS_COLUMN_DURATION = "duration";
    public static final String IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOAD_DATE = "uploadDate";
    //
    // //
    // // // Queries
    // // Selects
    public static final String QUERY_SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";
    // Table: Authorization Tokens
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_AUTHORIZATION_TOKENS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS);
    /**
     * 1. Argument: Token
     */
    public static final String QUERY_TABLE_AUTHORIZATION_TOKENS_SELECT_BY_TOKEN = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN);
    // Table: Channels
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_CHANNELS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_CHANNELS);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_CHANNELS_SELECT_BY_CHANNEL_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_CHANNELS, IDENTIFIER_TABLE_CHANNELS_COLUMN_ID);
    // Table: Extra Files
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_EXTRA_FILES_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_EXTRA_FILES);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_EXTRA_FILES_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_EXTRA_FILES, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: File Type
     */
    public static final String QUERY_TABLE_EXTRA_FILES_SELECT_ALL_BY_FILE_TYPE = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_EXTRA_FILES, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE_TYPE);
    /**
     * 1. Argument: Video ID
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_EXTRA_FILES_SELECT_BY_VIDEO_ID_AND_FILE = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_EXTRA_FILES, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE);
    // Table: Media Files
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_MEDIA_FILES_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_MEDIA_FILES);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_MEDIA_FILES_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_MEDIA_FILES, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: File Type
     */
    public static final String QUERY_TABLE_MEDIA_FILES_SELECT_ALL_BY_FILE_TYPE = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_MEDIA_FILES, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE_TYPE);
    /**
     * 1. Argument: Video ID
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_MEDIA_FILES_SELECT_BY_VIDEO_ID_AND_FILE = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_MEDIA_FILES, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE);
    // Table: Playlists
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_PLAYLISTS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_PLAYLISTS);
    /**
     * 1. Argument: Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLISTS_SELECT_BY_PLAYLIST_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLISTS, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_PLAYLISTS_SELECT_ALL_UPLOADER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLISTS, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_UPLOADER_ID);
    // Table: Playlist Videos
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_PLAYLIST_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID);
    /**
     * 1. Argument: Playlist ID
     * 2. Argument: Video ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_BY_PLAYLIST_ID_AND_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID);
    // Table: Requesters
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_REQUESTERS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_REQUESTERS);
    /**
     * 1. Argument: Requester ID
     */
    public static final String QUERY_TABLE_REQUESTERS_SELECT_BY_REQUESTER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTERS, IDENTIFIER_TABLE_REQUESTERS_COLUMN_ID);
    /**
     * 1. Argument: Tag
     */
    public static final String QUERY_TABLE_REQUESTERS_SELECT_BY_TAG = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTERS, IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG);
    // Table: Uploaders
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_UPLOADERS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_UPLOADERS);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_UPLOADERS_SELECT_BY_UPLOADER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_UPLOADERS, IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID);
    // Table: Video Queue
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_VIDEO_QUEUE);
    /**
     * 1. Argument: ID
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_SELECT_BY_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Requester ID
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_BY_REQUESTER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID);
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_SELECT_ALL_NEXT = String.format("SELECT * FROM %s WHERE %s = '%s' ORDER BY %s DESC, %s, %s;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE, QueuedVideoState.QUEUED.name());
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_SELECT_NEXT = String.format("SELECT * FROM %s WHERE %s = '%s' ORDER BY %s DESC, %s, %s LIMIT 1;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE, QueuedVideoState.QUEUED.name());
    // Table: Videos
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEOS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_VIDEOS);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEOS_SELECT_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_ID);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_VIDEOS_SELECT_ALL_BY_CHANNEL_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_CHANNEL_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_VIDEOS_SELECT_ALL_BY_UPLOADER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOADER_ID);
    //
    // // Inserts
    // Table: Authorization Tokens
    /**
     * 1. Argument: Token
     * <br>
     * 2. Argument: Level
     * <br>
     * 3. Argument: Created
     * <br>
     * 4. Argument: Expiration
     */
    public static final String QUERY_TABLE_AUTHORIZATION_TOKENS_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_LEVEL, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_CREATED, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION);
    // Table: Channels
    /**
     * 1. Argument: Channel ID
     * <br>
     * 2. Argument: Name
     */
    public static final String QUERY_TABLE_CHANNELS_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);", IDENTIFIER_TABLE_CHANNELS, IDENTIFIER_TABLE_CHANNELS_COLUMN_ID, IDENTIFIER_TABLE_CHANNELS_COLUMN_NAME);
    // Table: Extra Files
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     * <br>
     * 3. Argument: File Type
     */
    public static final String QUERY_TABLE_EXTRA_FILES_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", IDENTIFIER_TABLE_EXTRA_FILES, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE_TYPE);
    // Table: Media Files
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     * <br>
     * 3. Argument: File Type
     * <br>
     * 4. Argument: Format
     * <br>
     * 5. Argument: Video Codec
     * <br>
     * 6. Argument: Audio Codec
     * <br>
     * 7. Argument: Width
     * <br>
     * 8. Argument: Height
     * <br>
     * 9. Argument: FPS
     * <br>
     * 10. Argument: ASR
     */
    public static final String QUERY_TABLE_MEDIA_FILES_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", IDENTIFIER_TABLE_MEDIA_FILES, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FORMAT, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VCODEC, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ACODEC, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_WIDTH, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_HEIGHT, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FPS, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ASR);
    // Table: Playlists
    /**
     * 1. Argument: Playlist ID
     * <br>
     * 2. Argument: Title
     * <br>
     * 3. Argument: Playlist
     * <br>
     * 4. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_PLAYLISTS_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", IDENTIFIER_TABLE_PLAYLISTS, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_TITLE, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_PLAYLIST, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_UPLOADER_ID);
    // Table: Playlist Videos
    /**
     * 1. Argument: Playlist ID
     * <br>
     * 2. Argument: Video ID
     * <br>
     * 3. Argument: Playlist Index
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX);
    // Table: Requesters
    /**
     * 1. Argument: Tag
     * <br>
     * 2. Argument: Name
     */
    public static final String QUERY_TABLE_REQUESTERS_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);", IDENTIFIER_TABLE_REQUESTERS, IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG, IDENTIFIER_TABLE_REQUESTERS_COLUMN_NAME);
    // Table: Uploaders
    /**
     * 1. Argument: Uploader ID
     * <br>
     * 2. Argument: Name
     */
    public static final String QUERY_TABLE_UPLOADERS_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);", IDENTIFIER_TABLE_UPLOADERS, IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID, IDENTIFIER_TABLE_UPLOADERS_COLUMN_NAME);
    // Table: Video Queue
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: Priority
     * <br>
     * 3. Argument: Requested
     * <br>
     * 4. Argument: Requester ID
     * <br>
     * 5. Argument: File Type
     * <br>
     * 6. Argument: Arguments
     * <br>
     * 7. Argument: Config File
     * <br>
     * 8. Argument: Output Directory
     * <br>
     * 9. Argument: State
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ARGUMENTS, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_CONFIG_FILE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_OUTPUT_DIRECTORY, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE);
    // Table: Videos
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: Channel ID
     * <br>
     * 3. Argument: Uploader ID
     * <br>
     * 4. Argument: Title
     * <br>
     * 5. Argument: Alt Title
     * <br>
     * 6. Argument: Duration
     * <br>
     * 7. Argument: Upload Date
     */
    public static final String QUERY_TABLE_VIDEOS_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?);", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_ID, IDENTIFIER_TABLE_VIDEOS_COLUMN_CHANNEL_ID, IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOADER_ID, IDENTIFIER_TABLE_VIDEOS_COLUMN_TITLE, IDENTIFIER_TABLE_VIDEOS_COLUMN_ALT_TITLE, IDENTIFIER_TABLE_VIDEOS_COLUMN_DURATION, IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOAD_DATE);
    //
    // // Updates
    // Table: Authorization Tokens
    /**
     * 1. Argument: (New) Token
     * <br>
     * 2. Argument: Level
     * <br>
     * 3. Argument: Created
     * <br>
     * 4. Argument: Expiration
     * <br>
     * 5. Argument: Times used
     * <br>
     * 6. Argument: (Old) Token
     */
    public static final String QUERY_TABLE_AUTHORIZATION_TOKENS_UPDATE_BY_TOKEN = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_LEVEL, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_CREATED, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_EXPIRATION, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_USED, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN);
    /**
     * 1. Argument: Times used
     * <br>
     * 2. Argument: Token
     */
    public static final String QUERY_TABLE_AUTHORIZATION_TOKENS_UPDATE_USED_BY_TOKEN = String.format("UPDATE %s SET %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_USED, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN);
    // Table: Channels
    /**
     * 1. Argument: (New) Channel ID
     * <br>
     * 2. Argument: Name
     * <br>
     * 3. Argument: (Old) Channel ID
     */
    public static final String QUERY_TABLE_CHANNELS_UPDATE_BY_CHANNEL_ID = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_CHANNELS, IDENTIFIER_TABLE_CHANNELS_COLUMN_ID, IDENTIFIER_TABLE_CHANNELS_COLUMN_NAME, IDENTIFIER_TABLE_CHANNELS_COLUMN_ID);
    // Table: Extra Files
    /**
     * 1. Argument: (New) Video ID
     * <br>
     * 2. Argument: (New) File
     * <br>
     * 3. Argument: File Type
     * <br>
     * 4. Argument: (Old) Video ID
     * <br>
     * 5. Argument: (Old) File
     */
    public static final String QUERY_TABLE_EXTRA_FILES_UPDATE_BY_VIDEO_ID_AND_FILE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_EXTRA_FILES, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE);
    // Table: Media Files
    /**
     * 1. Argument: (New) Video ID
     * <br>
     * 2. Argument: (New) File
     * <br>
     * 3. Argument: File Type
     * <br>
     * 4. Argument: Format
     * <br>
     * 5. Argument: Video Codec
     * <br>
     * 6. Argument: Audio Codec
     * <br>
     * 7. Argument: Width
     * <br>
     * 8. Argument: Height
     * <br>
     * 9. Argument: FPS
     * <br>
     * 10. Argument: ASR
     * <br>
     * 11. Argument: (Old) Video ID
     * <br>
     * 12. Argument: (Old) File
     */
    public static final String QUERY_TABLE_MEDIA_FILES_UPDATE_BY_VIDEO_ID_AND_FILE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_MEDIA_FILES, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FORMAT, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VCODEC, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ACODEC, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_WIDTH, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_HEIGHT, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FPS, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_ASR, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE);
    // Table: Playlists
    /**
     * 1. Argument: (New) Playlist ID
     * <br>
     * 2. Argument: Title
     * <br>
     * 3. Argument: Playlist
     * <br>
     * 4. Argument: Uploader ID
     * <br>
     * 5. Argument: (Old) Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLISTS_UPDATE_BY_PLAYLIST_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLISTS, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_TITLE, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_PLAYLIST, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_UPLOADER_ID, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID);
    // Table: Playlist Videos
    /**
     * 1. Argument: (New) Playlist ID
     * <br>
     * 2. Argument: (New) Video ID
     * <br>
     * 3. Argument: Playlist Index
     * <br>
     * 4. Argument: (Old) Playlist ID
     * <br>
     * 5. Argument: (Old) Video ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_UPDATE_BY_PLAYLIST_ID_AND_VIDEO_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID);
    // Table: Requesters
    /**
     * 1. Argument: (New) Requester ID
     * <br>
     * 2. Argument: Tag
     * <br>
     * 3. Argument: Name
     * <br>
     * 4. Argument: (Old) Requester ID
     */
    public static final String QUERY_TABLE_REQUESTERS_UPDATE_BY_REQUESTER_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTERS, IDENTIFIER_TABLE_REQUESTERS_COLUMN_ID, IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG, IDENTIFIER_TABLE_REQUESTERS_COLUMN_NAME, IDENTIFIER_TABLE_REQUESTERS_COLUMN_ID);
    /**
     * 1. Argument: (New) Tag
     * <br>
     * 2. Argument: Name
     * <br>
     * 3. Argument: (Old) Tag
     */
    public static final String QUERY_TABLE_REQUESTERS_UPDATE_BY_TAG = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTERS, IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG, IDENTIFIER_TABLE_REQUESTERS_COLUMN_NAME, IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG);
    // Table: Uploaders
    /**
     * 1. Argument: (New) Uploader ID
     * <br>
     * 2. Argument: Name
     * <br>
     * 3. Argument: (Old) Uploader ID
     */
    public static final String QUERY_TABLE_UPLOADERS_UPDATE_BY_UPLOADER_ID = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_UPLOADERS, IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID, IDENTIFIER_TABLE_UPLOADERS_COLUMN_NAME, IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID);
    // Table: Video Queue
    /**
     * 1. Argument: (New) ID
     * <br>
     * 2. Argument: Video ID
     * <br>
     * 3. Argument: Priority
     * <br>
     * 4. Argument: Requested
     * <br>
     * 5. Argument: Requester ID
     * <br>
     * 6. Argument: File Type
     * <br>
     * 7. Argument: Arguments
     * <br>
     * 8. Argument: Config File
     * <br>
     * 9. Argument: Output Directory
     * <br>
     * 10. Argument: State
     * <br>
     * 11. Argument: (Old) ID
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_UPDATE_BY_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ARGUMENTS, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_CONFIG_FILE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_OUTPUT_DIRECTORY, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID);
    // Table: Videos
    /**
     * 1. Argument: (New) Video ID
     * <br>
     * 2. Argument: Channel ID
     * <br>
     * 3. Argument: Uploader ID
     * <br>
     * 4. Argument: Title
     * <br>
     * 5. Argument: Alt Title
     * <br>
     * 6. Argument: Duration
     * <br>
     * 7. Argument: Upload Date
     * <br>
     * 8. Argument: (Old) Video ID
     */
    public static final String QUERY_TABLE_VIDEOS_UPDATE_BY_VIDEO_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_ID, IDENTIFIER_TABLE_VIDEOS_COLUMN_CHANNEL_ID, IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOADER_ID, IDENTIFIER_TABLE_VIDEOS_COLUMN_TITLE, IDENTIFIER_TABLE_VIDEOS_COLUMN_ALT_TITLE, IDENTIFIER_TABLE_VIDEOS_COLUMN_DURATION, IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOAD_DATE, IDENTIFIER_TABLE_VIDEOS_COLUMN_ID);
    //
    // // Deletes
    // Table: Authorization Tokens
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_AUTHORIZATION_TOKENS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS);
    /**
     * 1. Argument: Token
     */
    public static final String QUERY_TABLE_AUTHORIZATION_TOKENS_DELETE_BY_TOKEN = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_AUTHORIZATION_TOKENS, IDENTIFIER_TABLE_AUTHORIZATION_TOKENS_COLUMN_TOKEN);
    // Table: Channels
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_CHANNELS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_CHANNELS);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_CHANNELS_DELETE_BY_CHANNEL_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_CHANNELS, IDENTIFIER_TABLE_CHANNELS_COLUMN_ID);
    // Table: Extra Files
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_EXTRA_FILES_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_EXTRA_FILES);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_EXTRA_FILES_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_EXTRA_FILES, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_EXTRA_FILES_DELETE_BY_VIDEO_ID_AND_FILE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_EXTRA_FILES, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_EXTRA_FILES_COLUMN_FILE);
    // Table: Media Files
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_MEDIA_FILES_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_MEDIA_FILES);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_MEDIA_FILES_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_MEDIA_FILES, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_MEDIA_FILES_DELETE_BY_VIDEO_ID_AND_FILE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_MEDIA_FILES, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_MEDIA_FILES_COLUMN_FILE);
    // Table: Playlists
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_PLAYLISTS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_PLAYLISTS);
    /**
     * 1. Argument: Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLISTS_DELETE_BY_PLAYLIST_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLISTS, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_PLAYLISTS_DELETE_ALL_BY_UPLOADER_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLISTS, IDENTIFIER_TABLE_PLAYLISTS_COLUMN_UPLOADER_ID);
    // Table: Playlist Videos
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS);
    /**
     * 1. Argument: Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_PLAYLIST_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Playlist ID
     * <br>
     * 2. Argument: Video ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_DELETE_BY_PLAYLIST_ID_AND_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID);
    // Table: Requesters
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_REQUESTERS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_REQUESTERS);
    /**
     * 1. Argument: Requester ID
     */
    public static final String QUERY_TABLE_REQUESTERS_DELETE_BY_REQUESTER_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTERS, IDENTIFIER_TABLE_REQUESTERS_COLUMN_ID);
    /**
     * 1. Argument: Tag
     */
    public static final String QUERY_TABLE_REQUESTERS_DELETE_BY_TAG = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTERS, IDENTIFIER_TABLE_REQUESTERS_COLUMN_TAG);
    // Table: Uploaders
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_UPLOADERS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_UPLOADERS);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_UPLOADERS_DELETE_BY_CHANNEL_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_UPLOADERS, IDENTIFIER_TABLE_UPLOADERS_COLUMN_ID);
    // Table: Video Queue
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_VIDEO_QUEUE);
    /**
     * 1. Argument: ID
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_DELETE_BY_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Requester ID
     */
    public static final String QUERY_TABLE_VIDEO_QUEUE_DELETE_ALL_BY_REQUESTER_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_QUEUE, IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID);
    // Table: Videos
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEOS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_VIDEOS);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEOS_DELETE_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_ID);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_VIDEOS_DELETE_ALL_BY_CHANNEL_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_CHANNEL_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_VIDEOS_DELETE_ALL_BY_UPLOADER_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEOS, IDENTIFIER_TABLE_VIDEOS_COLUMN_UPLOADER_ID);
    //
    // //
    // // //
    
}
