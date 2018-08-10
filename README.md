## 付款提示更换手机收货地址的Demo
<br><br>
    前不久，我在小米商城买路由器，我当天晚上下单，飞快的付完钱，就退出了小米商城玩游戏去了。第二天早上八九点的样子，我收到快递短信，让我去拿。
  我定睛一看，怎么让我去学校拿？？后来才反应过来我没有重新选择地址，默认地址又设置的是学校的地址，快递就直接送南昌去了。
  然后我只能在家里打电话给快递拒收了。没改收货地址的情况还发生过好几次。
  于是，我就希望网购时能有一个提醒功能，`当用户当前所在城市与默认地址中的城市不同，App提醒下用户是否需要更换收货地址。`然后我就自己动手做了一个demo。
    
   ## 默认地址中的城市与所在城市不同，提醒更换收货地址
   <div align=center>
   <img src="https://github.com/StevenReach/DeliveryAddressDemo/raw/master/gif/demo.gif" width="270" height="480"/></div>
    
   ## 默认地址中的城市与所在城市相同
   <div align=center>
   <img src="https://github.com/StevenReach/DeliveryAddressDemo/raw/master/gif/demo_same.gif" width="270" height="480"/></div>
   <br>
   这个demo的定位功能是用的高德地图API实现的，一进这个demo就会自动获取当前所在城市，城市数据用Constant传递。
   收货地址存储在SQLite数据库中，默认地址还另外存在了SharePreference中，因为我觉得在实际开发中应该是存在个人信息表中的，
   所以就把默认地址还另外存在了SharePreference里。
   <br>
   这个demo与地址相关的操作都是做好了的。
