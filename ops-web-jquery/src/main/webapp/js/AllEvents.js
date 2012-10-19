$.get("/ops-rest/resources/parcel/all/log", {} ,
    function(response1) { 
        $("#event_table").replaceWith("<table id='event_table' border='1'><tr><th>Parcels  Log Events</th></tr><tr><th>Event Id </th><th> Date</th><th>Location</th><th>Status</th><th>Message</th></tr></table>");
        $.each(response1.ParcelEvent, function(index, event){
        
            $("#event_table").append(
                "<tr><td>"+ response1.ParcelEvent[index].Id+
                "</td><td>" + response1.ParcelEvent[index].EventDate +
                "</td><td>" + response1.ParcelEvent[index].Location +
                "</td><td>" +response1.ParcelEvent[index].ParcelStatus + 
                "</td><td>" + response1.ParcelEvent[index].Message + "</td></tr>");
        });
    },"json");

                