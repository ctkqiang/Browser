package uk.co.johnmelodyme.browser.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uk.co.johnmelodyme.browser.R;
import uk.co.johnmelodyme.browser.constant.Constants;
import uk.co.johnmelodyme.browser.constant.LogLevel;
import uk.co.johnmelodyme.browser.functions.Functions;

public class WebViewActivity extends AppCompatActivity
{
    public LogLevel LEVEL = LogLevel.DEBUG;
    public EditText query;
    public Button search;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public void render_user_component(Bundle bundle)
    {
        Functions.log_output("render_user_component/1", 0, LEVEL);

        query = (EditText) findViewById(R.id.search);
        search = (Button) findViewById(R.id.button_search);

        query.setHint("Enter Search");
        search.setText("Search ");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Render User Component */
        render_user_component(savedInstanceState);

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (TextUtils.isEmpty(query.getText()))
                {
                    query.setError("This Field Should Not Be Empty!");
                }
                else if (query.getText().toString().contains("pornhub"))
                {
                    Functions.show_snack_bar(v, "Wow What Man Of Culture");

                    Functions.parse_url(
                            Constants.SEARCH_ENGINE_QUERY + query.getText().toString(),
                            WebViewActivity.this,
                            BrowserWebViewActivity.class
                    );

                    finish();
                }
                else
                {
                    Functions.parse_url(
                            Constants.SEARCH_ENGINE_QUERY + query.getText().toString(),
                            WebViewActivity.this,
                            BrowserWebViewActivity.class
                    );

                    finish();
                }
            }
        });

        findViewById(R.id.copyrighttextView).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Functions.parse_url(
                        Constants.DEV_PROFILE_URL,
                        WebViewActivity.this,
                        BrowserWebViewActivity.class
                );
            }
        });
    }

}