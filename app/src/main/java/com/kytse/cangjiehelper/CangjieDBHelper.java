/* Copyright 2015 Tse Kit Yam
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

            SQLiteDatabase db = getReadableDatabase();
            String sql = String.format(
                    "SELECT codes.code " +
                            "FROM codes " +
                            "WHERE codes.version = 3 " +
                            "AND " +
                            "codes.char_index = " +
                            "(SELECT chars.char_index FROM chars WHERE chars.chchar = '%s')",
                    character.toString());

            Cursor cursor = db.rawQuery(sql, null);
            String inputCodeEN = null;
            StringBuilder inputCodeCN = new StringBuilder();

            if (cursor.moveToFirst()) {
                inputCodeEN = cursor.getString(0);
            }

            for (int i = 0; i < (inputCodeEN != null ? inputCodeEN.length() : 0); i++) {
                switch (inputCodeEN.charAt(i)) {
                    case 'a':
                        inputCodeCN.append("日");
                        break;
                    case 'b':
                        inputCodeCN.append("月");
                        break;
                    case 'c':
                        inputCodeCN.append("金");
                        break;
                    case 'd':
                        inputCodeCN.append("木");
                        break;
                    case 'e':
                        inputCodeCN.append("水");
                        break;
                    case 'f':
                        inputCodeCN.append("火");
                        break;
                    case 'g':
                        inputCodeCN.append("土");
                        break;
                    case 'h':
                        inputCodeCN.append("竹");
                        break;
                    case 'i':
                        inputCodeCN.append("戈");
                        break;
                    case 'j':
                        inputCodeCN.append("十");
                        break;
                    case 'k':
                        inputCodeCN.append("大");
                        break;
                    case 'l':
                        inputCodeCN.append("中");
                        break;
                    case 'm':
                        inputCodeCN.append("一");
                        break;
                    case 'n':
                        inputCodeCN.append("弓");
                        break;
                    case 'o':
                        inputCodeCN.append("人");
                        break;
                    case 'p':
                        inputCodeCN.append("心");
                        break;
                    case 'q':
                        inputCodeCN.append("手");
                        break;
                    case 'r':
                        inputCodeCN.append("口");
                        break;
                    case 's':
                        inputCodeCN.append("尸");
                        break;
                    case 't':
                        inputCodeCN.append("廿");
                        break;
                    case 'u':
                        inputCodeCN.append("山");
                        break;
                    case 'v':
                        inputCodeCN.append("女");
                        break;
                    case 'w':
                        inputCodeCN.append("田");
                        break;
                    case 'x':
                        inputCodeCN.append("難");
                        break;
                    case 'y':
                        inputCodeCN.append("卜");
                        break;
                    default:
                        break;
                }
            }

            mCangjieMap.put(character, inputCodeCN.toString());

            cursor.close();
            return inputCodeCN.toString();
        }
    }
}
