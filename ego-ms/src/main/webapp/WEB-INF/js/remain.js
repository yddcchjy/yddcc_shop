function remaintime(){
var starttime = $("#starttime").html();
var s1 = new Date(starttime.replace("/-/g","/"));
var s2 = new Date();
var date3 = s1.getTime() - s2.getTime();//����һ�����ʱ���
if(date3 > 2){
$("#buybt").attr({"disabled":"disabled"});
var days = Math.floor(date3/(24*3600*1000));
var leave = date3%(24*3600*1000)
var hours = Math.floor(leave/(3600*1000));
var leave1 = leave%(3600*1000)
var minutes = Math.floor(leave1/(60*1000));
var leave2 = leave1%(60*1000)
var seconds = Math.floor(leave2/1000)
$("#remainnoties").html("s"+days+"d"+ hours + "h" + minutes + "m"+seconds+"s");
}else{
$("#remainnoties").html("");
$("#buybt").removeAttr("disabled");
$("#ac").attr("action","http://localhost:8087/order/order-cart.html");
}
}
// test js new 
setInterval('remaintime()',500);