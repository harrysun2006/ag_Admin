var Obj='';
document.onmouseup=MUp;
document.onmousemove=MMove;
var pX;
var pY;
function MDown(Object)
{
	if (document.uniqueID)
	{
		Obj=Object.id
		document.all(Obj).setCapture()
		pX=event.x-document.all(Obj).style.pixelLeft;
		pY=event.y-document.all(Obj).style.pixelTop;
	}
	else
	{
		Obj=Object.id
		document.captureEvents(Event.MOUSEEVENT | Event.MOUSEDOWN);  
//		document.all(Obj).setCapture()
		pX=Event.x-document.all(Obj).style.pixelLeft;
		pY=Event.y-document.all(Obj).style.pixelTop;
	}
}
function MMove()
{
	if (document.uniqueID)
	{
		if(Obj!='')
		{
			document.all(Obj).style.left=event.x-pX;
			document.all(Obj).style.top=event.y-pY;
		}
	}
	else
	{
		if(Obj!='')
		{
			document.all(Obj).style.left=Event.x-pX;
			document.all(Obj).style.top=Event.y-pY;
		}
	}
}
function MUp()
{
	if (document.uniqueID)
	{
		if(Obj!='')
		{
			document.all(Obj).releaseCapture();
			Obj='';
		}
	}
	else
	{
			document.captureEvents(Event.MOUSEEVENT | Event.MOUSEUP);  
			Obj='';		
	}
}

