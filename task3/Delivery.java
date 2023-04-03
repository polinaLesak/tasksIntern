import java.time.LocalDate;

public class Delivery {
    private final int matDoc;
    private final LocalDate docData;
    private final LocalDate pstngData;
    private final String userName;
    private final String authorised;
    private final Product product;

    public Delivery(int matDoc, LocalDate docData, LocalDate pstngData, String userName, String authorised, Product product){
        this.matDoc = matDoc;
        this.docData = docData;
        this.pstngData = pstngData;
        this.userName = userName;
        this.authorised = authorised;
        this.product = product;
    }
    public int getMatDoc() {
        return matDoc;
    }

    public LocalDate getDocData() {
        return docData;
    }

    public LocalDate getPstngData() {
        return pstngData;
    }

    public String getUserName() {
        return userName;
    }

    public String getAuthorised() {
        return authorised;
    }

    public Product getProduct() {
        return product;
    }

}
