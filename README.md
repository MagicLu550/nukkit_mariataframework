![LICENSE](https://img.shields.io/badge/license-GPL-blue.svg)

# nukkit_mariataframework
`nukkit framwork`

```
使用该框架后，如果使用框架提供的全新的插件结构，监听器(实现Listener接口)和命令(继承Command父类)会被框架默认为注册对象
，会根据指定的根包，对于下级包及本级包和下下级包和下下下...级包中的监听器和命令进行自动注册
```

* 解耦: 这里指将主类与注册类剥离开，使得它们之间完全独立
* 容器: 这里指该框架作为插件的容器而出现，注意的是，该框架不会提供api

另外mariata框架支持了全新的插件结构，文件目录是在plugins/mariataframework/plugin/下
将组建的jar包部署在这里即可



- jar包结构中，不需要plugin.yml，只需要mariata.oml文件

mariata.oml文件的配置格式:

```yaml
?encoding: utf-8 version: 1.0.0?

name: 插件名称

package: 根目录包
#(如net.noyark.oaml包，则只填写net即可，
#框架会根据根包目录扫描下级目录包括本级目录的全部监听器和命令，
#如果是多个根，可以使用数组[根1,根2...])

main: 主类 继承PluginBase的类
```
> oaml的语法结构: https://github.com/noyark-system/noyark_oaml_java

> github: https://github.com/MagicLu550/nukkit_mariataframework

`前置插件的设置`

- 在mariataSet中加入参数:
  * startbefore
  ```yaml
  startbefore:
    -
    -
    -
  ```
