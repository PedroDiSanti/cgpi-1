package cgpi.figuras.model;

import javafx.scene.paint.Color;

import java.util.List;

/**
 * @author vitor.alves
 */
public class Cor extends Figura{

    private static int R = 0;

    private static int G = 0;

    private static int B = 0;

    private static final String COR_R = "0";

    private static final String COR_G = "0";

    private static final String COR_B = "0";

    public Cor(String corR, String corG, String corB) {
        this.addColor(corR);
        this.addColor(corG);
        this.addColor(corB);
    }

    public Cor(List<String> cores) {
        R = Integer.parseInt( cores.get(0) );
        G = Integer.parseInt( cores.get(1) );
        B = Integer.parseInt( cores.get(2) );

        Color.rgb( R,G,B );
        //TODO criar método set para setar as cores lidas.
    }
}
