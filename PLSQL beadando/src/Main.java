import oracle.jdbc.internal.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static Connection connection;

    public static void main(String[] args) {
        DriverReg();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please give me a username and a password!");
        System.out.println("username:");
        String username = sc.next();
        System.out.println("password:");
        String password = sc.next();


        connection = ConnectToDataBase(username, password);

        boolean ok = false;
        do {
            ShowActions();
            System.out.println("Válassz egy számot!");
            int number = Integer.parseInt(sc.next());
            switch (number) {
                case 0 -> DeleteTables();
                case 1 -> InsertIntoAuto();
                case 2 -> InsertIntoAlkalmazott();
                case 3 -> {
                    System.out.println("A hivas táblába adat töltés:");
                    System.out.println("hívó:");
                    String hivo = sc.next();
                    System.out.println("helyszín:");
                    String helyszin = sc.next();
                    System.out.println("leírás:");
                    String leiras = sc.next();
                    System.out.println("Küld-e mentőt(0 vagy 1):");
                    int mentotkuld = Integer.parseInt(sc.next());
                    InsertIntoHivas(hivo, helyszin, leiras, mentotkuld);
                }
                case 4 -> {
                    InsertIntoBeteg();
                }
                case 5 -> {
                    System.out.println("Dátum(YYYY/MM/DD):");
                    String date = sc.next();
                    getNumberOfCalls(date);
                }
                case 6 -> {
                    System.out.println("Hívó:");
                    String hivo = sc.next();
                    getCallsByCalling(hivo);
                }
                case 7 -> {
                    System.out.println("Kezdő dátum(YYYY/MM/DD):");
                    String start = sc.next();
                    System.out.println("Záró dátum(YYYY/MM/DD):");
                    String end = sc.next();
                    getCallsByDate(start,end);
                }
                case 8 -> {
                    System.out.println("Beteg neve:");
                    String patient = sc.next();
                    getCallsByPatient(patient);
                }
                case 9 -> {
                    WriteOut(SelectAllFromNaplo());
                }
                case 10 -> {
                    Disconnect();
                    ok = true;
                    System.out.println("Goodbye!");
                }

                default -> System.out.println("Nincs ilyen opció!");
            }

        } while (!ok);
    }

    public static void DriverReg() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver is registered successfully!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void ShowActions() {
        System.out.println("A következő műveletek választhatók:");
        System.out.println("0 : Táblák adatainak törlése.");
        System.out.println("1 : Auto táblába adat töltés.(Törlés szükséges)");
        System.out.println("2 : Alkalmazott táblába adat töltés.(Törlés szükséges,kivéve ha előtte töltöttünk az auto táblába.)");
        System.out.println("3 : Új hívás felvitele.");
        System.out.println("4 : Beteg táblába adat töltés.");
        System.out.println("5 : Hívások lekérdezése dátum alapján.");
        System.out.println("6 : Hívások lekérdezése hívó alapján.");
        System.out.println("7 : Hívások lekérdezése dátum intervallum alapján.");
        System.out.println("8 : Hívások lekérdezése beteg alapján.");
        System.out.println("9 : Napló lekérdezése.");
        System.out.println("10 : Kilépés az applikációból.");
    }

    public static Connection ConnectToDataBase(String username, String password) {

        String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Successful connection!");
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void DeleteTables() {
        try {

            String sql = "{call mentoszolgalat.deletetablak}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.execute();

            stmt.close();
            System.out.println("Successful delete!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void InsertIntoAuto() {
        try {

            String sql = "{call mentoszolgalat.feltoltauto}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.execute();

            stmt.close();
            System.out.println("Successful insert!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void InsertIntoAlkalmazott() {
        try {

            String sql = "{call mentoszolgalat.FELTOLTLALK}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.execute();

            stmt.close();
            System.out.println("Successful insert!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void InsertIntoHivas(String hivo, String helyszin, String leiras, int mentotkuld) {
        try {

            String sql = "{call mentoszolgalat.ujhivas(?,?,?,?)}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(1, hivo);
            stmt.setString(2, helyszin);
            stmt.setString(3, leiras);
            stmt.setInt(4, mentotkuld);

            stmt.execute();

            stmt.close();
            System.out.println("Successful insert!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void InsertIntoBeteg() {
        try {

            String sql = "{call mentoszolgalat.beteg_feltolt}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.execute();

            stmt.close();
            System.out.println("Successful insert!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getNumberOfCalls(String date) {
        try {
            String sql = "{? = call mentoszolgalat.hivasokszama(?)}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(2, date);
            stmt.registerOutParameter(1,Types.INTEGER);

            stmt.execute();

            int result = stmt.getInt(1);
            System.out.println("Result: " + result);

            stmt.close();
            System.out.println("Successful select!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getCallsByCalling(String hivo) {
        try {
            String sql = "{ call mentoszolgalat.hivasok(?,?)}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(1, hivo);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);

            stmt.execute();

            ResultSet rs = ((OracleCallableStatement) stmt).getCursor(2);

            System.out.println("HivasId,idopont,hivo,helyszin,leiras,mentotkuld");
            while (rs.next()) {
                int hivasId = rs.getInt("hivasId");
                Timestamp idopont = rs.getTimestamp("idopont");
                String outhivo = rs.getString("hivo");
                String helyszin = rs.getString("helyszin");
                String leiras = rs.getString("leiras");
                int mentotkuld = rs.getInt("mentotkuld");
                System.out.println(hivasId + "," + idopont + "," + outhivo + "," + helyszin + "," + leiras + "," + mentotkuld);

            }
            stmt.close();
            System.out.println("Successful select!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getCallsByDate(String start, String end) {
        try {
            String sql = "{ call mentoszolgalat.hivasok_datum(?,?,?)}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(1, start);
            stmt.setString(2, end);
            stmt.registerOutParameter(3, OracleTypes.CURSOR);

            stmt.execute();

            ResultSet rs = ((OracleCallableStatement) stmt).getCursor(3);

            System.out.println("HivasId,idopont,hivo,helyszin,leiras,mentotkuld");
            while (rs.next()) {
                int hivasId = rs.getInt("hivasId");
                Timestamp idopont = rs.getTimestamp("idopont");
                String outhivo = rs.getString("hivo");
                String helyszin = rs.getString("helyszin");
                String leiras = rs.getString("leiras");
                int mentotkuld = rs.getInt("mentotkuld");
                System.out.println(hivasId + "," + idopont + "," + outhivo + "," + helyszin + "," + leiras + "," + mentotkuld);

            }
            stmt.close();
            System.out.println("Successful select!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getCallsByPatient(String patient) {
        try {
            String sql = "{ call mentoszolgalat.hivasok_beteg(?,?)}";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(1, patient);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);

            stmt.execute();

            ResultSet rs = ((OracleCallableStatement) stmt).getCursor(2);

            System.out.println("HivasId,idopont,beteg név,tajszám,diagnózis");
            while (rs.next()) {
                int hivasId = rs.getInt("hivasId");
                Timestamp idopont = rs.getTimestamp("idopont");
                String beteg = rs.getString("nev");
                String taj = rs.getString("tajszam");
                String diagnozis = rs.getString("diagnozis");
                System.out.println(hivasId + "," + idopont + "," + beteg + "," + taj + "," + diagnozis);

            }
            stmt.close();
            System.out.println("Successful select!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet SelectAllFromNaplo() {
        try {
            Statement stmt = connection.createStatement();

            String queryCmd = "SELECT * FROM naplo";
            ResultSet rs = stmt.executeQuery(queryCmd);

            System.out.println("Successful select.");
            return rs;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void WriteOut(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            for (int i = 1; i <= columnsNumber; i++) {
                System.out.print(rsmd.getColumnName(i) + ",  ");
            }
            System.out.println();

            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + ",  ");
                }
                System.out.println();
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void Disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Successful disconnection.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

