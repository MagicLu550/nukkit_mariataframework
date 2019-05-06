![LICENSE](https://img.shields.io/badge/license-GPL-blue.svg)
[![zh](https://img.shields.io/badge/readme-chinese-orange.svg)](README.md)

# nukkit_mariataframework
`nukkit framework`

```
After using this framework, if you use the new plugin structure provided by the framework, the listener (implementing the Listener interface) and the command (inherited from the Command parent class) will be registered as the default object by the framework.
According to the specified root package, the automatic registration of the listeners and commands in the lower level package and the lower level package and the lower level package and the lower level...
```

* Decoupling: This means separating the main class from the registration class, making them completely independent
* Container: This refers to the framework as a container for the plugin. Note that the framework does not provide an api

In addition, the mariata framework supports a new plugin structure, the file directory is under plugins/mariataframework/plugin/
Deploy the built jar package here.



- jar package structure, do not need plugin.yml, only need mariata.oml file

The configuration format of the mariata.oml file:

```yaml
?encoding: utf-8 version: 1.0.0?

name: the name of the plugin

package: root directory package
#(such as net.noyark.oaml package, just fill in the net,
#Frame will scan the lower directory according to the root package directory, including all listeners and commands of the directory.
#If it is multiple roots, you can use arrays [root 1, root 2...])

main: main class class that inherits from PluginBase
```
> The syntax structure of oaml: https://github.com/noyark-system/noyark_oaml_java

> github: https://github.com/MagicLu550/nukkit_mariataframework

`Pre-plugin settings`

- Add parameters to mariataSet:
  * startbefore
```yaml
startbefore:
 -
 -
 -
```

Currently loading plugins with plugin.yml is supported.

The class that inherits the task will be started immediately if you add a 
```
public int startNow;
```
getReflectLoader method
Regulation: The plugin using the MariataPluginBase as the main class must use mariata.oml

Use of custom hub methods

//this is the main class that inherits MariataPluginBase

this.getReflectLoader(this,(obj,clz)->{

/ / Object processing

If(obj instanceof xxx){

Object processing

}

},new String[]{root package name});
After using this method, all objects will be loaded here, and all objects can be loaded in lambada expressions.

Such as: processing all objects that inherit AAA class


this.getReflectLoader(this,(obj,clz)->{

/ / Object processing

If(obj instanceof AAA){



}

},new String[]{root package name});
