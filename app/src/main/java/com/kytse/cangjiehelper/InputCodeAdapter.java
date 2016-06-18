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

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InputCodeAdapter extends BaseAdapter {
    CangjieDBHelper mHelper;

    private ArrayList<Character> mCharacters;
    private LayoutInflater mLayoutInflater;

    public InputCodeAdapter(Activity activity, ArrayList<Character> characters) {
        mLayoutInflater = activity.getLayoutInflater();
        mCharacters = characters;

        mHelper = new CangjieDBHelper(activity);
    }

    @Override
    public int getCount() {
        return mCharacters.size();
    }

    @Override
    public Object getItem(int position) {
        return mCharacters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_input_code, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewInput = (TextView) convertView.findViewById(R.id.textView_input);
            viewHolder.textViewCode = (TextView) convertView.findViewById(R.id.textView_code);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.textViewCode.setText(mHelper.getInputCode(mCharacters.get(position)));
            viewHolder.textViewInput.setText(mCharacters.get(position).toString());

        return convertView;
    }

    private class ViewHolder {
        TextView textViewInput;
        TextView textViewCode;
    }
}
