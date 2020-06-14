//
//  KeplerApiManager.h
//  KeplerApp
//  提供Kepler服务
//  Created by JD.K on 16/6/20.
//  Copyright © 2016年 JD.K. All rights reserved.
//  version 3.0.1

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

/** 初始化成功回调 */
typedef void (^initSuccessCallback)();
/** 初始化失败回调 */
typedef void (^initFailedCallback)(NSError * _Nonnull error);
/**
 打开成功回调
 */
typedef void(^OpenSuccessBlock)();

/**
 * 响应失败的错误回调，返回一个错误的链接或信息
 */
typedef void(^OpenFailedBlock)(NSInteger code, NSString * _Nonnull url);

typedef enum {
    KeplerErrorCodeUnInstalled = 422, //未安装京东app
    KeplerErrorCodeWhited, //本地url不在白名单中
    KeplerErrorCodeResponsed, //本地呼起open协议失败
    KeplerErrorCodeNoSid, //sid为空
    KeplerErrorCodeInValidApp //应用不合法
} KeplerErrorCode;


@interface KeplerApiManager : NSObject

/**
 分佣的 AppKey2
 */
@property (nonatomic, copy) NSString *secondAppKey;
//*********************************     通过京东APP打开链接相关参数      ************************************

/**
 当isOpenByH5为 NO 时，准备跳转到JD APP时会调用这些代码。可以把开启 Loading动画的代码放到这里
 为避免造成混乱，在关闭Kepler界面时，会置为nil。因此需要在每次打开Kepler之前单独设置。
 */
@property (nonatomic, copy) void(^startOpenJDAppBlock)();

/**
 当isOpenByH5为 NO 时，跳转JD APP准备工作完成时会调用这些代码，success为YES表示成功，可以打开JD APP，为NO时表示打开失败。
 为避免造成混乱，在关闭Kepler界面时，会置为nil。因此需要在每次打开Kepler之前单独设置。
 
 update: success为NO且错误码等于-1005时，安全id为空
 */
@property (nonatomic, copy) void(^finishOpenJDAppBlock)(BOOL success,NSError *error);

/**
 *  京东达人内容ID 关闭kepler界面时会清除 如果需要此值 再次打开需要再次设置
 **/
@property (nonatomic, copy) NSString *actId;

/**
 *  京东达人 内容渠道扩展字段 关闭kepler界面时会清除 如果需要此值 再次打开需要再次设置
 **/
@property (nonatomic, copy) NSString *ext;

/**
 * 是否走服务端的渠道，默认走本地渠道
 */
@property (nonatomic, assign) BOOL isServerChannel;

/**
 打开京东超时时间设置 关闭 Kepler 界面时不会重置 默认为60
 */
@property (nonatomic, assign) NSTimeInterval openJDTimeout;


//*******************************************************************************************************

/**
 *  KeplerApiManager 单例
 *
 *  @return KeplerApiManager 单例
 */
+ (KeplerApiManager *)sharedKPService;

/**
 *  注册Kepler 服务
 *
 *  @param appKey      注册的appKey
 *  @param appSecret   注册的secretKey
 */
- (void)asyncInitSdk:(NSString *)appKey
           secretKey:(NSString *)appSecret
      sucessCallback:(initSuccessCallback)sucessCallback
      failedCallback:(initFailedCallback)failedCallback;

/**
 跳转京东app

 @param url 调用方传入的URl参数
 @param userInfo 调用sdk时传入的kepler自定义参数
 @param failedCallback code 表示错误编码, url 引起调用失败的url
 */
- (void)openKeplerPageWithURL:(NSString *)url
                     userInfo:(NSDictionary *)userInfo
              successCallback:(OpenSuccessBlock _Nullable)success
               failedCallback:(OpenFailedBlock _Nonnull)failure;

@end

