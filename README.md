# RemoteLogDemo
灵感来源于[RemoteLogcatViewer](https://github.com/8enet/RemoteLogcatViewer)

本程序的server端可以在外网服务器上，也可以是同局域网内的一台电脑。

配合[log-client](https://github.com/andych008/log-client)、[log-server](https://github.com/andych008/log-server)一起使用。


## configure this dependency
--------

```
repositories {
    maven { url 'https://dwvip.github.io/repo' }
}

implementation 'wang.catunclue:wslog:0.1'
```