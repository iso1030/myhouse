<#escape x as x?html>
<form class="form-horizontal" onsubmit="return false;" id="musicForm" method="POST" enctype="multipart/form-data" role="form">
	<input type="hidden" name="houseId" value="${house.id!"0"}"/>
	<#list musics as music>
	<div class="radio">
	  <label>
	    <input type="radio" name="music" id="musicRadio" value="${music}">${music} <a href="${music}" target="_blank"><span class="glyphicon glyphicon-headphones"></span></a>
	  </label>
	</div>
	</#list>
	<div class="form-group">
		<div class="col-sm-8" style="padding-top:10px;">
		  <button type="button" class="btn btn-default" id="saveBtn">Save</button>
		</div>
	</div>
</form><!-- /.form -->
</#escape>