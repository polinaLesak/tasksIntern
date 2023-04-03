public class task1 {
    public static void main(String[] args) {


        System.out.println(ipToInt("127.00.0.1"));

        System.out.println(intToIp(2130706433));

    }

    static private String transform_1(String str) {
        char[] zeros = {'0', '0', '0', '0', '0', '0', '0', '0' };
        int i = str.length();
        str.getChars(0, i, zeros, 8 - i);
        return new String(zeros);
    }

    public static int ipToInt(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        String num = "";

        for (int i = 0; i < octets.length; i++) {
            num += transform_1(Integer.toBinaryString(Integer.parseInt(octets[i])));

        }
        int result = Integer.parseInt(num, 2);

        return result;
    }

    public static String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt / 256 / 256 / 256).append(".");
        sb.append((ipInt / 256 / 256) % 256).append(".");
        sb.append((ipInt / 256) % 256).append(".");
        sb.append(ipInt % 256);
        return sb.toString();
    }
}
