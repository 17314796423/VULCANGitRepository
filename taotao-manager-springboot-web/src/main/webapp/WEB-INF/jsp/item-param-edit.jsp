<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table style="margin-left: 30px" id="itemParamEdit" >
	<tr>
		<td id="itemCat">商品类目:
			<input type="hidden" name="cid"/>
		</td>
	</tr>
	<tr class="addGroup">
		<td>规格参数:</td>
		<td>
			<ul>
				<li>
					<a href="javascript:void(0);" class="easyui-linkbutton" id="addGroupButton">添加分组</a>
				</li>
			</ul>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<a href="javascript:void(0);" class="easyui-linkbutton" id="submit">提交</a>
	    	<a href="javascript:void(0);" class="easyui-linkbutton" id="close">关闭</a>
		</td>
	</tr>
</table>
<div  id="itemParamEditTemplate" style="display: none;">
	<li class="params">
		<ul>
			<li>
				<input type="text" style="width: 150px;" name="group" />
				&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton" id="addParam"  title="添加参数" data-options="iconCls:'icon-add'"></a>
			</li>
			<li>
				<span>|-------</span>
				<input type="text" style="width: 150px;" name="param"/>
				&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton" id="delParam" title="删除" data-options="iconCls:'icon-cancel'"></a>						
			</li>
		</ul>
	</li>
</div>
<script type="text/javascript">
	$(function(){
	
		$("#addGroupButton").click(function(){
			  var temple = $("#itemParamEditTemplate li").eq(0).clone();
			  $(this).parent().parent().append(temple);
			  temple.find("#addParam").click(function(){
				  var li = $("#itemParamEditTemplate li").eq(2).clone();
				  li.find("#delParam").click(function(){
					  $(this).parent().remove();
				  });
				  li.appendTo($(this).parentsUntil("ul").parent());
			  });
			  temple.find("#delParam").click(function(){
				  $(this).parent().remove();
			  });
		});
		 
		$("#itemParamEdit #close").click(function(){
			$(".panel-tool-close").click();
		});
		
		$("#itemParamEdit #submit").click(function(){
			var params = [];
			var groups = $("#itemParamEdit .addGroup [name='group']");
			groups.each(function(i,e){
				var p = $(e).parentsUntil("ul").parent().find("li [name='param']");
				var _ps = [];
				p.each(function(_i,_e){
					var _val = $(_e).siblings("input").val();
					if($.trim(_val).length>0){
						_ps.push(_val);						
					}
				});
				var _val = $(e).siblings("input").val();
				if($.trim(_val).length>0 && _ps.length > 0){
					params.push({
						"group":_val,
						"params":_ps
					});					
				}
			});
			var url = "/itemParam/update/"+$("#itemParamEdit [name='cid']").val();
			$.post(url,{"paramData":JSON.stringify(params)},function(data){
				if(data.status == 200){
					$.messager.alert('提示','编辑商品规格成功!',undefined,function(){
						$(".panel-tool-close").click();
    					$("#itemParamList").datagrid("reload");
    				});
				}
			});
		});
			 
	});
</script>