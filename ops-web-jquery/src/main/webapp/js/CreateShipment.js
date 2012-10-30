function from(){
    
    document.getElementById("from_addressee_h").value =  $("#from_addressee").val();
    document.getElementById("from_addressLine1_h").value = $("#from_addressLine1").val();
    document.getElementById("from_addressLine2_h").value =  $("#from_addressLine1").val();
    document.getElementById("from_city_h").value =  $("#from_city").val();
    document.getElementById("from_state_h").value = $("#from_state").val();
    document.getElementById("from_postalCode_h").value = $("#from_postalCode").val();
    var reason = "";
    reason += validateEmpty(from_addressee);
    reason += validateEmpty(from_addressLine1);
    reason += validateEmpty(from_postalCode);
    if (reason == "") {
        $("#from_address_table").replaceWith("<table id='to_address_table' ><tr><th colspan='2'> Receipient  Information</th></tr><tr><td><label for='to_addressee'>In Care of: *</label></td><td><input type='text' name='to_addressee' id='to_addressee' onblur='validateEmpty(to_addressee)'/></td></tr><tr><td><label for='to_addressLine1'>Address Line 1: *</label></td><td><input type='text' name='to_addressLine1' id='to_addressLine1' onblur='validateEmpty(to_addressLine1)'/></td></tr><tr><td><label for='to_addressLine2'>Address Line 2:</label></td><td><input type='text' name='to_addressLine2' id='to_addressLine2'/></td></tr><tr><td><label for='to_postalCode'>Postal Code :</label></td><td><input type='text' name='to_postalCode' id='to_postalCode' onblur='validateEmpty(to_postalCode)'/></td></tr><tr><td></td><td><input type='button' value='Validate' id='validate' style='background-color: #A80329;' onclick='to_validation()'/></td></tr><tr><td><label for='to_city'>City :</label></td><td><input type='text' name='to_city' id='to_city' readonly='readonly'/></td></tr><tr><td><label for='to_state'>State :</label></td><td><input type='text' name='to_state' id='to_state' readonly='readonly'/></td></tr><tr><td><img src='image/back-icon.png' style='height: 40px; width: 40px;' onclick='fromback()'/></td><td><img src='image/next-icon.png' style='height: 40px; width: 40px;position: relative;left: 100px;' onclick='to()'/></td></tr></table>");
        $("#from_image").replaceWith("<image id='from_image' src='image/to.jpg' style='height: 300px; width: 400px;'/>"); 
    }
  
   document.getElementById("sender").style.backgroundImage="url('image/DIVIDER.gif')" ; 
   document.getElementById("receiver").style.backgroundImage="url('image/HOVER.gif')" ;
}
function to(){
    document.getElementById("to_addressee_h").value =  $("#to_addressee").val();
    document.getElementById("to_addressLine1_h").value =  $("#to_addressLine1").val();
    document.getElementById("to_addressLine2_h").value = $("#to_addressLine2").val();
    document.getElementById("to_city_h").value =  $("#to_city").val();
    document.getElementById("to_state_h").value =  $("#to_state").val();
    document.getElementById("to_postalCode_h").value =  $("#to_postalCode").val();
    
    var reason = "";
    reason += validateEmpty(to_addressee);
    reason += validateEmpty(to_addressLine1);
    reason += validateEmpty(to_postalCode);
    if (reason == "") {
        $("#to_address_table").replaceWith("<table id='service_input_table' ><tr><th colspan='2'>Shipment Service</th></tr><tr><td><label for='shipping_service_name'>Shipping Service Name :</label></td><td width='120px'><input type='radio' name='shipping_service_name' value='BASIC' onclick='shippingserviceB()' checked>BASIC<br><input type='radio' name='shipping_service_name' value='EXPEDITED' onclick='shippingserviceE()'>EXPEDITED</br><input type='radio' name='shipping_service_name' value='OVERNIGHT' onclick='shippingserviceO()'>OVERNIGHT</br></td></tr><tr><td><table id='service_info' border='1'><tr><th colspan='2'>Service Detail</th></tr><tr><td>Cost</td><td>10</td></tr><tr><td>Delivery Estimate</td><td>3 Days</td></tr><tr><td>Description</td><td>Basic Service</td></tr></table></td></tr><tr><td><img src='image/back-icon.png' style='height: 40px; width: 40px;' onclick='toback()'/></td><td><img src='image/next-icon.png' style='height: 40px; width: 40px;position: relative;left: 80px;' onclick='service()'/></td></tr></table>"); 
        $("#from_image").replaceWith("<image id='from_image' src='image/shippingService/BASIC.jpg' style='height: 300px; width: 400px;'/>");
    }
   document.getElementById("receiver").style.backgroundImage="url('image/DIVIDER.gif')";
   document.getElementById("shipping").style.backgroundImage="url('image/HOVER.gif')" ;
}
function service(){
    for (var i=0; i < document.createshipment.shipping_service_name.length; i++)
    {
        if (document.createshipment.shipping_service_name[i].checked)
        {
            var rad_val = document.createshipment.shipping_service_name[i].value;
        }
    }
   
    document.getElementById("shipping_service_name_h").value = rad_val;
   
    $("#service_input_table").replaceWith("<table id='parcel_input_table' ><tr><th colspan='2'>Parcel Detail</th></tr><tr><td><label for='parcel_contents'>Contents : *</label></td><td><input type='text' name='parcel_contents' id='parcel_contents' onblur='validateEmpty(parcel_contents)'/></td></tr><tr><td><label for='parcel_weight'>Weight :</label></td><td><input type='text' name='parcel_weight' id='parcel_weight' onblur='validateEmpty(parcel_weight)'/></td></tr><tr><td><label for='parcel_height'>Hight :*</label></td><td><input type='text' name='parcel_height' id='parcel_height' onblur='validateEmpty(parcel_height)'/></td></tr><tr><td><label for='parcel_width'>Width :</label></td><td><input type='text' name='parcel_width' id='parcel_width' onblur='validateEmpty(parcel_width)'/></td></tr><tr><td><label for='parcel_length'>Length :</label></td><td><input type='text' name='parcel_length' id='parcel_length' onblur='validateEmpty(parcel_length)'/></td></tr><tr><td><img src='image/back-icon.png' style='height: 40px; width: 40px;' onclick='service_back()'/></td><td><input type='submit' value='Submit' style='background-color: #74b0c6;font-family:Helvetica,Arial,Verdana,sans-serif;position:relative;left:100px;'/></td></tr></table>"); 
   document.getElementById("shipping").style.backgroundImage="url('image/DIVIDER.gif')";
   document.getElementById("parcel").style.backgroundImage="url('image/HOVER.gif')" ;
}
function validateEmpty(fld) {
    var error = "";
  
    if (fld.value.length == 0) {
        fld.style.background = 'Yellow'; 
        error = "The required field :";
        alert(error+fld.name+" has not been filled in");
    } else {
        fld.style.background = 'White';
        
       
    }
    return error;   
}

