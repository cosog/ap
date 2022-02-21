# 驱动配置

## RunMode		   运行模式	debug-调试	work-工作

## CommMode	  通信模式

### WiFi

#### AP							 	   热点模式

State										  开关		off-失效  on-使能

Wifi_interface						   针对该设备为固定值wlan1					

Interface_with_internet		  建立AP时的外网路由，eth0：网卡1；eth1：网卡2；eth2：代表4g；wlan0：代表sta

SSID									   	名称			缺省：AP_ID

PSK											密码			缺省：1234567889

#### STA						   	   站模式

State										  开关			off-失效  on-使能

IP										  	 (DHCP时不需配置)

Mask										 掩码  (DHCP时不需配置)

GW										    网关  (DHCP时不需配置)

SSID									      名称											缺省：STA_TEST

PSK										   密码											缺省：1234567889

#### Eth0				   	       100/1000Mbps网卡

State									    开关			off-失效  on-使能

IP											  网卡1和网卡2设置不同网段																				

Mask									    掩码													

GW										   网关

DNS									     域名服务器	可以设置与网关一致

#### Eth1				   		   10/100Mbps网卡

State										开关			off-失效  on-使能

IP											  网卡1和网卡2设置不同网段						

Mask									    掩码

GW										   网关

DNS										 域名服务器	可以设置与网关一致

#### Eth2				   		    4G模组

State									    开关			off-失效  on-使能

## Server		  	  服务器

### RemoteModbus   Modbus 连接的TCP服务器

State									     开关			off-失效  on-使能

ProtocolType						   协议类型  modbus-tcp或modbus-rtu 		缺省：modbus-tcp

IP					

Port

### RemoteKafka       远程Kafka服务器

State									    开关			off-失效  on-使能

IP 											 缺省：39.98.64.56

Port										  缺省：9092

#### Topic							 主题

##### Pub										  发布

Main       								  主数据

RawFES								    原始功图数据

RawWaterCut 					    原始含水率数据

Log         								  日志数据

Resource         						资源数据

##### Sub										   订阅

### RPC						  远程过程调用

State									    开关			off-失效  on-使能

UpIP										上行IP

UpPort									上行端口

DownIP								   下行IP

DownPort							   下行端口

#### Topic							 主题

##### Pub										  发布

Main       								  主数据

RawFES								    原始功图数据

RawWaterCut 					    原始含水率数据

Log         								  日志数据

Resource         						资源数据

##### Sub										   订阅

### LocalModbus        本地Modbus

State										 开关			off-失效  on-使能

IP											   缺省：127.0.0.1

Port										   缺省：502

### LocalApp   			 APP

State										  开关			off-失效  on-使能

IP											    缺省：192.168.12.1

Port										    缺省：17100

## Terminal	        端子配置

### RS485

State										 开关			off-失效  on-使能

SN											 端子号，最小值为1，端子1默认健丰含水仪，端子2默认健丰动液面仪

Baud										 波特率

DataBit									 数据位

Parity								   	 校验

StopBits							   	 停止位

Timeout							   	 超时   		ms

#### Slaves							从地址数组，支持总线模式

State										 开关			off-失效  on-使能

Slave										 从地址        填写十进制数值，例如1、2、3......

Driver  									 驱动名称，jf-hs/50：健丰含水仪  hik-ds-2ta21-2avf:海康红外测温仪仪

Tag									   	 预留

### RS232

State										 开关			off-失效  on-使能

SN											 端子号，最小值为1，调试端口

Baud										 波特率

DataBit									 数据位

Parity								   	 校验

StopBits							   	停止位

### DI

State										开关			off-失效  on-使能

SN											端子号，最小值为1，缺省端子1：抽油机运行状态

Tag  	                                	 标签，rpc_run_status：抽油机运行状态

### DO

State										开关			off-失效  on-使能

SN 	                                   	端子号，最小值为1，缺省端子1：启抽继电器  端子2：停抽继电器

Tag 	                                 	 标签，rpc_start_relay ：启抽继电器  rpc_stop_relay ：停抽继电器   

### AI

State										开关			off-失效  on-使能

SN											端子号，最小值为1，缺省端子1：载荷传感器  端子2：角位移传感器 

Tag									   	标签，load-载荷传感器	angle：角位移传感器 

#### Range							量程

MinCurrent						 	最小电流					缺省：4mA

MaxCurrent					    	最大电流					缺省：20mA

MinValue						     	量程下限					缺省：角度 -45度；载荷 0kN

MaxValue						    	量程上限					缺省：角度 45度；载荷 150kN

### AO						   预留

## Ratio				  互感器变比

V											    电压变比					 缺省：1

I									 		    电流变比					 缺省：1

## Ctrl 				    控制配置

RPCStopPosition	                间开抽油机停抽位置 top：驴头上死点 bottom：驴头下死点 空：即时停抽

