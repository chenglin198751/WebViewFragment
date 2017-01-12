package meilishuo.mytest;

import android.webkit.WebView;

/**
 * weichenglin create in 16/12/23
 */
public interface WebViewLoadImpl {
    void shouldOverrideUrlLoading(WebView view, String url);

    /**
     * 判断Activity第一次被初始化
     */
    boolean isActFirstCreate();
}
