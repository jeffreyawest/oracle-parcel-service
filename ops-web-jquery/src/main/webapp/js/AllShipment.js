$.get("/ops-rest/resources/shipment/all", {} ,
    function(response1) { 
        var i = 1 ;
        
        $("#shipments_table").replaceWith("<table id='shipments_table' border='1'><tr><th colspan='6' text-align='center'>Shipments</th></tr><tr><th>Id</th><th>Created Date</th><th>From Address</th><th>To Address</th><th>Service</th><th>Parcels</th></tr></table>");
        $.each(response1.Shipment, function(index, event){
           
           i = ( i + 1);
                               
           $("#shipments_table").append(
                "<tr><td>"+ response1.Shipment[index].Id +
                "</td><td>" + response1.Shipment[index].CreatedDate +
                "</td><td><table id='from"+i+"' border='1'><tr><th width='20%'>Addressee</th><th width='20%' >Address</th><th width='20%' >City</th><th width='20%' >State</th><th width='20%'>Postal Code</th></tr><tr><td>"  + response1.Shipment[index].FromAddress.Addressee + 
                "</td><td>" + response1.Shipment[index].FromAddress.AddressLine1 + 
                "</td><td>" + response1.Shipment[index].FromAddress.City + 
                "</td><td>" + response1.Shipment[index].FromAddress.State + 
                "</td><td>" + response1.Shipment[index].FromAddress.PostalCode + "</td></tr></table></td><td><table id='to"+i+"' border='1'><tr><th>Addressee</th><th>Address</th><th>City</th><th>State</th><th>Postal Code</th></tr><tr><td>" +  response1.Shipment[index].ToAddress.Addressee + 
                "</td><td>" + response1.Shipment[index].ToAddress.AddressLine1 + 
                "</td><td>" + response1.Shipment[index].ToAddress.City + 
                "</td><td>" + response1.Shipment[index].ToAddress.State + 
                "</td><td>" + response1.Shipment[index].ToAddress.PostalCode + "</td></tr></table></td><td>" + response1.Shipment[index].ShippingServiceName +"</td><td onclick='parcel_log(" + response1.Shipment[index].Id + "," + i +")'><p id='parcel_log_temp"+i+"'>Click here to see all parcel</p></td></tr></table></td></tr>");
                      
        });
    },"json");
    
    function parcel_log(shipment_id,i){
         
    $.get("/ops-rest/resources/shipment/" + shipment_id, {} ,
        function(response2) { 
                 
            $('#parcel_log_temp'+ i).replaceWith("<table id='parcel_log_temp"+i+"' border='1'><tr><th>Parcel ID</th><th>Status</th><th>Contents</th><th>Weight</th><th> W * L * H</th></tr></table>");
             if(Object.keys(response2.Parcel).length != 9)
                 {
                      $.each(response2.Parcel, function(index, event){
                        $('#parcel_log_temp'+ i).append("<tr><td>"+ response2.Parcel[index].Id +
                        "</td><td>" + response2.Parcel[index].ParcelStatus +
                        "</td><td>" + response2.Parcel[index].Contents +
                        "</td><td>" +response2.Parcel[index].Weight + 
                        "</td><td>" + response2.Parcel[index].Width + "*" + response2.Parcel[index].Length + "*" + response2.Parcel[index].Height +"</td></tr>");
                });
                 }
             else{
                         $('#parcel_log_temp'+ i).append("<tr><td>"+ response2.Parcel.Id +
                        "</td><td>" + response2.Parcel.ParcelStatus +
                        "</td><td>" + response2.Parcel.Contents +
                        "</td><td>" +response2.Parcel.Weight + 
                        "</td><td>" + response2.Parcel.Width + "*" + response2.Parcel.Length + "*" + response2.Parcel.Height +"</td></tr>");
             }
           
        },"json");  
        
}
    
   

                