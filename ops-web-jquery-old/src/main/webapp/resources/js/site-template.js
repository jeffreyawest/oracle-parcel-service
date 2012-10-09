if (parent.document.URL.indexOf('template=off') < 0)
{
  $(document).ready(function()
  {
    if (parent.document.URL.indexOf('header=off') < 0)
    {    // Add the header section
      $(".main").prepend("<div class=\"header\"></div>");
      $(".header").load("templates/site-template.html .header");
    }

    if (parent.document.URL.indexOf('sidenav=off') < 0)
    {
      // Add the sidenav bar
      $(".content").after("<div class=\"sidenav\"></div>");
      $(".sidenav").load("templates/site-template.html .sidenav");
    }

    if (parent.document.URL.indexOf('footer=off') < 0)
    {    // Finally add the footer section
      $(".main").after("<div class=\"footer\"></div>");
      $(".footer").load("templates/site-template.html .footer");
    }
  });
}