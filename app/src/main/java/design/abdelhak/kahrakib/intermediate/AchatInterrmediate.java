package design.abdelhak.kahrakib.intermediate;

import design.abdelhak.kahrakib.models.AchatModel;
import design.abdelhak.kahrakib.networks.requests.AchatWithDpsRequestModel;

public interface AchatInterrmediate {
    void debitePrixTotal(AchatWithDpsRequestModel achatWithDpsRequestModel);
    void creditePrixTotal(AchatWithDpsRequestModel achatWithDpsRequestModel);

    void affichageFenetereVide();
}
