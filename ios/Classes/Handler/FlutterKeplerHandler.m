

#import "FlutterKeplerHandler.h"
#import <jdsdk/JDKeplerSDK.h>
@implementation FlutterKeplerHandler
//初始化开普勒
- (void)initKepler:(FlutterMethodCall *)call result:(FlutterResult)result {
    NSString *appKey = call.arguments[@"appKey"];
    NSString *appSecret = call.arguments[@"appSecret"];
    NSLog (@"appKey is :%@", appKey);
    NSLog (@"appSecret is :%@", appSecret);
    [[KeplerApiManager sharedKPService]asyncInitSdk:appKey secretKey:appSecret sucessCallback:^(){
        NSLog (@"success");
        result(@"{status:1}");
    }failedCallback:^(NSError *error){
        NSLog (@"fail");
       result(@"{status:0}");
    }];
}

- (void)keplerPageWithURL:(FlutterMethodCall *)call result:(FlutterResult)result {
    NSString *url = call.arguments[@"url"];
    //    NSInteger jumpType = [call.arguments[@"jumpType"] integerValue];
    NSInteger jumpType = [[NSNumber numberWithInt:1] integerValue];
//    NSDictionary *userInfo = [FlutterKeplerTools nullToNil:call.arguments[@"userInfo"]];
    UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
    
  
    if ([[UIApplication sharedApplication]canOpenURL:[NSURL URLWithString:[NSString stringWithFormat:@"openapp.jdmobile://"]]]) {//判断是否安装京东app
       [ [KeplerApiManager sharedKPService]openKeplerPageWithURL:url userInfo:nil successCallback:^{
             NSLog (@"success");
                  result(@"{status:1}");
        } failedCallback:^(NSInteger code, NSString * _Nonnull url) {
                    NSLog (@"fail");
                  result(@"{status:0}");
        }];
    }else{
            result(@"{status:0}");
    }
    
}

@end
