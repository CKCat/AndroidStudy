# Android四大组件
* Activity
* BroadCastReceiver
* Service
* ContentProvider
# 创建第二个activity
* 新创建的activity，必须在清单文件中做配置，否则系统找不到，在显示时会直接报错

		<activity android:name="com.itheima.createactivity.SecondActivity"></activity>
* 只要有以下代码，那么就是入口activity，就会生成快捷图标

		<intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

* 如果Activity所在的包跟应用包名同名，那么可以省略不写

1. 创建class类继承Activity
2. 创建布局文件，作为Activity的显示内容
3. 在清单文件中注册Activity
# Activity的跳转
### 隐式跳转
* 一个Activity如果需要隐式跳转，那么在清单文件中必须添加以下子节点

		<intent-filter >
            <action android:name="com.itheima.sa"/>
            <category android:name="android.intent.category.DEFAULT"/>
        </intent-filter>
* action节点的name是自己定义的，定义好之后，这个name的值就会成为这个activity动作，在隐式启动Activity时，意图中设置的action必须跟"com.itheima.sa"是完全匹配的
### 应用场景
* 显示意图：启动同一个应用中的Activity
* 隐式意图：启动不同应用中的Activity
* 再启动效率上，隐式远远低于显式
* 如果系统中有多个Activity与意图设置的Action匹配，那么在启动Activity时，会弹出一个对话框，里面包含所有匹配的Activity

----
# Activity生命周期
* oncreate:Activity对象创建完毕，但此时不可见
* onstart:Activity在屏幕可见，但是此时没有焦点
* onResume：Activity在屏幕可见，并且获得焦点
* onPause：Activity此时在屏幕依然可见，但是已经没有焦点
* onStop：Activity已经不可见了，但此时Activity的对象还在内存中
* onDestroy：Activity对象被销毁

### 内存不足
* 内存不足时，系统会优先杀死后台Activity所在的进程，都杀光了，如果内存还是不足，那么就会杀死暂停状态的Activity所在的进程，如果还是不够，有可能杀死前台进程
* 如果有多个后台进程，在选择杀死的目标时，采用最近最少使用算法（LRU）

---
### Activity任务栈
* 应用运行过程中，内存中可能会打开多个Activity，那么所有打开的Activity都会被保存在Activity任务栈
* 栈：后进先出，最先进栈，就会最后出栈

### Activity的启动模式
* 标准模式：默认就是这个模式
* singleTop：如果目标Activity不在栈顶，那么就会启动一个新的Activity，如果已经在栈顶了，那么就不会再启动了
* singleTask：如果栈中没有该Activity，那么启动时就会创建一个该Activity，如果栈中已经有该Activity的实例存在了，那么在启动时，就会杀死在栈中处于该Activity上方的所有Activity全部杀死，那么此时该Activity就成为了栈顶Activity。
	* singleTask的作用：保证整个栈中只有一个该Activity的实例
* singleInstance：设为此模式的Activity会有一个自己独立的任务栈，该Activity的实例只会创建一个，保存在独立的任务栈中
	* singleInstance的作用：保证整个系统的内存中只有一个该Activity的实例

---
# 横竖屏的切换
* Activity在横竖屏切换时会销毁重建，目的就是为了读取新的布局文件
* 写死方向，不允许切换

		android:screenOrientation="portrait"
 		android:screenOrientation="landscape"
* 配置Activity时添加以下属性，横竖屏切换时就不会销毁重建

		android:configChanges="orientation|keyboardHidden|screenSize"

---
#Activity返回时传递数据
* 请求码：用来区分数据来自于哪一个Activity
* 结果码：用来区分，返回的数据时属于什么类型