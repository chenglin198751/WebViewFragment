package meilishuo.mytest;

import android.webkit.WebView;

/**
 * weichenglin create in 16/12/23
 */
public interface WebViewLoadImpl {
    void shouldOverrideUrlLoading(WebView view, String url);

    boolean isActFirstCreate();
}
