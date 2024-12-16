import model.User;

import cow.lowagie.text.Document;
import cow.lowagie.text.DocumentException;
import cow.lowagie.text.Font;
import cow.lowagie.text.FontFactory;
import cow.lowagie.text.PageSize;
import cow.lowagie.text.Phrase;
import cow.lowagie.text.pdf.PdfPCell;
import cow.lowagie.text.pdf.PdfPTable;
import cow.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserPdf {
    public void exportPdf(List<User> users) {
        System.out.println(users.size());
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter write = PdfWriter.getInstance(document, new FileOutputStream(Syste.getProperty("user.dir") + File.separator + "users.pdf"));
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();

            document.open();

            float[] columnDefinitionSize = {33.33F, 33.33F, 33.33F};

            float pos = height / 2;
            PdfPTable table = null;
            PdfPCell cell = null;

            table = new PdfTable(columnDefinitionSize);
            table.getDefaultCell().setBorder(1);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(width - 72);
            table.setLockedWidth(true);

            table.addCell(new Phrase("No"));
            table.addCell(new Phrase("Name"));
            table.addCell(new Phrase("Email"));

            int no = 1;
            for (User user: users) {
                table.addCell(new Phrase(String.valueOf(no++)));
                table.addCell(new Phrase(user.getName()));
                table.addCell(new Phrase(user.getEmail()));
            }

            document.add(table);
        } catch (DocumentException | IOException ex) {
            System.err.println(ex.getMessage());
        }
        document.close();
    }
}
