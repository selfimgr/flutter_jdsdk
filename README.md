# 京东sdk的唤醒
京东sdk在Flutter上的实现，通过它可以实现唤醒京东app打开京东任意的url，包括商品详情等功能。

## 使用需知
[京东官方接入文档](https://union.jd.com/helpcenter/13246-13248-46117),在android和ios分别加入安全图片。

### ios
 在 Info.plist 文件中添加
 ```dart
    <key>LSApplicationQueriesSchemes</key>
 	<array>
 		<string>openapp.jdmobile</string>
 		<string>jdlogin</string>
 	</array>
 	<key>CFBundleURLTypes</key>
    	<array>
    		<dict>
    			<key>CFBundleTypeRole</key>
    			<string>Editor</string>
    			<key>CFBundleURLSchemes</key>
    			<array>
    				<string>sdkback</string>
    				<string>此处填入appkey</string>
    			</array>
    		</dict>
    	</array>
 ```

## 初始化
```dart
 import 'package:jdsdk/jdsdk.dart';
 await Jdsdk.init(appKey: '', appSecret: '');
```


## 打开京东任意URL
```dart
 import 'package:jdsdk/jdsdk.dart';
 await Jdsdk.openUrl( url: 'https://item.m.jd.com/product/100009963992.html');
```


## 联系方式
QQ：`511644784`




