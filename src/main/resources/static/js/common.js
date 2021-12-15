/**
 * Helper functions & Common codes
 */
// IE doest support string date,need to parse as new Date(y,m,d)//
function getDateStr(dt) {
	if(dt != null){
		var arry = dt.toString().split("T");
		if (arry.length == 2) {
			var datePart = arry[0].split("-");
			var timePart = arry[1].split(":");
			var dt = new Date(datePart[0], (datePart[1] - 1), datePart[2],
					timePart[0], timePart[1]);
			debugger;
			return dt.getDate() + "-" + (dt.getMonth() + 1) + "-"
					+ dt.getFullYear() + " " + dt.getHours() + ":"
					+ dt.getMinutes();

		}
	}
	return "";
}