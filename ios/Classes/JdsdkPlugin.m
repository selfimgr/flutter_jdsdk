#import "JdsdkPlugin.h"
#import <jdsdk/JDKeplerSDK.h>
#import "FlutterKeplerHandler.h"

@interface JdsdkPlugin()
@property(nonatomic,strong) FlutterKeplerHandler *keplerHandler;
@end


@implementation JdsdkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
     NSLog(@"registerWithRegistrar");
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"jdsdk"
            binaryMessenger:[registrar messenger]];
 // JdsdkPlugin* instance = [[JdsdkPlugin alloc] init];
    //将 JdsdkPlugin 的无参 init 函数调整为 initWithRegistrar
  JdsdkPlugin* instance = [[JdsdkPlugin alloc] initWithRegistrar:registrar];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (instancetype)initWithRegistrar:(NSObject <FlutterPluginRegistrar> *)registrar {
    NSLog(@"initWithRegistrar");
    self = [super init];
    
    if (self) {
        self.keplerHandler = [[FlutterKeplerHandler alloc]init];
    }
    
    return self;
}


- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getPlatformVersion" isEqualToString:call.method]) {
    result([@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]]);
  } else if ([@"init" isEqualToString:call.method]) {
      NSLog(@"init flutter");
    [_keplerHandler initKepler:call result:result];
  }else if ([@"openUrl" isEqualToString:call.method]) {
       [_keplerHandler keplerPageWithURL:call result:result];
  }else {
    result(FlutterMethodNotImplemented);
  }
}

@end
