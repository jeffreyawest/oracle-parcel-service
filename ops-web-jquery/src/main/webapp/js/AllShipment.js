$.get("/ops-rest/resources/shipment/all", {} ,
    function(response1) { 
        var i = 1 ;
        
        $("#shipments_table").replaceWith("<table id='shipments_table' border='1'><tr><th>Shipments</th></tr><tr><th>Id</th><th>Created Date</th><td><table id='ship_from_address' border='1'><tr><th>From Address</th></tr><tr><th>Id</th><th>Created Date</th><th>Addressee</th><th>Address</th><th>City</th><th>State</th><th>Postal Code</th></th></table></td><td><table id='ship_to_address' border='1'><tr><th>To Address</th></tr><tr><th>Id</th><th>Created Date</th><th>Addressee</th><th>Address</th><th>City</th><th>State</th><th>Postal Code</th></tr></table></td><th>Service</th><td><table id='ship_parcel' border='1'><tr><th>Parcels</th></tr><tr><th>Id</th><th>Created Date</th><th>Shipment ID</th><th>Height</th><th>Weight</th><th>Width</th><th>Length</th><th>Parcel Status</th></tr></table></td></tr></table>");
        $.each(response1.Shipment, function(index, event){
           
           i = ( i + 1);
                                  
           $("#shipments_table").append(
                "<tr><td>"+ response1.Shipment[index].Id +
                "</td><td>" + response1.Shipment[index].CreatedDate +
                "</td><td><table id='from"+i+"' border='1'><tr><td>" + response1.Shipment[index].FromAddress.Id  + 
                "</td><td>" + response1.Shipment[index].FromAddress.CreatedDate + 
                "</td><td>" + response1.Shipment[index].FromAddress.Addressee + 
                "</td><td>" + response1.Shipment[index].FromAddress.AddressLine1 + 
                "</td><td>" + response1.Shipment[index].FromAddress.City + 
                "</td><td>" + response1.Shipment[index].FromAddress.State + 
                "</td><td>" + response1.Shipment[index].FromAddress.PostalCode + "</td></tr></table></td><td><table id='to"+i+"' border='1'><tr><td>" +  response1.Shipment[index].ToAddress.Addressee + 
                "</td><td>" + response1.Shipment[index].ToAddress.AddressLine1 + 
                "</td><td>" + response1.Shipment[index].ToAddress.City + 
                "</td><td>" + response1.Shipment[index].ToAddress.State + 
                "</td><td>" + response1.Shipment[index].ToAddress.PostalCode + "</td></tr></table></td><td>" + response1.Shipment[index].ShippingServiceName +"</td><td><table id='ship_parcel"+i+"' border='1'><tr><td>Id</td><td>Created Date</td><td>Shipment ID</td><td>Height</td><td>Weight</td><td>Width</td><td>Length</td><td>Parcel Status</td></tr><tr><td>"+ response1.Shipment[2].Parcel[0].Id +
                "</td><td>" + response1.Shipment[2].Parcel[0].CreatedDate +
                "</td><td>" + response1.Shipment[2].Parcel[0].ShipmentId +
                "</td><td>" +response1.Shipment[2].Parcel[0].Height + 
                "</td><td>" + response1.Shipment[2].Parcel[0].Width + 
                "</td><td>" + response1.Shipment[2].Parcel[0].Length + 
                "</td><td>" + response1.Shipment[2].Parcel[0].Weight +
                "</td><td>" + response1.Shipment[2].Parcel[0].ParcelStatus + "</td></tr></table></td></tr>");
                      
        });
    },"json");

                