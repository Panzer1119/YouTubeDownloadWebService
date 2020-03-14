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
import de.codemakers.download.database.entities.AbstractFile;

public class ExtraFile extends AbstractFile<ExtraFile, YouTubeDatabase> {
    
    public ExtraFile() {
        super();
    }
    
    public ExtraFile(String videoId, String file, String fileType) {
        super(videoId, file, fileType);
    }
    
    @Override
    protected ExtraFile getFromDatabase() {
        return (ExtraFile) useDatabaseOrNull((database) -> database.getExtraFileByVideoIdAndFile(getVideoId(), getFile()));
    }
    
    @Override
    public boolean save() {
        return useDatabaseOrFalse((database) -> database.setExtraFileByVideoIdAndFile(this, getVideoId(), getFile()));
    }
    
    @Override
    public void set(ExtraFile extraFile) {
        if (extraFile == null) {
            //TODO Maybe just set every value in this object to null?
            return;
        }
        setVideoId(extraFile.getVideoId());
        setFile(extraFile.getFile());
        setFileType(extraFile.getFileType());
    }
    
    @Override
    public String toString() {
        return "ExtraFile{" + "videoId='" + videoId + '\'' + ", file='" + file + '\'' + ", fileType='" + fileType + '\'' + '}';
    }
    
}
