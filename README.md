# simplebilling
基于JSP的简单响应式个人记账WEB应用

//2017.10.31
解决ajax只能登录一次问题，添加AES前后台加密

//2017.11.01
确定页面框架，解决echarts图表响应式问题
增加分页显示功能

//2017.11.02
基础的账目的CRUD做完, 页面小毛病更正

//TODO echats图表设置，AES加密，Excel导出，头像上传

//2017.11.03
优化SQL语句

//2017.11.04
解决图表显示问题，增加Excel导出功能，取消头像上传，用户注册，信息修改功能添加成功
重新设计表，更正日期转换错误，修改页面小错误

//TODO 加密，计算器问题，搜索功能

2017.11.05
增加代码注释,添加搜索功能，
页面存在的问题，修改信息，以及修改账目存在小问题，需要先点击才生效，页面计算器存在问题。
代码存在问题，没有检查不为空情况，直接拿取，容易服务器挂掉。
共同问题，没网络不能进行记录

//TODO 加密
代码思路，JS前端对称加密，后台解密，并没有多大安全意义，因为使用对称加密算法，前端JS明文暴露，则后台的解密并没有啥意义。

//TODO 密码修改

2017.11.06
完成密码修改功能