var xmlHttp;
var name;
var points;
var id;
var deleteID;
var EMP_PREFIX = "emp-";
var insertTable = "<table id='addTab'><tr><td><b>Name:</b></td><td><input id='name' value=''></td></tr>"
				+ "<tr><td><b>Points:</b></td><td><input id='points' value=''></td>"
				+ "</tr></table>";

function createXMLHttpRequest() {
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();

		if (xmlHttp.overrideMimeType) {
			xmlHttp.overrideMimeType("text/html");
		}
	}
	else if (window.ActiveXObject) {
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e) {
			try {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e) {
				try {
					xmlHttp = new XMLHttpRequest();
				}
				catch (e) {
				}
			}
		}
	}
}

function cancelAdd()
{
	var insertarea = document.getElementById("insertarea");
	insertarea.innerHTML = "";
	var cancelButton = document.getElementById("cancelButton");
	cancelButton.style.display="none";
	var addButton = document.getElementById("addButton");
	addButton.value = "add";
	document.getElementById("information").innerHTML = "";
}

function addFoodItem()
{
	var insertarea = document.getElementById("insertarea");
	var addButton = document.getElementById("addButton");
	if(insertarea.innerHTML=="")
	{
		document.getElementById("information").innerHTML= "Input the Item information!";
		insertarea.innerHTML = insertTable;
		var cancelButton = document.getElementById("cancelButton");
		cancelButton.style.display="";
		return;
	}
	
    name = document.getElementById("name").value;
    points = document.getElementById("points").value;
    action = addButton.value;
    
    if(name == "" || points == "") {
    	alert("Input the information!");
        return;
    }

    createXMLHttpRequest();
    var url = serverUrl+"/hibernate_ajax_servlet?" 
         + "ts=" + new Date().getTime();
    url = url+ "&name="+name+"&points="+points+"&action="+action
    xmlHttp.onreadystatechange = handleAddStateChange;
    if(action == "edit")
    {
    	url = url + "&id="+id;
	    xmlHttp.onreadystatechange = handleUpdateStateChange;
    }
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}
    
function handleAddStateChange() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
            addItemIntoList();
        }
        else {
        	document.getElementById("information").innerHTML="<font color=red>Add item unsuccessfully!</font>";
//            alert("Error while adding item.");
        }
    }
}

function addItemIntoList() {
    var responseXML = xmlHttp.responseXML;
    
    var status = responseXML.getElementsByTagName("status").item(0).firstChild.nodeValue;
    status = parseInt(status);
    if(status != 1) {
        document.getElementById("information").innerHTML="<font color=red>Add item unsuccessfully!</font>";
        return;
    }
    else
    {
    	with(document)
    	{
    		var addButton = getElementById("addButton").value;
    		addButton.value = "add";
    		getElementById("name").value = "";
    		getElementById("points").value = "";
    		getElementById("information").innerHTML="<font color=green>Add item successfully!</font>";
    	}
    }
    var foodItemId = responseXML.getElementsByTagName("foodItemId").item(0).firstChild.nodeValue;
   
    var row = document.createElement("tr");
    row.setAttribute("id", "row_"+foodItemId);
    
    row.appendChild(createCellWithText(foodItemId));
    row.appendChild(createCellWithText(name));
    row.appendChild(createCellWithText(points));
    
    var deleteButton = document.createElement("input");
    deleteButton.setAttribute("type", "button");
    deleteButton.setAttribute("value", "Delete");
    deleteButton.setAttribute("id", foodItemId);
    deleteButton.setAttribute("class", "portlet-form-button");
    deleteButton.onclick = function () { deleteItem(this); };
    
    var editButton = document.createElement("input");
    editButton.setAttribute("type", "button");
    editButton.setAttribute("value", "Edit");
    editButton.setAttribute("id", "edit_"+foodItemId);
    editButton.setAttribute("class", "portlet-form-button");
    editButton.onclick = function () { updateItem(foodItemId); };
    cell = document.createElement("td");
    cell.appendChild(editButton);
    cell.appendChild(deleteButton);
    row.appendChild(cell);
    
    document.getElementById("itemlist").appendChild(row);
    updateItemListVisibility();
}
   
