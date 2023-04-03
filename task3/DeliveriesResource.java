import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Path("/deliveries")
public class DeliveriesResource {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/task";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "12345678;";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDeliveriesByPeriod(@QueryParam("period") String period,
                                          @QueryParam("authorised") String authorised) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT d.MatDoc, d.DocData, d.PstngData, d.UserName, d.Authorised, " +
                            "p.item, p.MaterialDescription, p.Quantity, p.BUn, p.AmountLC, p.Crcy " +
                            "FROM deliveries d " +
                            "INNER JOIN products p ON d.MatDoc = p.MatDoc " +
                            "WHERE d.Authorised = ? AND d.DocData >= ? AND d.DocData <= ?");
            statement.setString(1, authorised);
            LocalDate startDate;
            LocalDate endDate = LocalDate.now();
            switch (period) {
                case "day":
                    startDate = endDate.minusDays(1);
                    break;
                case "month":
                    startDate = endDate.minusMonths(1);
                    break;
                case "quarter":
                    startDate = endDate.minusMonths(3);
                    break;
                case "year":
                    startDate = endDate.minusYears(1);
                    break;
                default:
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid period").build();
            }
            statement.setString(2, DATE_FORMATTER.format(startDate));
            statement.setString(3, DATE_FORMATTER.format(endDate));
            ResultSet resultSet = statement.executeQuery();
            List<Delivery> deliveries = new ArrayList<>();
            while (resultSet.next()) {
                Delivery delivery = new Delivery(
                        resultSet.getInt("MatDoc"),
                        resultSet.getDate("DocData").toLocalDate(),
                        resultSet.getDate("PstngData").toLocalDate(),
                        resultSet.getString("UserName"),
                        resultSet.getString("Authorised"),
                        new Product(
                                resultSet.getString("item"),
                                resultSet.getString("MaterialDescription"),
                                resultSet.getInt("Quantity"),
                                resultSet.getString("BUn"),
                                resultSet.getString("AmountLC"),
                                resultSet.getString("Crcy")
                        )
                );
                deliveries.add(delivery);
            }
            return Response.ok(deliveries).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.serverError().entity("Error while accessing the database").build();
        }
    }
}
