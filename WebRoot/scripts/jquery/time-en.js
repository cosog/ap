function showCurrentTime() {
	var time = new Date(); // 获得当前时间
	var year = time.getFullYear();
	var myclock = document.getElementById("time");
	var month = time.getMonth() + 1;
	var day = time.getDate();
	//alert(time.getDay());
	var  week;
	switch(time.getDay())
		{
		case 0:
		  week='Sunday';
		  break;
		 case 1:
		  week='Monday';
		   break;
		  case 2:
		  week='Tuesday';
		   break;
		  case 3:
		  week='Wednesday ';
		   break;
		  case 4:
		  week='Thursday';
		   break;
		  case 5:
		  week='Friday';
		   break;
		  case 6:
		  week='Saturday ';
		  break;
		default:week='Sunday';
		}
	var hour = time.getHours(); // 获得小时、分钟、秒
	var minute = time.getMinutes();
	var second = time.getSeconds();
	var apm = "Good morning！"; // 默认显示上午: AM
	// alert("hour="+hour);
	if (hour >= 12 && hour < 20) // 按12小时制显示
	{
		hour = hour;
		apm = "Good afternoon！";
	} else if (hour >= 20) {
		hour = hour;
		apm = "Good evening！";
	} else {
		hour = hour;
		apm = "Good morning！";
	}
	// alert(apm);
	if (month < 10) // 如果小时只有1位，补0显示
		month = "0"+month;
	if (day < 10) // 如果小时只有1位，补0显示
		day = "0" +day;
	if (hour < 10) // 如果小时只有1位，补0显示
		hour = "0" + hour;
	if (minute < 10) // 如果分钟只有1位，补0显示
		minute = "0" + minute;
	if (second < 10) // 如果秒数只有1位，补0显示
		second = "0" + second;
	// alert("day==="+day);
	/* 设置文本框的内容为当前时间 */
	myclock.innerHTML = "<span><font color='#428DDE' font-weight: bold;></font></span>" +day+"/"+month+"/" + year +"  "+ hour + ":" + minute + ":"
			+ second + " " + week;
	/* 设置定时器每隔1秒（1000毫秒），调用函数disptime()执行，刷新时钟显示 */
	var myTime = setTimeout("showCurrentTime()", 1000);
}

