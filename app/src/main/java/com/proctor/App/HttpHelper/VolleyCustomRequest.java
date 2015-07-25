package com.proctor.App.HttpHelper;

    import com.android.volley.Request;
    import com.android.volley.Response;
    import com.android.volley.AuthFailureError;
    import com.android.volley.NetworkResponse;
    import com.android.volley.ParseError;
    import com.android.volley.toolbox.HttpHeaderParser;
    import org.apache.http.message.BasicNameValuePair;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.UnsupportedEncodingException;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    /**
     * Custom Request Class for JSON
     */
    public class VolleyCustomRequest extends Request<JSONObject> {

        private List<BasicNameValuePair> params; // the request params
        private Response.Listener<JSONObject> listener; // the response listener

        public VolleyCustomRequest(int requestMethod, String url, List params,
                             Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {

            super(requestMethod, url, errorListener); // Call parent constructor
            this.params = params;
            this.listener = responseListener;
        }

        // We HAVE TO implement this function
        @Override
        protected void deliverResponse(JSONObject response) {
            listener.onResponse(response); // Call response listener
        }

        // Proper parameter behavior
        @Override
        public Map <String, String> getParams() throws AuthFailureError {
            Map <String, String> map = new HashMap <String, String>();

            // Iterate through the params and add them to our HashMap
            for (BasicNameValuePair pair: params) {
                map.put(pair.getName(), pair.getValue());
            }

            return map;
        }

        // Same as JsonObjectRequest#parseNetworkResponse
        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

    }