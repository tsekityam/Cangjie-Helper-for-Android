/*
 * Copyright 2016 Tse Kit Yam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kytse.cangjiehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.HashMap;

public class CangjieDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "cangjie.db";
    private static final int DATABASE_VERSION = 1;

    private HashMap<Character, String> mCangjieMap;

    public CangjieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mCangjieMap = new HashMap<>();
    }

    public String getInputCode(Character character) {
        if (mCangjieMap.containsKey(character)) {
            return mCangjieMap.get(character);
        } else {
            try {
                SQLiteDatabase db = getReadableDatabase();

                // get char index of the given char
                Cursor charIndexCursor = db.query(
                        "chars",
                        new String[]{"char_index"},
                        String.format("chchar = '%s'", character),
                        null, null, null, null);
                String char_index = charIndexCursor.moveToFirst() ? charIndexCursor.getString(0) : "";
                charIndexCursor.close();

                // return empty string if the char is not found in the table
                if (char_index.isEmpty()) {
                    return "";
                }

                // get the first cangjie input code with the given char index
                Cursor codeCursor = db.query(
                        "codes",
                        new String[]{"code"},
                        String.format("char_index = '%s'", char_index),
                        null, null, null, null);
                String inputCodeEN = codeCursor.moveToFirst() ? codeCursor.getString(0) : null;
                codeCursor.close();

                // return null of no input code is found
                if (inputCodeEN == null) {
                    return "";
                }

                String inputCodeCN = getCNInputCodeFromEN(inputCodeEN);

                mCangjieMap.put(character, inputCodeCN);
                return mCangjieMap.get(character);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }

            return "";
        }
    }

    @NonNull
    private String getCNInputCodeFromEN(String inputCodeEN) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputCodeEN.length(); i++) {
            switch (inputCodeEN.charAt(i)) {
                case 'a':
                    stringBuilder.append("日");
                    break;
                case 'b':
                    stringBuilder.append("月");
                    break;
                case 'c':
                    stringBuilder.append("金");
                    break;
                case 'd':
                    stringBuilder.append("木");
                    break;
                case 'e':
                    stringBuilder.append("水");
                    break;
                case 'f':
                    stringBuilder.append("火");
                    break;
                case 'g':
                    stringBuilder.append("土");
                    break;
                case 'h':
                    stringBuilder.append("竹");
                    break;
                case 'i':
                    stringBuilder.append("戈");
                    break;
                case 'j':
                    stringBuilder.append("十");
                    break;
                case 'k':
                    stringBuilder.append("大");
                    break;
                case 'l':
                    stringBuilder.append("中");
                    break;
                case 'm':
                    stringBuilder.append("一");
                    break;
                case 'n':
                    stringBuilder.append("弓");
                    break;
                case 'o':
                    stringBuilder.append("人");
                    break;
                case 'p':
                    stringBuilder.append("心");
                    break;
                case 'q':
                    stringBuilder.append("手");
                    break;
                case 'r':
                    stringBuilder.append("口");
                    break;
                case 's':
                    stringBuilder.append("尸");
                    break;
                case 't':
                    stringBuilder.append("廿");
                    break;
                case 'u':
                    stringBuilder.append("山");
                    break;
                case 'v':
                    stringBuilder.append("女");
                    break;
                case 'w':
                    stringBuilder.append("田");
                    break;
                case 'x':
                    stringBuilder.append("難");
                    break;
                case 'y':
                    stringBuilder.append("卜");
                    break;
                default:
                    break;
            }
        }

        return stringBuilder.toString();
    }
}
