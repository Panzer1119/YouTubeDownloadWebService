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

import de.codemakers.download.database.entities.impl.QueuedVideoState;

public class YouTubeDatabaseConstants {
    
    // // // Identifiers
    // // Tables
    // Database: YouTube
    public static final String IDENTIFIER_TABLE_CHANNEL = "channel";
    public static final String IDENTIFIER_TABLE_FILE_EXTRA = "file_extra";
    public static final String IDENTIFIER_TABLE_FILE_LOGS = "file_logs";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA = "file_media";
    public static final String IDENTIFIER_TABLE_LOG = "log";
    public static final String IDENTIFIER_TABLE_PLAYLIST = "playlist";
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS = "playlist_videos";
    public static final String IDENTIFIER_TABLE_REQUESTER = "requester";
    public static final String IDENTIFIER_TABLE_TOKEN = "token";
    public static final String IDENTIFIER_TABLE_UPLOADER = "uploader";
    public static final String IDENTIFIER_TABLE_VIDEO = "video";
    public static final String IDENTIFIER_TABLE_VIDEO_LOGS = "video_logs";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE = "video_queue";
    //
    // // Columns
    // Table: Channel
    public static final String IDENTIFIER_TABLE_CHANNEL_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_CHANNEL_COLUMN_NAME = "name";
    // Table: File Extra
    public static final String IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID = "video_id";
    public static final String IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE = "file";
    public static final String IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE_TYPE = "file_type";
    public static final String IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_CREATED = "created";
    // Table: File Logs
    public static final String IDENTIFIER_TABLE_FILE_LOGS_COLUMN_VIDEO_ID = "video_id";
    public static final String IDENTIFIER_TABLE_FILE_LOGS_COLUMN_FILE = "file";
    public static final String IDENTIFIER_TABLE_FILE_LOGS_COLUMN_LOG_ID = "log_id";
    public static final String IDENTIFIER_TABLE_FILE_LOGS_COLUMN_IS_MEDIA_FILE = "is_media_file";
    // Table: File Media
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID = "video_id";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE = "file";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE_TYPE = "file_type";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_CREATED = "created";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FORMAT = "format";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VCODEC = "vcodec";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ACODEC = "acodec";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_WIDTH = "width";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_HEIGHT = "height";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FPS = "fps";
    public static final String IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ASR = "asr";
    // Table: Log
    public static final String IDENTIFIER_TABLE_LOG_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_LOG_COLUMN_CREATED = "created";
    public static final String IDENTIFIER_TABLE_LOG_COLUMN_FILE = "file";
    // Table: Playlist
    public static final String IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_PLAYLIST_COLUMN_TITLE = "title";
    public static final String IDENTIFIER_TABLE_PLAYLIST_COLUMN_PLAYLIST = "playlist";
    public static final String IDENTIFIER_TABLE_PLAYLIST_COLUMN_UPLOADER_ID = "uploader_id";
    // Table: Playlist Videos
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID = "playlist_id";
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID = "video_id";
    public static final String IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX = "playlist_index";
    // Table: Requester
    public static final String IDENTIFIER_TABLE_REQUESTER_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG = "tag";
    public static final String IDENTIFIER_TABLE_REQUESTER_COLUMN_NAME = "name";
    public static final String IDENTIFIER_TABLE_REQUESTER_COLUMN_CREATED = "created";
    // Table: Token
    public static final String IDENTIFIER_TABLE_TOKEN_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN = "token";
    public static final String IDENTIFIER_TABLE_TOKEN_COLUMN_LEVEL = "level";
    public static final String IDENTIFIER_TABLE_TOKEN_COLUMN_CREATED = "created";
    public static final String IDENTIFIER_TABLE_TOKEN_COLUMN_EXPIRATION = "expiration";
    public static final String IDENTIFIER_TABLE_TOKEN_COLUMN_TIMES_USED = "times_used";
    // Table: Uploader
    public static final String IDENTIFIER_TABLE_UPLOADER_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_UPLOADER_COLUMN_NAME = "name";
    // Table: Video
    public static final String IDENTIFIER_TABLE_VIDEO_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_VIDEO_COLUMN_CHANNEL_ID = "channel_id";
    public static final String IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOADER_ID = "uploader_id";
    public static final String IDENTIFIER_TABLE_VIDEO_COLUMN_TITLE = "title";
    public static final String IDENTIFIER_TABLE_VIDEO_COLUMN_ALT_TITLE = "alt_title";
    public static final String IDENTIFIER_TABLE_VIDEO_COLUMN_DURATION = "duration";
    public static final String IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOAD_DATE = "upload_date";
    // Table: Video Logs
    public static final String IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_VIDEO_ID = "video_id";
    public static final String IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_LOG_ID = "log_id";
    // Table: Video Queue
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ID = "id";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_VIDEO_ID = "video_id";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_PRIORITY = "priority";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTED = "requested";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_REQUESTER_ID = "requester_id";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_FILE_TYPE = "file_type";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_ARGUMENTS = "arguments";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_CONFIG_FILE = "config_file";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_OUTPUT_DIRECTORY = "output_directory";
    public static final String IDENTIFIER_TABLE_VIDEO_QUEUE_COLUMN_STATE = "state";
    //
    // //
    // // // Queries
    // // Selects
    public static final String QUERY_SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";
    // Table: Channels
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_CHANNEL_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_CHANNEL);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_CHANNEL_SELECT_BY_CHANNEL_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_CHANNEL, IDENTIFIER_TABLE_CHANNEL_COLUMN_ID);
    // Table: File Extra
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_FILE_EXTRA_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_FILE_EXTRA);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_FILE_EXTRA_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_EXTRA, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: File Type
     */
    public static final String QUERY_TABLE_FILE_EXTRA_SELECT_ALL_BY_FILE_TYPE = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_EXTRA, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE_TYPE);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_FILE_EXTRA_SELECT_BY_VIDEO_ID_AND_FILE = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_EXTRA, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE);
    // Table: File Logs
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_FILE_LOGS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_FILE_LOGS);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_FILE_LOGS_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Log ID
     */
    public static final String QUERY_TABLE_FILE_LOGS_SELECT_ALL_BY_LOG_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_LOG_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     * <br>
     * 3. Argument: Log ID
     * <br>
     * 4. Argument: Is Media File
     */
    public static final String QUERY_TABLE_FILE_LOGS_SELECT_BY_VIDEO_ID_AND_FILE_AND_LOG_ID_AND_IS_MEDIA_FILE = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? AND %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_FILE, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_LOG_ID, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_IS_MEDIA_FILE);
    // Table: File Media
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_FILE_MEDIA_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_FILE_MEDIA);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_FILE_MEDIA_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_MEDIA, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: File Type
     */
    public static final String QUERY_TABLE_FILE_MEDIA_SELECT_ALL_BY_FILE_TYPE = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_MEDIA, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE_TYPE);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_FILE_MEDIA_SELECT_BY_VIDEO_ID_AND_FILE = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_MEDIA, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE);
    // Table: Log
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_LOG_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_LOG);
    /**
     * 1. Argument: Log ID
     */
    public static final String QUERY_TABLE_LOG_SELECT_BY_LOG_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_LOG, IDENTIFIER_TABLE_LOG_COLUMN_ID);
    // Table: Playlist
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_PLAYLIST_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_PLAYLIST);
    /**
     * 1. Argument: Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLIST_SELECT_BY_PLAYLIST_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_PLAYLIST_SELECT_ALL_UPLOADER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_UPLOADER_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_PLAYLIST_COUNT_BY_UPLOADER_ID = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_UPLOADER_ID);
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
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_ALL_BY_PLAYLIST_ID = String.format("SELECT * FROM %s WHERE %s = ? ORDER BY %s;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX);
    /**
     * 1. Argument: Playlist ID
     * <br>
     * 2. Argument: Video ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_SELECT_BY_PLAYLIST_ID_AND_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_COUNT_BY_PLAYLIST_ID = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_COUNT_BY_VIDEO_ID = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID);
    // Table: Requester
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_REQUESTER_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_REQUESTER);
    /**
     * 1. Argument: Requester ID
     */
    public static final String QUERY_TABLE_REQUESTER_SELECT_BY_REQUESTER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTER, IDENTIFIER_TABLE_REQUESTER_COLUMN_ID);
    /**
     * 1. Argument: Tag
     */
    public static final String QUERY_TABLE_REQUESTER_SELECT_BY_TAG = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTER, IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG);
    // Table: Uploader
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_UPLOADER_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_UPLOADER);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_UPLOADER_SELECT_BY_UPLOADER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_UPLOADER, IDENTIFIER_TABLE_UPLOADER_COLUMN_ID);
    // Table: Token
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_TOKEN_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_TOKEN);
    /**
     * 1. Argument: Token ID
     */
    public static final String QUERY_TABLE_TOKEN_SELECT_BY_TOKEN_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_ID);
    /**
     * 1. Argument: Token
     */
    public static final String QUERY_TABLE_TOKEN_SELECT_BY_TOKEN = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN);
    // Table: Video
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_VIDEO);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEO_SELECT_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_ID);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_VIDEO_SELECT_ALL_BY_CHANNEL_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_CHANNEL_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_VIDEO_SELECT_ALL_BY_UPLOADER_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOADER_ID);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_VIDEO_COUNT_BY_CHANNEL_ID = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_CHANNEL_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_VIDEO_COUNT_BY_UPLOADER_ID = String.format("SELECT COUNT(*) FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOADER_ID);
    // Table: Video Logs
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_SELECT_ALL = String.format("SELECT * FROM %s;", IDENTIFIER_TABLE_VIDEO_LOGS);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_SELECT_ALL_BY_VIDEO_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_LOGS, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Log ID
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_SELECT_ALL_BY_LOG_ID = String.format("SELECT * FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_LOGS, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_LOG_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: Log ID
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_SELECT_BY_VIDEO_ID_AND_LOG_ID = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_VIDEO_LOGS, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_LOG_ID);
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
    //
    // // Inserts
    // Table: Channel
    /**
     * 1. Argument: Channel ID
     * <br>
     * 2. Argument: Name
     */
    public static final String QUERY_TABLE_CHANNEL_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);", IDENTIFIER_TABLE_CHANNEL, IDENTIFIER_TABLE_CHANNEL_COLUMN_ID, IDENTIFIER_TABLE_CHANNEL_COLUMN_NAME);
    // Table: File Extra
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     * <br>
     * 3. Argument: File Type
     * <br>
     * 4. Argument: Created
     */
    public static final String QUERY_TABLE_FILE_EXTRA_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", IDENTIFIER_TABLE_FILE_EXTRA, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_CREATED);
    // Table: File Logs
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     * <br>
     * 3. Argument: Log ID
     * <br>
     * 4. Argument: Is Media File
     */
    public static final String QUERY_TABLE_FILE_LOGS_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_FILE, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_LOG_ID, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_IS_MEDIA_FILE);
    // Table: File Media
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     * <br>
     * 3. Argument: File Type
     * <br>
     * 4. Argument: Created
     * <br>
     * 5. Argument: Format
     * <br>
     * 6. Argument: Video Codec
     * <br>
     * 7. Argument: Audio Codec
     * <br>
     * 8. Argument: Width
     * <br>
     * 9. Argument: Height
     * <br>
     * 10. Argument: FPS
     * <br>
     * 11. Argument: ASR
     */
    public static final String QUERY_TABLE_FILE_MEDIA_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", IDENTIFIER_TABLE_FILE_MEDIA, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_CREATED, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FORMAT, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VCODEC, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ACODEC, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_WIDTH, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_HEIGHT, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FPS, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ASR);
    // Table: Log
    /**
     * 1. Argument: Log ID
     * <br>
     * 2. Argument: Created
     * <br>
     * 3. Argument: File
     */
    public static final String QUERY_TABLE_LOG_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", IDENTIFIER_TABLE_LOG, IDENTIFIER_TABLE_LOG_COLUMN_ID, IDENTIFIER_TABLE_LOG_COLUMN_CREATED, IDENTIFIER_TABLE_LOG_COLUMN_FILE);
    // Table: Playlist
    /**
     * 1. Argument: Playlist ID
     * <br>
     * 2. Argument: Title
     * <br>
     * 3. Argument: Playlist
     * <br>
     * 4. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_PLAYLIST_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", IDENTIFIER_TABLE_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID, IDENTIFIER_TABLE_PLAYLIST_COLUMN_TITLE, IDENTIFIER_TABLE_PLAYLIST_COLUMN_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_UPLOADER_ID);
    // Table: Playlist Videos
    /**
     * 1. Argument: Playlist ID
     * <br>
     * 2. Argument: Video ID
     * <br>
     * 3. Argument: Playlist Index
     */
    public static final String QUERY_TABLE_PLAYLIST_VIDEOS_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", IDENTIFIER_TABLE_PLAYLIST_VIDEOS, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_PLAYLIST_VIDEOS_COLUMN_PLAYLIST_INDEX);
    // Table: Requester
    /**
     * 1. Argument: Tag
     * <br>
     * 2. Argument: Name
     * <br>
     * 3. Argument: Created
     */
    public static final String QUERY_TABLE_REQUESTER_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", IDENTIFIER_TABLE_REQUESTER, IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG, IDENTIFIER_TABLE_REQUESTER_COLUMN_NAME, IDENTIFIER_TABLE_REQUESTER_COLUMN_CREATED);
    // Table: Token
    /**
     * 1. Argument: Token
     * <br>
     * 2. Argument: Level
     * <br>
     * 3. Argument: Created
     * <br>
     * 4. Argument: Expiration
     */
    public static final String QUERY_TABLE_TOKEN_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_LEVEL, IDENTIFIER_TABLE_TOKEN_COLUMN_CREATED, IDENTIFIER_TABLE_TOKEN_COLUMN_EXPIRATION);
    // Table: Uploader
    /**
     * 1. Argument: Uploader ID
     * <br>
     * 2. Argument: Name
     */
    public static final String QUERY_TABLE_UPLOADER_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);", IDENTIFIER_TABLE_UPLOADER, IDENTIFIER_TABLE_UPLOADER_COLUMN_ID, IDENTIFIER_TABLE_UPLOADER_COLUMN_NAME);
    // Table: Video
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
    public static final String QUERY_TABLE_VIDEO_INSERT = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?);", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_ID, IDENTIFIER_TABLE_VIDEO_COLUMN_CHANNEL_ID, IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOADER_ID, IDENTIFIER_TABLE_VIDEO_COLUMN_TITLE, IDENTIFIER_TABLE_VIDEO_COLUMN_ALT_TITLE, IDENTIFIER_TABLE_VIDEO_COLUMN_DURATION, IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOAD_DATE);
    // Table: Video Logs
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: Log ID
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);", IDENTIFIER_TABLE_VIDEO_LOGS, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_LOG_ID);
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
    //
    // // Updates
    // Table: Channel
    /**
     * 1. Argument: (New) Channel ID
     * <br>
     * 2. Argument: Name
     * <br>
     * 3. Argument: (Old) Channel ID
     */
    public static final String QUERY_TABLE_CHANNEL_UPDATE_BY_CHANNEL_ID = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_CHANNEL, IDENTIFIER_TABLE_CHANNEL_COLUMN_ID, IDENTIFIER_TABLE_CHANNEL_COLUMN_NAME, IDENTIFIER_TABLE_CHANNEL_COLUMN_ID);
    // Table: File Extra
    /**
     * 1. Argument: (New) Video ID
     * <br>
     * 2. Argument: (New) File
     * <br>
     * 3. Argument: File Type
     * <br>
     * 4. Argument: Created
     * <br>
     * 5. Argument: (Old) Video ID
     * <br>
     * 6. Argument: (Old) File
     */
    public static final String QUERY_TABLE_FILE_EXTRA_UPDATE_BY_VIDEO_ID_AND_FILE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_EXTRA, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_CREATED, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE);
    // Table: File Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
    // Table: File Media
    /**
     * 1. Argument: (New) Video ID
     * <br>
     * 2. Argument: (New) File
     * <br>
     * 3. Argument: File Type
     * <br>
     * 4. Argument: Created
     * <br>
     * 5. Argument: Format
     * <br>
     * 6. Argument: Video Codec
     * <br>
     * 7. Argument: Audio Codec
     * <br>
     * 8. Argument: Width
     * <br>
     * 9. Argument: Height
     * <br>
     * 10. Argument: FPS
     * <br>
     * 11. Argument: ASR
     * <br>
     * 12. Argument: (Old) Video ID
     * <br>
     * 13. Argument: (Old) File
     */
    public static final String QUERY_TABLE_FILE_MEDIA_UPDATE_BY_VIDEO_ID_AND_FILE = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_MEDIA, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE_TYPE, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_CREATED, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FORMAT, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VCODEC, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ACODEC, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_WIDTH, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_HEIGHT, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FPS, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_ASR, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE);
    // Table: Log
    /**
     * 1. Argument: (New) Log ID
     * <br>
     * 2. Argument: Created
     * <br>
     * 3. Argument: File
     * <br>
     * 4. Argument: (Old) Log ID
     */
    public static final String QUERY_TABLE_LOG_UPDATE_BY_LOG_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_LOG, IDENTIFIER_TABLE_LOG_COLUMN_ID, IDENTIFIER_TABLE_LOG_COLUMN_CREATED, IDENTIFIER_TABLE_LOG_COLUMN_FILE, IDENTIFIER_TABLE_LOG_COLUMN_ID);
    // Table: Playlist
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
    public static final String QUERY_TABLE_PLAYLIST_UPDATE_BY_PLAYLIST_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID, IDENTIFIER_TABLE_PLAYLIST_COLUMN_TITLE, IDENTIFIER_TABLE_PLAYLIST_COLUMN_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_UPLOADER_ID, IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID);
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
    // Table: Requester
    /**
     * 1. Argument: (New) Requester ID
     * <br>
     * 2. Argument: Tag
     * <br>
     * 3. Argument: Name
     * <br>
     * 4. Argument: (Old) Requester ID
     */
    public static final String QUERY_TABLE_REQUESTER_UPDATE_BY_REQUESTER_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTER, IDENTIFIER_TABLE_REQUESTER_COLUMN_ID, IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG, IDENTIFIER_TABLE_REQUESTER_COLUMN_NAME, IDENTIFIER_TABLE_REQUESTER_COLUMN_ID);
    /**
     * 1. Argument: (New) Tag
     * <br>
     * 2. Argument: Name
     * <br>
     * 3. Argument: (Old) Tag
     */
    public static final String QUERY_TABLE_REQUESTER_UPDATE_BY_TAG = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTER, IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG, IDENTIFIER_TABLE_REQUESTER_COLUMN_NAME, IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG);
    // Table: Token
    /**
     * 1. Argument: (New) Token ID
     * <br>
     * 2. Argument: Token
     * <br>
     * 3. Argument: Level
     * <br>
     * 4. Argument: Created
     * <br>
     * 5. Argument: Expiration
     * <br>
     * 6. Argument: Times used
     * <br>
     * 7. Argument: (Old) Token ID
     */
    public static final String QUERY_TABLE_TOKEN_UPDATE_BY_TOKEN_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_ID, IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_LEVEL, IDENTIFIER_TABLE_TOKEN_COLUMN_CREATED, IDENTIFIER_TABLE_TOKEN_COLUMN_EXPIRATION, IDENTIFIER_TABLE_TOKEN_COLUMN_TIMES_USED, IDENTIFIER_TABLE_TOKEN_COLUMN_ID);
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
    public static final String QUERY_TABLE_TOKEN_UPDATE_BY_TOKEN = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_LEVEL, IDENTIFIER_TABLE_TOKEN_COLUMN_CREATED, IDENTIFIER_TABLE_TOKEN_COLUMN_EXPIRATION, IDENTIFIER_TABLE_TOKEN_COLUMN_TIMES_USED, IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN);
    /**
     * 1. Argument: Times used
     * <br>
     * 2. Argument: Token ID
     */
    public static final String QUERY_TABLE_TOKEN_UPDATE_USED_BY_TOKEN_ID = String.format("UPDATE %s SET %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_TIMES_USED, IDENTIFIER_TABLE_TOKEN_COLUMN_ID);
    /**
     * 1. Argument: Times used
     * <br>
     * 2. Argument: Token
     */
    public static final String QUERY_TABLE_TOKEN_UPDATE_USED_BY_TOKEN = String.format("UPDATE %s SET %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_TIMES_USED, IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN);
    // Table: Uploader
    /**
     * 1. Argument: (New) Uploader ID
     * <br>
     * 2. Argument: Name
     * <br>
     * 3. Argument: (Old) Uploader ID
     */
    public static final String QUERY_TABLE_UPLOADER_UPDATE_BY_UPLOADER_ID = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_UPLOADER, IDENTIFIER_TABLE_UPLOADER_COLUMN_ID, IDENTIFIER_TABLE_UPLOADER_COLUMN_NAME, IDENTIFIER_TABLE_UPLOADER_COLUMN_ID);
    // Table: Video
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
    public static final String QUERY_TABLE_VIDEO_UPDATE_BY_VIDEO_ID = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_ID, IDENTIFIER_TABLE_VIDEO_COLUMN_CHANNEL_ID, IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOADER_ID, IDENTIFIER_TABLE_VIDEO_COLUMN_TITLE, IDENTIFIER_TABLE_VIDEO_COLUMN_ALT_TITLE, IDENTIFIER_TABLE_VIDEO_COLUMN_DURATION, IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOAD_DATE, IDENTIFIER_TABLE_VIDEO_COLUMN_ID);
    // Table: Video Logs //Hmmm, there should be no need to edit an entry in this table, because it exists, or not...
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
    //
    // // Deletes
    // Table: Channel
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_CHANNEL_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_CHANNEL);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_CHANNEL_DELETE_BY_CHANNEL_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_CHANNEL, IDENTIFIER_TABLE_CHANNEL_COLUMN_ID);
    // Table: File Extra
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_FILE_EXTRA_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_FILE_EXTRA);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_FILE_EXTRA_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_EXTRA, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_FILE_EXTRA_DELETE_BY_VIDEO_ID_AND_FILE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_EXTRA, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_EXTRA_COLUMN_FILE);
    // Table: File Logs
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_FILE_LOGS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_FILE_LOGS);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_FILE_LOGS_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: File
     * <br>
     * 2. Argument: Is Media File
     */
    public static final String QUERY_TABLE_FILE_LOGS_DELETE_ALL_BY_FILE_AND_IS_MEDIA_FILE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_FILE, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_IS_MEDIA_FILE);
    /**
     * 1. Argument: Log ID
     */
    public static final String QUERY_TABLE_FILE_LOGS_DELETE_ALL_BY_LOG_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_LOG_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     * <br>
     * 3. Argument: Log ID
     * <br>
     * 4. Argument: Is Media File
     */
    public static final String QUERY_TABLE_FILE_LOGS_DELETE_BY_VIDEO_ID_AND_FILE_AND_LOG_ID_AND_IS_MEDIA_FILE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_LOGS, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_FILE, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_LOG_ID, IDENTIFIER_TABLE_FILE_LOGS_COLUMN_IS_MEDIA_FILE);
    // Table: File Media
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_FILE_MEDIA_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_FILE_MEDIA);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_FILE_MEDIA_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_FILE_MEDIA, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: File
     */
    public static final String QUERY_TABLE_FILE_MEDIA_DELETE_BY_VIDEO_ID_AND_FILE = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_FILE_MEDIA, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_FILE_MEDIA_COLUMN_FILE);
    // Table: Log
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_LOG_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_LOG);
    /**
     * 1. Argument: Log ID
     */
    public static final String QUERY_TABLE_LOG_DELETE_BY_LOG_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_LOG, IDENTIFIER_TABLE_LOG_COLUMN_ID);
    // Table: Playlist
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_PLAYLIST_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_PLAYLIST);
    /**
     * 1. Argument: Playlist ID
     */
    public static final String QUERY_TABLE_PLAYLIST_DELETE_BY_PLAYLIST_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_PLAYLIST_DELETE_ALL_BY_UPLOADER_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_PLAYLIST, IDENTIFIER_TABLE_PLAYLIST_COLUMN_UPLOADER_ID);
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
    // Table: Requester
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_REQUESTER_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_REQUESTER);
    /**
     * 1. Argument: Requester ID
     */
    public static final String QUERY_TABLE_REQUESTER_DELETE_BY_REQUESTER_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTER, IDENTIFIER_TABLE_REQUESTER_COLUMN_ID);
    /**
     * 1. Argument: Tag
     */
    public static final String QUERY_TABLE_REQUESTER_DELETE_BY_TAG = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_REQUESTER, IDENTIFIER_TABLE_REQUESTER_COLUMN_TAG);
    // Table: Token
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_TOKEN_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_TOKEN);
    /**
     * 1. Argument: Token ID
     */
    public static final String QUERY_TABLE_TOKEN_DELETE_BY_TOKEN_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_ID);
    /**
     * 1. Argument: Token
     */
    public static final String QUERY_TABLE_TOKEN_DELETE_BY_TOKEN = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_TOKEN, IDENTIFIER_TABLE_TOKEN_COLUMN_TOKEN);
    // Table: Uploader
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_UPLOADER_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_UPLOADER);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_UPLOADER_DELETE_BY_CHANNEL_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_UPLOADER, IDENTIFIER_TABLE_UPLOADER_COLUMN_ID);
    // Table: Video
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_VIDEO);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEO_DELETE_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_ID);
    /**
     * 1. Argument: Channel ID
     */
    public static final String QUERY_TABLE_VIDEO_DELETE_ALL_BY_CHANNEL_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_CHANNEL_ID);
    /**
     * 1. Argument: Uploader ID
     */
    public static final String QUERY_TABLE_VIDEO_DELETE_ALL_BY_UPLOADER_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO, IDENTIFIER_TABLE_VIDEO_COLUMN_UPLOADER_ID);
    // Table: Video Logs
    /**
     * No arguments
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_DELETE_ALL = String.format("DELETE FROM %s;", IDENTIFIER_TABLE_VIDEO_LOGS);
    /**
     * 1. Argument: Video ID
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_DELETE_ALL_BY_VIDEO_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_LOGS, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_VIDEO_ID);
    /**
     * 1. Argument: Log ID
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_DELETE_ALL_BY_LOG_ID = String.format("DELETE FROM %s WHERE %s = ?;", IDENTIFIER_TABLE_VIDEO_LOGS, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_LOG_ID);
    /**
     * 1. Argument: Video ID
     * <br>
     * 2. Argument: Log ID
     */
    public static final String QUERY_TABLE_VIDEO_LOGS_DELETE_BY_VIDEO_ID_AND_LOG_ID = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?;", IDENTIFIER_TABLE_VIDEO_LOGS, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_VIDEO_ID, IDENTIFIER_TABLE_VIDEO_LOGS_COLUMN_LOG_ID);
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
    //
    // //
    // // //
    
}
