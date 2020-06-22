package com.jd.jdsdk;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jd.kepler.res.ApkResources;
import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;

import org.json.JSONException;

import java.util.Map;

import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class JDHelper {
    private static JDHelper jdHelper;
    private PluginRegistry.Registrar register;
    private ActivityPluginBinding binding;
    Activity activity;
    KelperTask mKelperTask;

    Handler mHandler = new Handler();
    KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();//这个是即时性参数  可以设置
    LoadingDialog dialog;


    private  Activity getActivity(){
        if(null != register){
            return  register.activity();
        }else if(null != binding){
            return  binding.getActivity();
        }
        return null;
    }

    OpenAppAction mOpenAppAction = new OpenAppAction() {

        @Override
        public void onStatus(final int status, final String url) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (status == OpenAppAction.OpenAppAction_start) {//开始状态未必一定执行，
                        dialogShow();
                    }else {
                        mKelperTask = null;
                        dialogDiss();
                    }
                    if(status == OpenAppAction.OpenAppAction_result_NoJDAPP) {
                       // Toast.makeText(getActivity(), "您未安装京东app，你可以手动打开以下链接地址："+url+" ,code="+status, Toast.LENGTH_SHORT).show();
                    }else if(status == OpenAppAction.OpenAppAction_result_BlackUrl){
                       // Toast.makeText(getActivity(), "url不在白名单，你可以手动打开以下链接地址："+url+" ,code="+status, Toast.LENGTH_SHORT).show();
                    }else if(status == OpenAppAction.OpenAppAction_result_ErrorScheme){
                       // Toast.makeText(getActivity(), "呼起协议异常"+" ,code="+status, Toast.LENGTH_SHORT).show();
                    }else if(status == OpenAppAction.OpenAppAction_result_APP){

                    }else if(status == OpenAppAction.OpenAppAction_result_NetError){
                       // Toast.makeText(getActivity(), ApkResources.getSingleton().getString("kepler_check_net")+" ,code="+status+" ,url="+url,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    private void dialogShow() {
        if (dialog == null) {
            dialog = new LoadingDialog(getActivity());
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mKelperTask != null) {//取消
                        mKelperTask.setCancel(true);
                    }
                }
            });
        }
        dialog.show();
    }

    private void dialogDiss() {
        if(dialog!=null)
            dialog.dismiss();
    }


    //第一次调用getInstance register不能为空
    public static JDHelper getInstance(PluginRegistry.Registrar register){
        if (jdHelper == null){
            synchronized (JDHelper.class){
                jdHelper = new JDHelper();

                if(null != register){
                    jdHelper.register = register;
                }
            }
        }
        return jdHelper;
    }

    //第一次调用getInstance register不能为空
    public static JDHelper getInstance(ActivityPluginBinding binding){
        if (jdHelper == null){
            synchronized (JDHelper.class){
                jdHelper = new JDHelper();
                if(null != binding){
                    jdHelper.binding = binding;
                }
            }
        }
        return jdHelper;
    }




    /**
     * 初始化开普勒
     * @param call
     * @param result
     */
    public void initKepler(MethodCall call, MethodChannel.Result result){
        String appKey = call.argument("appKey");
        String appSecret = call.argument("appSecret");
        Log.d("flutter-taoke","initKepler"+getActivity());
        KeplerApiManager.asyncInitSdk(getActivity().getApplication(), appKey, appSecret, new AsyncInitListener() {
            @Override
            public void onSuccess() {
                result.success(PluginResponse.success(null).toMap());
            }

            @Override
            public void onFailure() {
                String errorCode = "-1";
                String errorMsg = "初始化失败";
                result.success(new PluginResponse(errorCode, errorMsg, null).toMap());
            }
        });
    }


    /**
     * 通过URL方式打开
     * @param call
     * @param result
     */
    public void openUrl(MethodCall call, MethodChannel.Result result){
        String url = call.argument("url");
        if(TextUtils.isEmpty(url)){
            return;
        }
        mKelperTask = KeplerApiManager.getWebViewService().openAppWebViewPage(getActivity(),
                url,
                mKeplerAttachParameter,
                mOpenAppAction);
    }

}
