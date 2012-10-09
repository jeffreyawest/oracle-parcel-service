$('#toggle_message').click(function(){
   var value = $(this).attr('value');
   $('#parcel_table').toggle('fast');
   
   if(value == 'HideLogDetail'){
       $(this).attr('value','ShowLogDetail');
   }
   else if(value == 'ShowLogDetail'){
       $(this).attr('value','HideLogDetail');
   }
});
