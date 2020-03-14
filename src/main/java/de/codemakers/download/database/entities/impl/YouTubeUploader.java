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
import de.codemakers.download.database.entities.AbstractUploader;

public class YouTubeUploader extends AbstractUploader<YouTubeUploader, YouTubeDatabase, YouTubePlaylist, YouTubeVideo> {
    
    public YouTubeUploader() {
        super();
    }
    
    public YouTubeUploader(String uploaderId, String name) {
        super(uploaderId, name);
    }
    
    @Override
    public void set(YouTubeUploader youTubeUploader) {
        if (youTubeUploader == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setUploaderId(youTubeUploader.getUploaderId());
        setName(youTubeUploader.getName());
    }
    
    @Override
    public String toString() {
        return "YouTubeUploader{" + "uploaderId='" + uploaderId + '\'' + ", name='" + name + '\'' + '}';
    }
    
}
