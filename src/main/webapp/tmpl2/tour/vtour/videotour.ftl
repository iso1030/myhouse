<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title>House Tour</title>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<link rel="stylesheet" href="/static/styles/style.css"/>
</head>
<body>
  <script src="/static/js/swfobject.js"></script>
  <div id="ytapiplayer">
    You need Flash player 8+ and JavaScript enabled to view this video.
  </div>

  <script type="text/javascript">
    var params = { allowScriptAccess: "always" };
    var atts = { id: "myytplayer" };
    swfobject.embedSWF("http://www.youtube.com/v/${house.youtube}?enablejsapi=1&playerapiid=ytplayer&autoplay=1", 
                       "ytapiplayer", "960", "500", "8", null, null, params, atts);

  </script>
  <script>top.loaded();</script>
</body>
</html>