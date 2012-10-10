/**
 * Created by IntelliJ IDEA.
 * User: jeffrey.a.west
 * Date: Mar 1, 2011
 * Time: 6:58:16 PM
 * To change this template use File | Settings | File Templates.
 */

var $mfa;

$(document).ready(function ()
{
  applyTemplate();
});

function applyTemplate()
{
  $("#ops-main").addClass("pane ui-layout-center");

  $("#container").prepend("<div id=\"ops-header\"></div>");
  $("#ops-header").addClass("pane ui-layout-north");
  $('#ops-header').html("<h2>Oracle Parcel Service</h2>");

  $("#container").append("<div id=\"ops-sidenav\"></div>");
  $('#ops-sidenav').addClass("pane ui-layout-west");
  $.get('templates/sidenav.html',
        function(data)
        {
          $('#ops-sidenav').html(data);
        });

  $("#container").append("<div id=\"ops-footer\"></div>");
  $('#ops-footer').addClass("pane ui-layout-south");
  $('#ops-footer').html("<div class=\"footer\">&copy; 2011 - Oracle Corporation. This code and application are for example purposes only.</div>");

  //SAB - fix the initial sizing of the N/S panes
  $('#container').layout({
    applyDefaultStyles: true,
    north__size: 80,
    south__size: 50
  });
}

/*
 function resizeWidgets() {
 $sidenavAccordion.resize();
 }
 */

function addressOK(address)
{
  return address.addressee != ''
      && address.addressLine1 != ''
      && address.city != ''
      && address.state != ''
      && address.postalCode != '';

}

function getShipmentArrayTable(shipmentArray)
{
  var html = "<table border=\"1\"><tr>";
  html += "<th>ID</th>";
  html += "<th>External Reference Id</th>";
  html += "<th>Service</th>";
  html += "<th>From Address</th>";
  html += "<th>To Address</th>";
  html += "<th>Parcels</th></tr>";

  $.each(shipmentArray, function(index, element)
  {
    html += getShipmentTableRow(index, element)
  });

  html += "</table>";

  return html;
}

function getShipmentTableRow(rownum, shipment)
{
  var html = "<tr class=\"";
  if (rownum % 2 == 1)
  {
    html += "row-odd";
  }
  else
  {
    html += "row-even";
  }

  html+="\">";

  html += "<td>" + shipment.id + "</td>";
  html += "<td>" + shipment.externalReferenceId + "</td>";
  if (shipment.fromAddress != null)
  {
    html += "<td>" + getAddressTable(shipment.fromAddress) + "</td>";
  }
  else
  {
    html += "<td/>"
  }
  if (shipment.toAddress != null)
  {
    html += "<td>" + getAddressTable(shipment.toAddress) + "</td>";
  }
  else
  {
    html += "<td/>"
  }

  if (shipment.parcels != null)
  {
    html += "<td>" + getParcelArrayTable(shipment.parcels) + "</td>";
  }
  else
  {
    html += "<td/>";
  }

  html += "</tr>";

  return html;
}

function getParcelArrayTable(parcelArray)
{
  var html = "<table border=\"1\"><tr class=\"table-header\">";
  html += "<th>ID</th>";
  html += "<th>Contents</th>";
  html += "<th>Weight</th>";
  html += "<th>Height</th>";
  html += "<th>Width</th>";
  html += "<th>Length</th></tr>";

  $.each(parcelArray, function(index, element)
  {
    html += getParcelTableRow(element, index)
  });

  html += "</table>";

  return html;
}

function getParcelTableRow(parcel, index)
{
  var html = "<tr class=\"";
  if (index % 2 == 1)
  {
    html += "row-odd";
  }
  else
  {
    html += "row-even";
  }
  
  html+="\">";

  html += "<td>" + parcel.id + "</td>";
  html += "<td>" + parcel.contents + "</td>";
  html += "<td>" + parcel.weight + "</td>";
  html += "<td>" + parcel.height + "</td>";
  html += "<td>" + parcel.width + "</td>";
  html += "<td>" + parcel.length + "</td></tr>";

  return html;
}

function getAddressTable(address)
{
  var html = "<table border=\"1\"><tr>";

  html += "<tr class=\"row-odd\"><td>Addressee</td>";
  html += "<td>" + address.addressee + "</td></tr>";

  html += "<tr class=\"row-even\"><td>Address 1</td>";
  html += "<td>" + address.addressLine1 + "</td></tr>";

  html += "<tr class=\"row-odd\"><td>Address 2</td>";
  html += "<td>" + address.addressLine2 + "</td></tr>";

  html += "<tr class=\"row-even\"><td>City</td>";
  html += "<td>" + address.city + "</td></tr>";

  html += "<tr class=\"row-odd\"><td>State</td>";
  html += "<td>" + address.state + "</td></tr>";

  html += "<tr class=\"row-even\"><td>Postal Code</td>";
  html += "<td>" + address.postalCode + "</td></tr>";

  html += "</table>";

  return html;
}

