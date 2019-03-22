<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">  </ul>
</div>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	$("#contentCategory").tree({
		url : '/contentCategory/list',
		animate: true,
		method : "GET",
		onContextMenu: function(e,node){
            e.preventDefault();
            $(this).tree('select',node.target); //target表示该右击节点的dom对象
            $('#contentCategoryMenu').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        onAfterEdit : function(node){
        	var _tree = $(this);
        	if(node.id == 0){
        		// 新增节点
        		$.post("/contentCategory/create",{parentId:node.parentId,name:node.text},function(data){
        			if(data && data.status == 200){
        				_tree.tree("update",{
            				target : node.target,
            				id : data.data.id
            			});
        			}else{
        				$.messager.alert('提示',data.msg);
        				_tree.tree("remove",node.target);
        			}
        		});
        	}else{
        		$.post("/contentCategory/update",{id:node.id,name:node.text},function(data){
        			if(data && data.status == 200){
        				
        			}else{
        				$.messager.alert('提示','服务器繁忙，稍后再试！');
        			}
        		});
        	}
        }
	});
});
function menuHandler(item){
	var tree = $("#contentCategory");
	var node = tree.tree("getSelected");
	if(item.name === "add"){
		var a = null;
		$.ajax(			//判定该节点是否有内容，如果有内容不允许在该节点创建子节点
				{
				   type: "POST",
				   url: "/contentCategory/hasContent",
				   data: "id="+node.id,
				   async: false,
				   success: function(msg){
				     a = msg;
				   }
				}

		);
		if(a){
			tree.tree('append', {
	            parent: (node?node.target:null),
	            data: [{
	                text: '新建分类',
	                id : 0,
	                parentId : node.id
	            }]
	        }); 
			var _node = tree.tree('find',0);//根节点
			tree.tree("select",_node.target).tree('beginEdit',_node.target);
		}else{
			$.messager.alert('提示','该分类已存在内容，请先删除该分类对应的内容！');
		}
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/contentCategory/delete",{id:node.id},function(data){
					if(data.status == 200)
						tree.tree("remove",node.target);
					else{
						$.messager.alert("提示",data.msg);
					}
				});	
			}
		});
	}
}
</script>