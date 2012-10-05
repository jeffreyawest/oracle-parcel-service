/**
 * Created with IntelliJ IDEA.
 * User: jeffreyawest
 * Date: 9/28/12
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 2.   * Clock message handling function.
 3.   */
function clockHandler(event)
{
  var eventData = event.data.toString().replace(/^\s*/, "").replace(/\s*$/, "");
  var clock = document.getElementById("clock");
  clock.innerHTML = eventData;
}