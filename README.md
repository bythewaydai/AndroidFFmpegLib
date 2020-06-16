# AndroidFFmpegLib

简单介绍：Android平台下FFmpeg常用命令的使用工具库。
暂时有这两个类FFmpegCmd，FFmpegCommandLineUtil（具体可看源码）。

FFmpegCommandLineUtil：包含了各种FFmpeg命令的组合
FFmpegCmd：执行FFmpeg的命令并给出执行结果的回调

####引用方式：
在“Project”的build.gradle中添加以下仓库
```javascript
allprojects {
    	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

在“app”的build.gradle中添加引用
```javascript
dependencies {
            implementation 'com.github.bythewaydai:AndroidFFmpegLib:master-SNAPSHOT'
	}
```