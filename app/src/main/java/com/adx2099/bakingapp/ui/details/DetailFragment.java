package com.adx2099.bakingapp.ui.details;


import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.databinding.FragmentDetailBinding;
import com.adx2099.bakingapp.models.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import static com.adx2099.bakingapp.helper.BakingConstants.DETAIL_FRAG;
import static com.adx2099.bakingapp.helper.BakingConstants.INGR_FRAG;
import static com.adx2099.bakingapp.helper.BakingConstants.LAYOUT;
import static com.adx2099.bakingapp.helper.BakingConstants.LIST_STEP_KEY;
import static com.adx2099.bakingapp.helper.BakingConstants.RECIPE_DATA_KEY;
import static com.adx2099.bakingapp.helper.BakingConstants.STEP_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements View.OnClickListener, ExoPlayer.EventListener {
    private int lay;
    private SimpleExoPlayer mExoPlayer;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private static final String TAG = DetailFragment.class.getSimpleName();
    private Steps mStep;

    private  String videoUrl;
    private DataSource.Factory mMediaDataSourceFactory;
    private FragmentDetailBinding fragmentDetailBinding;
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private ConstraintLayout constraintLayout;
    private List<Steps> mList;
    private int stepPosition;
    private boolean NAVIGATION_PATH = false;
    private static final String STATE_CALLBACK = "instanceState";
    private static final String STATE_VIDEO_CALLBACK = "videoCallback";
    public static final String EXO_PLAYER_VIDEO_POSITION = "exo-player-video-position";
    public static final String EXO_PLAYER_PLAYING_STATE = "exo-player-playing-state";
    private static long mVideoPosition;
    private static boolean mShouldAutoPlay;
    private Dialog mFullScreenDialog;
    private SimpleExoPlayerView mExoPlayerView;
    private ImageView fullScreenIcon;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        lay = bundle.getInt(LAYOUT);
        mStep = bundle.getParcelable(STEP_KEY);
        mList = bundle.getParcelableArrayList(LIST_STEP_KEY);
        if (savedInstanceState == null) {
            mShouldAutoPlay = true;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        fragmentDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        fragmentDetailBinding.prev.setOnClickListener(this);
        fragmentDetailBinding.next.setOnClickListener(this);
        initializeMediaSession();
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(STATE_CALLBACK) || savedInstanceState.containsKey(STATE_VIDEO_CALLBACK)){
                fragmentDetailBinding.stepInstruction.setText(savedInstanceState.getString(STATE_CALLBACK));
                videoUrl = savedInstanceState.getString(STATE_VIDEO_CALLBACK);
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    initializePlayer();
                    openFullscreenDialog(mExoPlayerView);
                } else {
                    initializePlayer();
                }
            }
        }else{
            setUpViews();
            initializePlayer();
        }

        return fragmentDetailBinding.getRoot();
    }

    private void setUpViews() {
        if(NAVIGATION_PATH){
            videoUrl = mList.get(stepPosition).videoURL;
            fragmentDetailBinding.stepInstruction.setText(mList.get(stepPosition).description);
        }else{
            videoUrl = mStep.videoURL;
            fragmentDetailBinding.stepInstruction.setText(mStep.description);
        }

        if(mStep.stepId == 0 ){
            fragmentDetailBinding.prev.setVisibility(View.GONE);
        }else if(mStep.stepId == mList.size()-1){
            fragmentDetailBinding.next.setVisibility(View.GONE);
        }
    }


    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(App.getCurrentActivity(), TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);

        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prev:
                NAVIGATION_PATH = true;
                reloadThisFragment(searchWhichStepToGo("prev"));
                releasePlayer();
                break;
            case R.id.next:
                NAVIGATION_PATH = true;
                reloadThisFragment(searchWhichStepToGo("next"));
                releasePlayer();
                break;

        }
    }

    private void reloadThisFragment(int step) {
        stepPosition = step;
        Fragment frg = getFragmentManager().findFragmentByTag(DETAIL_FRAG);
        FragmentTransaction frgTrans = getFragmentManager().beginTransaction();
        frgTrans.detach(frg);
        frgTrans.attach(frg);
        frgTrans.commit();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }



    @Override
    public void onDetach() {
        super.onDetach();

    }

    private int searchWhichStepToGo(String type) {
        int stepToGo = 0;
        switch (type){
            case "prev":
                stepToGo = --mStep.stepId;
                return stepToGo;
            case "next":
                stepToGo = ++mStep.stepId;
                return stepToGo;
        }
        return stepToGo;
    }




    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    private void openFullscreenDialog(SimpleExoPlayerView myExoPlayerView){

        Dialog myFullScreenDialog = initFullscreenDialog();
        mFullScreenDialog = myFullScreenDialog;
        ((ViewGroup) fragmentDetailBinding.playerView.getParent()).removeView(myExoPlayerView);

        myFullScreenDialog.addContentView(myExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenIcon =  myExoPlayerView.findViewById(R.id.exo_fullscreen_icon);
        fullScreenIcon.setBackgroundResource(R.drawable.ic_fullscreen_skrink);
        mExoPlayerFullscreen = true;
        myFullScreenDialog.show();

    }

    private Dialog initFullscreenDialog() {
        Dialog mFullScreenDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {

            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };

        return mFullScreenDialog;
    }

    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) fragmentDetailBinding.mainMediaFrame).addView(mExoPlayerView);

        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        fullScreenIcon.setBackgroundResource(R.drawable.ic_fullscreen_expand);

    }

    private void initializePlayer() {

        if (mExoPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);

            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(App.getCurrentActivity());
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
            mExoPlayerView = fragmentDetailBinding.playerView;
            mExoPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(App.getCurrentActivity(), "BakingApp");

            if(mStep.videoURL != null && !mStep.videoURL.isEmpty()){

                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), new DefaultDataSourceFactory(
                        App.getCurrentActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(mShouldAutoPlay);
                mExoPlayer.seekTo(mVideoPosition);
            }


        }
    }

    private void initFullscreenButton(final SimpleExoPlayerView myExoPlayerView) {

        fullScreenIcon = myExoPlayerView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = myExoPlayerView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog(myExoPlayerView);
                else
                    closeFullscreenDialog();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        if(currentState != null){
            currentState.putLong(EXO_PLAYER_VIDEO_POSITION, mVideoPosition);
            currentState.putBoolean(EXO_PLAYER_PLAYING_STATE, mShouldAutoPlay);
            releasePlayer();
            currentState.putString(STATE_VIDEO_CALLBACK, videoUrl);
            currentState.putString(STATE_CALLBACK, fragmentDetailBinding.stepInstruction.getText().toString());

        }
        super.onSaveInstanceState(currentState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mVideoPosition = savedInstanceState.getLong(EXO_PLAYER_VIDEO_POSITION);
            mShouldAutoPlay = savedInstanceState.getBoolean(EXO_PLAYER_PLAYING_STATE);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mShouldAutoPlay = mExoPlayer.getPlayWhenReady();
            mVideoPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer = null;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }



    private class MySessionCallback extends MediaSessionCompat.Callback{
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }


        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }




}
