package io.numbersprotocol.capturelite.plugins.previewvideo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.util.ArrayList;
import java.util.List;

@CapacitorPlugin(name = "PreviewVideo")
public class PreviewVideoPlugin extends Plugin {

    public enum VideoSource {
        REMOTE,
        LOCAL
    }

    private PreviewVideo implementation = new PreviewVideo();

    private static String TAG = "PreviewVideo";
    private int containerViewId = 99;
    private PreviewFragment previewFragment;
    private List<PreviewFragment> previewFragments = new ArrayList();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void playFullScreenFromRemote(final PluginCall call) {
        bridge
            .getActivity()
            .runOnUiThread(
                () -> {
                    // final String url = call.getObject("src").getString("changingThisBreaksApplicationSecurity");
                    final String url = call.getString("url");

                    Log.d(TAG, "previewVideo: " + url);

                    if (url == null || url == "undefined") return;

                    FrameLayout containerView = getBridge().getActivity().findViewById(containerViewId);
                    if (containerView == null) {
                        containerView = new FrameLayout(getActivity().getApplicationContext());
                        containerView.setId(containerViewId);
                        ((ViewGroup) getBridge().getWebView().getParent().getParent()).addView(containerView);
                    }

                    getBridge().getWebView().getParent().bringChildToFront(containerView);

                    FragmentManager fragmentManager = getBridge().getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    for (PreviewFragment fragment : previewFragments) {
                        fragmentTransaction.remove(fragment);
                    }
                    previewFragments = new ArrayList<>();

                    previewFragment = PreviewFragment.newInstance(url, "REMOTE");
                    previewFragments.add(previewFragment);

                    fragmentTransaction.add(containerView.getId(), previewFragment);
                    fragmentTransaction.commit();

                    call.resolve();
                }
            );
    }

    @PluginMethod
    public void playFullScreenFromLocal(final PluginCall call) {
        bridge
            .getActivity()
            .runOnUiThread(
                () -> {
                    // final String url = call.getObject("src").getString("changingThisBreaksApplicationSecurity");
                    final String path = call.getString("path");

                    Log.d(TAG, "previewVideo: " + path);

                    if (path == null || path == "undefined") return;

                    FrameLayout containerView = getBridge().getActivity().findViewById(containerViewId);
                    if (containerView == null) {
                        containerView = new FrameLayout(getActivity().getApplicationContext());
                        containerView.setId(containerViewId);
                        ((ViewGroup) getBridge().getWebView().getParent().getParent()).addView(containerView);
                    }

                    getBridge().getWebView().getParent().bringChildToFront(containerView);

                    FragmentManager fragmentManager = getBridge().getActivity().getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    for (PreviewFragment fragment : previewFragments) {
                        fragmentTransaction.remove(fragment);
                    }
                    previewFragments = new ArrayList<>();

                    previewFragment = PreviewFragment.newInstance(path, "LOCAL");
                    previewFragments.add(previewFragment);

                    fragmentTransaction.add(containerView.getId(), previewFragment);
                    fragmentTransaction.commit();

                    call.resolve();
                }
            );
    }

    @PluginMethod
    public void stopFullScreen(final PluginCall call) {
        bridge
            .getActivity()
            .runOnUiThread(
                () -> {
                    FrameLayout containerView = getBridge().getActivity().findViewById(containerViewId);

                    if (containerView != null) {
                        ((ViewGroup) getBridge().getWebView().getParent()).removeView(containerView);

                        FragmentManager fragmentManager = getActivity().getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        for (PreviewFragment fragment : previewFragments) {
                            fragmentTransaction.remove(fragment);
                        }

                        previewFragments.clear();
                        fragmentTransaction.commit();
                    }

                    call.resolve();
                }
            );
    }
}
