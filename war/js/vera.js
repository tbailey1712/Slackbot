
// All Bright 23 Warm 22 Normal 54

// Audio on 53 
	var xmlhttp;
	xmlhttp=new XMLHttpRequest();
	var urlbase = "http://192.168.1.88:3480";

	var switchon = urlbase + "/data_request?id=action&output_format=xml&serviceId=urn:upnp-org:serviceId:SwitchPower1&action=SetTarget&newTargetValue=1&DeviceNum="
	var switchoff = urlbase + "/data_request?id=action&output_format=xml&serviceId=urn:upnp-org:serviceId:SwitchPower1&action=SetTarget&newTargetValue=0&DeviceNum="

	var sceneurl = urlbase + "/data_request?id=action&serviceId=urn:micasaverde-com:serviceId:HomeAutomationGateway1&action=RunScene&SceneNum="

function sendURL(geturl)
{
	xmlhttp.open("GET",geturl,true);
	xmlhttp.send();
}

function audio_on()
{
	url = sceneurl + "53";
	sendURL(url);
}

function porch_on()
{
//	url= switchon + "34";
//	sendURL(url);
	url= switchon + "131";
	sendURL(url);
}

function porch_off()
{
	url= switchoff + "34";
	sendURL(url);
//	url= switchoff + "131";
//	sendURL(url);
}

function backyard_on()
{
	url= switchon + "127";
	sendURL(url);
	url= switchon + "128";
	sendURL(url);
}

function backyard_off()
{
	url= switchoff + "127";
	sendURL(url);
	url= switchoff + "128";
	sendURL(url);
}

function main_on()
{
	url= switchon + "6";
	sendURL(url);
}

function main_off()
{
	url= switchoff + "6";
	sendURL(url);
}

function main_bright()
{
	url= sceneurl + "50";
	sendURL(url);
	url= sceneurl + "9";
	sendURL(url);
}

function main_normal()
{
	url= sceneurl + "54";
	sendURL(url);
}

function main_warm()
{
	url= sceneurl + "7";
	sendURL(url);
	url= sceneurl + "13";
	sendURL(url);
}

function kitchen_on()
{
	url= switchon + "7";
	sendURL(url);
}

function kitchen_off()
{
	url= switchoff + "7";
	sendURL(url);
}
