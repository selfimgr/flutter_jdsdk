import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class Jdsdk {
  static const MethodChannel _channel =
      const MethodChannel('jdsdk');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  ///初始化sdk
  static Future<String> init({@required String appKey, @required String appSecret}) async {
    final String version = await _channel.invokeMethod('init',{"appKey": appKey, "appSecret": appSecret});
    return version;
  }

  ///打开京东任意url
  static Future<String> openUrl({@required String url}) async {
    final String version = await _channel.invokeMethod('openUrl',{"url": url});
    return version;
  }
}
