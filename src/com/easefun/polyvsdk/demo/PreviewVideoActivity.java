package com.easefun.polyvsdk.demo;

import tv.danmaku.ijk.media.player.IMediaPlayer;

import com.easefun.polyvsdk.R;
import com.easefun.polyvsdk.ijk.IjkMediaController;
import com.easefun.polyvsdk.ijk.IjkVideoView;
import com.easefun.polyvsdk.ijk.OnPreparedListener;
import com.easefun.polyvsdk.ijk.PreviewIjkVideoView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PreviewVideoActivity extends Activity{
	   PreviewIjkVideoView videoview;
	   IjkMediaController mediaController;
	   private WindowManager wm;
	   float ratio;
	   int w,h,adjusted_h;
	   RelativeLayout rl,botlayout;
	   private boolean isLandscape=false;
	   private int stopPosition =0;
	   private View view =null;
	   private String path;
	   private String vid;
	   boolean isLocal=false;
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.video_small_preview);
	 //------------------------------------------------------
	    	Bundle e = getIntent().getExtras();
	    	if (e != null) {
				path = e.getString("path");
				vid = e.getString("vid");
			}
			if (path != null && path.length() > 0) {
				isLocal = true;
			}
	    	wm = this.getWindowManager();
			w = wm.getDefaultDisplay().getWidth();
			h = wm.getDefaultDisplay().getHeight();
			//小窗口的比例
			ratio=(float)4/3;
			adjusted_h= (int)Math.ceil((float)w/ratio);
			rl = (RelativeLayout) findViewById(R.id.rl);
			botlayout=(RelativeLayout)findViewById(R.id.botlayout);
			rl.setLayoutParams(new RelativeLayout.LayoutParams(w, adjusted_h));
			videoview=(PreviewIjkVideoView)findViewById(R.id.videoview);
			mediaController = new IjkMediaController(this,false);//
			videoview.setMediaController(mediaController);
			if(!isLocal){
				
			    videoview.setVideoId("sl8da4jjbx2262724b8a5132bd6103b2_s");
			}else{
				videoview.setLocalVideo(vid);
			}
			videoview.initPreview("sl8da4jjbx2262724b8a5132bd6103b2_s");
			videoview.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(IMediaPlayer mp) {
					// TODO Auto-generated method stub
					videoview.pause();
				}
			});
			
			//设置切屏事件
			mediaController.setOnBoardChangeListener(new IjkMediaController.OnBoardChangeListener() {
				
				@Override
				public void onPortrait() {
					// TODO Auto-generated method stub
					changeToLandscape();
				}
				
				@Override
				public void onLandscape() {
					// TODO Auto-generated method stub
					changeToPortrait();
				}
			});
			// 设置视频尺寸 ，在横屏下效果较明显
		   mediaController.setOnVideoChangeListener(new IjkMediaController.OnVideoChangeListener() {
			
			@Override
			public void onVideoChange(int layout) {
				// TODO Auto-generated method stub
				videoview.setVideoLayout(layout);
				switch (layout) {
				case IjkVideoView.VIDEO_LAYOUT_ORIGIN:
					Toast.makeText(PreviewVideoActivity.this, "VIDEO_LAYOUT_ORIGIN", 1).show();
					break;
	            case IjkVideoView.VIDEO_LAYOUT_SCALE:
	            	Toast.makeText(PreviewVideoActivity.this, "VIDEO_LAYOUT_SCALE", 1).show();
					break;
	            case IjkVideoView.VIDEO_LAYOUT_STRETCH:
	            	Toast.makeText(PreviewVideoActivity.this, "VIDEO_LAYOUT_STRETCH", 1).show();
		            break;
	             case IjkVideoView.VIDEO_LAYOUT_ZOOM:
	            	 Toast.makeText(PreviewVideoActivity.this, "VIDEO_LAYOUT_ZOOM", 1).show();
		            break;
		            }
			}
		});
			//播放视频
			findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				    videoview.start();	
				}
			});
			//切换下一个视频
	        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					videoview.setVideoId("sl8da4jjbxa3c15f99bc37545693f7f9_s");
				}
			});
	    }
		
		
		

//		   切换到横屏
		public void changeToLandscape(){
			RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(h,w);
			rl.setLayoutParams(p);
			stopPosition = videoview.getCurrentPosition();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			botlayout.setVisibility(View.GONE);
			isLandscape = !isLandscape;
		}
		
//		切换到竖屏
		public void changeToPortrait(){
			RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(w,adjusted_h);
			rl.setLayoutParams(p);
			stopPosition = videoview.getCurrentPosition();
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			botlayout.setVisibility(View.VISIBLE);
			isLandscape = !isLandscape;
		}
		
		
		// 配置文件设置congfigchange 切屏调用一次该方法，hide()之后再次show才会出现在正确位置
		@Override
		public void onConfigurationChanged(Configuration arg0) {
				// TODO Auto-generated method stub
				super.onConfigurationChanged(arg0);
				mediaController.hide();
			}
		
		 @Override
			protected void onResume() {
				// TODO Auto-generated method stub
				super.onResume();
		
		 }
}