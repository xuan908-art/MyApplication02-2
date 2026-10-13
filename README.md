一、实验目的
通过开发一个简单的任务管理应用，掌握了Jetpack Compose 声明式 UI 的基本开发方式，重点练习以下知识点：
1. Compose 基本布局组件（Column、Row、Spacer 、LazyColumn ）的使用；
2. Material3 组件（Text 、Button 、Checkbox 、OutlinedTextField 、Surface）的使用；
3. Compose 状态管理（remember 、mutableStateOf 、mutableStateListOf 、状态委托 by ）；
4. 列表项的增删、勾选状态切换及实时 UI 刷新机制。

二、实验内容与实现
1. 数据模型设计
在 MainActivity.kt 中定义了Task 数据类，其中isFinished 字段特意设计为MutableState<Boolean> 类型，使 Compose 可以直接监听勾选状态的变化，从而自动触发 UI 重组：
data class Task(
    val name: String,
    val isFinished: androidx.compose.runtime.MutableState<Boolean>
)

2. 主界面 TaskScreen
TaskScreen 是应用的核心界面，采用Column 垂直布局，包含四部分：
 标题 ："课程学习任务"，使用MaterialTheme.typography.headlineSmall 样式；
 输入区 ：OutlinedTextField + "添加" 按钮，输入非空时点击按钮将新任务加入列表并清空输入框；
 统计区 ：通过taskList.count { it.isFinished.value } 实时统计"已完成 x / 总数"；
 任务列表 ：使用LazyColumn 渲染任务项，实现高效的可滚动列表。
任务列表使用remember { mutableStateListOf(...) } 保存，保证重组过程中数据不丢失，且列表内容变化时 UI 自动更新。

3. 任务项 TaskItem
TaskItem 使用Row 水平布局，通过Arrangement.SpaceBetween 将复选框+任务名与删除按钮分居两端：
 勾选Checkbox 时直接修改task.isFinished.value ，UI 自动重组；
 已完成任务的文字添加删除线（TextDecoration.LineThrough ），直观区分任务状态；
 点击"删除"按钮通过回调onDelete 将任务从列表移除。

三、实验结果
应用实现了：
-  初始展示 3 条示例任务（其中 1 条已完成）；
-  输入任务名称后点击"添加"可新增任务；
-  勾选/取消勾选复选框，任务文字实时添加/取消删除线，顶部完成统计同步更新；
-  点击"删除"按钮可移除对应任务；
-  支持深色主题与 Android 12+ 动态取色（ Theme.kt ）。

四、实验体会
1. 声明式 UI 思维转变 ：与 View 体系"手动 findViewById + 更新视图"不同，Compose 中只需描述"UI 是状态的函数"，状态变化后框架自动重组界面，代码更简洁直观。
2. 状态管理是关键 ：实验中体会最深的是MutableState 的选择——最初若用普通Boolean字段，勾选后 UI 不会刷新；改为MutableState<Boolean>并将列表声明为mutableStateListOf后，所有增删改操作都能自动驱动界面更新。
3. 组件化拆分 ：将TaskScreen 与TaskItem 拆分为独立的@Composable 函数，职责清晰，删除逻辑通过回调参数传递给子组件，体现了 Compose 中"状态下沉、事件上浮"的设计原则。
4. 不足与改进方向 ：当前任务数据仅保存在内存中，应用退出后丢失，后续可结合 Room 数据库实现持久化；也可增加任务编辑、排序等功能进一步完善应用。

五、总结
通过本次实验，掌握了 Jetpack Compose 的基本开发流程和常用组件，理解了 Compose 的状态驱动 UI 机制，完成了一个功能完整的任务管理应用，为后续更复杂的 Android 应用开发打下了基础。
