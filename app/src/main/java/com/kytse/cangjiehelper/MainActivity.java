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

import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements TextWatcher {
    private InputCodeAdapter mAdapter;
    private ArrayList<Character> mCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCharacters = new ArrayList<>();
        mAdapter = new InputCodeAdapter(MainActivity.this, mCharacters);

        EditText editTextSearch = (EditText) findViewById(R.id.editText_search);
        editTextSearch.addTextChangedListener(this);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_legal) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            String url = "https://tsekityam.github.io/Cangjie-Helper-for-Android/legal.html";
            customTabsIntent.launchUrl(this, Uri.parse(url));
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void showInputCode(String input) {
        mCharacters.clear();
        for (int i = 0; i < input.length(); i++) {
            mCharacters.add(input.charAt(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        showInputCode(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
