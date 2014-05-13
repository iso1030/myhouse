<#escape x as x?html>
<form class="form-horizontal" onsubmit="return false;" id="videoForm" method="POST" enctype="multipart/form-data" role="form">
	<input type="hidden" name="houseId" value="${house.id!"0"}"/>
  	<div class="form-group">
		<label for="youtube" class="col-sm-3 control-label">http://www.youtube.com/watch?v=</label>
		<div class="col-sm-8">
		  <input type="text" class="form-control" id="youtube" name="youtube" value="${house.youtube!""}" placeholder="">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-8" style="padding-top:10px;">
		  <button type="button" class="btn btn-default" id="saveBtn">Save</button>
		</div>
	</div>
</form><!-- /.form -->
</#escape>