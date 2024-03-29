package com.huatec.dongxinyuan.http;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

public abstract class ProgressDialogSubscriber<T> extends Subscriber<T> {

    private final Context mContext;
    private ProgressDialog mDialog;

    public ProgressDialogSubscriber(Context context){
        this.mContext = context;
    }

    

    @Override
    public void onCompleted(){
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e){
        if (e instanceof SocketTimeoutException){
            Toast.makeText(mContext,"网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else if (e instanceof ConnectException){
            Toast.makeText(mContext,"网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.print("error:"+e.getMessage());
        }
        dismissProgressDialog();
    }

    private void dismissProgressDialog(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
            mDialog=null;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        showProgressDialog();
    }

    private void showProgressDialog(){
        if (mDialog == null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setCancelable(true);
//            mDialog.setOnCancelListener(
//                    ProgressDialogSubscriber.this.unsubscribe();
//            );
            mDialog.setOnCancelListener((new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //取消订阅，取消请求
                    ProgressDialogSubscriber.this.unsubscribe();
                }
            }));
        }
        if (mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
    }
//    private void httpRequest(final String username,final String password){
//        MemberPresenter.login2(new ProgressDialogSubscriber<MemberEntity>(this) {
//            @Override
//            public void onNext(MemberEntity memberEntity){
//                tv_result.setText(
//                        String.format("用户名：%s\n邮箱：%s"
//                                ,memberEntity.uname
//                                ,memberEntity.email)
//                );
//            }
//        },username,password);
//    }

}
