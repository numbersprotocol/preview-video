package io.numbersprotocol.capturelite.plugins.previewvideo;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.util.Util;
import io.numbersprotocol.capturelite.plugins.previewvideo.databinding.FragmentPreviewBinding;
import java.io.File;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreviewFragment extends Fragment {

    private final String TAG = "PreviewFragment";

    private FragmentPreviewBinding binding;

    private SimpleExoPlayer player;
    private Boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String videoPath;
    private PreviewVideoPlugin.VideoSource videoSource;

    public PreviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PreviewFragment newInstance(String param1, String param2) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoPath = getArguments().getString(ARG_PARAM1);

            String mParam2String = getArguments().getString(ARG_PARAM2);
            if (mParam2String == "REMOTE") videoSource = PreviewVideoPlugin.VideoSource.REMOTE;
            if (mParam2String == "LOCAL") videoSource = PreviewVideoPlugin.VideoSource.LOCAL;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPreviewBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: hide system ui (optional)
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        /*
          With API Level 24 and lower, there is no guarantee of onStop being called,
          so you have to release the player as early as possible in onPause.
          With API Level 24 and higher (which brought multi- and split-window mode),
          onStop is guaranteed to be called. In the paused state, your activity is still visible,
          so you wait to release the player until onStop.
         */
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        Log.d(TAG, "initializePlayer");
        if (videoSource == PreviewVideoPlugin.VideoSource.REMOTE) {
            initializePlayerForRemoteVideo();
        }

        if (videoSource == PreviewVideoPlugin.VideoSource.LOCAL) {
            initializePlayerForLocalVideo();
        }
    }

    private void initializePlayerForRemoteVideo() {
        Log.d(TAG, "initializePlayerForRemoteVideo");
        MediaItem mediaItem = MediaItem.fromUri(videoPath);

        player = new SimpleExoPlayer.Builder(getContext()).build();
        binding.videoView.setPlayer(player);

        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
        Log.d(TAG, "initializePlayerForRemoteVideo finish");
    }

    private void initializePlayerForLocalVideo() {
        String[] split = videoPath.split("/");
        Log.d(TAG, "split: " + Arrays.toString(split));

        String rootDir = split[split.length - 2];
        String fileName = split[split.length - 1];

        String path = rootDir + "/" + fileName;
        Log.d(TAG, "path: " + path);

        File file = new File(getContext().getFilesDir(), path);
        String absolutePath = file.getAbsolutePath();
        Log.d(TAG, "absolutePath: " + absolutePath);

        MediaItem mediaItem = MediaItem.fromUri(absolutePath);

        player = new SimpleExoPlayer.Builder(getContext()).build();
        binding.videoView.setPlayer(player);
        Log.d(TAG, "videoPath: " + videoPath);
        Log.d(TAG, "mediaItem: " + mediaItem);

        player.setMediaItem(mediaItem);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
    }

    private void releasePlayer() {
        this.playbackPosition = player.getCurrentPosition();
        this.currentWindow = player.getCurrentWindowIndex();
        this.playWhenReady = player.getPlayWhenReady();
        player.release();
        player = null;
    }
}
