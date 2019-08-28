<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="/js/kindeditor-4.1.10/themes/default/default.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<div style="padding:10px 10px 10px 10px">
	<form id="msitemAddForm" class="itemForm" method="post">
	    <table cellpadding="5">
	        <tr>
	            <td>商品标题:</td>
	            <td><input class="easyui-textbox" type="text" name="title" data-options="required:true" style="width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>商品图片:</td>
	            <td>
	            	 <a href="javascript:void(0)" class="easyui-linkbutton picFileUpload">上传图片</a>
	                 <input type="hidden" name="image"/>
	            </td>
	        </tr>
	        <tr>
	            <td>秒杀价格:</td>
	            <td><input class="easyui-numberbox" type="text" name="priceView1" data-options="min:1,max:99999999,precision:2,required:true" />
	            	<input type="hidden" name="msPrice"/>
	            </td>
	        </tr>
	        <tr>
	            <td>商品原价格:</td>
	            <td><input class="easyui-numberbox" type="text" name="priceView2" data-options="min:1,max:99999999,precision:2,required:true" />
	            	<input type="hidden" name="oldPrice"/>
	            </td>
	        </tr>
	        <tr>
	            <td>商家id:</td>
	            <td><input class="easyui-numberbox" type="text" name="merchantId" data-options="min:1,max:99999999,precision:0,required:true" /></td>
	        </tr>
	        <tr>
	            <td>秒杀开始时间:</td>
	            <td><input id = "time1" class="easyui-datetimebox" type="text" name="beginTime" data-options="required:true" /></td>
	        </tr>
	        <tr>
	            <td>秒杀结束时间:</td>
	            <td><input id = "time2" class="easyui-datetimebox" type="text" name="finishTime" data-options="required:true" /></td>
	        </tr>
	        <tr>
	            <td>商品类目:</td>
	            <td>
	            	<a href="javascript:void(0)" class="easyui-linkbutton selectItemCat">选择类目</a>
	            	<input type="hidden" name="cid" style="width: 280px;"></input>
	            </td>
	        </tr>
	        <tr>
	            <td>库存数量:</td>
	            <td><input class="easyui-numberbox" type="text" name="num" data-options="min:1,max:99999999,precision:0,required:true" /></td>
	        </tr>
	        <tr>
	            <td>商品卖点:</td>
	            <td><input class="easyui-textbox" name="sellPoint" data-options="multiline:true,validType:'length[0,150]'" style="height:60px;width: 280px;"></input></td>
	        </tr>
	        <tr>
	            <td>条形码:</td>
	            <td>
	                <input class="easyui-textbox" type="text" name="barcode" data-options="validType:'length[1,30]'" />
	            </td>
	        </tr>
	    </table>
	    <input type="hidden" name="itemParams"/>
	</form>
	<div style="padding:5px">
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="test()">测试</a>
	    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="test2()">测试2</a>
	</div>
</div>


<script type="text/javascript">
	//页面初始化完毕后执行此方法
	$(function(){
		//初始化类目选择和图片上传器
		EGO.init({fun:function(node){
			//根据商品的分类id取商品 的规格模板，生成规格信息。第四天内容。
			EGO.changeItemParam(node, "msitemAddForm");
		}});
	});
	
	
	function test(){
		var v = $('#time1').datetimebox('getValue');
		var d = new Date(v );
		$.messager.alert('提示',v);
	}
	
	
	function convert(x){
		var d = new Date(x );
		return d;
	}
	function test2(){
		//convert($('#time1').datetimebox('getValue'))
		$.messager.alert('提示',convert($('#time1').datetimebox('getValue')));
	}
	
	//提交表单
	function submitForm(){
		//有效性验证
		if(!$('#msitemAddForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//取商品价格，单位为“分”
		$("#msitemAddForm [name=msPrice]").val(eval($("#msitemAddForm [name=priceView1]").val()) * 100);
		$("#msitemAddForm [name=oldPrice]").val(eval($("#msitemAddForm [name=priceView2]").val()) * 100);
		
		 $("#msitemAddForm [name=beginTime]").val(convert($('#time1').datetimebox('getValue') ));
		 $("#msitemAddForm [name=finishTime]").val(convert($('#time2').datetimebox('getValue') ));
		 
		//ajax的post方式提交表单
		//$("#msitemAddForm").serialize()将表单序列号为key-value形式的字符串
		$.post("/msitem/save",$("#msitemAddForm").serialize(), function(data){
			if(data.status == 200){
				$.messager.alert('提示','新增商品成功!');
			}else{
				$.messager.alert('提示','新增商品失败!<br/> 原因:'+data.data);
			}
		});
	}
	
	function clearForm(){
		$('#msitemAddForm').form('reset');
		itemAddEditor.html('');
	}
</script>
