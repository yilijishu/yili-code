# code代码生成机说明

## 版本

|版本号|说明|
|---|---|
|1.1.2|初始化生成机|
|||

## 代码仓库

|平台|地址|
|---|---|
|github|https://github.com/yilijishu/yili-code|
|gitee|https://gitee.com/yilijishu/yili-code|

## 一、 基础说明
``
如果使用code进行操作代码的生成，需要依赖yili-utils
生成的代码需要依赖yili-mybatis-plus运行。
``
## 二、 步骤

* ``步骤一、使用当前code生成需要在application.xml配置com.yilijishu.code.generate.enabled参数为true``
* ``步骤二、配置com.yilijishu.code.generate.build.path:/xxxx/xxx/src/main/java 此路径要到项目的java目录``
* ``步骤三、生成类依赖ApplicationContext。所以要保证ApplicationContext注册``
* ``步骤四、如果生成完成可以设置为com.yilijishu.code.generate.enabled:false``

## 三、 注意事项

* ``1、 生成会识别所有的@Table的entity类。``
* ``2、 entity路径假设为com.yilijishu.entity.User, 那么生成的service、impl、manager 则与entity包平级。``
* ``3、切记一定要设置com.yilijishu.code.generate.build.path路径。路径要到项目的java层,例如C:/project/yili-code/src/main/java/``