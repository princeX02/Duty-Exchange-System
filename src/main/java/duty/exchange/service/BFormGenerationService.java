package duty.exchange.service;

import com.itextpdf.text.DocumentException;
import duty.exchange.model.DutyExchangeRequest;
import duty.exchange.util.PdfGeneratorUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service for generating B-Form (PDF) documents
 */
@Service
public class BFormGenerationService {
    
    /**
     * Generate B-Form PDF for approved duty exchange request
     */
    public byte[] generateBForm(DutyExchangeRequest request) {
        try {
            if (request.getStatus() != duty.exchange.model.ApprovalStatus.APPROVED) {
                throw new IllegalStateException(
                    "B-Form can only be generated for approved requests"
                );
            }
            return PdfGeneratorUtil.generateBForm(request);
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error generating B-Form PDF", e);
        }
    }
}
