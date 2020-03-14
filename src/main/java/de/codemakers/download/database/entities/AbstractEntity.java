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

package de.codemakers.download.database.entities;

import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.download.database.AbstractDatabase;

public abstract class AbstractEntity<T extends AbstractEntity, D extends AbstractDatabase> {
    
    private D database = null;
    
    public D getDatabase() {
        return database;
    }
    
    public T setDatabase(D database) {
        this.database = database;
        return (T) this;
    }
    
    public boolean reload() {
        final T t = getFromDatabase();
        if (t == null) {
            return false;
        }
        set(t);
        return true;
    }
    
    protected abstract T getFromDatabase();
    
    public abstract boolean save();
    
    public abstract void set(T t);
    
    protected <R> R useDatabaseOrNull(ToughFunction<D, R> databaseFunction) {
        return useDatabase(databaseFunction, null);
    }
    
    protected boolean useDatabaseOrFalse(ToughFunction<D, Boolean> databaseFunction) {
        return useDatabase(databaseFunction, false);
    }
    
    protected <R> R useDatabase(ToughFunction<D, R> databaseFunction, R defaultValue) {
        final D database = getDatabase();
        if (database == null) {
            return defaultValue;
        }
        return databaseFunction.applyWithoutException(database);
    }
    
}
