package com.jd.jdsdk;

import android.util.Log;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** JdsdkPlugin */
public class JdsdkPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  public static JDHelper mJDHelper;
  @Override
  public void onAttachedToEngine( FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "jdsdk");
    channel.setMethodCallHandler(new JdsdkPlugin());
  }


  public static void registerWith(Registrar registrar) {
    mJDHelper = JDHelper.getInstance(registrar);
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "jdsdk");
    channel.setMethodCallHandler(new JdsdkPlugin());
  }

  @Override
  public void onMethodCall( MethodCall call,  Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("init")) {
      mJDHelper.initKepler(call,result);
    }else if (call.method.equals("openUrl")) {
      Log.d("flutter-test","openUrl");
      mJDHelper.openUrl(call,result);
    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine( FlutterPluginBinding binding) {
  }

  @Override
  public void onAttachedToActivity( ActivityPluginBinding binding) {
    Log.d("flutter-taoke","onAttachedToActivity"+binding);
    mJDHelper = JDHelper.getInstance(binding);
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges( ActivityPluginBinding binding) {

  }

  @Override
  public void onDetachedFromActivity() {

  }
}
