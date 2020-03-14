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

package de.codemakers.download.database.entities.impl;

import de.codemakers.download.database.YouTubeDatabase;
import de.codemakers.download.database.entities.AbstractPlaylist;

public class YouTubePlaylist extends AbstractPlaylist<YouTubePlaylist, MediaFile, ExtraFile, YouTubeDatabase, YouTubeVideo> {
    
    public YouTubePlaylist() {
        super();
    }
    
    public YouTubePlaylist(String playlistId, String title, String playlist, String uploaderId) {
        super(playlistId, title, playlist, uploaderId);
    }
    
    @Override
    public void set(YouTubePlaylist youTubePlaylist) {
        if (youTubePlaylist == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setPlaylistId(youTubePlaylist.getPlaylistId());
        setTitle(youTubePlaylist.getTitle());
        setPlaylist(youTubePlaylist.getPlaylist());
        setUploaderId(youTubePlaylist.getUploaderId());
    }
    
    @Override
    public String toString() {
        return "YouTubePlaylist{" + "playlistId='" + playlistId + '\'' + ", title='" + title + '\'' + ", playlist='" + playlist + '\'' + ", uploaderId='" + uploaderId + '\'' + '}';
    }
    
}
