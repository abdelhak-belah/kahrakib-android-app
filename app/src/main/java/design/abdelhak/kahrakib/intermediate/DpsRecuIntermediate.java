package design.abdelhak.kahrakib.intermediate;

import java.util.List;

import design.abdelhak.kahrakib.models.AchatModel;
import design.abdelhak.kahrakib.models.DpsModel;
import design.abdelhak.kahrakib.networks.responses.DpsResponseModel;

public interface DpsRecuIntermediate {
    void setPrixTotal(List<DpsResponseModel> dpsResponseModels);
}
