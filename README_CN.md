![LICENSE](https://img.shields.io/badge/license-GPL-blue.svg)
[![zh](https://img.shields.io/badge/readme-english-orange.svg)](README_EN.md)

# nukkit_mariataframework
`nukkit framework`

```
使用该框架后，如果使用框架提供的全新的插件结构，监听器(实现Listener接口)和命令(继承Command父类)会被框架默认为注册对象
，会根据指定的根包，对于下级包及本级包和下下级包和下下下...级包中的监听器和命令进行自动注册
```

* 解耦: 这里指将主类与注册类剥离开，使得它们之间完全独立
* 容器: 这里指该框架作为插件的容器而出现

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

目前支持加载带有plugin.yml的插件

继承Task的类如果添加一个
public int startNow;
就会被立即启动


- getReflectLoader方法



规定:使用该MariataPluginBase作为主类的插件必须使用mariata.oml



自定义中枢方法的使用

//this为继承MariataPluginBase的主类


```
this.getReflectLoader(this,(obj,clz)->{

//对象处理

if(obj instanceof xxx){

对象的处理

}

},new String[]{根包名});
```

使用该方法后，所有的对象都会在这里被加载，并且在lambada表达式可以对全部对象加载


`如:处理全部继承AAA类的对象`

```

this.getReflectLoader(this,(obj,clz)->{

//对象处理

if(obj instanceof AAA){



}

},new String[]{根包名}); 
```

