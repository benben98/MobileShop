package com.huatec.dongxinyuan.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatec.dongxinyuan.R;
import com.huatec.dongxinyuan.common.BaseActivity;
import com.huatec.dongxinyuan.common.MobileShopApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;



public class AdActivity extends BaseActivity {

    private TextView tv_skip;

    @Override
    public int getContentViewId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void initView(){
        super.initView();
        //显示图片
        tv_skip = (TextView) findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.iv_image);
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575376781617&di=c92ac7b729e11719cfc73e56703c3e39&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F-vo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fpic%2Fitem%2Fa044ad345982b2b70d8d80c437adcbef76099ba4.jpg";
        ImageLoader.getInstance().displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                //图片加载失败，跳转到主页
                //启动跳转页面倒计时
                MobileShopApp.handler.post(jumpTread);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                //图片加载成功时，跳转到主页
                //启动跳转页面倒计时
                MobileShopApp.handler.post(jumpTread);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                //图片加载取消，跳转到主页
                //启动跳转页面倒计时
                MobileShopApp.handler.post(jumpTread);
            }
        });
    }

    private void skip(){
        Intent intent = new Intent(AdActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        //移除跳转操作
        c_count = COUNT;
        MobileShopApp.handler.removeCallbacks(jumpTread);
    }

    private static final String SKIP_TEXT = "点击跳过 %d";
    //定义几秒后跳转，每次延时1秒DELAYDE(1000毫秒)，总共COUNT次
    private static final int COUNT = 5;
    private static final int DELAYED = 1000;//1000毫秒
    private int c_count = COUNT;//记录当前循环的剩余次数，倒计时

    //跳转执行的Runable
    private Runnable jumpTread = new Runnable() {
        @Override
        public void run() {
            if (c_count <= 0) {
                //循环的跳转次数为0时跳转
                skip();
            } else {
                tv_skip.setText(String.format(SKIP_TEXT, c_count));
                //自减，继续循环
                c_count--;
                MobileShopApp.handler.postDelayed(jumpTread, DELAYED);
            }
        }
    };
}
