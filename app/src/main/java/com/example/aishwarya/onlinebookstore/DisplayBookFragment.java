package com.example.aishwarya.onlinebookstore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static com.example.aishwarya.onlinebookstore.PurchaseHistoryFragment.path;

/**
 * Created by aishwarya on 04-Apr-17.
 */

public class DisplayBookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.display_book_fragment, container, false);
        UserHomeActivity.title.setText("Display Book");

        WebView webView=new WebView(getActivity());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webView.setWebViewClient(new Callback());

        
        webView.loadUrl(
                "http://docs.google.com/gview?embedded=true&url=" + path);

        //mainView.setContentView(webView);

        return webView;
    }

}

class Callback extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(
            WebView view, String url) {
        return(false);
    }
}