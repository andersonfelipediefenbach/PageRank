import services.SearchLinks;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            SearchLinks.findLinks();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
