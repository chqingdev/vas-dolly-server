# 简介

一个 Android 快速多渠道工具。基于 VasDolly 实现。更多 VasDolly 原理详见 [VasDolly](https://github.com/Tencent/VasDolly)。
无需多次构建渠道包，使用简单服务部署方便。

# 接入流程：

### Android 工程接入

1、Android 项目中添加获取渠道工具类的依赖（build.gradle 无需再添加任何依赖和配置）

```groovy
dependencies {
    api 'com.leon.channel:helper:2.0.3'
}
```

2、获取渠道 (可直接食用) ：

```java

public final class ChannelHelper {
    private static final String TAG = "ChannelHelper";
    private static final String DEFAULT_CHANNEL_NAME = "defaultChannel";

    private PackerNg() {
        throw new AssertionError("Don't instance! ");
    }

    @NonNull
    public static String getChannel(@NonNull Context context) {
        return getChannel(context, DEFAULT_CHANNEL_NAME);
    }

    /**
     * obtain channel
     *
     * @param context      Context
     * @param defaultValue  Default channel
     * @return channel name
     */
    @NonNull
    public static String getChannel(@NonNull Context context, @NonNull String defaultValue) {
        Log.d(TAG, String.format("getChannel startTime -> %s,", SystemClock.elapsedRealtime()));
        final String channel = ChannelReaderUtil.getChannel(context.getApplicationContext());
        Log.d(TAG, String.format("getChannel endTime -> %s,", SystemClock.elapsedRealtime()));
        return channel != null ? channel : defaultValue;
    }
}
```

### 渠道配置和服务部署

1、打包。下载编译后的服务端程序压缩包，执行 ```./bin/start.sh``` (原谅我暂时没支持 window)，服务启动后打开 http://host:9999 访问服务，上床 apk 选择渠道生成渠道包。
![pic](snapshot/screen-record.gif)

2、打包完成后会自动下载 channels.zip 文件，解压 channels.zip 文件 channelName-文件名称.zpk 就是生成后的渠道包。

<b>注：</b>
  - 服务应用程序没做用户相关的逻辑，因此，同一时刻只能一个人使用；
  - 渠道包生成过程不会删除或者修改签名信息，签名后的 apk 生成渠道后可以直接发布。

### 添加、删除、修改渠道
1、在服务端程序的 ./config/application.yaml 文件中，channels 节点下修改或添加渠道配置；默认配置如下：
```yaml
    channel:
      channels:
        - "xiaomi"    # 小米
        - "huawei"    # 华为
        - "official"  # 官方渠道
        - "oppo"      # Oppo
        - "vivo"      # vivo
```
2、重启服务【一定要重启，否则配置不会生效】

### Future: 
- 目前的错误处理并不人性化，优化异常处理信息；
- 支持同一时刻多人同时构建渠道。
- 支持 windows 服务启动脚本



