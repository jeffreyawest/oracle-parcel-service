function parcel_refresh(){
    var id = $("#parcel_id").val();
    $.get("/ops-rest/resources/parcel/" + id, {} ,
        function(Parcel) {
        
            $("#parcel_update").replaceWith("<table border='1' id='parcel_update'><tr><th colspan='2'>"+
                "Parcel Detail</th><tr><td>Shipment Created</td><td>" + Parcel.CreatedDate  +
                "</td></tr><tr><td>Shipment Id</td><td>"+Parcel.ShipmentId +
                "</td></tr><tr><td> Status</td><td>"+Parcel.ParcelStatus +
                "</td></tr><tr><td>Contents</td><td>"+Parcel.Contents +
                "</td></tr><tr><td>Dimensions(H*W*L) </td><td>"+ Parcel.Height +"*"+Parcel.Width  +"*"+Parcel.Length  +
                "</td></tr><tr><td>Weight</td><td>"+ Parcel.Weight +
                "</td></tr></table>");
        },"json");
     $.get("/ops-rest/resources/parcel/" + id + "/log", {} ,
        function(response) { 
            
         //   alert(Object.keys(response.ParcelEvent).length);
            if(Object.keys(response.ParcelEvent).length != 8)
            {   
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
            
            }
            else{
                
               $("#parcel_table1").replaceWith("<table id='parcel_table1' border='1'><tr><th colspan='5' text-align='center'>Parcel Log Detail</th></tr><tr><th>Parcel ID</th><th>Event Date</th><th>Message</th><th>Location</th><th>Parcel Status</th></tr><tr><td>"+ response.ParcelEvent.ParcelId +
                   "</td><td>"+ response.ParcelEvent.EventDate +
                   "</td><td>"+ response.ParcelEvent.Message +
                   "</td><td>"+ response.ParcelEvent.Location +
                   "</td><td>"+ response.ParcelEvent.ParcelStatus +
                   "</td></tr></table>"); 
            }
            
            
            
            
        },"json");
}