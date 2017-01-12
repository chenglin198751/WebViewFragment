package meilishuo.mytest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import java.util.LinkedList;

/**
 * weichenglin create in 17/1/10
 */
public class WebActivity extends BaseActivity implements WebViewLoadImpl {
    RelativeLayout mWebViewRoot;
    LinkedList<WebFragment> fragmentList = new LinkedList<WebFragment>();
    boolean isCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_act_layout);

        mWebViewRoot = (RelativeLayout) findViewById(R.id.webview_root);
        String url = getIntent().getStringExtra("url");
        addWebFragment(url);
    }


    private void addWebFragment(String url) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        //全部隐藏
        if (fragmentList.size() > 0) {
            for (int i = 0; i < fragmentList.size(); i++) {
                Fragment fragment = fragmentList.get(i);
                transaction.hide(fragment);
            }
        }

        WebFragment webFragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        webFragment.setArguments(bundle);

        transaction.add(R.id.webview_root, webFragment);

        transaction.commit();
        fragmentList.add(webFragment);

        Log.v("tag_2", "fragmentList.size() = " + fragmentList.size());
    }

    private void removeWebFragment(final Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }



    @Override
    public void shouldOverrideUrlLoading(WebView view, String url) {
        addWebFragment(url);
    }

    @Override
    public boolean isActFirstCreate() {
        if (isCreate){
            isCreate = false;
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fragmentList.size() > 1) {
            WebFragment fragment = fragmentList.get(fragmentList.size() - 1);

            if (fragment.getWebView().canGoBack()){
                fragment.getWebView().goBack();
            }else {
                removeWebFragment(fragment);
                fragmentList.remove(fragment);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment showFragment = fragmentList.get(fragmentList.size() - 1);
                transaction.show(showFragment);
                transaction.commit();
            }
        } else {
            super.onBackPressed();
        }
    }

}


