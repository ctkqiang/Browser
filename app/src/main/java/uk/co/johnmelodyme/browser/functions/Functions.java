package uk.co.johnmelodyme.browser.functions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import uk.co.johnmelodyme.browser.constant.Constants;
import uk.co.johnmelodyme.browser.constant.LogLevel;

public class Functions extends Constants
{
    public static String TAG = APP_NAME;

    public static void log_output(@NonNull String string, int _status_, LogLevel logLevel)
    {
        switch (logLevel)
        {
            case DEBUG:
            case INFO:
            case ERROR:
            case VERBOSE:
            case WARN:
            {
                if (_status_ == 0)
                {
                    Log.d(TAG, logLevel.toString() + " {:ok " + string + "}");
                }
                else
                {
                    Log.d(TAG, logLevel.toString() + " {:error " + string + "}");
                }
                break;
            }
            default:
            {
                Log.d(TAG, " {:unknown " + string + "}");
                break;
            }
        }

    }

    /**
     * @param view    Get User's Current View Components
     * @param message User Custom input value
     *                #Required
     */
    public static void show_snack_bar(View view, String message)
    {
        log_output("show_snack_bar/2", 0, LogLevel.DEBUG);

        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * @param context   required Context for register
     * @param classname required for routing {@link #route_to(Context, Class)}
     */
    public static void route_to(Context context, Class<?> classname)
    {
        log_output("route_to/2, to:" + classname.getSimpleName(), 0, LogLevel.DEBUG);

        Intent navigation = new Intent(context, classname);
        context.startActivity(navigation);
    }


    /**
     * @param activity The Current instance of the activity for opening url in app.
     * @param webView  Link to the webview activity, required for resource
     */
    @SuppressLint("SetJavaScriptEnabled")
    public static void open_url_in_app(@NonNull AppCompatActivity activity, Bundle bundle,
                                       WebView webView)
    {
        String url;

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        bundle = activity.getIntent().getExtras();
        url = bundle.getString("url");

        log_output("open_url_in_app/3", 0, LogLevel.DEBUG);

        webView.animate();
        webView.loadUrl(url);
    }

    public static String getUrl( AppCompatActivity activity, Bundle bundle)
    {
        String url;

        bundle = activity.getIntent().getExtras();
        url = bundle.getString("url");

        return url;
    }

    public static void parse_url(@NonNull String url, Context context, @NonNull Class<?> classname)
    {
        Intent intent = new Intent(context, classname);
        intent.putExtra("url", url);
        context.startActivity(intent);

        log_output("parse_url/3", 0, LogLevel.DEBUG);
    }
}
