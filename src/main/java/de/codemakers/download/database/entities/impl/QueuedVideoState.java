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

package de.codemakers.download.database.entities.impl;

public enum QueuedVideoState {
    /**
     * Unknown state
     */
    UNKNOWN,
    /**
     * Waiting to be started
     */
    QUEUED,
    /**
     * Something has failed (take a look at the "temp" column, maybe there's the error message)
     */
    ERRORED,
    /**
     * Task is being worked on
     * <br>
     * The VideoInstanceInfo has been downloaded and saved as JsonObject in the column "temp" //TODO Do we need this? The program could just save the VideoInstanceInfo object while running the download? If the download errors, then the information is unnecessary, because it could change the next time this task is being requeued
     */
    STARTED,
    /**
     * The video is being downloaded/processed
     */
    RUNNING,
    /**
     * Task has been finished
     */
    FINISHED;
    
    public static QueuedVideoState ofState(String state) {
        if (state == null || state.isEmpty()) {
            return UNKNOWN;
        }
        for (QueuedVideoState state_ : values()) {
            if (state.equalsIgnoreCase(state_.name())) {
                return state_;
            }
        }
        return UNKNOWN;
    }
    
}
