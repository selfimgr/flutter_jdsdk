

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
NS_ASSUME_NONNULL_BEGIN

@interface FlutterKeplerHandler : NSObject
//初始化
- (void)initKepler:(FlutterMethodCall *)call result:(FlutterResult)result;
// *  通过URL打开任意商品页面
- (void)keplerPageWithURL:(FlutterMethodCall *)call result:(FlutterResult)result;

//设置进度条颜色
- (void)setKeplerProgressBarColor:(FlutterMethodCall *)call result:(FlutterResult)result;

@end

NS_ASSUME_NONNULL_END
