import java.util.ArrayList;
import java.util.Arrays;

public class DefesaEspecial {
    public enum TipoDefesaEspecial {
        ESQUIVA,REFLEXAO,ROBUSTA,TENAZ;
    }
    int custoDeMana;

    ArrayList<DefesaEspecial.TipoDefesaEspecial> defesaEspecial;

    public DefesaEspecial(ArrayList<DefesaEspecial.TipoDefesaEspecial> modificadores) {
        defesaEspecial = modificadores;
        int temp = 0;
        if (isEsquiva()) temp+=2;
        if (isReflexao()) temp++;
        temp += isTenaz();
        custoDeMana = temp;
    }

    public DefesaEspecial(DefesaEspecial.TipoDefesaEspecial[] modificadores) {
        this(new ArrayList<>(Arrays.asList(modificadores)));
    }
    public DefesaEspecial(){
        custoDeMana = 0;
        defesaEspecial = new ArrayList<>();
    }

    /**
     * 2PM - usa habilidade ao invés de resistência na defesa
     * @return
     */
    public boolean isEsquiva(){
        boolean ret = false;
        for (DefesaEspecial.TipoDefesaEspecial t : defesaEspecial) {
            if (t.name().equals("ESQUIVA")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 1PM - Devolve o excesso de defesa como dano
     * @return
     */
    public boolean isReflexao(){
        boolean ret = false;
        for (DefesaEspecial.TipoDefesaEspecial t : defesaEspecial) {
            if (t.name().equals("REFLEXAO")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 2PM / crítico - soma Res mais uma vez
     * @return
     */
    public boolean isRobusta(){
        boolean ret = false;
        for (DefesaEspecial.TipoDefesaEspecial t : defesaEspecial) {
            if (t.name().equals("ROBUSTA")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 1PM - soma 2 na defesa, pode acumular
     * @return
     */
    public int isTenaz(){
        int ret = 0;
        for (DefesaEspecial.TipoDefesaEspecial t : defesaEspecial) {
            if (t.name().equals("TENAZ")) ret++;
        }
        return ret;
    }

}
