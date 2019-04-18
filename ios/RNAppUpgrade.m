
#import "RNAppUpgrade.h"
#import <UIKit/UIKit.h>

@implementation RNAppUpgrade

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(check:(NSString *)appID callback:(RCTResponseSenderBlock)callback){
  
  //先获取当前工程项目版本号
  NSDictionary *infoDic = [[NSBundle mainBundle] infoDictionary];
  NSString *currentVersion = infoDic[@"CFBundleShortVersionString"];
  //从网络获取appStore版本号
  NSString *storeAppID = appID;//配置自己项目在商店的ID
  NSError *error;
  NSData *response = [NSURLConnection sendSynchronousRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://itunes.apple.com/cn/lookup?id=%@",storeAppID]]] returningResponse:nil error:nil];
  if (response == nil) {
    NSLog(@"你可能没有连接网络哦");
    callback(@[[NSNumber numberWithInt:-3]]);
    return;
  }
  NSDictionary *appInfoDic = [NSJSONSerialization JSONObjectWithData:response options:NSJSONReadingMutableLeaves error:&error];
  if (error) {
    NSLog(@"hsUpdateAppError:%@",error);
    callback(@[[NSNumber numberWithInt:-2]]);
    return;
  }

  NSLog(@"可输出一下看看%@",appInfoDic);
  NSArray *array = appInfoDic[@"results"];
  if (array.count < 1) {
    NSLog(@"此APPID为未上架的APP或者查询不到");
    callback(@[[NSNumber numberWithInt:-1]]);
    return;
  }

  NSDictionary *dic = array[0];
  //商店版本号
  NSString *appStoreVersion = dic[@"version"];
  
  NSLog(@"当前版本号:%@\n商店版本号:%@",currentVersion,appStoreVersion);
  //设置版本号，主要是为了区分不同的版本，比如有1.2.1、1.2、1.31各种类型
  currentVersion = [currentVersion stringByReplacingOccurrencesOfString:@"." withString:@""];
  if (currentVersion.length==2) {
    currentVersion  = [currentVersion stringByAppendingString:@"0"];

  } else if (currentVersion.length==1){
    currentVersion  = [currentVersion stringByAppendingString:@"00"];
  }

  appStoreVersion = [appStoreVersion stringByReplacingOccurrencesOfString:@"." withString:@""];
  if (appStoreVersion.length==2) {
    appStoreVersion  = [appStoreVersion stringByAppendingString:@"0"];

  } else if (appStoreVersion.length==1){
    appStoreVersion  = [appStoreVersion stringByAppendingString:@"00"];
  }
  
  //当前版本号小于商店版本号,就更新
  if([currentVersion floatValue] < [appStoreVersion floatValue])
  {    
    callback(@[[NSNumber numberWithInt:1]]);

  } else {
    NSLog(@"版本号好像比商店大噢!检测到不需要更新");
    callback(@[[NSNumber numberWithInt:0]]);
  }
}

RCT_EXPORT_METHOD(openAppStore:(NSString *) appID ){
  //此处加入应用在app store的地址，方便用户去更新，一种实现方式如下
  NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:@"itms-apps://itunes.apple.com/cn/app/id%@?ls=1&mt=8", appID]];
  [[UIApplication sharedApplication] openURL:url];
}

@end
  