function updateItem(tmpId)
{
	id = tmpId
	var insertarea = document.getElementById("insertarea");
	var rowToUpdate = document.getElementById("row_"+id);
	with(document)
	{
		if(getElementById("addButton").value != "edit")
		{
			if(insertarea.innerHTML == "")
			{
				insertarea.innerHTML = insertTable;
				var cancelButton = getElementById("cancelButton");
			    getElementById("name").value = rowToUpdate.cells[1].innerHTML;
			    getElementById("points").value = rowToUpdate.cells[2].innerHTML;
				cancelButton.style.display="";
				getElementById("addButton").value = "edit";
				getElementById("information").innerHTML= "Update item. ID:"+tmpId;
			}
			else
			{
			    getElementById("name").value = rowToUpdate.cells[1].innerHTML;
			    getElementById("points").value = rowToUpdate.cells[2].innerHTML;
				getElementById("addButton").value = "edit";
				getElementById("information").innerHTML= "Update item. ID:"+tmpId;
			}
		}
		else
		{
			getElementById("name").value = rowToUpdate.cells[1].innerHTML;
			getElementById("points").value = rowToUpdate.cells[2].innerHTML;
			getElementById("information").innerHTML= "Update item. ID:"+tmpId;
		}
	}
	return;
}
    
function handleUpdateStateChange() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
            updateItemFromList();
        }
        else {
        	document.getElementById("information").innerHTML="<font color=red>Update item unsuccessfully!</font>";
//            alert("Error while adding item.");
        }
    }
}
    
function updateItemFromList() {
    var responseXML = xmlHttp.responseXML;
    
    var status = responseXML.getElementsByTagName("status").item(0).firstChild.nodeValue;
    status = parseInt(status);
    if(status != 1) {
        document.getElementById("information").innerHTML="<font color=red>Update item unsuccessfully!</font>";
        return;
    }
    else
    {
    	with(document)
    	{
    		var addButton = getElementById("addButton");
    		addButton.value = "add";
    		getElementById("name").value = "";
    		getElementById("points").value = "";
    		getElementById("information").innerHTML="<font color=green>Update item successfully!</font>";
    	}
    }
   
    var rowToUpdate = document.getElementById("row_"+id);
    rowToUpdate.cells[1].innerHTML = name;
	rowToUpdate.cells[2].innerHTML = points;
    
    updateItemListVisibility();
}

function deleteItem(obj) {
    deleteID = obj.id;
    
    var url = serverUrl+"/hibernate_ajax_servlet?" 
        + "action=delete" 
        + "&id=" + deleteID;
    
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleDeleteStateChange;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
}

function handleDeleteStateChange() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
            deleteItemFromList();
        	document.getElementById("information").innerHTML="<font color=green>Delete item successfully!</font>";
        }
        else {
        	document.getElementById("information").innerHTML="<font color=red>Delete item unsuccessfully!</font>";
//            alert("Error while deleting employee.");
        }
    }

}

function deleteItemFromList() {
    var status = xmlHttp.responseXML.getElementsByTagName("status").item(0).firstChild.nodeValue;
    status = parseInt(status);
    if(status != 1) {
        return;
    }

    var deleteButton = document.getElementById(deleteID);
    var rowToDelete = document.getElementById('row_' + deleteID);
    var tab = document.getElementById("itemlist");
    tab.removeChild(rowToDelete);
    
    updateItemListVisibility();
}

function createCellWithText(text) {
    var cell = document.createElement("td");
    cell.appendChild(document.createTextNode(text));
    return cell;
}

function updateItemListVisibility() {
    var employeeList = document.getElementById("tab");
    if(employeeList.childNodes.length > 0) {
        document.getElementById("itemlist").style.display = "";
    }
    else {
        document.getElementById("itemlist").style.display = "none";
    }
}