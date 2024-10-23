/*==============================================================*/
/* 初始化组织数据                                                 */
/*==============================================================*/
insert into TBL_ORG (ORG_ID, ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
values (2, '1000', '油田公司', '油田公司', 1, null);

insert into TBL_ORG ( ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
select '100000','采油一厂','采油一厂',t2.org_id, null from TBL_ORG t2 where t2.org_code='1000';

insert into TBL_ORG ( ORG_CODE, ORG_NAME, ORG_MEMO, ORG_PARENT, ORG_SEQ)
select '10000000','采油一队','采油一队',t2.org_id, null from TBL_ORG t2 where t2.org_code='100000';


/*==============================================================*/
/* 初始化设备数据                                              */
/*==============================================================*/
insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc01', 101, 1, '1010001', '01', 'instance1', 'alarminstance1', null, 1, 1, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.87,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.63,
    "SaturationPressure": 9.7
  },
  "Reservoir": {
    "Depth": 1657.0,
    "Temperature": 69.0
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 1069.51,
        "OutsideDiameter": 0.022,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 1.5,
    "PumpBoreDiameter": 0.057,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.1397,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 97.02,
    "ProductionGasOilRatio": 75.0,
    "TubingPressure": 0.64,
    "CasingPressure": 0.7,
    "WellHeadFluidTemperature": 25.0,
    "ProducingfluidLevel": 190.0,
    "PumpSettingDepth": 1113.72,
    "LevelCorrectValue": 6.61
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc02', 101, 1, '1010002', '01', 'instance1', 'alarminstance1', null, 1, 2, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.25
  },
  "Reservoir": {
    "Depth": 946.5,
    "Temperature": 48.1
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 840.94,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 2.5,
    "PumpBoreDiameter": 0.038,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.121360004,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 15.7,
    "ProductionGasOilRatio": 14.8,
    "TubingPressure": 0.7,
    "CasingPressure": 0.55,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 792.9,
    "PumpSettingDepth": 855.28,
    "LevelCorrectValue": 2.67
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 1.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc03', 101, 1, '1010003', '01', 'instance1', 'alarminstance1', null, 1, 3, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.68
  },
  "Reservoir": {
    "Depth": 1134.6,
    "Temperature": 56.6
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 1038.84,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 1.6,
    "PumpBoreDiameter": 0.032,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.121360004,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 39.84,
    "ProductionGasOilRatio": 20.2,
    "TubingPressure": 0.34,
    "CasingPressure": 0.21,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 1033.0,
    "PumpSettingDepth": 1053.2,
    "LevelCorrectValue": 6.22
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc04', 101, 1, '1010004', '01', 'instance1', 'alarminstance1', null, 1, 4, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.68
  },
  "Reservoir": {
    "Depth": 1052.9,
    "Temperature": 52.9
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 945.48,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 2.5,
    "PumpBoreDiameter": 0.038,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.12426,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 79.01,
    "ProductionGasOilRatio": 20.2,
    "TubingPressure": 0.37,
    "CasingPressure": 0.32,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 923.8,
    "PumpSettingDepth": 959.38,
    "LevelCorrectValue": -9.32
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc05', 101, 1, '1010005', '01', 'instance1', 'alarminstance1', null, 1, 5, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.68
  },
  "Reservoir": {
    "Depth": 1202.5,
    "Temperature": 59.6
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 1150.55,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 1.6,
    "PumpBoreDiameter": 0.032,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.121360004,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 46.73,
    "ProductionGasOilRatio": 20.2,
    "TubingPressure": 0.31,
    "CasingPressure": 0.2,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 1152.0,
    "PumpSettingDepth": 1161.75,
    "LevelCorrectValue": -18.12
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc06', 101, 1, '1010006', '01', 'instance1', 'alarminstance1', null, 1, 6, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.25
  },
  "Reservoir": {
    "Depth": 966.1,
    "Temperature": 49.0
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 931.65,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 1.6,
    "PumpBoreDiameter": 0.032,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.12426,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 13.36,
    "ProductionGasOilRatio": 14.8,
    "TubingPressure": 0.21,
    "CasingPressure": 0.31,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 0.0,
    "PumpSettingDepth": 942.21,
    "LevelCorrectValue": 11.96
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc07', 101, 1, '1010007', '01', 'instance1', 'alarminstance1', null, 1, 7, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.68
  },
  "Reservoir": {
    "Depth": 1035.7,
    "Temperature": 52.1
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 929.51,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 2.5,
    "PumpBoreDiameter": 0.032,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.121360004,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 42.77,
    "ProductionGasOilRatio": 20.2,
    "TubingPressure": 0.32,
    "CasingPressure": 0.21,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 885.1,
    "PumpSettingDepth": 940.39,
    "LevelCorrectValue": 0.43
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc08', 101, 1, '1010008', '01', 'instance1', 'alarminstance1', null, 1, 8, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.68
  },
  "Reservoir": {
    "Depth": 1012.4,
    "Temperature": 51.1
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 931.17,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 2.5,
    "PumpBoreDiameter": 0.038,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.121360004,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 52.36,
    "ProductionGasOilRatio": 20.2,
    "TubingPressure": 0.31,
    "CasingPressure": 0.62,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 900.4,
    "PumpSettingDepth": 942.59,
    "LevelCorrectValue": -0.31
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc09', 101, 1, '1010009', '01', 'instance1', 'alarminstance1', null, 1, 9, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.68
  },
  "Reservoir": {
    "Depth": 1093.7,
    "Temperature": 54.7
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 998.33,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 2.5,
    "PumpBoreDiameter": 0.038,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.12426,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 41.6,
    "ProductionGasOilRatio": 20.2,
    "TubingPressure": 0.31,
    "CasingPressure": 0.75,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 969.3,
    "PumpSettingDepth": 1015.58,
    "LevelCorrectValue": 2.62
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

insert into tbl_device (ORGID, WELLNAME, DEVICETYPE, APPLICATIONSCENARIOS, SIGNINID, SLAVE, INSTANCECODE, ALARMINSTANCECODE, VIDEOURL, STATUS, SORTNUM, PRODUCTIONDATA, PUMPINGMODELID, STROKE, BALANCEINFO, DISPLAYINSTANCECODE,reportinstancecode) select t2.org_id, 'rpc10', 101, 1, '1010010', '01', 'instance1', 'alarminstance1', null, 1, 10, '{
  "FluidPVT": {
    "CrudeOilDensity": 0.86,
    "WaterDensity": 1.0,
    "NaturalGasRelativeDensity": 0.6,
    "SaturationPressure": 3.25
  },
  "Reservoir": {
    "Depth": 832.6,
    "Temperature": 43.0
  },
  "RodString": {
    "EveryRod": [
      {
        "Type": 1,
        "Grade": "HY",
        "Length": 778.05,
        "OutsideDiameter": 0.019,
        "InsideDiameter": 0.0,
        "Density": 0.0
      }
    ]
  },
  "TubingString": {
    "EveryTubing": [
      {
        "length": 0.0,
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.062,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Pump": {
    "PumpType": "T",
    "BarrelType": "H",
    "PumpGrade": 2,
    "BarrelLength": 0,
    "PlungerLength": 1.6,
    "PumpBoreDiameter": 0.038,
    "Clearance": 0.0,
    "AntiImpactStroke": 0.0
  },
  "CasingString": {
    "EveryCasing": [
      {
        "OutsideDiameter": 0.0,
        "InsideDiameter": 0.12426,
        "Length": 0.0,
        "Density": 0.0,
        "WeightPerMeter": 0.0
      }
    ]
  },
  "Production": {
    "WaterCut": 0.0,
    "ProductionGasOilRatio": 14.8,
    "TubingPressure": 0.0,
    "CasingPressure": 1.36,
    "WellHeadFluidTemperature": 15.0,
    "ProducingfluidLevel": 644.3,
    "PumpSettingDepth": 792.38,
    "LevelCorrectValue": 1.36
  },
  "ManualIntervention": {
    "Code": 0,
    "NetGrossRatio": 1.0
  }
}', 1, 3.00, '{"EveryBalance":[{"Position":"0.5","Weight":"7.71"},{"Position":"0.5","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"},{"Position":"0.6","Weight":"7.71"}]}', 'displayinstance1','reportinstance1' from TBL_ORG t2 where t2.org_code='10000000';

commit;

exit;