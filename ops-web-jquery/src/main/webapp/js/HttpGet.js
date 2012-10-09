      
                                          
function testFun(){
            
    var id = $("#parcel_input").val(); //This id will be used with the REST back-end.
    //This id will be used with the REST back-end.
            
    $.get("/ops-rest/resources/parcel/" + id, {} ,
        function(Parcel) {
           
            $("#parcel_table1").replaceWith(
                "<div id=\"parcel_table1\"><table  border='1'><tr>Parcel Information</tr><tr><td> Shipment Id </td><td> Contents </td><td> Height </td><td> Width</td><td>Length</td><td>Weight</td><td> Parcel Status</td></tr>"+
                "<tr><td>" +Parcel.ShipmentId + 
                "</td><td>" + Parcel.Contents +
                "</td><td>"+ Parcel.Height + 
                "</td><td>" + Parcel.Width + 
                "</td><td>" + Parcel.Length +
                "</td><td>" + Parcel.Weight + 
                "</td><td>" + Parcel.ParcelStatus + "</td></tr></table></div>"
                );
            $("#parcel_output_wrapper1").slideDown("slow");
        },
        "json");
                      
           
            
    $.get("/ops-rest/resources/parcel/" + id + "/log", {} ,
        function(response) { 
            $("#parcel_table").replaceWith("<table id='parcel_table' border='1'><tr><td>Parcel ID</td><td>Event Date</td><td>Message</td><td>Location</td><td>Parcel Status</td></tr></table>");
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
        
        
                  