function getParcelLogTable(parcelEvents)
{
  var html = "<table border=\"1\"><tr class=\"table-header\">";
  html += "<th>Event Id</th>";
  html += "<th>Parcel Id</th>";
  html += "<th>Parcel Status</th>";
  html += "<th>Location</th>";
  html += "<th>Message</th></tr>";

  $.each(parcelEvents, function(index, element)
  {
    html += getParcelLogRow(element)
  });

  html += "</table>";

  return html;
}

function getParcelLogRow(parcelEventLog)
{
  var html = "<tr>";

  html += "<td>" + parcelEventLog.id + "</td>";
  html += "<td>" + parcelEventLog.parcelId + "</td>";
  html += "<td>" + parcelEventLog.parcelStatus + "</td>";
  html += "<td>" + parcelEventLog.location + "</td>";
  html += "<td>" + parcelEventLog.message + "</td></tr>";

  return html;

}

function buildAddressJSON(addressee, addressLine1, addressLine2, city, state, postalCode)
{
  return {
    "addressee" : addressee,
    "addressLine1" : addressLine1,
    "addressLine2" : addressLine2,
    "city" : city,
    "state" : state,
    "postalCode" : postalCode
  };
}

function displayJSONonPage(jsonObject)
{
  $("#JSONDisplayArea").empty();
  $("#JSONDisplayArea").append(JSON.stringify(jsonObject));
}


function createShipment(shipment)
{
  var url = "/ops-rest/resource/shipment/create.json";

  $.ajax({
    type: 'PUT',
    url: url,
    dataType: 'json',
    contentType: 'application/json',
    data: JSON.stringify(shipment),
    success: function(data)
    {
      alert("Shipment Created Successtully!  ID=[" + data.id + "]");
    },
    error: function(jqXHR, textStatus, errorThrown)
    {
      alert("Create Shipment Failed: " + textStatus);
    },
    statusCode: {
      404: function()
      {
        alert('Page not found!');
      }
    }
  });
}

function getShipment(shipmentId)
{
  var url = "/ops-rest/resource/shipment" + "/" + shipmentId + ".json";

  shipment = $.ajax({
    type: 'get',
    url: url,
    dataType: 'json',
    contentType: 'application/json',
    success: function(data)
    {
      alert("Retrieved Shipment!  ID=[" + data.id + "]");
    },
    error: function(jqXHR, textStatus, errorThrown)
    {
      alert("Create Shipment Failed: " + textStatus);
    },
    statusCode: {
      404: function()
      {
        alert('Page not found!');
      }
    }
  });
}

function getParcelLog(parcelId, successCallback)
{
  var url = "/ops-rest/resource/parcel/log/" + parcelId + ".json";

  return $.ajax({
    type: 'get',
    url: url,
    dataType: 'json',
    contentType: 'application/json',
    success: successCallback,
    error: function(jqXHR, textStatus, errorThrown)
    {
      alert("Get Parcel Log Failed: " + textStatus);
    },
    statusCode: {
      404: function()
      {
        alert('Page not found!');
      }
    }
  });
}
function getParcelEvents(successCallback)
{
  var url = "/ops-rest/resource/parcel/log/list.json";

  return $.ajax({
    type: 'get',
    url: url,
    dataType: 'json',
    contentType: 'application/json',
    success: successCallback,
    error: function(jqXHR, textStatus, errorThrown)
    {
      alert("Get Parcel Log Failed: " + textStatus);
    },
    statusCode: {
      404: function()
      {
        alert('Page not found!');
      }
    }
  });
}

function getAllEntities(entityName, successCallback)
{
  var url = "/ops-rest/resource/" + entityName + "/list.json";

  return $.ajax({
    type: 'get',
    url: url,
    dataType: 'json',
    contentType: 'application/json',
    success: successCallback,
    error: function(jqXHR, textStatus, errorThrown)
    {
      alert("List Shipments Failed: " + textStatus);
    },
    statusCode: {
      404: function()
      {
        alert('Page not found!');
      }
    }
  });
}

function getShipmentPrototype()
{
  return {
    "externalReferenceId":null,
    "toAddress":
    {
      "addressee":"",
      "addressLine1":"",
      "addressLine2":null,
      "city":"","state":"",
      "postalCode":""
    },
    "fromAddress":
    {
      "addressee":"",
      "addressLine1":"",
      "addressLine2":null,
      "city":"",
      "state":"",
      "postalCode":""
    },
    "parcels":[]
  };
}

function addParcel(shipment, contents, weight, height, width, length, tableJQObject)
{
  var parcel = {
    "contents":contents,
    "weight"  :parseInt(weight),
    "height"  :parseInt(height),
    "width"   :parseInt(width),
    "length"  :parseInt(length)
  };

  shipment.parcels.push(parcel);


  if (tableJQObject != null)
  {
    var rowHTML = "<tr>";
    rowHTML += "<td>" + contents + "</td>";
    rowHTML += "<td>" + weight + "</td>";
    rowHTML += "<td>" + height + "</td>";
    rowHTML += "<td>" + width + "</td>";
    rowHTML += "<td>" + length + "</td>";
    rowHTML += "</tr>";

    $("#parcelTable").append(rowHTML);
  }
}