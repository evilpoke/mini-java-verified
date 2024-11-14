package testingstuff;

import java.io.IOException;

public class TestServer {

    public static void main(String[] args) throws IOException {

        ReverseService.main(null);

        ReverseClient.main(null);
        ReverseClient.main(null);

    }

}
