/*
Copyright [2016] [name of copyright owner <-Put your name]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 * Android SDK for UI/UX Authorig Tool Analytics
 * @Author: Jamil Hussain
 *  This is the extension of PIWIK android SDK @link https://github.com/piwik/piwik-android-sdk for sending data to custom Analytics engine developed in Django
*/

package org.uclab.mm.sl.uiux.analytics.dispatcher;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.uclab.mm.sl.uiux.analytics.UIUXAnalytics;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import timber.log.Timber;


/**
 * The type Tracker bulk url wrapper.
 */
public class TrackerBulkURLWrapper {
    private static final String LOGGER_TAG = UIUXAnalytics.LOGGER_PREFIX + "TrackerBulkURLWrapper";
    private static final int EVENTS_PER_PAGE = 20;
    private int mCurrentPage = 0;
    private final int mPages;
    private final URL mApiUrl;
    private final String mAuthtoken;
    private final List<String> mEvents;

    /**
     * Instantiates a new Tracker bulk url wrapper.
     *
     * @param apiUrl    the api url
     * @param events    the events
     * @param authToken the auth token
     */
    public TrackerBulkURLWrapper(@NonNull final URL apiUrl, @NonNull final List<String> events, @Nullable final String authToken) {
        mApiUrl = apiUrl;
        mAuthtoken = authToken;
        mPages = (int) Math.ceil(events.size() * 1.0 / EVENTS_PER_PAGE);
        mEvents = events;
    }

    /**
     * Gets events per page.
     *
     * @return the events per page
     */
    protected static int getEventsPerPage() {
        return EVENTS_PER_PAGE;
    }

    /**
     * page iterator
     *
     * @return iterator iterator
     */
    public Iterator<Page> iterator() {
        return new Iterator<Page>() {
            @Override
            public boolean hasNext() {
                return mCurrentPage < mPages;
            }

            @Override
            public Page next() {
                if (hasNext()) {
                    return new Page(mCurrentPage++);
                }
                return null;
            }

            @Override
            public void remove() {
            }
        };
    }

    /**
     * Gets api url.
     *
     * @return the api url
     */
    @NonNull
    public URL getApiUrl() {
        return mApiUrl;
    }

    /**
     * {
     * "requests": ["?idsite=1&url=http://example.org&action_name=Test bulk log Pageview&rec=1",
     * "?idsite=1&url=http://example.net/test.htm&action_name=Another bul k page view&rec=1"],
     * "token_auth": "33dc3f2536d3025974cccb4b4d2d98f4"
     * }
     *
     * @param page the page
     * @return json object
     */
    @Nullable
    public JSONObject getEvents(Page page) {
        if (page == null || page.isEmpty()) {
            return null;
        }

        List<String> pageElements = mEvents.subList(page.fromIndex, page.toIndex);

        Timber.tag(LOGGER_TAG).w("Page element: "+ pageElements );

        if (pageElements.size() == 0) {
            Timber.tag(LOGGER_TAG).w("Empty page");
            return null;
        }

        JSONObject params = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();
            for(String jsonString : pageElements){
                jArray.put(new JSONObject(jsonString));
            }
            params.accumulate("logs", jArray);


        } catch (JSONException e) {
            Timber.tag(LOGGER_TAG).w(e, "Cannot create json object:\n%s", TextUtils.join(", ", pageElements));
            return null;
        }
        return params;
    }

    /**
     * Gets event url.
     *
     * @param page Page object
     * @return tracked url. For example "http://domain.com/piwik.php?idsite=1&url=http://a.org&action_name=Test bulk log Pageview&rec=1"
     */
    @Nullable
    public URL getEventUrl(Page page) {
        if (page == null || page.isEmpty())
            return null;

        try {
            return new URL(getApiUrl().toString() + mEvents.get(page.fromIndex));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The type Page.
     */
    public final class Page {

        /**
         * The From index.
         */
        protected final int fromIndex, /**
         * The To index.
         */
        toIndex;

        /**
         * Instantiates a new Page.
         *
         * @param pageNumber the page number
         */
        protected Page(int pageNumber) {
            if (!(pageNumber >= 0 || pageNumber < mPages)) {
                fromIndex = toIndex = -1;
                return;
            }
            fromIndex = pageNumber * EVENTS_PER_PAGE;
            toIndex = Math.min(fromIndex + EVENTS_PER_PAGE, mEvents.size());
        }

        /**
         * Elements count int.
         *
         * @return the int
         */
        public int elementsCount() {
            return toIndex - fromIndex;
        }

        /**
         * Is empty boolean.
         *
         * @return the boolean
         */
        public boolean isEmpty() {
            return fromIndex == -1 || elementsCount() == 0;
        }
    }

}
