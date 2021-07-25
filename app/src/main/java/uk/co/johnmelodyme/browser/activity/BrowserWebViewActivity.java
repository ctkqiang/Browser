package uk.co.johnmelodyme.browser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

import uk.co.johnmelodyme.browser.R;
import uk.co.johnmelodyme.browser.constant.LogLevel;
import uk.co.johnmelodyme.browser.functions.Functions;

/**
 * @author John Melody Me <johnmelody@dingtalk.com>
 * <p>
 * This file is part of Malaysian Sign Language.
 * <p>
 * Malaysian Sign Language is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Malaysian Sign Language is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Malaysian Sign Language.  If not, see <https://www.gnu.org/licenses/>.
 */
public class BrowserWebViewActivity extends AppCompatActivity
{

    public static final LogLevel LOG_LEVEL = LogLevel.DEBUG;
    public ProgressBar progressBar;
    public WebView webView;


    /**
     * @param bundle required for user interface rendering at
     *               beginning of the Instance.
     */
    public void render_user_interface_components(Bundle bundle)
    {
        Functions.log_output("render_user_interface_components/1", 0, LOG_LEVEL);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                set_progress_bar_visibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);
                set_progress_bar_visibility(View.GONE);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error)
            {
                super.onReceivedError(view, request, error);
                set_progress_bar_visibility(View.GONE);
            }
        });

        /* Render WebView */
        Functions.open_url_in_app(this, bundle, webView);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_web_view);

        /* Render User Interface Components */
        render_user_interface_components(savedInstanceState);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();


        TextView _url = (TextView) view.findViewById(R.id.url);
        _url.setText(Functions.getUrl(this, savedInstanceState));
    }

    private void set_progress_bar_visibility(int visibility)
    {
        if (progressBar != null)
        {
            Functions.log_output("set_progress_bar_visibility/1", 0, LOG_LEVEL);

            progressBar.setVisibility(visibility);
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    /**
     * Called when the activity has detected the user's press of the back
     * key. The {@link #getOnBackPressedDispatcher() OnBackPressedDispatcher} will be given a
     * chance to handle the back button before the default behavior of
     * {@link Activity #onBackPressed()} is invoked.
     *
     * @see #getOnBackPressedDispatcher()
     */
    @Override
    public void onBackPressed()
    {
        webView.goBack();
        super.onBackPressed();
        this.deleteDatabase("webview.db");
        this.deleteDatabase("webviewCache.db");
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        this.deleteDatabase("webview.db");
        this.deleteDatabase("webviewCache.db");

        webView.clearCache(true);
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        WebStorage.getInstance().deleteAllData();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
        {
            webView.goBack();
            return true;
        }
        else
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     *
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     *
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.refresh:
            {
                webView.reload();
                break;
            }

            default:
            {
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}