RPCStopAngleTHR	             抽油机停抽角度阈值						缺省：2度

RPCStartRelayDuration	     抽油机启抽继电器吸合时间			缺省：60秒

RPCStartSoundDuration	   抽油机启抽音乐时间						缺省：60秒

RPCStopRelayDuration		 抽油机停抽继电器吸合时间			 缺省：10秒

RPCStartSoundPath	          启抽音乐路径									 缺省：sound/rpc_start.wav

EnterWellSiteSoundPath      进入井场音乐路径							 缺省：sound/enter_wellsite.wav

## Timer			     定时器

StartOverload					    启抽过载定时器		缺省：5分钟	

TransferInterval				    传输间隔定时器		缺省：3分钟

HeartbeatInterval                 心跳包间隔定时器    缺省：10秒

AcqSampleInterval               采样间隔                   缺省： 50毫秒

DailyStartTimer	                 日报上传起始时间，区间内随机时间上传，填写1-24整型  缺省：2

DailyEndTimer					   日报上传结束时间，区间内随机时间上传，填写1-24整型  缺省：5

## Counter		    计数器

StopCNT								 停抽计数器													缺省：1000

UnderloadCNT					  欠载计数器													缺省：1000

OverloadCNT					     过载计数器													缺省：1

OpenPhaseCNT					 缺相计数器													缺省：1000

Imbalance3ICNT				    三相电流不平衡计数器				 				缺省：1000

## Limit				  界限值

### I							   电流

#### A								 								

Noload								   空载电流													  	 缺省：20A

Zero									    零值界限，需考虑冬季电保温等接入			缺省：3A

#### B								  

Noload								   空载电流													   	缺省：20A

Zero									    零值界限，需考虑冬季电保温等接入			缺省：3A

#### C								  

Noload								   空载电流													   	缺省：20A

Zero									    零值界限，需考虑冬季电保温等接入			缺省：3A

## Length			  记录数

FEAStack							   采集载荷-电参-角度原始数据曲线点数			缺省：1000

StoreFEA							   存储载荷-电参-角度功图记录数						缺省：100

StoreFES							   存储载荷-电参-位移功图记录数						缺省：100

StoreFESR						     存储功图计算结果记录数								  缺省：100

StoreAcqWaterCut		      存储采集含水仪数据记录数			                  缺省：1000

StoreCalcWaterCut	         存储计算含水仪数据记录数			                  缺省：1000

StoreCalcInfrared				存储计算红外温度数据记录数		                  缺省：1000

StoreAcqLevel	                  存储采集动液面仪记录数						          缺省：1000

StoreCalcLevel	                 存储计算动液面仪记录数						          缺省：1000

StoreAcqBP					      存储采集回压记录数										 缺省：1000

StoreCalcBP					      存储计算回压记录数										 缺省：1000

StoreAcqTP					       存储采集油压记录数										 缺省：1000

StoreCalcTP					      存储计算油压记录数										 缺省：1000

StoreAcqWT					      存储采集井口流温记录数								 缺省：1000

StoreCalcWT					     存储计算井口流温记录数							     缺省：1000

StoreAcqCP					      存储采集套压记录数									     缺省：1000

StoreCalcCP					     存储计算套压记录数									     缺省：1000

StoreAcqFreq				      存储采集变频器频率记录数						      缺省：1000

StoreCalcFreq				     存储计算变频器频率记录数						      缺省：1000

StoreAcqE					        存储采集电参记录数										 缺省：1000

StoreCalcE					       存储计算电参记录数										 缺省：1000

StoreAcqF					        存储采集载荷记录数										 缺省：1000

StoreCalcF					       存储计算载荷记录数										 缺省：1000

StoreAcqA					        存储采集角度记录数										 缺省：1000

StoreCalcA					       存储计算角度记录数										 缺省：1000

StoreAcqRun				       存储采集运行状态记录数							     缺省：1000

StoreCalcRun				      存储计算运行状态记录数							     缺省：1000

StoreAcqComm			      存储采集通信状态记录数						         缺省：1000

StoreCalcComm			     存储计算通信状态记录数						         缺省：1000

StoreDaily					       存储日报记录数										         缺省：10

StoreYHHDAngle			   预留																	缺省：1000

StoreYHHDLFJ					预留																	缺省：1000

StoreYHHDFSL				   预留																	缺省：1000

Log					   			    启动日志记录数										         缺省：10

## Filter			   滤波配置

FTimes							   载荷滤波次数														缺省：0

ATimes						      角度滤波次数														缺省：0

WattTimes					    功率滤波次数							   						 缺省：0

ITimes							   电流滤波次数														缺省：0

FZero						        载荷零值滤波数值						 						缺省：0kN

AZero							    角度零值滤波数值						 						缺省：0度
