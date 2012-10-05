function checkForm(form)
{
  var name = form['#{compositeComponent.clientId}:name'].value;
  var pwd = form['#{compositeComponent.clientId}:password'].value;

  if (name == "" || pwd == "")
  {
    alert("Please enter name and password.");
    return false;
  }
  return true;
}
