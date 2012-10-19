$.get("/ops-rest/resources/shipingservice/", {} ,
    function(response1) { 
        $("#ship_service").replaceWith("<table id='ship_service' border='1'><tr><th>Shipping Services</th></tr><tr><th>NAME</th><th>DESCRIPTION</th><th>COST</th><th>DELIVERY ESTIMATE</th></tr></table>");
        $.each(response1.ShippingService, function(index, event){
        
            $("#ship_service").append(
                "<tr><td>"+ response1.ShippingService[index].Name +
                "</td><td>" + response1.ShippingService[index].Description +
                "</td><td>" + response1.ShippingService[index].Cost +
                "</td><td>" + response1.ShippingService[index].DeliveryEstimate + "</td></tr>");
            
            
        
        });
    },"json");

                