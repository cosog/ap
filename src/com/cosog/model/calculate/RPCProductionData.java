package com.cosog.model.calculate;

public class RPCProductionData {
	
	private String WellName;

    private RPCCalculateRequestData.FluidPVT FluidPVT;

    private RPCCalculateRequestData.Reservoir Reservoir;

    private RPCCalculateRequestData.RodString RodString;

    private RPCCalculateRequestData.TubingString TubingString;

    private RPCCalculateRequestData.Pump Pump;

    private RPCCalculateRequestData.CasingString CasingString;

    private RPCCalculateRequestData.PumpingUnit PumpingUnit;

    private RPCCalculateRequestData.Production Production;

    private RPCCalculateRequestData.ManualIntervention ManualIntervention;

    public void setWellName(String WellName){
        this.WellName = WellName;
    }
    public String getWellName(){
        return this.WellName;
    }
    public void setFluidPVT(RPCCalculateRequestData.FluidPVT FluidPVT){
        this.FluidPVT = FluidPVT;
    }
    public RPCCalculateRequestData.FluidPVT getFluidPVT(){
        return this.FluidPVT;
    }
    public void setReservoir(RPCCalculateRequestData.Reservoir Reservoir){
        this.Reservoir = Reservoir;
    }
    public RPCCalculateRequestData.Reservoir getReservoir(){
        return this.Reservoir;
    }
    public void setRodString(RPCCalculateRequestData.RodString RodString){
        this.RodString = RodString;
    }
    public RPCCalculateRequestData.RodString getRodString(){
        return this.RodString;
    }
    public void setTubingString(RPCCalculateRequestData.TubingString TubingString){
        this.TubingString = TubingString;
    }
    public RPCCalculateRequestData.TubingString getTubingString(){
        return this.TubingString;
    }
    public void setPump(RPCCalculateRequestData.Pump Pump){
        this.Pump = Pump;
    }
    public RPCCalculateRequestData.Pump getPump(){
        return this.Pump;
    }
    public void setCasingString(RPCCalculateRequestData.CasingString CasingString){
        this.CasingString = CasingString;
    }
    public RPCCalculateRequestData.CasingString getCasingString(){
        return this.CasingString;
    }
    public void setPumpingUnit(RPCCalculateRequestData.PumpingUnit PumpingUnit){
        this.PumpingUnit = PumpingUnit;
    }
    public RPCCalculateRequestData.PumpingUnit getPumpingUnit(){
        return this.PumpingUnit;
    }
    public void setProduction(RPCCalculateRequestData.Production Production){
        this.Production = Production;
    }
    public RPCCalculateRequestData.Production getProduction(){
        return this.Production;
    }
    
    public void setManualIntervention(RPCCalculateRequestData.ManualIntervention ManualIntervention){
        this.ManualIntervention = ManualIntervention;
    }
    public RPCCalculateRequestData.ManualIntervention getManualIntervention(){
        return this.ManualIntervention;
    }

}
