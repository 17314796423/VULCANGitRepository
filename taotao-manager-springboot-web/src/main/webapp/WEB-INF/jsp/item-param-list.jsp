<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="itemParamList" title="商品规格列表" >
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'id',width:60">ID</th>
        	<th data-options="field:'itemCatId',width:80">商品类目ID</th>
        	<th data-options="field:'itemCatName',width:100">商品类目</th>
            <th data-options="field:'paramData',width:300,formatter:formatItemParamData">规格(只显示分组名称)</th>
            <th data-options="field:'created',width:130,align:'center',formatter:TAOTAO.formatDateTime">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:TAOTAO.formatDateTime">更新日期</th>
        </tr>
    </thead>
</table>
<div id="itemParamEditWindow" class="easyui-window" style="padding:10px;width:30%;height:50%" data-options="modal:true,closed:true">
</div>
<script>
	
	function formatItemParamData(value , index){
		if(value != null){
			var json = JSON.parse(value);
			var array = [];
			$.each(json,function(i,e){
				array.push(e.group);
			});
			return array.join(",");
		}
		return '';
	}

    function getSelectionsIds(){
    	var itemList = $("#itemParamList");
    	var sels = itemList.datagrid("getSelections");
    	var ids = [];
    	for(var i in sels){
    		ids.push(sels[i].id);
    	}
    	ids = ids.join(",");
    	return ids;
    }
    
    var itemParamListToolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
        	$("#itemParamEditWindow").window({
        		href:'/item-param-add',
        		title:'添加商品规格',
        		iconCls:'icon-add'
        	}).window('open');
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','必须选择一个规格参数才能编辑!');
        		return ;
        	}
        	if(ids.indexOf(',') > 0){
        		$.messager.alert('提示','只能选择一个规格参数!');
        		return ;
			}
			$("#itemParamEditWindow").window({ //嵌套关系：组li 嵌套 ul 嵌套 （同一组的组li + 同一组的参数li）
				iconCls:'icon-edit',
				title:'编辑商品规格',
				href:'/item-param-edit',
				onLoad : function() {
					//回显数据
					var data = $("#itemParamList").datagrid("getSelections")[0];
					$("#itemParamEdit [name='cid']").val(data.itemCatId);
					$("#itemParamEdit #itemCat").next().text(data.itemCatName).attr("cid", data.itemCatId);
					var jsonObj = eval(data.paramData);
					if (jsonObj && jsonObj.length > 0) {
						//迭代group
						for (var i = 0; i < jsonObj.length; i++) {
							//克隆最大的li
							$("#itemParamEdit #addGroupButton").click();
							var temple = $("#itemParamEdit .addGroup .params").last();
							//删除最大li的第二个li，也就是参数的那个li
							temple.find("#delParam").click();
							//为最大的li的第一个li中的input赋值
							temple.find("input[name='group']").textbox({value:jsonObj[i].group});
							//判定是否有params
							if (jsonObj[i].params && jsonObj[i].params.length > 0) {
								//迭代group的params
								for (var j=0; j < jsonObj[i].params.length; j++) {
									//克隆最大的li的第二个li，也就是参数的那个li
									temple.find("#addParam").click();
									//为这个li内部input赋值
									temple.find("input[name='param']").last().textbox({value:jsonObj[i].params[j]});
								}
							}
						}
					}
				}
			}).window("open");
		}
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
        	var ids = getSelectionsIds();
        	if(ids.length == 0){
        		$.messager.alert('提示','未选中商品规格!');
        		return ;
        	}
        	$.messager.confirm('确认','确定删除ID为 '+ids+' 的商品规格吗？',function(r){
        	    if (r){
        	    	var params = {"ids":ids};
                	$.post("/itemParam/delete",params, function(data){
            			if(data.status == 200){
            				$.messager.alert('提示','删除商品规格成功!',undefined,function(){
            					$("#itemParamList").datagrid("reload");
            				});
            			}
            		});
        	    }
        	});
        }
    }];
    $(function(){
    	$("#itemParamList").datagrid({
    		toolbar:itemParamListToolbar,
    		singleSelect:false,
    		collapsible:true,
    		pagination:true,
    		url:'/itemParam/list',
    		method:'get',
    		pageSize:30
    	});
    });
</script>