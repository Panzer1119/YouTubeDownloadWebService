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

package de.codemakers.youtubedownloadserver;

import de.codemakers.base.logger.LogLevel;
import de.codemakers.base.logger.Logger;
import de.codemakers.base.util.DefaultSettings;
import de.codemakers.io.file.AdvancedFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YouTubeDownloadServerApplication {
    
    private static final AdvancedFile DATABASE_FILE = new AdvancedFile("data/database.txt"); //TODO Only temporary?
    
    public static void main(String[] args) {
        Logger.getDefaultAdvancedLeveledLogger().setMinimumLogLevel(LogLevel.FINE); //TODO DEBUG Remove this?
        final DefaultSettings settings = new DefaultSettings(DATABASE_FILE);
        settings.loadSettings();
        YouTubeDownloadServer.connectToDatabase(settings.getProperty("host"), settings.getProperty("database"), settings.getProperty("username"), settings.getProperty("password").getBytes()); //FIXME Change this?
        settings.clear();
        Logger.logDebug("YouTubeDownloadServer.getDatabase()=" + YouTubeDownloadServer.getDatabase()); //TODO DEBUG Remove this
        SpringApplication.run(YouTubeDownloadServerApplication.class, args);
    }
    
}
