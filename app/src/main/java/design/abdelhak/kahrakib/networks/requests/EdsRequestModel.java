package design.abdelhak.kahrakib.networks.requests;

import java.util.List;

public class EdsRequestModel {

    private Long cassierId;
    private List<Long> dpsId;



    public EdsRequestModel(Long cassierId, List<Long> dpsId) {
        this.cassierId = cassierId;
        this.dpsId = dpsId;
    }

    public Long getCassierId() {
        return cassierId;
    }

    public void setCassierId(Long cassierId) {
        this.cassierId = cassierId;
    }

    public List<Long> getDpsId() {
        return dpsId;
    }

    public void setDpsId(List<Long> dpsId) {
        this.dpsId = dpsId;
    }
}
