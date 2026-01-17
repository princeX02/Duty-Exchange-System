package duty.exchange.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import duty.exchange.model.DutyExchangeRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for PDF generation (B-Form generation)
 */
public class PdfGeneratorUtil {
    
    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font HEADING_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    /**
     * Generate B-Form PDF for approved duty exchange request
     */
    public static byte[] generateBForm(DutyExchangeRequest request) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);
        
        document.open();
        
        // Title
        Paragraph title = new Paragraph("DUTY EXCHANGE APPROVAL FORM (B-FORM)", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        // Request Details
        document.add(createHeading("Request Details"));
        PdfPTable requestTable = new PdfPTable(2);
        requestTable.setWidthPercentage(100);
        requestTable.setSpacingBefore(10);
        requestTable.setSpacingAfter(10);
        
        addTableRow(requestTable, "Request ID:", request.getRequestId());
        addTableRow(requestTable, "Request Date:", 
                   request.getRequestedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        addTableRow(requestTable, "Status:", request.getStatus().toString());
        
        document.add(requestTable);
        
        // Requester Details
        document.add(createHeading("Requester Details"));
        PdfPTable requesterTable = new PdfPTable(2);
        requesterTable.setWidthPercentage(100);
        requesterTable.setSpacingBefore(10);
        requesterTable.setSpacingAfter(10);
        
        addTableRow(requesterTable, "Name:", request.getRequester().getFullName());
        addTableRow(requesterTable, "Employee ID:", request.getRequester().getEmployeeId());
        addTableRow(requesterTable, "Department:", request.getRequester().getDepartment());
        addTableRow(requesterTable, "Designation:", request.getRequester().getDesignation());
        addTableRow(requesterTable, "Email:", request.getRequester().getEmail());
        
        document.add(requesterTable);
        
        // Requester Duty Details
        document.add(createHeading("Requester Duty Details"));
        PdfPTable requesterDutyTable = new PdfPTable(2);
        requesterDutyTable.setWidthPercentage(100);
        requesterDutyTable.setSpacingBefore(10);
        requesterDutyTable.setSpacingAfter(10);
        
        addTableRow(requesterDutyTable, "Date:", 
                   request.getRequesterDuty().getDutyDate().format(DATE_FORMATTER));
        addTableRow(requesterDutyTable, "Time:", 
                   request.getRequesterDuty().getStartTime().format(TIME_FORMATTER) + " - " +
                   request.getRequesterDuty().getEndTime().format(TIME_FORMATTER));
        addTableRow(requesterDutyTable, "Venue:", request.getRequesterDuty().getVenue());
        addTableRow(requesterDutyTable, "Subject:", request.getRequesterDuty().getSubject());
        addTableRow(requesterDutyTable, "Course Code:", request.getRequesterDuty().getCourseCode());
        
        document.add(requesterDutyTable);
        
        // Recipient Details
        document.add(createHeading("Recipient Details"));
        PdfPTable recipientTable = new PdfPTable(2);
        recipientTable.setWidthPercentage(100);
        recipientTable.setSpacingBefore(10);
        recipientTable.setSpacingAfter(10);
        
        addTableRow(recipientTable, "Name:", request.getRecipient().getFullName());
        addTableRow(recipientTable, "Employee ID:", request.getRecipient().getEmployeeId());
        addTableRow(recipientTable, "Department:", request.getRecipient().getDepartment());
        addTableRow(recipientTable, "Designation:", request.getRecipient().getDesignation());
        addTableRow(recipientTable, "Email:", request.getRecipient().getEmail());
        
        document.add(recipientTable);
        
        // Recipient Duty Details
        document.add(createHeading("Recipient Duty Details"));
        PdfPTable recipientDutyTable = new PdfPTable(2);
        recipientDutyTable.setWidthPercentage(100);
        recipientDutyTable.setSpacingBefore(10);
        recipientDutyTable.setSpacingAfter(10);
        
        addTableRow(recipientDutyTable, "Date:", 
                   request.getRecipientDuty().getDutyDate().format(DATE_FORMATTER));
        addTableRow(recipientDutyTable, "Time:", 
                   request.getRecipientDuty().getStartTime().format(TIME_FORMATTER) + " - " +
                   request.getRecipientDuty().getEndTime().format(TIME_FORMATTER));
        addTableRow(recipientDutyTable, "Venue:", request.getRecipientDuty().getVenue());
        addTableRow(recipientDutyTable, "Subject:", request.getRecipientDuty().getSubject());
        addTableRow(recipientDutyTable, "Course Code:", request.getRecipientDuty().getCourseCode());
        
        document.add(recipientDutyTable);
        
        // Reason
        document.add(createHeading("Reason for Exchange"));
        Paragraph reason = new Paragraph(request.getReason(), NORMAL_FONT);
        reason.setSpacingBefore(10);
        reason.setSpacingAfter(10);
        document.add(reason);
        
        // Approval Details
        if (request.getApprover() != null) {
            document.add(createHeading("Approval Details"));
            PdfPTable approvalTable = new PdfPTable(2);
            approvalTable.setWidthPercentage(100);
            approvalTable.setSpacingBefore(10);
            approvalTable.setSpacingAfter(10);
            
            addTableRow(approvalTable, "Approved By:", request.getApprover().getFullName());
            addTableRow(approvalTable, "Approval Date:", 
                       request.getApprovedAt() != null ? 
                       request.getApprovedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")) : "N/A");
            addTableRow(approvalTable, "Comments:", 
                       request.getApprovalComments() != null ? request.getApprovalComments() : "N/A");
            
            document.add(approvalTable);
        }
        
        document.close();
        return baos.toByteArray();
    }
    
    private static Paragraph createHeading(String text) {
        Paragraph heading = new Paragraph(text, HEADING_FONT);
        heading.setSpacingBefore(15);
        heading.setSpacingAfter(5);
        return heading;
    }
    
    private static void addTableRow(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, NORMAL_FONT));
        labelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        labelCell.setPadding(8);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "N/A", NORMAL_FONT));
        valueCell.setPadding(8);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}
