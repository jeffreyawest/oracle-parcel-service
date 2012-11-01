function trackfunction( parcel_id){
    var di = parcel_id; 
              
              
    $.get("/ops-rest/resources/parcel/" + di + "/log", {} ,
        function(response) { 
            $("#parcel_table1").replaceWith("<table id='parcel_table1' border='1'><th colspan='5' text-align='center'>Parcel Log Detail</th><tr><th>Parcel ID</th><th>Event Date</th><th>Message</th><th>Location</th><th>Parcel Status</th></tr></table>");
            $.each(response.ParcelEvent, function(index, event){
                        
                $("#parcel_table1").append(
                    "<tr><td>" +response.ParcelEvent[index].ParcelId +
                    "</td><td>" + response.ParcelEvent[index].EventDate +
                    "</td><td>" + response.ParcelEvent[index].Message +
                    "</td><td>" +response.ParcelEvent[index].Location + 
                    "</td><td>" + response.ParcelEvent[index].ParcelStatus + "</td></tr>");
                        
                $("#parcel_output_wrapper").slideDown("slow");
                        
            });
        },"json");
}
function testParcel(){
    var oRows = document.getElementById('main_table').getElementsByTagName('tr');
    var iRowCount = oRows.length;
  //  alert(iRowCount);
    if (iRowCount == 4 ){
   $("#main_table").append("<tr><td><table id='address_table'><tr><td><table id='from_address'></table></td><td><table id='to_address' ></table></td><td><table id='service_table'></table></td></tr></table></td></tr>");
   $("#main_table").append("<tr><td><table id='parcel_table'></table></td></tr>");
   $("#main_table").append("<tr><td colspan='2'> <table id='parcel_table1'></table></td></tr>");
    }
                
    var id = $("#parcel_input").val(); //This id will be used with the REST back-end.
    //This id will be used with the REST back-end.
                
    $.get("/ops-rest/resources/parcel/" + id, {} ,
        function(Parcel) {
                    
                    
            $.get("/ops-rest/resources/shipment/" + Parcel.ShipmentId, {} ,
                function(response1) { 
                        
                     $("#from_address").replaceWith("<table id='from_address' border='1' ><tr><th>From Address</th></tr><tr><td>" + response1.FromAddress.Addressee +"</br>" + response1.FromAddress.AddressLine1 +"</br>" + response1.FromAddress.City +"</br>" + response1.FromAddress.State +"</br>" + response1.FromAddress.PostalCode + "</td></tr></table>");
                    $("#to_address").replaceWith("<table id='to_address' border='1'><tr><th>To Address</th></tr><tr><td>" +  response1.ToAddress.Addressee + "</br>" + response1.ToAddress.AddressLine1 + "</br>" + response1.ToAddress.City + "</br>" + response1.ToAddress.State +"</br>" + response1.ToAddress.PostalCode + "</td></tr></table>");
                    $("#service_table").replaceWith("<table id='service_table' border='1' ><th align='center' >Service Detail</th></table>");
                        
                    $("#service_table").append(
                        "<tr><td>" + response1.ShippingServiceName + "</td></tr>"
                        );
                     alert(Object.keys(response1).length);
                     
                        if(Object.keys(response1).length != 7){
                    $("#parcel_table").replaceWith("<table id='parcel_table' border='1'><th colspan='6' text-align='center'>Shipment Detail</th><tr><th>Track</th><th>Parcel ID</th><th>Status</th><th>Contents</th><th>Weight</th><th> W * L * H</th></tr></table>");
                    $.each(response1.Parcel, function(index, event){
                        $("#parcel_table").append("<tr><td id='parcel_id' onclick='trackfunction(" + response1.Parcel[index].Id + ")'><img src='image/track.png'/></td><td>"+ response1.Parcel[index].Id +
                            "</td><td>" + response1.Parcel[index].ParcelStatus +
                            "</td><td>" + response1.Parcel[index].Contents +
                            "</td><td>" +response1.Parcel[index].Weight + 
                            "</td><td>" + response1.Parcel[index].Width + "*" + response1.Parcel[index].Length + "*" + response1.
                            Parcel[index].Height +"</td></tr>");
                            
                        $("#parcel_output_wrapper1").slideDown("slow");
                            
                    });
                        }
                        else{
                           $("#parcel_table").replaceWith("<table id='parcel_table' border='1'><th colspan='6' text-align='center'>Shipment Detail</th><tr><th>Track</th><th>Parcel ID</th><th>Status</th><th>Contents</th><th>Weight</th><th> W * L * H</th></tr></table>");  
                           $("#parcel_table").append("<tr><td id='parcel_id' onclick='trackfunction(" + response1.Parcel.Id + ")'><img src='image/track.png'/></td><td>"+ response1.Parcel.Id +
                            "</td><td>" + response1.Parcel.ParcelStatus +
                            "</td><td>" + response1.Parcel.Contents +
                            "</td><td>" +response1.Parcel.Weight + 
                            "</td><td>" + response1.Parcel.Width + "*" + response1.Parcel.Length + "*" + response1.
                            Parcel.Height +"</td></tr>");     
                    }
                },"json");
        },
        "json");
                
                
                
    $.get("/ops-rest/resources/parcel/" + id + "/log", {} ,
        function(response) { 
            $("#parcel_table1").replaceWith("<table id='parcel_table1' border='1'><th colspan='5' text-align='center'><b>Parcel Log Detail</b></th><tr><th>Parcel ID</th><th>Event Date</th><th>Message</th><th>Location</th><th>Parcel Status</th></tr></table>");
            $.each(response.ParcelEvent, function(index, event){
                        
                $("#parcel_table1").append(
                    "<tr><td>" +response.ParcelEvent[index].ParcelId +
                    "</td><td>" + response.ParcelEvent[index].EventDate +
                    "</td><td>" + response.ParcelEvent[index].Message +
                    "</td><td>" +response.ParcelEvent[index].Location + 
                    "</td><td>" + response.ParcelEvent[index].ParcelStatus + "</td></tr>");
                        
                $("#parcel_output_wrapper").slideDown("slow");
                        
            });
        },"json");
}
        
 function testShipment(){
    var id = $("#shipment_input").val(); //This id will be used with the REST back-end.
    //This id will be used with the REST back-end.
    
    var oRows = document.getElementById('main_table').getElementsByTagName('tr');
    var iRowCount = oRows.length;
   // alert(iRowCount);
    if (iRowCount == 4 ){
  $("#main_table").append("<tr><td><table id='address_table'><tr><td><table id='from_address'></table></td><td><table id='to_address' ></table></td><td><table id='service_table'></table></td></tr></table></td></tr>");
   $("#main_table").append("<tr><td><table id='parcel_table'></table></td></tr>");
    }
    $.get("/ops-rest/resources/shipment/" + id, {} ,
        function(Shipment) {
             
                    $("#from_address").replaceWith("<table id='from_address' border='1' ><tr><th>From Address</th></tr><tr><td>" + Shipment.FromAddress.Addressee +"</br>" + Shipment.FromAddress.AddressLine1 +"</br>" + Shipment.FromAddress.City +"</br>" + Shipment.FromAddress.State +"</br>" + Shipment.FromAddress.PostalCode + "</td></tr></table>");
                    $("#to_address").replaceWith("<table id='to_address' border='1'><tr><th>To Address</th></tr><tr><td>" +  Shipment.ToAddress.Addressee + "</br>" + Shipment.ToAddress.AddressLine1 + "</br>" + Shipment.ToAddress.City + "</br>" + Shipment.ToAddress.State +"</br>" + Shipment.ToAddress.PostalCode + "</td></tr></table>");
                    
                       
                    $("#service_table").replaceWith("<table id='service_table' border='1' ><th align='center' >Service Detail</th></table>");
                        
                    $("#service_table").append(
                        "<tr><td>" + Shipment.ShippingServiceName + "</td></tr>"
                        );
                    $("#parcel_table").replaceWith("<table id='parcel_table' border='1'><th colspan='6' text-align='center'>Shipment Detail</th><tr><th>Parcel ID</th><th>Status</th><th>Contents</th><th>Weight</th><th> W * L * H</th></tr></table>");
                            $.each(Shipment.Parcel, function(index, event){
                                $("#parcel_table").append("<tr><td>"+ Shipment.Parcel[index].Id +
                                    "</td><td>" + Shipment.Parcel[index].ParcelStatus +
                                    "</td><td>" + Shipment.Parcel[index].Contents +
                                    "</td><td>" +Shipment.Parcel[index].Weight + 
                                    "</td><td>" + Shipment.Parcel[index].Width + "*" + Shipment.Parcel[index].Length + "*" + Shipment.Parcel[index].Height +"</td></tr>");

                                $("#parcel_output_wrapper1").slideDown("slow");

                            });      

                    $("#parcel_table1").replaceWith("<table id='parcel_table1'></table>");



        },
        "json");
     
 }       
                      