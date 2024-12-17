package com.cosog.model.calculate;

public class SRPProductionData {
	
	private String WellName;

    private SRPCalculateRequestData.FluidPVT FluidPVT;

    private SRPCalculateRequestData.Reservoir Reservoir;

    private SRPCalculateRequestData.RodString RodString;

    private SRPCalculateRequestData.TubingString TubingString;

    private SRPCalculateRequestData.Pump Pump;

    private SRPCalculateRequestData.CasingString CasingString;

    private SRPCalculateRequestData.PumpingUnit PumpingUnit;

    private SRPCalculateRequestData.Production Production;

    private SRPCalculateRequestData.ManualIntervention ManualIntervention;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setFluidPVT(SRPCalculateRequestData.FluidPVT FluidPVT){
        this.FluidPVT = FluidPVT;
    }
    public SRPCalculateRequestData.FluidPVT getFluidPVT(){
        return this.FluidPVT;
    }
    public void setReservoir(SRPCalculateRequestData.Reservoir Reservoir){
        this.Reservoir = Reservoir;
    }
    public SRPCalculateRequestData.Reservoir getReservoir(){
        return this.Reservoir;
    }
    public void setRodString(SRPCalculateRequestData.RodString RodString){
        this.RodString = RodString;
    }
    public SRPCalculateRequestData.RodString getRodString(){
        return this.RodString;
    }
    public void setTubingString(SRPCalculateRequestData.TubingString TubingString){
        this.TubingString = TubingString;
    }
    public SRPCalculateRequestData.TubingString getTubingString(){
        return this.TubingString;
    }
    public void setPump(SRPCalculateRequestData.Pump Pump){
        this.Pump = Pump;
    }
    public SRPCalculateRequestData.Pump getPump(){
        return this.Pump;
    }
    public void setCasingString(SRPCalculateRequestData.CasingString CasingString){
        this.CasingString = CasingString;
    }
    public SRPCalculateRequestData.CasingString getCasingString(){
        return this.CasingString;
    }
    public void setPumpingUnit(SRPCalculateRequestData.PumpingUnit PumpingUnit){
        this.PumpingUnit = PumpingUnit;
    }
    public SRPCalculateRequestData.PumpingUnit getPumpingUnit(){
        return this.PumpingUnit;
    }
    public void setProduction(SRPCalculateRequestData.Production Production){
        this.Production = Production;
    }
    public SRPCalculateRequestData.Production getProduction(){
        return this.Production;
    }
    
    public void setManualIntervention(SRPCalculateRequestData.ManualIntervention ManualIntervention){
        this.ManualIntervention = ManualIntervention;
    }
    public SRPCalculateRequestData.ManualIntervention getManualIntervention(){
        return this.ManualIntervention;
    }

}
