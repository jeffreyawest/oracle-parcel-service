$.get("/ops-rest/resources/parcel/", {} ,
    function(response1) { 
        $("#parcels_table").replaceWith("<table id='parcels_table' border='1'><tr><th>Parcels</th><tr><th>Id</th><th>Created Date</th><th>Shipment ID</th><th>Height</th><th>Weight</th><th>Width</th><th>Length</th><th>Parcel Status</th></tr></table>");
        $.each(response1.Parcel, function(index, event){
        
            $("#parcels_table").append(
                "<tr><td>"+ response1.Parcel[index].Id +
                "</td><td>" + response1.Parcel[index].CreatedDate +
                "</td><td>" + response1.Parcel[index].ShipmentId +
                "</td><td>" +response1.Parcel[index].Height + 
                "</td><td>" + response1.Parcel[index].Width + 
                "</td><td>" + response1.Parcel[index].Length + 
                "</td><td>" + response1.Parcel[index].Weight +
                "</td><td>" + response1.Parcel[index].ParcelStatus + "</td></tr>");
            
            $("#parcel_output_wrapper1").slideDown("slow");
        
        });
    },"json");

                