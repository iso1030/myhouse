<#escape x as x?html>
<form class="form-horizontal" onsubmit="return false;" id="tourForm" method="POST" enctype="multipart/form-data" role="form">
	<input type="hidden" name="houseId" value="${house.id!"0"}"/>
  	<input type="hidden" id="coverImg" name="coverImg" value="${house.coverImg!""}"/>
  	<input type="hidden" name="uid" value="${house.uid!"0"}"/>
  	<div class="form-group">
		<label for="telephone" class="col-sm-2 control-label">详细地址</label>
		<div class="col-sm-8">
		  <input type="text" class="form-control" id="address" name="address" value="${house.address!""}" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<label for="realname" class="col-sm-2 control-label">价格</label>
		<div class="col-sm-8">
		  <input type="text" class="form-control" id="price" name="price" value="${house.price!""}" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<label for="email" class="col-sm-2 control-label">面积</label>
		<div class="col-sm-8">
		  <input type="email" class="form-control" id="area" name="area" value="${house.area!""}" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<label for="company" class="col-sm-2 control-label">厅室</label>
		<div class="col-sm-8">
		  <input type="text" class="form-control" id="bedrooms" name="bedrooms" value="${house.bedrooms!""}" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<label for="company" class="col-sm-2 control-label">拍摄公司</label>
		<div class="col-sm-8">
		  <input type="text" class="form-control" id="photographer" name="photographer" value="${house.photographer!""}" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<label for="company" class="col-sm-2 control-label">开放时间</label>
		<div class="col-sm-8">
		  <input type="text" class="form-control" id="openTime" name="openTime" value="<#if house.openTime gt 0>${house.openTime?number_to_date?string("MM/dd/yyyy")}</#if>" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<label for="fileupload" class="col-sm-2 control-label">封面图片</label>
		<div class="col-sm-8">
		  <input type="file" class="" id="fileupload" name="fileupload" data-url="/upload?type=cover">
		  <div class="progress" style="display:none;margin-top:10px;">
			<div id="progress" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
			  <span class="sr-only">60% Complete</span>
			</div>
		  </div>
		  <p class="help-block"><img id="preview"<#if house.coverImg?has_content> src="${house.coverImg}"</#if> style="width:180px;height:120px;"/></p>
		</div>
	</div>
	<div class="form-group">
		<label for="company" class="col-sm-2 control-label">描述</label>
		<div class="col-sm-8">
		  <textarea class="form-control" id="description" name="description" placeholder="less than 1000 character">${house.description!""}</textarea>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-8">
		  <button type="button" class="btn btn-default" id="saveInfoBtn">Save</button>
		</div>
	Í</div>
</form><!-- /.form -->
</#escape>