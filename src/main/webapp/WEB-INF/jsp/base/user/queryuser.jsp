<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/base/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 引用jquery easy ui的js库及css -->
<LINK rel="stylesheet" type="text/css" href="${baseurl}js/easyui/styles/default.css">
<%@ include file="/WEB-INF/jsp/base/common_css.jsp"%>
<%@ include file="/WEB-INF/jsp/base/common_js.jsp"%>
<title>用户管理</title>
<script type="text/javascript">

//定义数据列表列
//field中的name对应datagrid中json数据的key
var v_columns=[
[
	{
		field:'userId',
		title:'用户账号',
		width:120
	},
	{
		field:'userName',
		title:'用户名称',
		width:120
	},
	{
		field:'userState',
		title:'用户状态',
		width:120,
		formatter:function (value) {
		    if(value){
		        return "正常";

			}else{
		        return "已禁用";
			}

        }
	},
	{
		field:'groupId',
		title:'用户类型',
		width:120,
		//formatter对单元格数据的格式化，因为jquery easyui框架去加载数据列表时第一个单元格加载时都调用此方法
		//value：单元格的数据,row：一行数据，是jquery easyui的对象，index：行的下标，从0开始
		 formatter: function(value,row,index){
			if(value==1){
				return "卫生局";
			}else if(value==2){
				return "卫生院";
			}else if(value==3){
				return "卫生室";
			}else if(value==4){
				return "供货商";
			}else if(value==0){
				return "系统管理员";
			}
		}
	},

	{
		field:'opt1',
		title:'删除',
		width:70,
		formatter: function(value,row,index){
			//生成一个连接
			return "<a href=javascript:deleteuser('"+row.id+"')>删除</a>";
			
		}
	},
	{
		field:'opt2',
		title:'修改',
		width:70,
		formatter: function(value,row,index){
			//生成一个连接
			return "<a href=javascript:edituser('"+row.id+"')>修改</a>";
			
		}
	}
	
]

];

//定义数据列表工具栏
var toolbar=[{//工具栏
	id:'btnadd',
	text:'添加',
	iconCls:'icon-add',
	handler:adduser
}];

//定义用户添加的js方法
function adduser(){
	
	//打开一个窗口，窗口的内容是用户添加action的页面
	//参数：窗口的title、宽、高、url地址
	createmodalwindow("添加用户信息", 800, 250, '${baseurl}user/adduser');
}
//修改用户
function edituser(id){
	//打开修改用户窗口
	createmodalwindow("修改用户信息", 800, 250, '${baseurl}user/edituser.action?id='+id);
}
//删除用户方法
function deleteuser(id){
	//确认删除
	
	//第一个参数是提示信息，第二个参数，取消执行的函数指针，第三个参是，确定执行的函数指针
	_confirm('您确认删除吗？',null,function (){

		//将要删除的id赋值给deleteid，deleteid在sysuserdeleteform中
		$("#delete_id").val(id);
		//使用ajax的from提交执行删除
		//sysuserdeleteform：form的id，userdel_callback：删除回调函数，
		//第三个参数是url的参数
		//第四个参数是datatype，表示服务器返回的类型
		jquerySubByFId('sysuserdeleteform',userdel_callback,null,"json");
		
		
	});
	
	
}
//用户删除的回调函数
function userdel_callback(data){
	
	message_alert(data);
	//在操作成功后重新查询一个列表刷新页面
	if(data.resultInfo.type==1){
		queryuser();
	}
	
}
$(function(){
	$('#sysuserlist').datagrid({
		title:'用户列表',//数据列表标题
		//width:100%,//数据列表的宽度
		//height:350,//列表的高度
		nowrap: false,//单元格中的数据不换行，如果为true表示不换行，不换行情况下数据加载性能高，如果为false就是换行，换行数据加载性能不高
		striped:true,//条纹显示效果
		loadMsg:"",//加载时jquery easyui不显示加载信息，使用自己的loading
		url:rootURl+'query_list',//加载数据的连接，引连接请求过来是json数据
		idField:'id',//此字段很重要，数据结果集的唯一约束，如果设置错误导致一些datagrid执行错误（获取选中行的方法）
		columns:v_columns,
		pagination:true,//是否显示分页
		rownumbers:true,//是否显示行号
		toolbar:toolbar//工具栏
	});
})

//查询方法
function queryuser(){
	
	/* $('#sysuserlist').datagrid('load',{
		//传的查询条件
		'sysuserCustom.userid': 'gcxqptc'
	}); */

	//需要将表单中的input的值自动拼接成json
	
	//获取from里边所有的对象及值，并组织成json格式
	var formdata = $("#sysuserqueryForm").serializeJson();
	 //根据查询重新加载datagrid，传入的参数是查询条件
	$('#sysuserlist').datagrid('load',formdata);

}


/*
//回调函数复习
//fun形参表示函数的地址(针对)
function test(fun){
	//通过fun指针调用指针指向的函数
	//alert(fun);
	
	//要执行fun指向的函数
	//fun();
	//通过调用test_3执行fun指向函数
	test_3(fun);
}

//test_1函数叫做回调函数，因为在test方法体中调用，通过fun指针调用的
//只要将回调函数的指针传一个函数体，函数体中可以通过无数层转发调用，通过这个指针调用指向的函数
function test_1(id){
	
	alert("test_1"+id);
}

var test_2 = function(){
	
	alert("test_2");
};

function test_3(fun){
	//通过fun指针fun指向的函数
	fun();
	
}
//将函数名称传入形参，将这个函数的指针（内存地址）传给了test方法
//test可以调用fun指向的函数
test(test_1);

//test_2就是变量，将变量传给test，test_2这个变量值就是函数的指针(地址)
test(test_2);

*/

</script>
</head>
<body>

<form id="sysuserqueryForm">
<TABLE class="table_search">
				<TBODY>
					<TR>
						<TD class="left">用户账号：</td>
						<td><INPUT type="text" name="sysuserCustom.userid" /></TD>
						<TD class="left">用户名称：</TD>
						<td><INPUT type="text" name="sysuserCustom.username" /></TD>
						
						<TD class="left">单位名称：</TD>
						<td><INPUT type="text" name="sysuserCustom.sysmc" /></TD>
						<TD class="left">用户类型：</TD>
						<td>
						
							<select name="sysuserCustom.groupid">
								<option value="">请选择</option>
								<c:forEach items="${grouplist}" var="dictinfo">
						          <option value="${dictinfo.dictcode}">${dictinfo.info }</option>
						        </c:forEach>
								
							</select>
						</TD>
						<td ><a id="btn" href="#" onclick="queryuser()"
							class="easyui-linkbutton" iconCls='icon-search'>查询</a></td>
					</TR>


				</TBODY>
			</TABLE>
			
			<TABLE border=0 cellSpacing=0 cellPadding=0 width="99%" align=center>
			<TBODY>
				<TR>
					<TD>
						<table id="sysuserlist"></table>
					</TD>
				</TR>
			</TBODY>
		   </TABLE>
</form>
<form id="sysuserdeleteform" action="${baseurl}user/deleteuser.action">
  <input type="hidden" id="delete_id" name="delete_id" /> 
</form>
</body>
</html>