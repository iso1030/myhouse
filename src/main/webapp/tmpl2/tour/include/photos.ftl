<#escape x as x?html>
<div class="row">
  <form class="form-inline" onsubmit="return false;" id="tourForm" method="POST" enctype="multipart/form-data" role="form">
  	<input type="hidden" name="houseId" value="${house.id!"0"}"/>
  	<div class="form-group">
		<label for="fileupload" class="control-label">上传打包图片</label>
		<input type="file" class="form-control" id="fileupload" name="fileupload" data-url="/upload/timages?houseId=${house.id!"0"}&type=2">
	</div>
  </form><!-- /.form -->
  <div class="col-sm-6" style="margin-top:10px;">
	  <div class="progress" style="display:none;">
		<div id="progress" class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">
		  <span class="sr-only">60% Complete</span>
	    </div>
	  </div>
  </div>
</div>
<br/>
<div class="row" id="sortable">
  <#if d2images?exists>
  <#list d2images as i>
  <div class="col-sm-6 col-md-2" id="photo${i.id}">
    <div class="thumbnail">
      <img data-id="${i.id}" data-name="${i.name}" data-url="${i.url}" data-src="${i.thumbnail}"
      	alt="${i.name}" style="width:180px;height:120px;" src="${i.thumbnail}">
      <div class="caption">
        <p style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">${i.name}</p>
        <p><button type="button" id="delBtn${i.id}" data-id="${i.id}" class="btn btn-primary btn-xs">Delete</button> <a href="${i.url}" class="btn btn-default btn-xs" target="_blank">Origin</a></p>
      </div>
    </div>
  </div>
  </#list>
  </#if>
</div>
<br/>
<div class="row" <#if !d2images?exists || d2images?size lt 1>style="display:none;"</#if>>
  <button type="button" class="btn btn-default" id="saveBtn">Save</button>
</div>
<script id="template" type="x-tmpl-mustache">
  <div class="col-sm-6 col-md-2" id="photo{{id}}">
    <div class="thumbnail">
      <img data-id="{{id}}" data-name="{{name}}" data-url="{{url}}" data-src="{{thumbnail}}"
      	alt="{{name}}" style="width:180px;height:120px;" src="{{thumbnail}}">
      <div class="caption">
        <p title="{{name}}" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;">{{name}}</p>
        <p><button type="button" id="delBtn{{id}}" data-id="{{id}}" class="btn btn-primary btn-xs">Delete</button> <a href="{{url}}" class="btn btn-default btn-xs" target="_blank">Origin</a></p>
      </div>
    </div>
  </div>
</script>
</#escape>