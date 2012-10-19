function trackfunction( parcel_id){
               var di = parcel_id; 
              
              
                $.get("/ops-rest/resources/parcel/" + di + "/log", {} ,
                function(response) { 
                    $("#parcel_table").replaceWith("<table id='parcel_table' border='1'><th>Parcel Log Detail</th><tr><th>Parcel ID</th><th>Event Date</th><th>Message</th><th>Location</th><th>Parcel Status</th></tr></table>");
                    $.each(response.ParcelEvent, function(index, event){
                        
                        $("#parcel_table").append(
                        "<tr><td>" +response.ParcelEvent[index].ParcelId +
                            "</td><td>" + response.ParcelEvent[index].EventDate +
                            "</td><td>" + response.ParcelEvent[index].Message +
                            "</td><td>" +response.ParcelEvent[index].Location + 
                            "</td><td>" + response.ParcelEvent[index].ParcelStatus + "</td></tr>");
                        
                        $("#parcel_output_wrapper").slideDown("slow");
                        
                    });
                },"json");
            }
            function testFun(){
                
                var id = $("#parcel_input").val(); //This id will be used with the REST back-end.
                //This id will be used with the REST back-end.
                
                $.get("/ops-rest/resources/parcel/" + id, {} ,
                function(Parcel) {
                    
                    
                    $.get("/ops-rest/resources/shipment/" + Parcel.ShipmentId, {} ,
                    function(response1) { 
                        
                        $("#from_address").replaceWith("<table id='from_address'border='1' ><th>From Address</th></table>");
                         $("#from_address").append(
                        
                         "<tr><td>" +      response1.FromAddress.Addressee + 
                         "</td><td>" + response1.FromAddress.AddressLine1 + 
                         "</td><td>" + response1.FromAddress.City + 
                         "</td><td>" + response1.FromAddress.State + 
                         "</td><td>" + response1.FromAddress.PostalCode + "</td></tr>"
                     );
                         $("#to_address").replaceWith("<table id='to_address' border='1'><th>To Address</th></table>");
                        $("#to_address").append(
                        "<tr><td>" +  response1.ToAddress.Addressee + 
                        "</td><td>" + response1.ToAddress.AddressLine1 + 
                        "</td><td>" + response1.ToAddress.City + 
                        "</td><td>" + response1.ToAddress.State + 
                        "</td><td>" + response1.ToAddress.PostalCode + "</td></tr>"
                    );
                        $("#service_table").replaceWith("<table id='service_table' border='1' ><th align='center' >Service Detail</th></table>");
                        
                         $("#service_table").append(
                         "<tr><td>" + response1.ShippingServiceName + "</td></tr>"
                     );
                     
                        
                        $("#parcel_table1").replaceWith("<table id='parcel_table1' border='1'><th align='center' >Shipment Detail</th><tr><th>Track</th><th>Parcel ID</th><th>Status</th><th>Contents</th><th>Weight</th><th> W * L * H</th></tr></table>");
                        $.each(response1.Parcel, function(index, event){
                            
                            $("#parcel_table1").append(
                            "<tr><td id='parcel_id' onclick='trackfunction(" + response1.Parcel[index].Id + ")'><img src='image/track.png'/></td><td>"+ response1.Parcel[index].Id +
                                "</td><td>" + response1.Parcel[index].ParcelStatus +
                                "</td><td>" + response1.Parcel[index].Contents +
                                "</td><td>" +response1.Parcel[index].Weight + 
                                "</td><td>" + response1.Parcel[index].Width + "*" + response1.Parcel[index].Length + "*" + response1.
                                Parcel[index].Height +"</td></tr>");
                            
                            $("#parcel_output_wrapper1").slideDown("slow");
                            
                        });
                    },"json");
                },
                "json");
                
                
                
                $.get("/ops-rest/resources/parcel/" + id + "/log", {} ,
                function(response) { 
                    $("#parcel_table").replaceWith("<table id='parcel_table' border='1'><th align='center'><b>Parcel Log Detail</b></th><tr><th>Parcel ID</th><th>Event Date</th><th>Message</th><th>Location</th><th>Parcel Status</th></tr></table>");
                    $.each(response.ParcelEvent, function(index, event){
                        
                        $("#parcel_table").append(
                        "<tr><td>" +response.ParcelEvent[index].ParcelId +
                            "</td><td>" + response.ParcelEvent[index].EventDate +
                            "</td><td>" + response.ParcelEvent[index].Message +
                            "</td><td>" +response.ParcelEvent[index].Location + 
                            "</td><td>" + response.ParcelEvent[index].ParcelStatus + "</td></tr>");
                        
                        $("#parcel_output_wrapper").slideDown("slow");
                        
                    });
                },"json");
            }
        
        
                      