document.write("<script src='/html/agloco/js/common.js'></script>");

var portletId = "";
var today = new Date();

function setPortletId(tmpId)
{
	portletId=tmpId;
}

function checkForm()
{
	//Null value checked!
	var requiredFields = "firstName,lastName,emailAddress,emailAddressCfm,city,captchaText,state";
	var requiredFieldsName = "your first name,your last name,your email,the confirm email,your city,the validate code,your state";
	var requiredFieldsArr = requiredFields.split(',');
	var requiredFieldsNameArr = requiredFieldsName.split(',');
	var obj;
	var objName;
	var flag = "true";
	var emailObj;
	for(idx=0;idx<requiredFieldsArr.length;idx++)
	{
		objName = portletId+requiredFieldsArr[idx];
		obj = document.getElementById(objName);
		if(isNull(obj))
		{
			flag = "Please enter "+requiredFieldsNameArr[idx]+"!";
			return flag;
		}
		else if(objName.indexOf("emailAddress")>0)
		{
			if(objName.indexOf("emailAddressCfm")<0)
				emailObj = obj;
			if(!isEmail(obj.value))
			{
				flag = "Please provide a valid email address!";
				return flag;
			}else if(objName.indexOf("emailAddressCfm"))
			{
				if(obj.value!=emailObj.value)
				{
					flag = "The email and confirm email should be equal!";
					return flag;
				}	
			}
		}
	}

	//Year of birth
	objName = portletId+'birthDate';
	obj = document.getElementById(objName);
	if(!isDigit(obj.value) || obj.value > today.getYear() || obj.value<1900)
	{
		flag = "Please enter a valid year!";
		return flag
	}
	
	obj = document.getElementById(portletId+'country');
	if(obj.value == null || obj.value =="SELECT" || obj.selectedIndex == 0)
	{
		flag = "Please select your country!";
		return flag;
	}
	
	obj = document.getElementById(portletId+'postCode');
	if(obj.value == "" || obj.value =="None if not applicable")
	{
		if(needPost())
		{
			flag = "Please enter your ZIP/Post code!";
			return flag;
		}
	}
	
	obj = document.getElementById(portletId+'agree');
	if(!obj.checked)
	{
		flag = "You must agree the Terms of service and Privacy policy!";
		return flag;
	}
	return flag;
}

function needPost()
{
	var needPostC = "Afghanistan,Belize,Colombia,East Timor,Hong Kong,Macau,Namibia,Panama";
	var countryObj = document.getElementById(portletId+'country');
	if(needPostC.indexOf(countryObj.options[countryObj.selectedIndex].text)>0)
	{
		return false;
	}
	return true;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