function shippingserviceB(){
    $("#from_image").replaceWith("<image id='from_image' src='image/shippingService/BASIC.jpg' style='height: 300px; width: 400px;'/>");
    $("#service_info").replaceWith("<table id='service_info' border='1'><tr><th colspan='2'>Service Detail</th></tr><tr><td>Cost</td><td>10</td></tr><tr><td>Delivery Estimate</td><td>3 Days</td></tr><tr><td>Description</td><td>Basic Service</td></tr></table>");
}
function shippingserviceE(){
    $("#from_image").replaceWith("<image id='from_image' src='image/shippingService/EXPEDITED.jpg' style='height: 300px; width: 400px;'/>");
    $("#service_info").replaceWith("<table id='service_info' border='1'><tr><th colspan='2'>Service Detail</th></tr><tr><td>Cost</td><td>25</td></tr><tr><td>Delivery Estimate</td><td>2 Days</td></tr><tr><td>Description</td><td>Expedited Service</td></tr></table>");
}
function shippingserviceO(){
    $("#from_image").replaceWith("<image id='from_image' src='image/shippingService/OVERNIGHT.jpg' style='height: 300px; width: 400px;'/>");
    $("#service_info").replaceWith("<table id='service_info' border='1'><tr><th colspan='2'>Service Detail</th></tr><tr><td>Cost</td><td>100</td></tr><tr><td>Delivery Estimate</td><td>1 Days</td></tr><tr><td>Description</td><td>Overnight Service</td></tr></table>");
}
function fromback(){
    $("#to_address_table").replaceWith("<table id='from_address_table' ><tr><th colspan='2' style='alignment-adjust: middle;'>Sender Information</th></tr><tr><td><label for='from_addressee'>In Care of: *</label></td><td><input type='text' name='from_addressee' id='from_addressee' onblur='validateEmpty(from_addressee)'/></td></tr> <tr>  <td><label for='from_addressLine1'>Address Line 1: *</label></td>    <td><input type='text' name='from_addressLine1' id='from_addressLine1' onblur='validateEmpty(from_addressLine1)'/></td>  </tr> <tr> <td><label for='from_addressLine2'>Address Line 2:</label></td> <td><input type='text' name='from_addressLine2' id='from_addressLine2' /></td></tr><tr> <td><label for='from_postalCode'>Postal Code :</label></td><td><input type='text' name='from_postalCode' id='from_postalCode' onblur='validateEmpty(from_postalCode)'/></td></tr><tr><td></td><td><input type='button' value='Validate' id='validate' style='background-color: #A80329;' onclick='from_validation()'/></td></tr><tr><td><label for='from_city'>City : </label></td><td><input type='text' name='from_city' id='from_city' readonly='readonly'/></td></tr><tr><td><label for='from_state'>State :</label></td><td><input type='text' name='from_state' id='from_state' readonly='readonly'/></td></tr><tr><td></td><td><img src='image/next-icon.png' style='height: 40px; width: 40px;position: relative;left: 100px;' onclick='from()'/></td></tr></table>");
    $("#from_image").replaceWith("<image id='from_image' src='image/from.jpg' style='height: 300px; width: 400px;'/>"); 
     document.getElementById("receiver").style.backgroundImage="url('image/DIVIDER.gif')" ; 
   document.getElementById("sender").style.backgroundImage="url('image/HOVER.gif')" ;
}
function toback(){
    $("#service_input_table").replaceWith("<table id='to_address_table' ><tr><th colspan='2'> Receipient  Information</th></tr><tr><td><label for='to_addressee'>In Care of: *</label></td><td><input type='text' name='to_addressee' id='to_addressee' onblur='validateEmpty(to_addressee)'/></td></tr><tr><td><label for='to_addressLine1'>Address Line 1: *</label></td><td><input type='text' name='to_addressLine1' id='to_addressLine1' onblur='validateEmpty(to_addressLine1)'/></td></tr><tr><td><label for='to_addressLine2'>Address Line 2:</label></td><td><input type='text' name='to_addressLine2' id='to_addressLine2'/></td></tr><tr><td><label for='to_postalCode'>Postal Code :</label></td><td><input type='text' name='to_postalCode' id='to_postalCode' onblur='validateEmpty(to_postalCode)'/></td></tr><tr><td></td><td><input type='button' value='Validate' id='validate' style='background-color: #A80329;' onclick='to_validation()'/></td></tr><tr><td><label for='to_city'>City :</label></td><td><input type='text' name='to_city' id='to_city' readonly='readonly'/></td></tr><tr><td><label for='to_state'>State :</label></td><td><input type='text' name='to_state' id='to_state' readonly='readonly'/></td></tr><tr><td><img src='image/back-icon.png' style='height: 40px; width: 40px;' onclick='fromback()'/></td><td><img src='image/next-icon.png' style='height: 40px; width: 40px;' onclick='to()'/></td></tr></table>");  
    $("#from_image").replaceWith("<image id='from_image' src='image/to.jpg' style='height: 300px; width: 400px;'/>");
    document.getElementById("shipping").style.backgroundImage="url('image/DIVIDER.gif')";
   document.getElementById("receiver").style.backgroundImage="url('image/HOVER.gif')" ;
}
function service_back(){
    $("#parcel_input_table").replaceWith("<table id='service_input_table' ><tr><th colspan='2'>Shipment Service</th></tr><tr><td><label for='shipping_service_name'>Shipping Service Name :</label></td><td><input type='radio' name='shipping_service_name' value='BASIC' onclick='shippingserviceB()' checked>BASIC<br><input type='radio' name='shipping_service_name' value='EXPEDITED' onclick='shippingserviceE()'>EXPEDITED</br><input type='radio' name='shipping_service_name' value='OVERNIGHT' onclick='shippingserviceO()'>OVERNIGHT</br></td></tr><tr><td><table id='service_info' border='1'><tr><th colspan='2'>Service Detail</th></tr><tr><td>Cost</td><td>10</td></tr><tr><td>Delivery Estimate</td><td>3 Days</td></tr><tr><td>Description</td><td>Basic Service</td></tr></table></td></tr><tr><td><img src='image/back-icon.png' style='height: 40px; width: 40px;' onclick='toback()'/></td><td><img src='image/next-icon.png' style='height: 40px; width: 40px;' onclick='service()'/></td></tr></table>");  
    $("#from_image").replaceWith("<image id='from_image' src='image/shippingService/BASIC.jpg' style='height: 300px; width: 400px;'/>");
      document.getElementById("parcel").style.backgroundImage="url('image/DIVIDER.gif')";
   document.getElementById("shipping").style.backgroundImage="url('image/HOVER.gif')" ;
}
function from_validation(){
    var postal_code = $("#from_postalCode").val();
    $.get("/ops-rest/resources/geolocation/" + postal_code, {} ,
        function(response) { 
            document.getElementById("from_city").value = response.City;
            document.getElementById("from_state").value = response.State;
           
           // alert(response.State);
              },"json"); 
}
function to_validation(){
    var postal_code = $("#to_postalCode").val();
    $.get("/ops-rest/resources/geolocation/" + postal_code, {} ,
        function(response) { 
            document.getElementById("to_city").value = response.City;
            document.getElementById("to_state").value = response.State;
           
           // alert(response.State);
              },"json"); 
}