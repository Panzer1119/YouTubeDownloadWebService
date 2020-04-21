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

package de.codemakers.download.database.entities;

import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.download.database.AbstractDatabase;

public interface DatabaseEntity<T extends DatabaseEntity, D extends AbstractDatabase> {
    
    T setDatabase(D database);
    
    D getDatabase();
    
    default boolean reload() {
        final T t = loadFromDatabase();
        if (t == null) {
            return false;
        }
        set(t);
        return true;
    }
    
    T loadFromDatabase();
    
    boolean save();
    
    void set(T t);
    
    default <R> R useDatabaseOrNull(ToughFunction<D, R> databaseFunction) {
        return useDatabase(databaseFunction, null);
    }
    
    default boolean useDatabaseOrFalse(ToughFunction<D, Boolean> databaseFunction) {
        return useDatabase(databaseFunction, false);
    }
    
    default <R> R useDatabase(ToughFunction<D, R> databaseFunction, R defaultValue) {
        final D database = getDatabase();
        if (database == null) {
            return defaultValue;
        }
        return databaseFunction.applyWithoutException(database);
    }
    
}
