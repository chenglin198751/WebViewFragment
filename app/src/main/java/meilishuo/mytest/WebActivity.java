package meilishuo.mytest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * weichenglin create in 17/1/10
 */
public class WebActivity extends BaseActivity implements WebViewLoadImpl {
    public static final String URL = "url";
    private RelativeLayout mWebViewRoot;
    private List<WebFragment> fragmentList = new LinkedList<WebFragment>();
    private boolean isCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_act_layout);
        mWebViewRoot = (RelativeLayout) findViewById(R.id.webview_root);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        String url = getIntent().getStringExtra(URL);
        addWebFragment(url);
    }


    private void addWebFragment(final String url) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //隐藏上一个
        if (fragmentList.size() > 0) {
            Fragment fragment = getStackTopWebFragment();
            transaction.hide(fragment);
        }

        WebFragment webFragment = new WebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        webFragment.setArguments(bundle);

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(R.id.webview_root, webFragment);
        transaction.commit();
        fragmentList.add(webFragment);
        Log.v("tag_2", "fragmentList.size() = " + fragmentList.size());
    }

    private void removeWebFragment(final Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.remove(fragment);
        fragmentList.remove(fragment);

        //remove的同时还得让上一个显示出来
        if (fragmentList.size() > 0) {
            Fragment showFragment = getStackTopWebFragment();
            transaction.show(showFragment);
        }
        transaction.commit();
    }

    /**
     * 得到栈顶的Fragment
     */
    private WebFragment getStackTopWebFragment() {
        if (fragmentList.size() > 0) {
            WebFragment showFragment = fragmentList.get(fragmentList.size() - 1);
            return showFragment;
        }
        return null;
    }


    @Override
    public void shouldOverrideUrlLoading(WebView view, String url) {
        addWebFragment(url);
    }

    @Override
    public boolean isActFirstCreate() {
        if (isCreate) {
            isCreate = false;
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (fragmentList.size() > 1) {
            WebFragment fragment = getStackTopWebFragment();

            if (fragment.getWebView().canGoBack()) {
                fragment.getWebView().goBack();
            } else {
                removeWebFragment(fragment);
            }
        } else {
            super.onBackPressed();
        }
    }

}


